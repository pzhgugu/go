package com.ansteel.report.makereport.web;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ansteel.core.utils.RequestUtils;
import com.ansteel.report.excel.service.ExcelService;
import com.ansteel.report.jasperReports.service.JasperReportsService;
import com.ansteel.report.makereport.service.MakeReportService;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.excel.core.ExcelReportFactory;
import com.ansteel.report.excel.core.IExcelShowFactory;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表生成控制器。
 */
@Controller
@RequestMapping(value = "/makeReport")
public class MakeReportController {
	

	
	@Autowired
	MakeReportService makeReportService;
	
	@Autowired
	JasperReportsService jasperReportsService;

	@RequestMapping("/excel/{name}")
	public String excel(@PathVariable String name,
			@RequestParam(value="rType",required = false) String rType,
			@RequestParam(value="inline",required = false) String inline,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		
		String path=makeReportService.show(name,rType,inline,null,request,response);		
		
		if(rType.equals("SWF")){
			return "redirect:/browsen?url="+path;
		}
		return null;		
	}
	
	@RequestMapping("/jasper/{name}")
	public String jasper(@PathVariable String name,
			@RequestParam(value="rType",required = false) String rType,
			@RequestParam(value="inline",required = false) String inline,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> parameters = RequestUtils.getRequestMap(request);
		String path=jasperReportsService.show(name,rType,inline,null,parameters,response);		
		
		if(rType.equals("SWF")){
			return "redirect:/browsen?url="+path;
		}
		return null;		
	}
}
