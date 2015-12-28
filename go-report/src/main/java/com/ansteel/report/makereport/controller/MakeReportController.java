package com.ansteel.report.makereport.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ansteel.core.utils.RequestUtils;
import com.ansteel.report.jasperReports.service.JasperReportsService;
import com.ansteel.report.makereport.service.MakeReportService;

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
			@RequestParam(value="filename",required = false) String fileName,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		
		String path=makeReportService.show(name,rType,inline,null,request,response,fileName);
		
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
