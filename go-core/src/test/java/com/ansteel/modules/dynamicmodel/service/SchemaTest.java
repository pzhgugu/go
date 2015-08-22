package com.ansteel.modules.dynamicmodel.service;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.metamodel.Metamodel;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.jpa.internal.EntityManagerImpl;
import org.hibernate.jpa.spi.AbstractEntityManagerImpl;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.utils.SpringBaseTest;

public class SchemaTest  extends SpringBaseTest {
	

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;

	

	/**
	 * 更新表
	 * @throws SQLException 
	 */
	@Test
	@Ignore
	public void testSchemaUpdate() throws SQLException {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) em.getSessionFactory();
		Properties extraProp = sessionFactory.getProperties();
		ServiceRegistry serviceRegistry=sessionFactory.getServiceRegistry();
		Configuration config = new Configuration();
		extraProp.put("hibernate.hbm2ddl.auto", "update");
		config.addProperties(extraProp);
		File xmlFile = new File(
				"E:/CCRC/0333377_GoReport/vobs/2015_GoReport_vob/GoReport_src/go/go-core/src/test/resources/DynamicModelTest.hbm.xml");
		config.addFile(xmlFile);

		SchemaUpdate schemaUpdate = new SchemaUpdate(serviceRegistry,config);
		schemaUpdate.execute(true, true);
	}

	/**
	 * 创建表（删除旧表，创建新表）
	 */
	@Test
	public void testSchemaExport() {
/*
		LocalSessionFactoryBean sessionFactoryBean = ContextHolder
				.getSpringBean("&sessionFactory");
		Properties extraProp = sessionFactoryBean.getConfiguration()
				.getProperties();
		Configuration config = new Configuration();
		extraProp.put("hibernate.hbm2ddl.auto", "update");
		config.addProperties(extraProp);
		File xmlFile = new File(
				"E:/CCRC/0333377_GoReport/vobs/2015_GoReport_vob/GoReport_src/go/go-core/src/test/resources/DynamicModelTest.hbm.xml");
		config.addFile(xmlFile);

		try {
			SchemaExport schemaExport = new SchemaExport(config);
			// 设置脚本文件
			// schemaExport.setOutputFile(scriptFileName);
			schemaExport.create(true,true);

		} catch (Exception e) {
			e.printStackTrace();
		}
*/
	}
	
	@Test
	public void test(){

		Configuration config = new Configuration();
		Properties extraProp = new Properties();
		Map<String, Object> map = entityManagerFactory.getProperties();
		System.out.println(map.get("hibernate.hbm2ddl.auto"));
		extraProp.putAll(map);
	}

}
