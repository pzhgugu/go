package com.ansteel.common.security.service;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.repository.UsersRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：yxb
 * 修改日 期：2015-06-20
 * 描   述：用户服务接口实现。  
 */
@Transactional(readOnly=true)
@Service
public class UserServiceBean implements UserService {
	
	@Autowired
	UsersRepository usersRepository;
	
	@Resource(name="passwordEncoder")
	PasswordEncoder passwordEncoder;
	
	@Override
	public long count() {
		return usersRepository.count();
	}

	@Override
	@Transactional
	public Users save(Users user) {
		return usersRepository.save(user);
	}

	@Override
	public Users findOne(String id) {
		return usersRepository.findOne(id);
	}

	@Override
	public Users findOneByName(String name) {
		return usersRepository.findOneByName(name);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass){
		return passwordEncoder.isPasswordValid(encPass, rawPass, null);
	}
	
	@Transactional
	public String changePassword(String oldPwd,String newPwd,String newPwdConfirm,String name )
	{
		Assert.isTrue(newPwd.length()>0&&newPwdConfirm.length()>0&&oldPwd.length()>0, "原密码和新密码不能为空！");
		Assert.isTrue(newPwd.equals(newPwdConfirm), "两次输入的密码不一致，请重新输入！");
		Assert.isTrue(!(oldPwd.equals(newPwdConfirm)||oldPwd.equals(newPwd)),"新密码与原密码相同，请重新输入！");		

		Users user = this.findOneByName(name);
		boolean isVal = this.isPasswordValid(user.getPassword(), oldPwd);
		Assert.isTrue(isVal, "原密码输入错误，请重新输入！");
		newPwdConfirm= this.getPasswordEncoder(newPwdConfirm,null);
		user.setPassword(newPwdConfirm);
		this.save(user);
		return "密码修改成功！";
	}

	public String getPasswordEncoder(String rawPass, Object salt){
		return passwordEncoder.encodePassword(rawPass,salt);
	}

	@Override
	@Transactional
	public void delect(String id) {
		Users user = this.findOne(id);
		Collection<Roles> rList = user.getRoles();
		Assert.isTrue(!(rList.size()>0), "该用户关联了角色，请选取消角色关联！");
		usersRepository.delete(user);
	}
}
