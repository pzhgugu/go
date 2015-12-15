package com.ansteel.report.jasperReports.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.jasperReports.domain.JasperReport;
import com.ansteel.report.jasperReports.service.JasperReportsService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：Jasper报表控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/jasper")
public class JasperReportsController  extends BaseController {
	
	@Autowired
	CheckService checkService;
	
	@Autowired
	JasperReportsService jasperReportsService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
		
		EntityInfo report = new EntityInfo();
		report.setClazz(JasperReport.class);
		
		eis.add(report);
		return eis;
	}
	
	@RequestMapping("/a/saveFile/{className}")
	public @ResponseBody int saveFileAjax(
			@Valid @PathDatabaseEntity("className") BaseEntity entity,
			@RequestParam(value = "_key", required = false) String key,
			@RequestParam(value = "_value", required = false) String value,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, 
			HttpServletResponse response,
			BindingResult result) {
		if (entity.getClass() == JasperReport.class) {
			
			JasperReport report=(JasperReport) entity;
			Assert.isTrue(!checkService.isNameRepeat(report),report.getName()+",已经存在，请检查！");
			if(file!=null&& file.getSize() > 0){
				String attPath = jasperReportsService.saveAttachment(file,report);
				report.setAttachmentPath(attPath);
			}
			super.saveAjax(report, result, key, value, request,response);
			ResponseUtils.setContentType(response);
		} else {
			super.saveAjax(entity, result, key, value, request,response);
		}
		return 1;
	}
	
	public @ResponseBody UDataSet loadPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			HttpServletRequest request,
			HttpServletResponse response){
		UDataSet dataSet = super.loadPageAjax(clazz, key, value, posStart, count, order, request, response);
		if(clazz==JasperReport.class){
			return jasperReportsService.setAttPath(dataSet,request);
		}
		return dataSet;
	}


	public  @ResponseBody UDataSet queryDetailPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		
		UDataSet dataSet = super.queryDetailPageAjax(clazz, key, value, posStart, count, order, queryList, request, response);
		if(clazz==JasperReport.class){
			return jasperReportsService.setAttPath(dataSet,request);
		}
		return dataSet;
	}
	
	@RequestMapping("/a/queryPage/{className}")
	public  @ResponseBody UDataSet queryPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		
		UDataSet dataSet = super.queryPageAjax(clazz, key, value, posStart, count, order, queryList, request, response);
		if(clazz==JasperReport.class){
			return jasperReportsService.setAttPath(dataSet,request);
		}
		return dataSet;
	}

	
}
