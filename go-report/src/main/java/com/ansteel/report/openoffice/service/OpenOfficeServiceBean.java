package com.ansteel.report.openoffice.service;

/*import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;*/
import org.springframework.stereotype.Service;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：pdf转换服务实现类。
 */
@Service
public class OpenOfficeServiceBean implements OpenOfficeService {

	/*static Logger logger = Logger.getLogger(OpenOfficeServiceBean.class);
	
	private static OfficeManager officeManager;
	
	private static int port[] = { 8100,8110,8120 };
	
	@Value("${go_pro.OpenOffice}")
	private String openOfficePath;
	
	@Value("${go_pro.OpenOfficeCache}")
	private Integer openOfficeCache;
	
	@Override
	public synchronized boolean convert2PDF(File inputFile, File pdfFile) {
		Date start = new Date();
		try{
			startService();
			logger.info("进行文档转换:" + inputFile.getName() );
			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			converter.convert(inputFile, pdfFile);
		}catch (Exception e) {
			return false;
		}finally{
			stopService();
		}
		long l = (start.getTime() - new Date().getTime());
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		logger.info("生成" + pdfFile.getName() + "耗费：" + min + "分" + s + "秒");
		if (pdfFile.exists()) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public void startService() {
		if(officeManager==null){
			DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
			try {
				configuration.setOfficeHome(getOpenOfficePath());// 设置OpenOffice.org安装目录
				configuration.setPortNumbers(port); // 设置转换端口，默认为8100
				configuration.setMaxTasksPerProcess(3);//设置进程
				configuration.setTaskExecutionTimeout(1000 * 60 * 3L);// 设置任务执行超时分钟
				configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时4小时
				officeManager = configuration.buildOfficeManager();
				officeManager.start(); // 启动服务
				logger.info("office转换服务启动成功!");
			} catch (Exception ce) {
				logger.info("office转换服务启动失败!详细信息:" + ce);
			}
		}
	}
	
	public String getOpenOfficePath(){
		return openOfficePath;		
	}

	public  void stopService() {
		logger.info("关闭office转换服务....");
		if(openOfficeCache!=1){
			if (officeManager != null) {
				officeManager.stop();
			}
			logger.info("关闭office转换成功!");
		}
	}*/

}
