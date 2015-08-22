package com.ansteel.common.backup.core;

import java.util.Map;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导出管理接口。  
 */
public interface IXmlExportManage {	
	String exportMapToXml(Map<Class, Object> map, String filePath);
}
