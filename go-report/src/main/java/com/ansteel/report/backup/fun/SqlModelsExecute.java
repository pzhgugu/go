package com.ansteel.report.backup.fun;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.utils.BeanUtils;
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
 * 描   述：sql模型字段模型备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class SqlModelsExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return SqlModels.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<SqlModels> entityList = (List<SqlModels>) o;
		Collection<SqlModels> dataBaseList = baseService.findAll(SqlModels.class);
		Map<String, SqlModels> dataBaseMap = new HashMap<String, SqlModels>();
		for (SqlModels entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (SqlModels entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				SqlModels dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					Long vp = dataBaseEntity.getVersionPublish();
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
					dataBaseEntity.setVersionPublish(vp);
				}
			} else {
				SqlModels parent = entity.getParent();
				entity.setParent(null);
				entity.setVersionPublish(entity.getVersion());
				SqlModels newEntity = new SqlModels();
				try {
					BeanUtils.copyProperties(newEntity, entity);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newEntity.setVersion(null);
				baseService.save(newEntity);
				entity.setParent(parent);
			}
		}
		// 树节点写入父节点
		Collection<SqlModels> dataBaseParentList = baseService.findAll(SqlModels.class);
		HashMap<String, SqlModels> dataBaseParentMap = new HashMap<String, SqlModels>(); 
		for (SqlModels entity : dataBaseParentList) {
			dataBaseParentMap.put(entity.getName(), entity);
		}
		for (SqlModels entity : entityList) {
			String name = entity.getName();
			SqlModels dataBaseEntity = dataBaseParentMap.get(name);
			Long versionPublish=null;
			if(dataBaseMap.containsKey(name)){
				versionPublish=dataBaseMap.get(name).getVersionPublish();
			}
			if ( versionPublish!= entity.getVersion()) {
				baseService.save(this.updateParent(dataBaseEntity, entity, dataBaseParentMap));
			}
		}
	}

	private SqlModels updateParent(SqlModels databaseEntity, SqlModels entity,
			Map<String, SqlModels> dataBaseMap) {
		SqlModels pEntity = entity.getParent();
		if (pEntity != null) {
			String name = pEntity.getName();
			if (dataBaseMap.containsKey(name)) {
				databaseEntity.setParent(dataBaseMap.get(name));
			}
		} else {
			databaseEntity.setParent(null);
		}
		databaseEntity.setVersionPublish(entity.getVersion());
		return databaseEntity;
	}

	public SqlModels getUpdateEntity(SqlModels databaseEntity, SqlModels entity) {
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
		databaseEntity.setParent(null);
		return databaseEntity;
	}

}
