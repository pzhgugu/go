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
import com.ansteel.report.chart.domain.Chart;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：图表生成。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ChartExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return Chart.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		List<Chart> xmlEntityList = (List<Chart>) o;
		Collection<Chart> dataBaseList = baseService.findAll(Chart.class);
		Map<String, Chart> dataBaseMap = new HashMap<String, Chart>();
		for(Chart entity :dataBaseList){
			dataBaseMap.put(entity.getName(), entity);
		}
		for (Chart entity : xmlEntityList) {
			if(dataBaseMap.containsKey(entity.getName())){
				Chart dataBaseEntity = dataBaseMap.get(entity.getName());
				if (dataBaseEntity.getVersionPublish() != entity.getVersion()) {
					baseService.save(getUpdateEntity(dataBaseEntity, entity));
				}
			}else{
				entity.setVersionPublish(entity.getVersion());
				baseService.save(entity);
			}
		}
		
		
	}
	
	
	
	private Chart getUpdateEntity(Chart databaseEntity, Chart entity) {
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
