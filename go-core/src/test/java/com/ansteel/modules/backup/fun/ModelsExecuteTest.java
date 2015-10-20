package com.ansteel.modules.backup.fun;

import static org.junit.Assert.*;

import com.ansteel.common.viewelement.service.ViewElementService;
import com.ansteel.core.context.ContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.model.domain.Models;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;

public class ModelsExecuteTest extends SpringBaseTest {
	
	@Autowired
	BaseService baseService;

	@Test
	@Ignore
	public void testModelsSave() {
		Models models = new Models();
		models.setAlias("1");
		models.setName("1");
		baseService.save(models);
	}

}
