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
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态图建模字段分类增量备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DynamicFieldsCategoryExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return DynamicFieldsCategory.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		//父节点
		Collection<DynamicModels> dataBaseResList = baseService.findAll(DynamicModels.class);
		Map<String, DynamicModels> dataBaseResMap = new HashMap<String, DynamicModels>();
		for(DynamicModels entity :dataBaseResList){
			dataBaseResMap.put(entity.getName(), entity);
		}

		List<DynamicModels> xmlParentList=(List<DynamicModels>) xmlMap.get(DynamicModelsExecute.class);
		Map<String, DynamicModels> parentXmlToDataBaseMap = new HashMap<String, DynamicModels>();
		for(DynamicModels entity :xmlParentList){
			String name = entity.getName();
			if(dataBaseResMap.containsKey(name)){
				if(StringUtils.hasText(name)){
					parentXmlToDataBaseMap.put(entity.getId(), dataBaseResMap.get(name));
				}
			}
		}
		//父节点
		

		List<DynamicFieldsCategory> xmlEntityList = (List<DynamicFieldsCategory>) o;
		Collection<DynamicFieldsCategory> dataBaseList = baseService.findAll(DynamicFieldsCategory.class);
		for (DynamicFieldsCategory entity : xmlEntityList) {
			DynamicFieldsCategory xmlEntity =new DynamicFieldsCategory();
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
			DynamicModels parentXmlToDataBaseEntity=null;
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
			for (DynamicFieldsCategory dataBaseEntity : dataBaseList) {
				//数据库节点名称
				String dataBaseName = dataBaseEntity.getName();
				if(dataBaseName.equals(xmlName)){
					//数据库父节点名称
					String dataBaseParentName = dataBaseEntity.getModels()==null?"":dataBaseEntity.getModels().getName();
					String parentXmlName = parentXmlToDataBaseEntity==null?"":parentXmlToDataBaseEntity.getName();
					if(dataBaseParentName.equals(parentXmlName)){
					
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							DynamicFieldsCategory parent = getUpdateEntity(dataBaseEntity, xmlEntity);
							baseService.save(parent);
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
	
	private DynamicFieldsCategory getUpdateEntity(DynamicFieldsCategory databaseEntity, DynamicFieldsCategory entity) {
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
