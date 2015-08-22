package com.ansteel.report.jasperReports.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.jasperReports.domain.JasperReport;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表数据仓库。
 */
public interface JasperReportRepository extends ProjectRepository<JasperReport, String>{

	/**
	 * 根据名称查询Jasper报表实体
	 * @param name
	 * @return
	 */
	JasperReport findOneByName(String name);

}
