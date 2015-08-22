package com.ansteel.report.chart.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.sql.service.SqlService;
import com.ansteel.report.chart.domain.Chart;
import com.ansteel.report.chart.repository.ChartRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-07
 * 修 改 人：
 * 修改日 期：
 * 描   述：图表服务实现。 
 */
@Service
@Transactional(readOnly=true)
public class ChartServiceBean implements ChartService {
	
	private static final String KEY = "_KEY";
	
	private static final String VALUE = "_VALUE";
	@Autowired
	SqlService sqlService;
	
	@Autowired
	ChartRepository chartRepository;

	@Override
	public Chart getChartByName(String name) {
		return chartRepository.findOneByName(name);
	}

	@Override
	public Map<String, Object> getNameData(Chart chart,HttpServletRequest request) {
		List list=sqlService.querySql(chart.getSqlContent(), request);
		Integer dataAspect = chart.getDataAspect();
		if(list.size()>0){
			if(dataAspect!=null&&dataAspect==2){
				Map<String,Object> dataMap = new LinkedMap();
				for(Object o:list){
					Map<String,Object> map=(Map) o;
					if(map.containsKey(KEY)){
						String key=(String) map.get(KEY);
						if(map.containsKey(VALUE)){
							dataMap.put(key, map.get(VALUE));
						}
					}
				}
				return dataMap;
			}else{
				/*Map<String,Object> map=(Map<String, Object>) list.get(0);
				StringBuffer sb = new StringBuffer();
				for(Entry<String, Object> entry:map.entrySet()){
					sb.append("['");
					sb.append(entry.getKey());
					sb.append("',");
					sb.append(entry.getValue());
					sb.append("],");
				}
				if(sb.length()>0){
					return sb.substring(0, sb.length()-1);
				}*/
				return (Map<String, Object>) list.get(0);
			}
		}
		return null;
	}

}
