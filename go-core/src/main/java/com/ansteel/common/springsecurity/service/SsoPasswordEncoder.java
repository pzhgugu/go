package com.ansteel.common.springsecurity.service;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：单点登录接密码校验类。  
 */
@Service("ssoPasswordEncoder")
public class SsoPasswordEncoder extends Md5PasswordEncoder {

	public boolean isPasswordValid(String encPass, String rawPass, Object salt) {
		if (encPass.equals(rawPass)) {
			return true;
		}
		return super.isPasswordValid(encPass, rawPass, salt);
	}
}
