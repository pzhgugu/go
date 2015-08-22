package com.ansteel.core.constant;

import com.ansteel.common.attachment.domain.AttachmentTree;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述： 系统附件常量
 * 定义系统Excel模板路径、Excel模板名称、Excel模板别名、Jasper模板路径、Jasper模名称、Jasper模板别名等常量
 * 定义获取附件树、模板树的方法
 */
public class AttachmentConstant {
	//Excel模板路径
	public static final String EXCEL_TPL_PATH = "/ExcelTpl";
	//Excel模板名称
	public static final String EXCEL_TPL_NAME = "ExcelTpl";	
	//Excel模板别名
	public static final String EXCEL_TPL_ALIAS = "excel报表模板";

	//Jasper模板路径
	public static final String JASPER_TPL_PATH = "/JasperTpl";
	//Jasper模板名称
	public static final String JASPER_TPL_NAME = "JasperTpl";
	//Jasper模板别名
	public static final String JASPER_TPL_ALIAS = "Jasper报表模板";
	

	//报表文件别名
	public static final String REPORT_FILE_ALIAS = "报表文件";
	//报表文件名称
	public static final String REPORT_FILE_NAME = "ReportFile";	
	//报表文件路径
	public static final String REPORT_FILE_PATH = "/ReportFile";
	
	
	
	/*
	 * 获取报表文件附件树
	 */
	public static AttachmentTree getReportFileAttachmentTree(){
		return getAttachmentTree(REPORT_FILE_NAME,REPORT_FILE_ALIAS,REPORT_FILE_PATH,1);
	}
	/*
	 * 获取Excel模板文件附件树
	 */
	public static AttachmentTree getExcelTplAttachmentTree(){
		return getAttachmentTree(EXCEL_TPL_NAME,EXCEL_TPL_ALIAS,EXCEL_TPL_PATH,0);
	}
	/*
	 * 获取Jasper模板文件附件树
	 */
	public static AttachmentTree getJasperTplAttachmentTree(){
		return getAttachmentTree(JASPER_TPL_NAME,JASPER_TPL_ALIAS,JASPER_TPL_PATH,0);
	}
	/*
	 * 获取附件树
	 */
	public static AttachmentTree getAttachmentTree(String name,String alias,String catlogue,Integer isDate){
		AttachmentTree attachmentTree = new AttachmentTree();
		attachmentTree.setName(name);
		attachmentTree.setAlias(alias);
		attachmentTree.setIsDate(isDate);
		attachmentTree.setCatalogue(catlogue);
		attachmentTree.setLayer(0);
		return attachmentTree;
	}
}
