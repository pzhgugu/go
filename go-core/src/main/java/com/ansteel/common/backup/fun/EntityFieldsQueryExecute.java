package com.ansteel.common.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型字段查询增量备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class EntityFieldsQueryExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return EntityFieldsQuery.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		List<EntityFieldsQuery> xmlEntityList = (List<EntityFieldsQuery>) o;
		
		Map<String, EntityFieldsCategory> parentDataBase=this.getParentDataBase(xmlMap);
		
		this.save(parentDataBase,xmlEntityList);
	}
	
	
	
	private Map<String, EntityFieldsCategory> getParentDataBase(
			Map<Class, Object> xmlMap) {
		Map<String, EntityFieldsCategory> parentDataBase = new HashMap<>();
		//得到所有父类
		List<EntityFieldsCategory> xmlParentList=(List<EntityFieldsCategory>) xmlMap.get(EntityFieldsCategoryExecute.class);
		//得到所有顶层
		List<Models> xmlTopList=(List<Models>) xmlMap.get(ModelsExecute.class);
		Map<EntityFieldsCategory,Models> xmlTopNameToParent = new HashMap<>();
		for(EntityFieldsCategory e :xmlParentList){
			String topId = e.getModels().getId();
			for(Models m:xmlTopList){
				if(topId.equals(m.getId())){
					xmlTopNameToParent.put(e,m);
					break;
				}
			}
		}
		//所有顶层数据库实体
		List<Models> dataBaseTopList = baseService.findAll(Models.class);
		Map<String ,Collection<EntityFieldsCategory>> dataBaseParent = new HashMap<>();
		for(Models entity:dataBaseTopList){
			Collection<EntityFieldsCategory> eList = entity.getFieldsCategory();
			if(eList.size()>0){
				dataBaseParent.put(entity.getName(), eList);
			}else{
				logger.info(entity.getAlias());
			}
		}
		
		for(Entry<EntityFieldsCategory, Models> entry:xmlTopNameToParent.entrySet()){
			EntityFieldsCategory parentE = entry.getKey();
			Models topE = entry.getValue();
			String name = topE.getName();
			if(dataBaseParent.containsKey(name)){
				Collection<EntityFieldsCategory> dataBaseParentList = dataBaseParent.get(name);
				boolean is = false;
				for(EntityFieldsCategory e:dataBaseParentList){
					 if(parentE.getName().equals(e.getName())){
						 parentDataBase.put(parentE.getId(),e);
						 is=true;
						 break;
					 }
				 }
				if(!is){
					throw new PageException("数据异常！");
				}
			}else{
				throw new PageException("数据异常！");
			}
			
		}
		return parentDataBase;
	}

	private void save(Map<String, EntityFieldsCategory> parentDataBase, List<EntityFieldsQuery> xmlEntityList) {

		for (EntityFieldsQuery entity : xmlEntityList) {
			EntityFieldsQuery xmlEntity =new EntityFieldsQuery();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlParentId = entity.getFieldsCategory().getId();
			if(parentDataBase.containsKey(xmlParentId)){
				EntityFieldsCategory parentDataBaseEntity = parentDataBase.get(xmlParentId);
				xmlEntity.setFieldsCategory(parentDataBaseEntity);
				//确认xml中是否有数据库中的实体
				boolean isSave = true;

				Collection<EntityFieldsQuery> dataBaseEntityList = parentDataBaseEntity.getFieldsQuery();
				String xmlName = xmlEntity.getName();
				
				for (EntityFieldsQuery dataBaseEntity : dataBaseEntityList) {						
					String dataBaseName = dataBaseEntity.getName();
					if(xmlName.equals(dataBaseName)){
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							baseService.save(getUpdateEntity(dataBaseEntity, xmlEntity));
						}
						isSave=false;
						break;
					}						
				}
				
				if (isSave) {	
					xmlEntity.setVersionPublish(xmlEntity.getVersion());
					baseService.save(xmlEntity);
				}
			}else{
				throw new PageException(xmlParentId+",不存在,数据异常！");
			}
		}	
	}
	
	
	
	private EntityFieldsQuery getUpdateEntity(EntityFieldsQuery databaseEntity, EntityFieldsQuery entity) {
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
