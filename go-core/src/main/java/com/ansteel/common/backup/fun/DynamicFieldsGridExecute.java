package com.ansteel.common.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态图建模字段表格增量备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DynamicFieldsGridExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return DynamicFieldsGrid.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		List<DynamicFieldsGrid> xmlEntityList = (List<DynamicFieldsGrid>) o;
		
		Map<String, DynamicFieldsCategory> parentDataBase=this.getParentDataBase(xmlMap);
		
		this.save(parentDataBase,xmlEntityList);
	}
	
	
	private Map<String, DynamicFieldsCategory> getParentDataBase(
			Map<Class, Object> xmlMap) {
		Map<String, DynamicFieldsCategory> parentDataBase = new HashMap<>();
		//得到所有父类
		List<DynamicFieldsCategory> xmlParentList=(List<DynamicFieldsCategory>) xmlMap.get(DynamicFieldsCategoryExecute.class);
		//得到所有顶层
		List<DynamicModels> xmlTopList=(List<DynamicModels>) xmlMap.get(DynamicModelsExecute.class);
		Map<DynamicFieldsCategory,DynamicModels> xmlTopNameToParent = new HashMap<>();
		for(DynamicFieldsCategory e :xmlParentList){
			String topId = e.getModels().getId();
			for(DynamicModels m:xmlTopList){
				if(topId.equals(m.getId())){
					xmlTopNameToParent.put(e,m);
					break;
				}
			}
		}
		//所有顶层数据库实体
		List<DynamicModels> dataBaseTopList = baseService.findAll(DynamicModels.class);
		Map<String ,Collection<DynamicFieldsCategory>> dataBaseParent = new HashMap<>();
		for(DynamicModels entity:dataBaseTopList){
			Collection<DynamicFieldsCategory> eList = entity.getFieldsCategory();
			if(eList.size()>0){
				dataBaseParent.put(entity.getName(), eList);
			}else{
				logger.info(entity.getAlias());
			}
		}
		
		for(Entry<DynamicFieldsCategory, DynamicModels> entry:xmlTopNameToParent.entrySet()){
			DynamicFieldsCategory parentE = entry.getKey();
			DynamicModels topE = entry.getValue();
			String name = topE.getName();
			if(dataBaseParent.containsKey(name)){
				Collection<DynamicFieldsCategory> dataBaseParentList = dataBaseParent.get(name);
				boolean is = false;
				for(DynamicFieldsCategory e:dataBaseParentList){
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

	private void save(Map<String, DynamicFieldsCategory> parentDataBase, List<DynamicFieldsGrid> xmlEntityList) {

		for (DynamicFieldsGrid entity : xmlEntityList) {
			DynamicFieldsGrid xmlEntity =new DynamicFieldsGrid();
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
				DynamicFieldsCategory parentDataBaseEntity = parentDataBase.get(xmlParentId);
				xmlEntity.setFieldsCategory(parentDataBaseEntity);
				//确认xml中是否有数据库中的实体
				boolean isSave = true;

				Collection<DynamicFieldsGrid> dataBaseEntityList = parentDataBaseEntity.getFieldsGrid();
				String xmlName = xmlEntity.getName();
				
				for (DynamicFieldsGrid dataBaseEntity : dataBaseEntityList) {						
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


	
	
	
	private DynamicFieldsGrid getUpdateEntity(DynamicFieldsGrid databaseEntity, DynamicFieldsGrid entity) {
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
