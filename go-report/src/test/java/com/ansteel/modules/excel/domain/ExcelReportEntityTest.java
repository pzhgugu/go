package com.ansteel.modules.excel.domain;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.report.excel.domain.ExcelReport;

public class ExcelReportEntityTest extends SpringBaseTest{
	
	@Autowired
	BaseService baseService;

	@Test
	@Rollback(false)
	@Ignore
	public void test() {
		baseService.delete(ExcelReport.class,"402897814d42ef77014d43054afa0001");
	}

	
}
