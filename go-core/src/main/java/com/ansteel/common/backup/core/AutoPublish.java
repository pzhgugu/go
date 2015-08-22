package com.ansteel.common.backup.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import com.ansteel.core.constant.Public;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：自动发布实现。  
 */
@Service
@Transactional
public class AutoPublish implements IAutoPublish {
	
	private static final Logger logger=Logger.getLogger(AutoPublish.class);

	private static final String RUN_SURROUNDINGS = "10";
	
	@Value("${go_pro.surroundings}")
	public String  surroundings;
	
	@Value("${go_pro.autoPublish_path}")
	public String publishPath;
	
	@Autowired
	IDataXmlEport dataXmlEport;
	
	@Autowired
	IXmlVersion xmlVersion;
	
	@Autowired
	IXmlImportManage xmlImportManage;
	
	@Autowired
	IBackupXmlImport backupXmlImport;
	
	
	@Override
	public synchronized void publish() {
		logger.info("环境编号："+surroundings);
		 if(StringUtils.hasText(surroundings)&&surroundings.equals(RUN_SURROUNDINGS)){
			 //String path = this.getPublishPath();
			 
			File file=null;
			try {
				file = this.getPublishPath();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 logger.info("开始导入："+file.getPath());
			 Assert.notNull(file, "没有发现此备份文件！");
			 FileInputStream fileInputStream = null;
             try {
                 fileInputStream=new FileInputStream(file);
             }catch (IOException e){
            	 logger.error(e.getMessage());
             }
             String versionOld= xmlVersion.getVersion();
             List<Class> listClass = dataXmlEport.getRegister();
             Map<Class,Object> map = xmlImportManage.importXmlVersion(fileInputStream, listClass,versionOld);
             
             if(map==null){
                 return ;
             }
             //开始导入
             backupXmlImport.setType(BackupConstant.ALL_VERSION_UPDATE);
             backupXmlImport.importXml(map);
             //版本更新
             String version=xmlImportManage.getVersion();
             xmlVersion.versionUpdate(version);
		 }		
	}
	
	public File getPublishPath() throws FileNotFoundException {		
        return ResourceUtils.getFile("classpath:/publish/"+Public.AUTO_PUBLISH_XML);
    }

}
