package com.ansteel.core.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态获取资源文件。 
 */
public class PropertiesConfigurationLoader {
	
	
	public static Properties getGoProperties() {
		Resource resource = new ClassPathResource("/go.properties");
		Properties props=null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return props;
	}

}
