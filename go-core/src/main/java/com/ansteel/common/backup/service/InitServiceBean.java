package com.ansteel.common.backup.service;

import java.util.Collection;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.security.domain.PermissionURL;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.service.ResourcesService;
import com.ansteel.common.security.service.RolesResourcesService;
import com.ansteel.common.security.service.RolesService;
import com.ansteel.common.security.service.UserService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：初始化接口实现。  
 */
@Service
@Transactional
public class InitServiceBean implements InitService {
	
	private static final Logger logger=Logger.getLogger(InitServiceBean.class);
	
	@Value("${go_pro.init}")
	private Integer isInit;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ResourcesService resourcesService;
	
	@Autowired
	RolesResourcesService rolesResourcesService;
	
	@Override
	public void run() {
		if(isInit!=null&&isInit==1){
			initUser();
		}
	}
	
	public void initUser(){
		long count = userService.count();
		if(count<1){
			logger.info("开始导入初始化数据！---");
			//查询默认资源是否存在
			Resources resources =resourcesService.findOneByName("ALL");
			if(resources==null){
				resources=new Resources();
				resources.setName("ALL");
				resources.setAlias("所有页面");
				resources.setType("U");
				PermissionURL permissionURL = new PermissionURL();
				permissionURL.setPath("/**");
				permissionURL.setPermissionBit(1);
				permissionURL.setResources(resources);
				resources.getPermissionUrls().add(permissionURL);
				resources=resourcesService.save(resources);
			}
			
			Users users = new Users();
			users.setName("administrator");
			users.setAlias("管理员");
			users.setPassword("b59c67bf196a4758191e42f76670ceba");
			users.setType(2);
			users.setEnable(0);
			users=userService.save(users);
			
			Roles roles = new Roles();
			roles.setName("ROLE_ADMIN");
			roles.setAlias("管理员");			
			roles.getUsers().add(users);
			users.getRoles().add(roles);
			roles=rolesService.save(roles);
			
			RolesResources rolesResources = new RolesResources();
			rolesResources.setRoles(roles);
			rolesResources.setResources(resources);
			rolesResources.setPermission(15);
			rolesResources=rolesResourcesService.save(rolesResources);
			
			
			Roles rolesAnonumous = new Roles();
			rolesAnonumous.setName("ROLE_ANONYMOUS");
			rolesAnonumous.setAlias("游客");
			rolesService.save(rolesAnonumous);
		}
	}

}
