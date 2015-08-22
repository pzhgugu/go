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
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.utils.BeanUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class TplExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return Tpl.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<Tpl> entityList = (List<Tpl>) o;
		Collection<Tpl> dataBaseList = baseService.findAll(Tpl.class);
		Map<String, Tpl> dataBaseMap = new HashMap<String, Tpl>();
		for (Tpl entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (Tpl entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				Tpl dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
				}
			} else {
				Tpl newEntity = new Tpl();
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

	private Tpl getUpdateEntity(Tpl databaseEntity, Tpl entity) {
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
