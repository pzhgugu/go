package com.ansteel.common.security.service;

import java.util.Map;

import com.ansteel.common.security.domain.Users;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户服务接口。  
 */
public interface UserService {

	long count();

	Users save(Users users);

	Users findOne(String id);

	Users findOneByName(String name);

	boolean isPasswordValid(String encPass, String rawPass);

	/**修改密码
	 * @param  oldPwd 原密码
	 * @param  newPwd 新密码
	 * @param  newPwdConfirm 新密码确认
	 * @param  name 当前用户ID
	 * @return 返回修改结果
	 *  */
	String changePassword(String oldPwd,String newPwd,String newPwdConfirm,String name );
	
	String getPasswordEncoder(String rawPass, Object salt);

	void delect(String id);

}
