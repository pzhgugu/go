package com.ansteel.common.security.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.repository.ResourcesRepository;
import com.ansteel.common.security.repository.RolesResourcesRepository;
import com.ansteel.core.exception.PageException;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class ResourcesServiceBean implements ResourcesService {
	
	@Autowired
	ResourcesRepository resourcesRepository;
	
	@Autowired
	RolesResourcesRepository rolesResourcesRepository;

	@Override
	public Resources findOneByName(String name) {
		return resourcesRepository.findOneByName(name);
	}

	@Override
	public Resources save(Resources resources) {
		return resourcesRepository.save(resources);
	}

	@Override
	public Resources findOne(String id) {
		return resourcesRepository.findOne(id);
	}

	@Override
	@Transactional
	public void delect(String id) {
		Resources resources=this.findOne(id);
		Collection<RolesResources> rrList = resources.getRolesResources();
		for(RolesResources rr:rrList){
			Assert.isNull(rr.getPermission(),"资源已经被分配，请先删除分配！");
			rr.getRoles().getRolesResources().remove(rr);
			rolesResourcesRepository.delete(rr);
		}
		resourcesRepository.delete(resources);
	}

}
