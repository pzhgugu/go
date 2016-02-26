package com.ansteel.report.makereport.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.common.attachment.service.FileAttachmentService;
import com.ansteel.report.aspose.service.AsposeExcelToPdfService;
import com.ansteel.report.excel.service.ReportAttachmentTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.constant.AttachmentConstant;
import com.ansteel.core.constant.ReportConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.report.excel.service.ExcelService;
import com.ansteel.report.poi.utils.Excel;
import com.ansteel.report.poi.utils.ToHtml;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.report.excel.core.ExcelReportFactory;
import com.ansteel.report.excel.core.IExcelShowFactory;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;
import com.ansteel.report.reportlist.domain.ReportMapped;
import com.ansteel.report.swftools.service.SWFToolsService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表生成服务实现类。
 */
@Service
@Transactional(readOnly=true)
public class MakeReportServiceBean implements MakeReportService {
	
	@Autowired
	ExcelService excelService;
	
	@Autowired
	IExcelShowFactory excelShowFactory;
	
	@Autowired
	FileAttachmentService fileAttachmentService;

	@Autowired
	AsposeExcelToPdfService asposeExcelToPdfService;
	
	@Autowired
	SWFToolsService swfToolsService;
	
	@Value("${go_pro.attPath}")
	private String attPath;
	
	@Value("${go_pro.attTempPath}")
	private String attTempPath;
	
	@Value("${go_pro.attTempWeb}")
	private String attTempWeb;

	@Autowired
	ReportAttachmentTreeService reportAttachmentTreeService;
	
	@Override
	public Excel getExcel(String name, HttpServletRequest request, Map<String, Object> parameterMap) {
		ExcelReport excelReport=excelService.getExcelReportToName(name);
		Assert.notNull(excelReport,name+"，报表没有找到！");
		//2、得到数据
		Map<ExcelReportSQL, List> mapExcel = excelService.getExcelReportSqlData(excelReport, request, parameterMap);
				
		//String tplPath = excelService.getFilePathById(excelReport.getAttachmentId());
		//由于自动发布，不发布附件表，所有模板直接读路径
		String tplPath = attPath+"/"+excelReport.getAttachmentPath();
		//3、生成excel
		ExcelReportFactory excelFactory=new ExcelReportFactory();
		return excelFactory.getExcel(tplPath, mapExcel);
	}
	
	@Override
	public Excel getExcel(List<Map> list,Map<String,String> nameMap) {
		if(nameMap==null){
			nameMap=new HashMap<>();
		}
		Map<ExcelReportSQL,List> mapExcel = new LinkedHashMap<ExcelReportSQL, List>();
		Assert.isTrue(list.size()>0, "记录为空！");
		Map<String,Object> map = list.get(0);
		StringBuffer headBuffer= new StringBuffer();
		Map<String,Object> headMap = new HashMap<>();
		StringBuffer recodeBuffer= new StringBuffer();
		int i=0;
		for(Entry<String, Object> entry:map.entrySet()){
			String figureHead= Excel.getCellFigure(1, i);
			String figure= Excel.getCellFigure(2, i);
			String key = entry.getKey();
			if(nameMap.containsKey(key)){
				headMap.put(key, nameMap.get(key));
			}else{
				headMap.put(key, key);
			}
			headBuffer.append(key+"="+figureHead+";");
			recodeBuffer.append(key+"="+figure+";");
			i++;
		}
		ExcelReportSQL excelReportSQLHead= new ExcelReportSQL();
		excelReportSQLHead.setSheetName("sheet1");
		excelReportSQLHead.setId("1");
		excelReportSQLHead.setFixedRecode(headBuffer.toString());
		List headList = new ArrayList<Map>();
		headList.add(headMap);
		mapExcel.put(excelReportSQLHead, headList);
		
		ExcelReportSQL excelReportSQL= new ExcelReportSQL();
		excelReportSQL.setSheetName("sheet1");
		excelReportSQL.setRecode(recodeBuffer.toString());
		excelReportSQL.setIsLoop(1);
		excelReportSQL.setId("2");
		mapExcel.put(excelReportSQL, list);
		
		//3、生成excel
		ExcelReportFactory excelFactory=new ExcelReportFactory();
		return excelFactory.getExcel(mapExcel);
	}
	
	@Override
	public String show(List<Map> list,Map<String,String> nameMap,
			String rType,String inline,
			HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		Excel excel = this.getExcel(list, nameMap);
		return excelShowFactory.show(excel,rType,inline,null,response,fileName);
	}

	@Override
	public String show(String name, String rType, String inline,
			String outPath, HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		Excel excel = this.getExcel(name, request, null);
		return excelShowFactory.show(excel,rType,inline,null,response,fileName);
	}

	@Override
	public String show(String name, String rType, String inline, String outPath, Map<String, Object> parameterMap, HttpServletRequest request, HttpServletResponse response,String fileName) {
		Excel excel = this.getExcel(name, request, parameterMap);
		return excelShowFactory.show(excel, rType, inline, null, response,fileName);
	}

