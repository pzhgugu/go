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
import com.ansteel.common.view.domain.ViewTree;
import com.ansteel.core.utils.BeanUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图树备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ViewTreeExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return ViewTree.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<ViewTree> entityList = (List<ViewTree>) o;
		Collection<ViewTree> dataBaseList = baseService.findAll(ViewTree.class);
		Map<String, ViewTree> dataBaseMap = new HashMap<String, ViewTree>();
		for (ViewTree entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (ViewTree entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				ViewTree dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					Long vp = dataBaseEntity.getVersionPublish();
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
					dataBaseEntity.setVersionPublish(vp);
				}
			} else {
				ViewTree parent = entity.getParent();
				entity.setParent(null);
				entity.setVersionPublish(entity.getVersion());
				ViewTree newEntity = new ViewTree();
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
		Collection<ViewTree> dataBaseParentList = baseService.findAll(ViewTree.class);
		HashMap<String, ViewTree> dataBaseParentMap = new HashMap<String, ViewTree>(); 
		for (ViewTree entity : dataBaseParentList) {
			dataBaseParentMap.put(entity.getName(), entity);
		}
		for (ViewTree entity : entityList) {
			String name = entity.getName();
			ViewTree dataBaseEntity = dataBaseParentMap.get(name);
			Long versionPublish=null;
			if(dataBaseMap.containsKey(name)){
				versionPublish=dataBaseMap.get(name).getVersionPublish();
			}
			if ( versionPublish!= entity.getVersion()) {
				baseService.save(this.updateParent(dataBaseEntity, entity, dataBaseParentMap));
			}
		}
	}

	private ViewTree updateParent(ViewTree databaseEntity, ViewTree entity,
			Map<String, ViewTree> dataBaseMap) {
		ViewTree pEntity = entity.getParent();
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

	public ViewTree getUpdateEntity(ViewTree databaseEntity, ViewTree entity) {
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
