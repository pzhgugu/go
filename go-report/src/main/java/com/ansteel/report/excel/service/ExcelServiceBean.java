package com.ansteel.report.excel.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.common.attachment.service.FileAttachmentService;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.report.excel.repository.ExcelReportSQLRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.constant.AttachmentConstant;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.beetl.service.BeetlService;
import com.ansteel.common.sql.service.SqlService;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;
import com.ansteel.report.excel.domain.ExcelReportTest;
import com.ansteel.report.excel.repository.ExcelReportRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表服务实现。
 */
@Service
@Transactional(readOnly=true)
public class ExcelServiceBean implements ExcelService{
	
	Logger logger=Logger.getLogger(ExcelServiceBean.class);
	
	@Autowired
	FileAttachmentService fileAttachmentService;
	
	@Autowired
	ExcelReportRepository excelReportRepository;

	@Autowired
	ExcelReportSQLRepository excelReportSQLRepository;

	@Autowired
	ExcelAttachmentTreeService excelAttachmentTreeService;

	@Autowired
	SqlService sqlService;
	
	@Value("${go_pro.attPath}")
	private String attPath;

	@Override
	public String saveAttachment(MultipartFile file,ExcelReport excelReport) {
		Attachment attachment=null;
		if(StringUtils.hasText(excelReport.getId())){
			String aId = excelReport.getAttachmentId();
			if(StringUtils.hasText(aId)){
				attachment = fileAttachmentService.findOne(excelReport.getAttachmentId());
			}
		}
		
		String fileType = FileUtils.getFileType(file.getOriginalFilename());
		if(fileType.equals("xls")||fileType.equals("xlsx")){
			if(attachment==null){
				//获取exclTpl附件目录
				AttachmentTree attachmentTree = excelAttachmentTreeService.get();
				attachment = fileAttachmentService.save(attachmentTree, file);
			}else{
                String fileName=attachment.getFileName();
                if(StringUtils.hasText(fileName)){
                    String[] nameArray = fileName.split("\\.");
                    String type=FileUtils.getFileType(file.getOriginalFilename()).toLowerCase();
                    String newFileName=nameArray[0]+"."+type;
                    attachment.setFileName(newFileName);
                    newFileName=excelAttachmentTreeService.getPath(attachment.getAttachmentTree(),newFileName,type);
                    attachment.setPath(newFileName);
                }
				attachment = fileAttachmentService.save(attachment, file);
			}
			excelReport.setAttachmentId(attachment.getId());
			return attachment.getPath();
		}else{
			throw new PageException("请上传excel文件！");
		}
	}

	@Override
	@Transactional
	public void delectExcelReport(String id) {
		ExcelReport excelReport=excelReportRepository.findOne(id);
		String attId = excelReport.getAttachmentId();
		if(StringUtils.hasText(attId)){
			fileAttachmentService.delete(attId);
		}
		excelReportRepository.delete(excelReport);	
	}

	@Override
	public UDataSet setAttPath(UDataSet dataSet, HttpServletRequest request) {
		Page result = (Page) dataSet.getResult();
		List<ExcelReport> ers=(List<ExcelReport>) result.getContent();
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);
		for(ExcelReport er:ers){
			//String webUrl = attachmentService.getWebPathById(request,er.getAttachmentId());
			String path = attPath+"/"+er.getAttachmentPath();
			String webUrl=url+"/att/downloadPath?_path="+path;
			er.setAttPath(webUrl+"^"+webUrl);
			String type = er.getType();
			if(StringUtils.hasText(type)){
				String listWebUrl=url+"/report/list/"+er.getName();
				er.setTypePath(listWebUrl+"^"+listWebUrl);
			}
		}
		return dataSet;
	}

	@Override
	public UDataSet setTestPath(UDataSet dataSet, HttpServletRequest request) {
		Page result = (Page) dataSet.getResult();
		List<ExcelReportTest> ers=(List<ExcelReportTest>) result.getContent();
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);
		for(ExcelReportTest er:ers){
			String testParam = er.getTestParam();
			Integer iType = er.getType();
			StringBuffer sbLink = new StringBuffer();
			sbLink.append(url);
			sbLink.append("/makeReport/excel/");
			sbLink.append(er.getExcelReport().getName());	
			sbLink.append("?");
			sbLink.append(this.getTestType(iType));
			sbLink.append("&");
			sbLink.append(this.getTestOpenTyep(er.getOpenType()));
			if(StringUtils.hasText(testParam)){
				sbLink.append("&");
				sbLink.append(testParam);
			}
			String webUrl = sbLink.toString();
			er.setTestLink(webUrl+"^"+webUrl);
		}
		return dataSet;
	}

	private String getTestOpenTyep(Integer openType) {
		StringBuffer sb = new StringBuffer();
		sb.append("inline=");
		if(openType!=null){
			sb.append(openType);
		}
		return sb.toString();
	}

	/**
	 * 报表生成类型
	 * @return
	 */
	private String getTestType(int iType) {
		StringBuffer sb = new StringBuffer();
		sb.append("rType=");
		switch (iType) {
		case 2:
			sb.append("PDF");
			break;
		case 3:
			sb.append("SWF");
			break;
		case 4:
			sb.append("HTML");
			break;
		default:
			sb.append("EXCEL");
			break;
		}
		return sb.toString();
	}

	@Override
	public ExcelReport getExcelReportToName(String name) {
		return excelReportRepository.findOneByName(name);
	}

	@Override
	public Map<ExcelReportSQL, List> getExcelReportSqlData(ExcelReport excelReport, HttpServletRequest request, Map<String, Object> parameterMap) {
		Map<ExcelReportSQL, List> map = new LinkedHashMap<ExcelReportSQL, List>();
		Collection<ExcelReportSQL> excelReportSQLList = excelReport.getExcelReportSQLList();
		for(ExcelReportSQL excelReportSQL:excelReportSQLList){
			String sql = excelReportSQL.getSqlContent();
			Assert.hasText(sql, excelReportSQL.getName()+":SQL不能为空！");
			//List lsit=this.getSqlData(excelReportSQL.getSqlContent(),request);
			List lsit = sqlService.querySql(sql, request, parameterMap);
			map.put(excelReportSQL, lsit);
		}
		return map;
	}

	/**
	 * 获取SQL数据
	 * @return
	 *//*
	private List getSqlData(String sqlContent,HttpServletRequest request) {
		String sql = beetlService.outSQLQuery(sqlContent, request);
		logger.info("SQL:"+sql);
		if(sql.indexOf(";")>-1){
			throw new PageException("SQL语句错误:请去除分号"+sql);
		}
		sql=sql.toUpperCase();
		List list=null;
		try {
			list=new BaseEntity().querySQLClobMapList(sql);
		} catch (Exception e) {
			throw new PageException("SQL语句错误:"+sql+",错误日志："+e.getMessage());
		}

		return list;
	}*/

	@Override
	public String getFilePathById(String attachmentId) {
		Attachment attachment=fileAttachmentService.findOne(attachmentId);
		return attachment.getPath();
	}

	@Override
	public String findByReportAndSql(String reportName, String sqlName) {
		ExcelReport excelReport = excelReportRepository.findOneByName(reportName);
		Assert.notNull(excelReport, reportName + ",报表没有找到！");
		ExcelReportSQL excelReportSQL = excelReportSQLRepository.findOneByExcelReportAndName(excelReport, sqlName);
		Assert.notNull(excelReportSQL, sqlName + "，sql没有找到！");
		return excelReportSQL.getSqlContent();
	}

}
