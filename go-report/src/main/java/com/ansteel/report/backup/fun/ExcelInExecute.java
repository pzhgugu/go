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
import com.ansteel.report.excelin.domain.ExcelIn;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入执行。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ExcelInExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return ExcelIn.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<ExcelIn> xmlEntityList = (List<ExcelIn>) o;
		Collection<ExcelIn> dataBaseList = baseService.findAll(ExcelIn.class);
		Map<String, ExcelIn> dataBaseMap = new HashMap<String, ExcelIn>();
		for(ExcelIn entity :dataBaseList){
			dataBaseMap.put(entity.getName(), entity);
		}
		for (ExcelIn entity : xmlEntityList) {
			if(dataBaseMap.containsKey(entity.getName())){
				ExcelIn dataBaseEntity = dataBaseMap.get(entity.getName());
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
				}
			}else{
				entity.setVersionPublish(entity.getVersion());
				baseService.save(entity);
			}
		}
		
		
	}
	
	
	
	private ExcelIn getUpdateEntity(ExcelIn databaseEntity, ExcelIn entity) {
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
