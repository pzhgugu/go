package com.ansteel.core.utils;


import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ansteel.core.context.ContextHolder;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath*:/applicationContext.xml",
		"classpath*:/spring-mvc.xml","classpath*:/spring-sec.xml"  })
public class SpringBaseTest extends AbstractTransactionalJUnit4SpringContextTests {


	@Before
	public void setup() {
		new ContextHolder(applicationContext);
	}


	@Test
	public void testApplicationContext() {
		assertNotNull(applicationContext);
	}
}
