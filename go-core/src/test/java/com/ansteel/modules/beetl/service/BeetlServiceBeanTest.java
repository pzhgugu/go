package com.ansteel.modules.beetl.service;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.beetl.service.BeetlService;
import com.ansteel.core.utils.SpringBaseTest;

public class BeetlServiceBeanTest extends SpringBaseTest{
	
	@Autowired
	BeetlService beetlService;

	@Test
	public void testOutContent() {
		
		String sql = "select * from w=${id}";
		Map<String,Object> rMap = new HashMap<>();
		rMap.put("id", 123);
		String value=beetlService.outContent(sql, rMap);
		assertThat(value, is("select * from w=123"));
	}


	@Test
	public void testBeetlService() {
		Map<String, Class> map = beetlService.getRegisterFunction();
		assertNotNull("beetlService RegisterFunction注入函数失败！",map);
	}
}
