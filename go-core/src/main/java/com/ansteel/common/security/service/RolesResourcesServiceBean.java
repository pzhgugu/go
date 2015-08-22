package com.ansteel.common.security.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.repository.RolesResourcesRepository;
import com.ansteel.core.exception.PageException;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色资源服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class RolesResourcesServiceBean implements RolesResourcesService {
	
	@Autowired
	RolesResourcesRepository rolesResourcesRepository;

	@Override
	@Transactional
	public RolesResources save(RolesResources rolesResources) {
		return rolesResourcesRepository.save(rolesResources);
	}

	@Override
	public RolesResources getRolesResources(String rolesName,
			String resourcesName) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resources.name",resourcesName);
		map.put("roles.name", rolesName);
		List<RolesResources> rolesResources=rolesResourcesRepository.find(map);
		int size = rolesResources.size();
		if(size>0){
			if(size==1){
				return rolesResources.get(0);
			}
			throw new PageException("角色资源表异常！");
		}
		return null;
	}

}
