package com.ansteel.modules.usermanage.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.dynamicmodel.repository.DynamicFieldsCategoryRepository;
import com.ansteel.common.model.repository.ModelsRepository;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.common.security.service.UserService;

public class BaseRepositoryTest extends SpringBaseTest{	
	
	//@Autowired
	//UserService userService;
	
	@Autowired
	DynamicFieldsCategoryRepository permissionRepository;
	
	@Test
	public void testUserCount(){
		logger.info(permissionRepository.count());
	}
	
}
