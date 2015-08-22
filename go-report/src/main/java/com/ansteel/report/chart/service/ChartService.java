package com.ansteel.report.chart.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.report.chart.domain.Chart;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-07
 * 修 改 人：
 * 修改日 期：
 * 描   述：图表服务。 
 */
public interface ChartService {

	/**
	 * 根据名称获取图表
	 * @param name 名称
	 * @return
	 */
	Chart getChartByName(String name);

	/**
	 * 获取图表数据
	 * @param chart 图表
	 * @param request http请求
	 * @return
	 */
	Map<String, Object> getNameData(Chart chart,HttpServletRequest request);

}
