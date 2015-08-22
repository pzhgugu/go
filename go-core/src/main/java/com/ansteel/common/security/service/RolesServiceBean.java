package com.ansteel.common.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.repository.RolesRepository;
import com.ansteel.common.security.repository.RolesResourcesRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色服务接口实现。  
 */
@Transactional(readOnly=true)
@Service
public class RolesServiceBean implements RolesService {
	
	@Autowired
	RolesRepository rolesRepository;
	
	@Autowired
	RolesResourcesRepository rolesResourcesRepository;

	@Override
	@Transactional
	public Roles save(Roles roles) {
		return rolesRepository.save(roles);
	}

	@Override
	public long count() {
		return rolesRepository.count();
	}

	@Override
	public Page findMap(String queryString, Map<String, Object> whereMap,String countQuery, Map<String, Object> countMap,Pageable pageable) {
		return rolesRepository.findMap(queryString, whereMap,countQuery,countMap,pageable);
	}

	@Override
	public Roles findOne(String id) {
		return rolesRepository.findOne(id);
	}

	@Override
	public Collection<Roles> findAll() {
		return rolesRepository.findAll();
	}

	@Override
	public Roles findOneByName(String name) {
		return rolesRepository.findOneByName(name);
	}

	@Override
	@Transactional
	public void delect(String id) {
		Roles role=this.findOne(id);

		Collection<Users> userList = role.getUsers();
		Assert.isTrue(!(userList.size()>0), "该角色关联了用户，请选取消用户关联！");
		Collection<RolesResources> rrList = role.getRolesResources();
		for(RolesResources rr:rrList){
			Assert.isNull(rr.getPermission(),"资源已经被分配，请先删除分配！");
			rr.getResources().getRolesResources().remove(rr);
			rolesResourcesRepository.delete(rr);
		}
		rolesRepository.delete(role);
	}

}
