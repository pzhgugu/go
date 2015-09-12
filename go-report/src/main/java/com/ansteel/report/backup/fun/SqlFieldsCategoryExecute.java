package com.ansteel.report.backup.fun;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.sqlmodel.domain.SqlFieldsCategory;
import com.ansteel.report.sqlmodel.domain.SqlModels;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql模型分类备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SqlFieldsCategoryExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return SqlFieldsCategory.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		//父节点
		Collection<SqlModels> dataBaseResList = baseService.findAll(SqlModels.class);
		Map<String, SqlModels> dataBaseResMap = new HashMap<String, SqlModels>();
		for(SqlModels entity :dataBaseResList){
			dataBaseResMap.put(entity.getName(), entity);
		}

		List<SqlModels> xmlParentList=(List<SqlModels>) xmlMap.get(SqlModelsExecute.class);
		Map<String, SqlModels> parentXmlToDataBaseMap = new HashMap<String, SqlModels>();
		for(SqlModels entity :xmlParentList){
			String name = entity.getName();
			if(dataBaseResMap.containsKey(name)){
				if(StringUtils.hasText(name)){
					parentXmlToDataBaseMap.put(entity.getId(), dataBaseResMap.get(name));
				}
			}
		}
		//父节点
		

		List<SqlFieldsCategory> xmlEntityList = (List<SqlFieldsCategory>) o;
		Collection<SqlFieldsCategory> dataBaseList = baseService.findAll(SqlFieldsCategory.class);
		for (SqlFieldsCategory entity : xmlEntityList) {
			SqlFieldsCategory xmlEntity =new SqlFieldsCategory();
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
			SqlModels parentXmlToDataBaseEntity=null;
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
			for (SqlFieldsCategory dataBaseEntity : dataBaseList) {
				//数据库节点名称
				String dataBaseName = dataBaseEntity.getName();
				if(dataBaseName.equals(xmlName)){
					//数据库父节点名称
					String dataBaseParentName = dataBaseEntity.getModels()==null?"":dataBaseEntity.getModels().getName();
					String parentXmlName = parentXmlToDataBaseEntity==null?"":parentXmlToDataBaseEntity.getName();
					if(dataBaseParentName.equals(parentXmlName)){
					
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							SqlFieldsCategory parent = getUpdateEntity(dataBaseEntity, xmlEntity);
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
	
	private SqlFieldsCategory getUpdateEntity(SqlFieldsCategory databaseEntity, SqlFieldsCategory entity) {
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
