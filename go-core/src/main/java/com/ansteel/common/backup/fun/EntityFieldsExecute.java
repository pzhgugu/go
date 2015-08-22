package com.ansteel.common.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.Models;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型字段增量备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class EntityFieldsExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return EntityFields.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		//父节点
		Collection<Models> dataBaseResList = baseService.findAll(Models.class);
		Map<String, Models> dataBaseResMap = new HashMap<String, Models>();
		for(Models entity :dataBaseResList){
			dataBaseResMap.put(entity.getName(), entity);
		}

		List<Models> xmlParentList=(List<Models>) xmlMap.get(ModelsExecute.class);
		Map<String, Models> parentXmlToDataBaseMap = new HashMap<String, Models>();
		for(Models entity :xmlParentList){
			String name = entity.getName();
			if(dataBaseResMap.containsKey(name)){
				if(StringUtils.hasText(name)){
					parentXmlToDataBaseMap.put(entity.getId(), dataBaseResMap.get(name));
				}
			}
		}
		//父节点
		

		List<EntityFields> xmlEntityList = (List<EntityFields>) o;
		Collection<EntityFields> dataBaseList = baseService.findAll(EntityFields.class);
		for (EntityFields entity : xmlEntityList) {
			EntityFields xmlEntity =new EntityFields();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlName = xmlEntity.getName();
			//此节点只有ID
			String xmlParentId=entity.getModels().getId();
			Models parentXmlToDataBaseEntity=null;
			if(StringUtils.hasText(xmlParentId)){
				if(parentXmlToDataBaseMap.containsKey(xmlParentId)){
					parentXmlToDataBaseEntity=parentXmlToDataBaseMap.get(xmlParentId);
					xmlEntity.setModels(parentXmlToDataBaseEntity);
				}else{
					continue;
				}
			}else{
				continue;
			}
		
			//确认xml中是否有数据库中的实体
			boolean isSave = true;
			for (EntityFields dataBaseEntity : dataBaseList) {
				//数据库节点名称
				String dataBaseName = dataBaseEntity.getName();
				if(dataBaseName.equals(xmlName)){
					//数据库父节点名称
					String dataBaseParentName = dataBaseEntity.getModels()==null?"":dataBaseEntity.getModels().getName();
					String parentXmlName = parentXmlToDataBaseEntity==null?"":parentXmlToDataBaseEntity.getName();
					if(dataBaseParentName.equals(parentXmlName)){
					
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							baseService.save(getUpdateEntity(dataBaseEntity, xmlEntity));
						}
						isSave=false;
						break;
					}
				}
			}
			
			
			if (isSave) {	
				xmlEntity.setVersionPublish(xmlEntity.getVersion());
				baseService.save(xmlEntity);
			}
		}
		
		
	}
	
	
	
	private EntityFields getUpdateEntity(EntityFields databaseEntity, EntityFields entity) {
		String oldId = databaseEntity.getId();
		Long version = databaseEntity.getVersion();
		try {
			BeanUtils.copyProperties(databaseEntity, entity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		databaseEntity.setId(oldId);
		databaseEntity.setVersion(version);
		databaseEntity.setVersionPublish(entity.getVersion());
		return databaseEntity;
	}

}
