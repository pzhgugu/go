package com.ansteel.common.backup.core;

import java.util.List;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份数据导出接口。  
 */
public interface IDataXmlEport {

	String backupAll(String path);
	
	List getRegister();

	String getPath();

}
