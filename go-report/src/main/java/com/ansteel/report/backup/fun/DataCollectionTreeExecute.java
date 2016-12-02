package com.ansteel.report.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ansteel.report.dc.domain.DataCollectionTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.utils.BeanUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表树执行。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DataCollectionTreeExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return DataCollectionTree.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<DataCollectionTree> entityList = (List<DataCollectionTree>) o;
		Collection<DataCollectionTree> dataBaseList = baseService.findAll(DataCollectionTree.class);
		Map<String, DataCollectionTree> dataBaseMap = new HashMap<String, DataCollectionTree>();
		for (DataCollectionTree entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (DataCollectionTree entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				DataCollectionTree dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					Long vp = dataBaseEntity.getVersionPublish();
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
					dataBaseEntity.setVersionPublish(vp);
				}
			} else {
				DataCollectionTree parent = entity.getParent();
				entity.setParent(null);
				entity.setVersionPublish(entity.getVersion());
				DataCollectionTree newEntity = new DataCollectionTree();
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
		Collection<DataCollectionTree> dataBaseParentList = baseService.findAll(DataCollectionTree.class);
		HashMap<String, DataCollectionTree> dataBaseParentMap = new HashMap<String, DataCollectionTree>();
		for (DataCollectionTree entity : dataBaseParentList) {
			dataBaseParentMap.put(entity.getName(), entity);
		}
		for (DataCollectionTree entity : entityList) {
			String name = entity.getName();
			DataCollectionTree dataBaseEntity = dataBaseParentMap.get(name);
			Long versionPublish=null;
			if(dataBaseMap.containsKey(name)){
				versionPublish=dataBaseMap.get(name).getVersionPublish();
			}
			if ( versionPublish!= entity.getVersion()) {
				baseService.save(this.updateParent(dataBaseEntity, entity, dataBaseParentMap));
			}
		}
	}

	private DataCollectionTree updateParent(DataCollectionTree databaseEntity, DataCollectionTree entity,
			Map<String, DataCollectionTree> dataBaseMap) {
		DataCollectionTree pEntity = entity.getParent();
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

	public DataCollectionTree getUpdateEntity(DataCollectionTree databaseEntity, DataCollectionTree entity) {
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
