package com.ansteel.common.backup.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导入管理接口。  
 */
public interface IXmlImportManage {

	Map<Class, Object> importXmlVersion(InputStream is,
			List<Class> listClass, String version);

	String getVersion();

}
