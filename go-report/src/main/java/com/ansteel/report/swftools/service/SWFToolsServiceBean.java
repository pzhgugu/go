package com.ansteel.report.swftools.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import com.ansteel.core.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-13
 * 修 改 人：
 * 修改日 期：
 * 描   述：swf转换工具实现。
 */
@Service
public class SWFToolsServiceBean implements SWFToolsService {

	static Logger logger = Logger.getLogger(SWFToolsServiceBean.class);
	
	@Value("${go_pro.pdf2swf}")
	private String pdf2swfPath;
	@Value("${go_pro.pdf2swf.languagedir}")
	private String languagedir;

	//private static String cmd = "{0} \"{1}\" -o \"{2}\" -T 9 -f";
	private static String cmd = "{0} -T 9 -s poly2bitmap -s zoom=150 -s flashversion=9  {1} -o {2} ";

	private static String getCmdStr(String languageDir, String pdf2swfPath, String sourcePath,
			String destPath) {
		if (StringUtils.hasText(languageDir)) {
			pdf2swfPath += " languagedir=" + languageDir;
		}
		return MessageFormat.format(cmd, pdf2swfPath, sourcePath, destPath);
	}

	@Override
	public boolean convert2SWF(File pdfFile, File swfFile) {
		if (!pdfFile.getName().endsWith(".pdf")) {
			logger.info("文件格式非PDF！");
			return false;
		}
		if (!pdfFile.exists()) {
			logger.info("PDF文件不存在！");
			return false;
		}
		if (swfFile.exists()) {
			logger.info("SWF文件已存在！");
			return true;
		}
		// 请注意swf软件安装路径
		String command = this.getCmdStr(languagedir, getPdf2swfPath(),
				pdfFile.getAbsolutePath(), swfFile.getAbsolutePath());

		try {
			logger.info("开始转换文档: " + pdfFile.getName());
			System.out.print(command);
			Process pro = Runtime.getRuntime().exec(command);

			logger.info(loadStream(pro.getInputStream()));
			logger.info(loadStream(pro.getErrorStream()));
			logger.info(loadStream(pro.getInputStream()));
			logger.info("****swf转换成功，文件输出：" + swfFile.getPath() + "****");
			logger.info("成功转换为SWF文件！");

			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(pro.getInputStream()));
			while (bufferedReader.readLine() != null)
				;

			try {
				pro.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			pro.exitValue();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("转换文档为swf文件失败！");
			return false;
		}
	}

	public String loadStream(InputStream in) throws IOException {

		int ptr = 0;
		in = new BufferedInputStream(in);
		StringBuffer buffer = new StringBuffer();

		while ((ptr = in.read()) != -1) {
			buffer.append((char) ptr);
		}

		return buffer.toString();
	}

	public String getPdf2swfPath() {
		return pdf2swfPath;

	}

}
