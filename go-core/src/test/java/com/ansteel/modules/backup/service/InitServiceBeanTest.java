package com.ansteel.modules.backup.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.common.backup.service.InitService;
import com.ansteel.core.utils.SpringBaseTest;

public class InitServiceBeanTest extends SpringBaseTest{
	
	@Autowired
	InitService initService;

	@Test
	@Rollback(false)
	public void testInitUser() {
		initService.run();
	}

}
