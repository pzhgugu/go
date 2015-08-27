package com.ansteel.report.reportlist.controller;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.tpl.core.IDhtmlxWidget;
import com.ansteel.common.view.domain.View;
import com.ansteel.common.viewelement.core.ViewElementUtils;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.SimpleController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.OperEntity;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.excel.service.ExcelService;
import com.ansteel.report.makereport.service.MakeReportService;
import com.ansteel.report.jasperReports.service.JasperReportsService;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.view.service.ViewService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.jasperReports.domain.JasperReport;
import com.ansteel.report.reportlist.domain.ReportMapped;
import com.ansteel.report.reportlist.service.ReportMappedService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-13
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表列表控制器。
 */
@Controller
@RequestMapping(value = "/report")
public class ReportListController extends SimpleController{

	@Autowired
	MakeReportService makeReportService;
	
	@Autowired
	ExcelService excelService;
	
	@Autowired
	ReportMappedService reportMappedService;
	
	@Autowired
	ModelService modelService;
	
	@Autowired
	JasperReportsService jasperReportsService;
	
	@Autowired
	ViewService viewService;
	
	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo entity = new EntityInfo();
		entity.setClazz(ReportMapped.class);
		entityInfos.add(entity);
		return entityInfos;
	}

	
	/**
	 * ajax调用
	 * 单条件查询、带分页
	 * @param clazz
	 * @param id
	 * @return
	 */
	@RequestMapping("/a/queryPage/{type}")
	public  @ResponseBody UDataSet queryPageAjax(@PathVariable("type")String type,
			@RequestParam(value="rKind",required=false)String rKind,//1为excel 2为jasper
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		String id;
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			JasperReport jasperReport=jasperReportsService.findOneByName(type);
			id=jasperReport.getId();
		}else{
			ExcelReport excelReport = excelService.getExcelReportToName(type);
			id=excelReport.getId();
		}
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());		
		Page result = reportMappedService.findByTypeOrderByRDate(id,queryList,pageable);
		this.setReportMappedToPath(result,rKind,request);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,result);
	}
	
	/**
	 * ajax调用
	 * 通过实体class、id查询数据
	 * @param clazz
	 * @param id
	 * @return
	 */
	@RequestMapping("/a/loadPage/{type}")
	public @ResponseBody UDataSet loadPageAjax(@PathVariable("type")String type,
			@RequestParam(value="rKind",required=false)String rKind,//1为excel 2为jasper
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			HttpServletRequest request,
			HttpServletResponse response){
		String id;
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			JasperReport jasperReport=jasperReportsService.findOneByName(type);
			id=jasperReport.getId();
		}else{
			ExcelReport excelReport = excelService.getExcelReportToName(type);
			id=excelReport.getId();
		}
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		Page result = reportMappedService.findByTypeOrderByRDate(id,pageable);
		this.setReportMappedToPath(result,rKind,request);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,result);
	}
	
	public void setReportMappedToPath(Page r, String rKind,HttpServletRequest request) {
		List<ReportMapped> result = (List<ReportMapped>) r.getContent();
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);		
		for(ReportMapped att:result){
			String downloadUrl=url+"/report/download/"+att.getId();
			att.setHtml("打开^"+downloadUrl+"/html?rKind="+rKind);
			att.setExcel("打开^"+downloadUrl+"/excel?rKind="+rKind);
			att.setPdf("打开^"+downloadUrl+"/pdf?rKind="+rKind);
			att.setSwf("打开^"+downloadUrl+"/swf?rKind="+rKind);
			String web=url+"/att/download/"+att.getAttPath()+"?_inline=1";
			att.setAttPath("打开^"+web);
		}
	}

	@RequestMapping("/list/{type}")
	public String tpl(@PathVariable("type")String type,
			@RequestParam(value="rKind",required=false)String rKind,//1为excel 2为jasper
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		Assert.hasText(type, "报表类型不能为空！");
		Models models=modelService.findOneByName(ReportMapped.class.getName());
		Assert.notNull(models, "请先扫描,运行/show/report/scan/ReportMapped!");
		ViewElement viewElement = new ViewElement();
		viewElement.setRequest(request);
		viewElement.setResponse(response);
		viewElement.setRequestMappingName("/report");
		ViewElementUtils.setViewElement(viewElement,models,TplViewConstant.DEFAULT_SHORT,ReportMapped.class);
		
		Map<String, Object> widgetModel=new HashMap<String, Object>();
		IDhtmlxWidget grid=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_GRID);
		widgetModel.putAll(grid.structure(viewElement));		
		IDhtmlxWidget query=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_SIMPLE_QUERY);
		widgetModel.putAll(query.structure(viewElement));		
		model.addAllAttributes(widgetModel);
		
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			JasperReport jasperReport=jasperReportsService.findOneByName(type);
			Assert.notNull(jasperReport, "jasperReport中没有["+type+"]类型报表。");
			model.addAttribute("P_REPORT_TYPE", jasperReport.getDateType());
			model.addAttribute("P_REPORT_NAME", jasperReport.getName());
			model.addAttribute("P_REPORT", "2");
		}else{
			ExcelReport excelReport = excelService.getExcelReportToName(type);
			Assert.notNull(excelReport, "excel报表中没有["+type+"]类型报表。");
			model.addAttribute("P_REPORT_TYPE", excelReport.getType());
			model.addAttribute("P_REPORT_NAME", excelReport.getName());
		}
		//加入视图变量
		View view = viewService.getCacheView(null, request.getServletPath());
		if(view!=null){
			model.addAllAttributes(viewService.getViewVariable(view));
		}
		return FisUtils.page("report:page/reportlist/reportlist.html");
		
	}
	
	@RequestMapping("/make/{type}")
	@ResponseBody
	public int makeReport(@PathVariable("type")String type,
			@RequestParam(value = "beginDate", required = false) String beginDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value="rType",required = false) String rType,
			@RequestParam(value="rKind",required=false)String rKind,//1为excel 2为jasper
			@RequestParam(value="inline",required = false) String inline,
			HttpServletRequest request,
			HttpServletResponse response){
		
		String id;
		String reportType;
		OperEntity reportEntity;
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			reportEntity=jasperReportsService.findOneByName(type);
			JasperReport jasperReport=(JasperReport) reportEntity;
			id=jasperReport.getId();
			reportType = jasperReport.getDateType();
		}else{
			reportEntity=excelService.getExcelReportToName(type);
			ExcelReport excelReport = (ExcelReport) reportEntity;
			id=excelReport.getId();
			reportType = excelReport.getType();
		}
		Assert.notNull(reportType, "请在报表中选择报表类型！");
		if(reportType.equals("region")){
			Assert.notNull(beginDate, "请选择开始时间！");
			Assert.notNull(endDate, "请选择结束时间！");
			ReportMapped reportMapped = reportMappedService.findOneByScription(beginDate,endDate);
			if(reportMapped!=null){
				reportMappedService.delect(reportMapped);
			}
		}else{
			Assert.notNull(beginDate, "请选择时间！");
			ReportMapped reportMapped = reportMappedService.findOneByRDate(beginDate);
			if(reportMapped!=null){
				reportMappedService.delect(reportMapped);
			}
		}

		String attid="";
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			JasperReport jasperReport=(JasperReport) reportEntity;
			Map<String, Object> parameters=RequestUtils.getRequestMap(request);
			attid=jasperReportsService.saveReport(jasperReport,rType,parameters);
		}else{
			attid=makeReportService.saveReport(type,rType,request);
		}
		reportMappedService.save(id,attid,beginDate,endDate);
		return 0;
		
	}
	
	@RequestMapping("/a/delect/{id}")
	@ResponseBody
	public int delectAjax(@PathVariable("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		ReportMapped reportMapped = reportMappedService.findOne(id);
		reportMappedService.delect(reportMapped);
		return 0;
	}
	
	@RequestMapping("/download/{id}/{type}")
	public String open(@PathVariable("id")String id,
			@PathVariable("type")String type,
			@RequestParam(value="rKind",required=false)String rKind,//1为excel 2为jasper
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		type=type.toUpperCase();
		ReportMapped reportMapped=reportMappedService.findOne(id);
		Assert.notNull(reportMapped, id+",此报表列表中没有找到！");
		Map parameters = RequestUtils.getRequestMap(request);
		String path = "";
		if(StringUtils.hasText(rKind)&&rKind.equals("2")){
			path=jasperReportsService.makeReport(reportMapped,type,parameters,response);
		}else{
			path=makeReportService.makeReport(reportMapped,type,parameters,response);
		}
	
		if(type.equals("SWF")){
			return "redirect:/browsen?url="+path;
		}
		return path;
	}
}
