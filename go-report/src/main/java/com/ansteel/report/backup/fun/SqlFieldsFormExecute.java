package com.ansteel.report.backup.fun;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.report.sqlmodel.domain.SqlFieldsCategory;
import com.ansteel.report.sqlmodel.domain.SqlFieldsForm;
import com.ansteel.report.sqlmodel.domain.SqlModels;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型字段表单备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SqlFieldsFormExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return SqlFieldsForm.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		List<SqlFieldsForm> xmlEntityList = (List<SqlFieldsForm>) o;
		
		Map<String, SqlFieldsCategory> parentDataBase=this.getParentDataBase(xmlMap);
		
		this.save(parentDataBase,xmlEntityList);
	}
	
	
	
	private Map<String, SqlFieldsCategory> getParentDataBase(
			Map<Class, Object> xmlMap) {
		Map<String, SqlFieldsCategory> parentDataBase = new HashMap<>();
		//得到所有父类
		List<SqlFieldsCategory> xmlParentList=(List<SqlFieldsCategory>) xmlMap.get(SqlFieldsCategoryExecute.class);
		//得到所有顶层
		List<SqlModels> xmlTopList=(List<SqlModels>) xmlMap.get(SqlModelsExecute.class);
		Map<SqlFieldsCategory,SqlModels> xmlTopNameToParent = new HashMap<>();
		for(SqlFieldsCategory e :xmlParentList){
			String topId = e.getModels().getId();
			for(SqlModels m:xmlTopList){
				if(topId.equals(m.getId())){
					xmlTopNameToParent.put(e,m);
					break;
				}
			}
		}
		//所有顶层数据库实体
		List<SqlModels> dataBaseTopList = baseService.findAll(SqlModels.class);
		Map<String ,Collection<SqlFieldsCategory>> dataBaseParent = new HashMap<>();
		for(SqlModels entity:dataBaseTopList){
			Collection<SqlFieldsCategory> eList = entity.getFieldsCategory();
			if(eList.size()>0){
				dataBaseParent.put(entity.getName(), eList);
			}else{
				logger.info(entity.getAlias());
			}
		}
		
		for(Entry<SqlFieldsCategory, SqlModels> entry:xmlTopNameToParent.entrySet()){
			SqlFieldsCategory parentE = entry.getKey();
			SqlModels topE = entry.getValue();
			String name = topE.getName();
			if(dataBaseParent.containsKey(name)){
				Collection<SqlFieldsCategory> dataBaseParentList = dataBaseParent.get(name);
				boolean is = false;
				for(SqlFieldsCategory e:dataBaseParentList){
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

	private void save(Map<String, SqlFieldsCategory> parentDataBase, List<SqlFieldsForm> xmlEntityList) {

		for (SqlFieldsForm entity : xmlEntityList) {
			SqlFieldsForm xmlEntity =new SqlFieldsForm();
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
				SqlFieldsCategory parentDataBaseEntity = parentDataBase.get(xmlParentId);
				xmlEntity.setFieldsCategory(parentDataBaseEntity);
				//确认xml中是否有数据库中的实体
				boolean isSave = true;

				Collection<SqlFieldsForm> dataBaseEntityList = parentDataBaseEntity.getFieldsForm();
				String xmlName = xmlEntity.getName();
				
				for (SqlFieldsForm dataBaseEntity : dataBaseEntityList) {						
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
	
	private SqlFieldsForm getUpdateEntity(SqlFieldsForm databaseEntity, SqlFieldsForm entity) {
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
