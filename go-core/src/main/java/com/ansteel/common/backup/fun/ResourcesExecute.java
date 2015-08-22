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
import com.ansteel.common.security.domain.Resources;
import com.ansteel.core.utils.BeanUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ResourcesExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return Resources.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<Resources> entityList = (List<Resources>) o;
		Collection<Resources> dataBaseList = baseService.findAll(Resources.class);
		Map<String, Resources> dataBaseMap = new HashMap<String, Resources>();
		for (Resources entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (Resources entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				Resources dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
				}
			} else {
				Resources newEntity = new Resources();
				try {
					BeanUtils.copyProperties(newEntity, entity);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newEntity.setVersionPublish(entity.getVersion());
				baseService.save(newEntity);
			}
		}
	}

	private Resources getUpdateEntity(Resources databaseEntity, Resources entity) {
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
