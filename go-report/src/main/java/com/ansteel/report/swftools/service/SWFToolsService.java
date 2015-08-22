package com.ansteel.report.swftools.service;

import java.io.File;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-13
 * 修 改 人：
 * 修改日 期：
 * 描   述：swf转换工具。
 */
public interface SWFToolsService {
	/**
	 * pdf转换swf
	 * @param pdfFile
	 * @param swfFile
	 * @return
	 */
	boolean convert2SWF(File pdfFile, File swfFile);
}