	@Override
	@Transactional(readOnly=false)
	public String saveReport(String type,String rType, HttpServletRequest request) {
		AttachmentTree attachmentTree = reportAttachmentTreeService.get();
		Excel excel = this.getExcel(type, request, null);
		String catalogue=reportAttachmentTreeService.getCatalogue(attachmentTree, rType);
		String uuid = StringUtils.getUuid();
		String path="";
		if(rType==null){
			rType=ReportConstant.EXCEL;
		}
		switch (rType) {
		case ReportConstant.PDF:			
			path=this.savePDF(excel,catalogue,uuid);
			break;
		case ReportConstant.HTML:
			path=this.saveHtml(excel,catalogue,uuid);
			break;
		case ReportConstant.SWF:
			path=this.saveSWF(excel,catalogue,uuid);
			break;
		default:			
			path=this.saveExcel(excel,catalogue,uuid);
			break;
		}
		Attachment attachment = new Attachment();
		attachment.setAlias(uuid);
		attachment.setName(uuid);
		attachment.setAttachmentTree(attachmentTree);
		attachment.setPath(path);
		attachment=fileAttachmentService.save(attachment);
		return attachment.getId();
	}

	private String saveSWF(Excel excel, String catalogue,String uuid) {
		String path=attPath+catalogue+uuid+".swf";
		String excelPath = saveExcel(excel,catalogue,uuid);		
		return excelToSwf(excelPath,path);
	}
	
	private String excelToSwf(String excelPath,String path) {
		String pdfPath=path.replace(".swf", ".pdf");
		File excelFile = new File(excelPath);
		if(excelFile.exists()){
			File pdfFile = new File(pdfPath);
			if(pdfFile.exists())//pdf文件存在则删除
				pdfFile.delete();
			if (!asposeExcelToPdfService.convert2PDF(excelFile, pdfFile)) {
				throw new PageException("pdf文档转换失败！");
			}	
			File swfFile = new File(path);
			if(swfFile.exists())//pdf文件存在则删除
				swfFile.delete();
			if(!swfToolsService.convert2SWF(pdfFile, swfFile)){
				throw new PageException("swf文档转换失败！");
			}
		}
		
		return path;
	}

	private String savePDF(Excel excel, String catalogue,String uuid) {
		String excelPath = saveExcel(excel,catalogue,uuid);
		String path=catalogue+uuid+".pdf";
		return excelToPdf(excelPath,path);
	}
	
	public String excelToPdf(String excelPath,String path){
		File excelFile = new File(excelPath);
		if(excelFile.exists()){
			File pdfFile = new File(path);
			if(pdfFile.exists())//pdf文件存在则删除
				pdfFile.delete();
			if (!asposeExcelToPdfService.convert2PDF(excelFile, pdfFile)) {
				throw new PageException("pdf文档转换失败！");
			}	
		}
		return path;
	}
	
	public void excelToHtml(String excelPath, HttpServletResponse response){
		response.setCharacterEncoding("utf8");
	    PrintWriter out=null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ToHtml toHtml = ToHtml.create(excelPath, out);
			toHtml.setCompleteHTML(true);
			toHtml.printPage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	

	private String saveExcel(Excel excel,String catalogue,String uuid) {
		String path=attPath+catalogue+uuid+"."+excel.getType();
		excel.writeMkdir(path);
		return catalogue+uuid+"."+excel.getType();
	}
	
	private String saveHtml(Excel excel,String catalogue,String uuid) {
		String path=attPath+catalogue+uuid+".html";
		try {
			ToHtml toHtml = ToHtml.create(excel.getWorkbook(), new PrintWriter(
					new FileWriter(path)));

			toHtml.setCompleteHTML(true);
			toHtml.printPage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return catalogue+uuid+".html";
	}


	@Override
	public Excel getExcel(List listMap, String modelName) {
		ExcelReport excelReport=excelService.getExcelReportToName(modelName);
		Assert.notNull(excelReport,modelName+"，报表没有找到！");
		//2、得到数据
		Map<ExcelReportSQL, List> mapExcel = new LinkedHashMap<ExcelReportSQL, List>();
		Collection<ExcelReportSQL> excelReportSQLList = excelReport.getExcelReportSQLList();
		for(ExcelReportSQL excelReportSQL:excelReportSQLList){
			mapExcel.put(excelReportSQL, listMap);
		}				
		//由于自动发布，不发布附件表，所有模板直接读路径
		String tplPath = attPath+"/"+excelReport.getAttachmentPath();
		//3、生成excel
		ExcelReportFactory excelFactory=new ExcelReportFactory();
		return excelFactory.getExcel(tplPath, mapExcel);
	}

	@Override
	public String show(String modelName, List listMap, String type,
			String inline, HttpServletRequest request,
			HttpServletResponse response,String fileName) {
			Excel excel = this.getExcel(listMap, modelName);
			return excelShowFactory.show(excel,type,inline,null,response,fileName);
	}

	@Override
	public String makeReport(ReportMapped reportMapped, String type,
			Map parameters, HttpServletResponse response) {
		response.setCharacterEncoding("utf8");
		String attId = reportMapped.getAttPath();
		Attachment att = fileAttachmentService.findOne(attId);
		String path =att.getPath();
		String outPath="";
		switch (type) {
		case ReportConstant.PDF:	
			outPath = attTempPath+"/"+StringUtils.getUuid()+".pdf";
			this.excelToPdf(path,outPath);
			DownloadUtils.download(response, outPath, "1");
			return null;
		case ReportConstant.HTML:
			outPath = attTempPath+"/"+StringUtils.getUuid()+".html";
			this.excelToHtml(path,response); 
			return null;
		case ReportConstant.SWF:
			String uuid = StringUtils.getUuid();
			outPath = attTempPath+"/"+uuid+".swf";
			this.excelToSwf(path, outPath);
			return "/"+attTempWeb+"/"+uuid+".swf";
		}
		
		return null;
	}
	

}
