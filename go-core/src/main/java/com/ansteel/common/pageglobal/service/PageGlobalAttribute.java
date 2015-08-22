package com.ansteel.common.pageglobal.service;

import javax.servlet.http.HttpServletRequest;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：页面全局变量接口。  
 */
public interface PageGlobalAttribute {

	void set(HttpServletRequest request);

}
