package com.ansteel.report.jasperReports.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.common.attachment.service.FileAttachmentService;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.constant.AttachmentConstant;
import com.ansteel.core.constant.ReportConstant;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.report.jasperReports.core.IJasperShow;
import com.ansteel.report.jasperReports.domain.JasperReport;
import com.ansteel.report.jasperReports.repository.JasperReportRepository;
import com.ansteel.report.reportlist.domain.ReportMapped;

@Transactional(readOnly = true)
@Service
public class JasperReportsServiceBean implements JasperReportsService {

	

	@Autowired
	JasperReportRepository jasperReportRepository;

	@Autowired
	FileAttachmentService fileAttachmentService;

	@Autowired
	JasperAttachmentTreeService jasperAttachmentTreeService;

	@Value("${go_pro.attPath}")
	private String attPath;
	
	@Value("${go_pro.attTempPath}")
	private String attTempPath;

	@Autowired
	protected EntityManagerFactory entityManagerFactory;

	@Override
	@Transactional
	public String saveAttachment(MultipartFile file, JasperReport report) {
		Attachment attachment = null;
		if (StringUtils.hasText(report.getId())) {
			String aId = report.getAttachmentId();
			if (StringUtils.hasText(aId)) {
				attachment = fileAttachmentService.findOne(aId);
			}
		}
		String fileType = FileUtils.getFileType(file.getOriginalFilename());
		if (fileType.equals("jasper")) {
			if (attachment == null) {
				// 获取exclTpl附件目录
				AttachmentTree attachmentTree = jasperAttachmentTreeService.get();
				attachment = fileAttachmentService.save(attachmentTree,
						file);
			} else {
				attachment = fileAttachmentService.save(attachment, file);
			}
			report.setAttachmentId(attachment.getId());
			return attachment.getPath();
		} else {
			throw new PageException("请上传.jasper文件！");
		}
	}

	@Override
	public UDataSet setAttPath(UDataSet dataSet, HttpServletRequest request) {
		Page result = (Page) dataSet.getResult();
		List<JasperReport> ers = (List<JasperReport>) result.getContent();
		String url = (String) request.getAttribute(ViewModelConstant.S_URL);
		for (JasperReport er : ers) {
			String path = attPath + "/" + er.getAttachmentPath();
			String webUrl = url + "/att/downloadPath?_path=" + path;
			er.setAttPath(webUrl + "^" + webUrl);

			StringBuffer sb = new StringBuffer();
			String testParameter = er.getTestParameter();
			if (StringUtils.hasText(testParameter)) {
				String[] tArray = testParameter.split(";");
				for (String s : tArray) {
					String[] point = s.split("=");
					if (point.length == 2) {
						sb.append(point[0]);
						sb.append("=");
						sb.append(point[1]);
						sb.append("&");
					}
				}
			}
			Integer type = er.getType();
			sb.append("rType=");
			switch (type) {
			case 1:
				sb.append(ReportConstant.EXCEL);
				break;
			case 3:
				sb.append(ReportConstant.SWF);
				break;
			case 4:
				sb.append(ReportConstant.HTML);
				break;
			default:
				sb.append(ReportConstant.PDF);
				break;
			}
			sb.append("&");

			Integer ot = er.getOpenType();
			sb.append("inline=");
			if (ot != null && ot == 1) {
				sb.append("1");
			} else {
				sb.append("2");
			}
			String testUrl = url + "/makeReport/jasper/" + er.getName()
					+ "?" + sb.toString();
			er.setTestPath(testUrl + "^" + testUrl);
		}
		return dataSet;
	}

	@Override
	public String show(String name, String rType, String inline,
			String outPath, Map parameters,
			HttpServletResponse response) {
		JasperReport jasperReport = jasperReportRepository.findOneByName(name);
		Assert.notNull(jasperReport, name + ",没有找到！");
		String sourceFileName = attPath + "/"
				+ jasperReport.getAttachmentPath();

		Connection connection = null;
		try {
			connection = this.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new PageException("获取Connection连接失败！");
		}

		if (!StringUtils.hasText(rType)) {
			rType = ReportConstant.PDF;
		}
		if (!StringUtils.hasText(inline)) {
			inline = "1";
		}

		return this.show(rType, inline, outPath, sourceFileName, parameters,
				response, connection);
	}

	private Connection getConnection() throws SQLException {
		HibernateEntityManagerFactory em = (HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImpl sessionFactory = (SessionFactoryImpl) em
				.getSessionFactory();
		return sessionFactory.getConnectionProvider().getConnection();
	}

	public String show(String rType, String inline, String outPath,
			String sourceFileName, Map<String, Object> parameters,
			HttpServletResponse response, Connection connection) {
		if (inline!=null&&inline.equals("1")) {
			rType += "_INLINE";
		}
		IJasperShow jShow = null;
		switch (rType) {
		case ReportConstant.PDF:
			jShow = ContextHolder.getSpringBean("pdfJasperShow");
			break;
		case ReportConstant.PDF_INLINE:
			jShow = ContextHolder.getSpringBean("pdfInlineJasperShow");
			break;
		case ReportConstant.HTML: 			
		case ReportConstant.HTML_INLINE: 
			jShow = ContextHolder.getSpringBean("htmlJasperShow");
		break;
		case ReportConstant.EXCEL: 
			jShow = ContextHolder.getSpringBean("excelJasperShow");
			break;
		case ReportConstant.EXCEL_INLINE: 
			jShow = ContextHolder.getSpringBean("excelInlineJasperShow");
		break;
		case ReportConstant.SWF:
		case ReportConstant.SWF_INLINE: 
			jShow = ContextHolder.getSpringBean("swfJasperShow");
			break;
		default:
			break;
		}
		return jShow.show(sourceFileName, outPath, parameters, response,
				connection);
	}

	@Override
	public JasperReport findOneByName(String name) {
		return jasperReportRepository.findOneByName(name);
	}

	@Override
	@Transactional(readOnly=false)
	public String saveReport(JasperReport jasperReport, String rType,
			Map<String, Object> parameters) {
		AttachmentTree attachmentTree = jasperAttachmentTreeService.get();
		String tplPath = attPath+"/"+jasperReport.getAttachmentPath();
		String outPath=attPath+"/"+jasperAttachmentTreeService.getPath(attachmentTree, StringUtils.getUuid()+".pdf");
		try {
			FileUtils.createNewFile(outPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			this.show(ReportConstant.PDF, null, outPath, tplPath, parameters, null, this.getConnection());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Attachment att=fileAttachmentService.save(outPath, attachmentTree);
		return att.getId();
	}

	@Override
	public String makeReport(ReportMapped reportMapped, String type,
			Map parameters, HttpServletResponse response) {
		JasperReport jasperReport=jasperReportRepository.findOne(reportMapped.getType());
		Assert.notNull(jasperReport, reportMapped.getId()+",此报表没有找到对应的jasper类型！");
		parameters.put("beginDate", reportMapped.getrDate());
		String scription = reportMapped.getScription();
		if(StringUtils.hasText(scription)){
			String[] sArray = scription.split("@");
			if(sArray.length==2){
				parameters.put("beginDate", sArray[0]);
				parameters.put("endDate", sArray[1]);
			}
		}
		String outPath = null;
		if(!type.equals("SWF")){			
			outPath=attTempPath+"/"+StringUtils.getUuid()+"."+ReportConstant.getSuffix(type);
		}
		return this.show(jasperReport.getName(), type, "1", outPath, parameters, response);
	}
}
