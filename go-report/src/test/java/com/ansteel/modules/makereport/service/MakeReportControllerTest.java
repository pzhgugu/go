package com.ansteel.modules.makereport.service;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.common.attachment.service.FileAttachmentService;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.report.excel.service.ExcelService;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.report.excel.core.ExcelReportFactory;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;

public class MakeReportControllerTest  extends SpringBaseTest{
	
	@Autowired
	ExcelService excelService;
	@Autowired
	FileAttachmentService fileAttachmentService;
	
	private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

	@Test
	@Ignore
	public void testExcelReport() {
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		Map map= new HashMap<String, Object>();
		map.put("name", "administrator");
		//这样的用法只能使用与mock对象的有返回值的方法，如果mock对象的方法是void，则需要使用expectLastCall()：
		attributes.put("S_URL", "/show/");
		
		Enumeration<String>  e=Collections.enumeration(new LinkedHashSet<String>(this.attributes.keySet()));		
		EasyMock.expect(request.getAttributeNames()).andReturn(e);
		EasyMock.expect(request.getParameterMap()).andReturn(map);
		EasyMock.expect(request.getAttribute("S_URL")).andReturn("/show/");
		EasyMock.replay(request); 
		
		//1、 通过name获取ExcelReport
		String name="Field";
		ExcelReport excelReport=excelService.getExcelReportToName(name);
		Assert.notNull(excelReport,name+"，报表没有找到！");
        Map<ExcelReportSQL, List> mapExcel = excelService.getExcelReportSqlData(excelReport, request, null);
        System.out.println(mapExcel.size());
		//2、得到数据
		//3、生成excel

		String tplPath = fileAttachmentService.findOne(excelReport.getAttachmentId()).getPath();
		ExcelReportFactory excelFactory=new ExcelReportFactory();
		Excel excel = excelFactory.getExcel(tplPath, mapExcel);
		 
		//4、显示
		try {
			excel.write("d:/test/poi/user."+excel.getType());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//进入verify阶段  
        EasyMock.verify(request); 
	}

}
