package com.ansteel.report.backup.fun;

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
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.report.excel.domain.ExcelReportTree;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表树执行。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ExcelReportTreeExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return ExcelReportTree.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<ExcelReportTree> entityList = (List<ExcelReportTree>) o;
		Collection<ExcelReportTree> dataBaseList = baseService.findAll(ExcelReportTree.class);
		Map<String, ExcelReportTree> dataBaseMap = new HashMap<String, ExcelReportTree>();
		for (ExcelReportTree entity : dataBaseList) {
			dataBaseMap.put(entity.getName(), entity);
		}
		for (ExcelReportTree entity : entityList) {
			String name = entity.getName();
			if (dataBaseMap.containsKey(name)) {
				ExcelReportTree dataBaseEntity = dataBaseMap.get(name);
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					Long vp = dataBaseEntity.getVersionPublish();
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
					dataBaseEntity.setVersionPublish(vp);
				}
			} else {
				ExcelReportTree parent = entity.getParent();
				entity.setParent(null);
				entity.setVersionPublish(entity.getVersion());
				ExcelReportTree newEntity = new ExcelReportTree();
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
		Collection<ExcelReportTree> dataBaseParentList = baseService.findAll(ExcelReportTree.class);
		HashMap<String, ExcelReportTree> dataBaseParentMap = new HashMap<String, ExcelReportTree>(); 
		for (ExcelReportTree entity : dataBaseParentList) {
			dataBaseParentMap.put(entity.getName(), entity);
		}
		for (ExcelReportTree entity : entityList) {
			String name = entity.getName();
			ExcelReportTree dataBaseEntity = dataBaseParentMap.get(name);
			Long versionPublish=null;
			if(dataBaseMap.containsKey(name)){
				versionPublish=dataBaseMap.get(name).getVersionPublish();
			}
			if ( versionPublish!= entity.getVersion()) {
				baseService.save(this.updateParent(dataBaseEntity, entity, dataBaseParentMap));
			}
		}
	}

	private ExcelReportTree updateParent(ExcelReportTree databaseEntity, ExcelReportTree entity,
			Map<String, ExcelReportTree> dataBaseMap) {
		ExcelReportTree pEntity = entity.getParent();
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

	public ExcelReportTree getUpdateEntity(ExcelReportTree databaseEntity, ExcelReportTree entity) {
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
