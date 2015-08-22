package com.ansteel.common.springsecurity.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：单点登录接口类。  
 */
public interface SingleSignOn {

	void on(HttpServletRequest request, HttpServletResponse response);

}
