package com.ansteel.report.excel.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.core.constant.Public;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.utils.StringUtils;
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
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;
import com.ansteel.report.excel.domain.ExcelReportTest;
import com.ansteel.report.excel.domain.ExcelReportTree;
import com.ansteel.report.excel.service.ExcelService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+ "/excel")
public class ExcelController extends BaseController  implements SaveBefore  {
	
	@Autowired
	ExcelService excelService;
	
	@Autowired
	CheckService checkService;
	
	@Override
	public Collection<EntityInfo> getEntityInfos() {
		//树设置
		TreeInfo treeInfo=new TreeInfo();
		treeInfo.setTree(ExcelReportTree.class);
		Collection<Class> tables=new ArrayList<Class>();
		tables.add(ExcelReport.class);
		treeInfo.setTables(tables);
		
		MainSubInfo msi=new MainSubInfo();
		msi.setPrincipal(ExcelReport.class);
		Collection<Class> subs=new ArrayList<Class>();
		subs.add(ExcelReportSQL.class);
		subs.add(ExcelReportTest.class);
		msi.setSubordinate(subs);
		
		Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
		EntityInfo tree = new EntityInfo();
		tree.setClazz(ExcelReportTree.class);
		tree.setTree(treeInfo);
		eis.add(tree);
		
		EntityInfo excelReport = new EntityInfo();
		excelReport.setClazz(ExcelReport.class);
		excelReport.setTree(treeInfo);
		excelReport.setMainSub(msi);
		eis.add(excelReport);
		
		EntityInfo excelReportSQL = new EntityInfo();
		excelReportSQL.setClazz(ExcelReportSQL.class);
		excelReportSQL.setMainSub(msi);
		eis.add(excelReportSQL);
		
		EntityInfo excelReportTest = new EntityInfo();
		excelReportTest.setClazz(ExcelReportTest.class);
		excelReportTest.setMainSub(msi);
		eis.add(excelReportTest);
		
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
		if (entity.getClass() == ExcelReport.class ) {
			
			ExcelReport excelReport=(ExcelReport) entity;
			Assert.isTrue(!checkService.isNameRepeat(excelReport),excelReport.getName()+",已经存在，请检查！");
			if(file!=null&& file.getSize() > 0){
				String attPath = excelService.saveAttachment(file,excelReport);
				excelReport.setAttachmentPath(attPath);
			}
			super.saveAjax(excelReport, result, key, value, request,response);
			
			ResponseUtils.setContentType(response);
		} else {
			super.saveAjax(entity, result, key, value, request,response);
		}
		return 0;
	}
	
	
	public @ResponseBody void delectAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		if(clazz==ExcelReport.class){
			excelService.delectExcelReport(id);
		}else{
			super.delectAjax(clazz, id, request, response);
		}
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
		if(clazz==ExcelReport.class){
			return excelService.setAttPath(dataSet,request);
		}
		if(clazz==ExcelReportTest.class){
			return excelService.setTestPath(dataSet,request);
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
		if(clazz==ExcelReport.class){
			return excelService.setAttPath(dataSet,request);
		}
		if(clazz==ExcelReportTest.class){
			return excelService.setTestPath(dataSet,request);
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
		if(clazz==ExcelReport.class){
			return excelService.setAttPath(dataSet,request);
		}
		if(clazz==ExcelReportTest.class){
			return excelService.setTestPath(dataSet,request);
		}
		return dataSet;
	}

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity.getClass()==ExcelReportSQL.class){
			ExcelReportSQL sqlEntty= (ExcelReportSQL) entity;
			sqlEntty.setRecode(sqlEntty.getRecode().toUpperCase());
			String fixedRecode = sqlEntty.getFixedRecode();
			if (StringUtils.hasText(fixedRecode)) {
				sqlEntty.setFixedRecode(fixedRecode.toUpperCase());
			}
		}
	}
}
