package com.ansteel.report.chart.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.chart.domain.Chart;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：图表数据仓库。 
 */
public interface ChartRepository extends ProjectRepository<Chart, String> {
	Chart findOneByName(String name);
}
