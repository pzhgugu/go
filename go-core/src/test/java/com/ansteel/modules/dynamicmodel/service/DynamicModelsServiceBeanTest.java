package com.ansteel.modules.dynamicmodel.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Type;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.hbm2ddl.Target;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.ansteel.common.dynamicmodel.service.DynamicModelsService;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.utils.SpringBaseTest;

public class DynamicModelsServiceBeanTest extends SpringBaseTest {
	
	@Autowired
	DynamicModelsService dynamicModelsService;
	
	@Test
	@Ignore
	public void createTableTest(){
		dynamicModelsService.createTable("402897814cda6569014cda7411280003");
	}
	
	@Test
	public void saveTest(){
		Map<String,Object> valueMap = new HashMap<>();
		valueMap.put("GO_NAME", "gugu");
		//dynamicModelsService.save("testTel",valueMap);
	}

	@Test
	public void updateTest(){
		Map<String,Object> valueMap = new HashMap<>();
		valueMap.put("GO_NAME", "gugu1");
		valueMap.put("ID", "402897814cdf977b014cdf97930c0000");
		//dynamicModelsService.save("testTel",valueMap);
	}

	@Test
	@Ignore
	public void delectTest(){
		dynamicModelsService.delect("testTel","402897814cdf8233014cdf824ce90000");
	}
	
	
	@Test
	public void loadAllTest(){
		/*Result result=dynamicModelsService.loadAll("testTel","0","100",null);
		System.out.println(result);*/
	}
}
