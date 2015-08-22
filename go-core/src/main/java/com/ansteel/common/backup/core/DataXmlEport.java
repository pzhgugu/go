package com.ansteel.common.backup.core;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ansteel.core.constant.Public;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份数据导出xml类。  
 */
@Service
public class DataXmlEport implements IDataXmlEport {
	
	@Autowired
    IXmlExportManage xmlExportManage;
	
	List<Class> register ;
	

	@Value("${go_pro.autoPublish_path}")
	public String publishPath;
	
	public List<Class> getRegister() {
		return register;
	}
	public void setRegister(List<Class> register) {
		this.register = register;
	}

	@Override
	public String backupAll(String path) {
		 Map<Class,Object> xmlMap = new LinkedHashMap<Class, Object>();
		for(Class clazz :register){
			IExecuteXml executeXml = BackupUtils.getExecuteXml(clazz);
			xmlMap.put(executeXml.getClass(), executeXml.getAllRecode());
		}
		return xmlExportManage.exportMapToXml(xmlMap,path);
	}
	
	@Override
	public String getPath() {
		Assert.hasText(publishPath, "没有设置发布路径！");
		return publishPath+"/"+Public.AUTO_PUBLISH_XML;
	}

	
}
