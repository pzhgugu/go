package com.ansteel.core.constant;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表固定常量定义。 
 */
public class ReportConstant {
	public static final String PDF = "PDF";
	public static final String HTML = "HTML";
	public static final String SWF = "SWF";
	public static final String EXCEL = "EXCEL";
	public static final String PDF_INLINE = "PDF_INLINE";
	public static final String HTML_INLINE = "HTML_INLINE";
	public static final String EXCEL_INLINE = "EXCEL_INLINE";
	public static final String SWF_INLINE = "SWF_INLINE";
	
	/**
	 * 获取后缀，根据常量类型返回文件后缀名
	 * @param type
	 * @return
	 */
	public static String getSuffix(String type) {
		switch (type) {
		case PDF:
			return "pdf";
		case HTML:
			return "html";
		case EXCEL:
			return "xls";
		case SWF:
			return "swf";
		default:
			break;
		}
		return "txt";
	}
}
