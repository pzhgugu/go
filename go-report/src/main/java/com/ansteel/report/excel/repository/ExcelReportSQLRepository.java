package com.ansteel.report.excel.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * Created by Administrator on 2015/9/13.
 */
public interface ExcelReportSQLRepository extends ProjectRepository<ExcelReportSQL, String> {
    ExcelReportSQL findOneByExcelReportAndName(ExcelReport excelReport, String sqlName);
        }
