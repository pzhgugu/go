package com.ansteel.modules.model.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.common.model.domain.Models;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;

public class ModelsTest extends SpringBaseTest {


	@Autowired
	BaseService baseService;

	
	@Test
	@Rollback(false)
	public void test() {
		Models models=new Models();
		models.setName("1");
		models.setAlias("1");
		models.setLayer(1);
		baseService.save(models);
	}

}
