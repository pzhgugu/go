package com.ansteel.modules.excel.domain;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.report.excel.domain.ExcelReportSQL;

public class ExcelReportSQLTest extends SpringBaseTest{
	
	@Autowired
	protected BaseService baseService;


	@Test
	@Rollback(value=false)
	@Ignore
	public void test() {
		ExcelReportSQL excelReportSQL=baseService.findOne(ExcelReportSQL.class, "402897814d419a4b014d419f98a50001");
		baseService.delete(ExcelReportSQL.class, "402897814d419a4b014d419f98a50001");
	}

}
