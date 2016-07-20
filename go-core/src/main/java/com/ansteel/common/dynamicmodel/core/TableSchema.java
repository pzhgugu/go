package com.ansteel.common.dynamicmodel.core;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.beetl.service.BeetlService;
import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.core.constant.Public;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.StringUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模核心类实现。  
 */
@Service
@Transactional
public class TableSchema implements ITableSchema {
	
	private static final Logger logger=Logger.getLogger(TableSchema.class);

	private static final String HBM = "DynamicModel.xml";
	
	@Autowired
	BeetlService beetlService;
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void updateTable(String name, Collection<DynamicFields> fields) {
		
		String xml=this.getXML(name, fields);
		Assert.hasText(xml, "解析hbm文件有误,请检查！");
		logger.info(xml);
		schemaExport(xml);
	}
	
	public void schemaExport(String xml) {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) em.getSessionFactory();
		Properties extraProp = sessionFactory.getProperties();
		ServiceRegistry serviceRegistry=sessionFactory.getServiceRegistry();
		Configuration config = new Configuration();
		extraProp.put("hibernate.hbm2ddl.auto", "update");
		config.addProperties(extraProp);
		config.addXML(xml);
		SchemaUpdate schemaUpdate = new SchemaUpdate(serviceRegistry,config);
		schemaUpdate.execute(true, true);
	}

	private String getXML(String name, Collection<DynamicFields> fields) {
		name = this.getTableName(name);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("TableName", name);
		map.put("Fields", fields);
		String hbm = FileUtils.readClassLoaderFile(HBM,this.getClass());		
		return beetlService.outContent(hbm, map);
	}

	@Override
	public String getTableName(String name) {
		name = name.toUpperCase();
		if(name.indexOf(Public.TABLE_PREFIX)!=0){
			return Public.TABLE_PREFIX+name;
		}else{
			return name;
		}
	}

	@Override
	public void save(String name, Collection<DynamicFields> fields,
			Map<String, Object> valueMap) {
		String xml=this.getXML(name, fields);
		name = this.getTableName(name);
		SessionFactory  factory =this.getSessionFactory(xml);
		Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        String id="";
        if(valueMap.containsKey("id")){
        	id=(String) valueMap.get("id");
        }
        String userName="";
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();
			userName=userDetails.getUsername();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        valueMap.put("LASTUPDATER", userName);
        valueMap.put("UPDATED", new Date());
        if(StringUtils.hasText(id)){
        	Map data = (Map) session.load(name, id);
        	data.putAll(valueMap);
        	try {
        		session.update(name,data);
			} catch (Exception e) {
				throw new PageException(id+"不存在，请检查！"+e.getMessage());
			}	        	
        }else{
        	valueMap.put("CREATOR", userName);
            valueMap.put("CREATED", new Date());
        	session.save(name, valueMap);
        }        
        tx.commit();
        session.close();
	}

	private SessionFactory getSessionFactory(String xml) {
		HibernateEntityManagerFactory em=(HibernateEntityManagerFactory) entityManagerFactory;
		SessionFactoryImplementor sessionFactory = (SessionFactoryImplementor) em.getSessionFactory();
		Properties extraProp = sessionFactory.getProperties();
		ServiceRegistry serviceRegistry=sessionFactory.getServiceRegistry();
		Configuration config = new Configuration();
		config.addProperties(extraProp);
		config.addXML(xml);
		return config.buildSessionFactory(serviceRegistry);
	}

	@Override
	public void delect(String name, Collection<DynamicFields> fields, String id) {
		String xml=this.getXML(name, fields);
		name = this.getTableName(name);
		SessionFactory  factory =this.getSessionFactory(xml);
		Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        Map data = (Map) session.load(name, id);  
        try {
        	session.delete(name, data);
			tx.commit();
		} catch (Exception e) {
			throw new PageException(id+"不存在，请检查！"+e.getMessage());
		}finally {
			session.close();
		}
	}

	@Override
	public int clean(String name, Collection<DynamicFields> fields){
		int result = 0 ;
		String xml=this.getXML(name, fields);
		name = this.getTableName(name);
		SessionFactory  factory =this.getSessionFactory(xml);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			StringBuffer cleanSql = new StringBuffer("delete from ");
			cleanSql.append(name);
			result=session.createSQLQuery(cleanSql.toString()).executeUpdate();
			tx.commit();
		} catch (Exception e) {
			throw new PageException("清除表失败："+e.getMessage());
		}finally {
			session.close();
		}
		return result;
	}

}
