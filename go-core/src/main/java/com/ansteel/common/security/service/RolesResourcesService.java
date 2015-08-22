package com.ansteel.common.security.service;

import com.ansteel.common.security.domain.RolesResources;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色资源服务接口。  
 */
public interface RolesResourcesService {

	RolesResources save(RolesResources rolesResources);

	/**
	 * 通过角色名、资源名获取
	 * @param rolesName
	 * @param resourcesName
	 * @return
	 */
	RolesResources getRolesResources(String rolesName, String resourcesName);

}
