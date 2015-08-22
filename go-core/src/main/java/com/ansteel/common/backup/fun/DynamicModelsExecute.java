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
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.core.utils.BeanUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态图建模模型增量备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DynamicModelsExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return DynamicModels.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<DynamicModels> entityList = (List<DynamicModels>) o;
		Collection<DynamicModels> dataBaseList = baseService.findAll(DynamicModels.class);
		Map<String, DynamicModels> dataBaseMap = new HashMap<String, DynamicModels>();
		for (DynamicModels entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (DynamicModels entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				DynamicModels dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					Long vp = dataBaseEntity.getVersionPublish();
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
					dataBaseEntity.setVersionPublish(vp);
				}
			} else {
				DynamicModels parent = entity.getParent();
				entity.setParent(null);
				entity.setVersionPublish(entity.getVersion());
				DynamicModels newEntity = new DynamicModels();
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
		Collection<DynamicModels> dataBaseParentList = baseService.findAll(DynamicModels.class);
		HashMap<String, DynamicModels> dataBaseParentMap = new HashMap<String, DynamicModels>(); 
		for (DynamicModels entity : dataBaseParentList) {
			dataBaseParentMap.put(entity.getName(), entity);
		}
		for (DynamicModels entity : entityList) {
			String name = entity.getName();
			DynamicModels dataBaseEntity = dataBaseParentMap.get(name);
			Long versionPublish=null;
			if(dataBaseMap.containsKey(name)){
				versionPublish=dataBaseMap.get(name).getVersionPublish();
			}
			if ( versionPublish!= entity.getVersion()) {
				baseService.save(this.updateParent(dataBaseEntity, entity, dataBaseParentMap));
			}
		}
	}

	private DynamicModels updateParent(DynamicModels databaseEntity, DynamicModels entity,
			Map<String, DynamicModels> dataBaseMap) {
		DynamicModels pEntity = entity.getParent();
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

	public DynamicModels getUpdateEntity(DynamicModels databaseEntity, DynamicModels entity) {
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
