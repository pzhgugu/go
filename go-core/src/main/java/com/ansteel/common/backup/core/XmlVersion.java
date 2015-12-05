package com.ansteel.common.backup.core;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FileUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份版本管理实现。  
 */
@Service
public class XmlVersion implements IXmlVersion {
	



	private static final String VERSION_FILENAME = "PublishVersion.txt";
	
	@Value("${go_pro.attPath}")
	public String  attPath;

	@Override
	public String getVersion() {
		String path=this.getPath();
		String content = FileUtils.readFile(path);
		if(content==null){
			try {
				FileUtils.createNewFile(path);
			} catch (IOException e) {
				throw new PageException("创建版本文件失败！");
			}
			content="";
		}
		return content.trim();
	}

	private String getPath() {
		String path="";
		if(StringUtils.hasText(attPath)){
			path = attPath + File.separator + VERSION_FILENAME;
		}else{
			path=System.getProperty("java.io.tmpdir")+VERSION_FILENAME;
		}
		return path;
	}

	@Override
	public void versionUpdate(String version) {
		String path=this.getPath();
		try {
			FileUtils.write(path, version);
		} catch (IOException e) {
			e.printStackTrace();
			throw new PageException("写版本文件失败！");
		}
	}

}
