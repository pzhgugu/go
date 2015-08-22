package com.ansteel.common.backup.core;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份版本管理接口。  
 */
public interface IXmlVersion {

	String getVersion();

	void versionUpdate(String version);

}
