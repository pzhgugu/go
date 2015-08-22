package com.ansteel.report.excel.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.excel.domain.ExcelReport;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表数据仓库。
 */
public interface ExcelReportRepository extends ProjectRepository<ExcelReport,String> {

	ExcelReport findOneByName(String name);

}
