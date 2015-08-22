package com.ansteel.spring.test;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.hamcrest.Matchers.*;

import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.context.PropertiesConfigurationLoader;
import com.ansteel.core.utils.SpringBaseTest;

import org.hibernate.validator.*;
import org.hibernate.validator.method.MethodValidator;

public class RequestContextTest extends SpringBaseTest {

	/**
	 * 国际化信息代码提取
	 */
	@Test
	public void messageTest() {
		String message=ContextHolder.getSpringBeanFactory().getMessage("exception.Dao", null,
				Locale.CHINESE);
		assertThat(message, is("数据库异常："));
	}
	/**
	 * 配置信息代码提取
	 */
	@Test
	public void propertiesConfigurationLoaderTest() {
		Properties pro = PropertiesConfigurationLoader.getGoProperties();
		String debug = (String)pro.get("go_pro.debug");
		assertThat(debug, is("1"));
	}
	
	@Value("${go_pro.debug}")
	private String debug;
	

	/**
	 * 配置信息注解提取
	 */
	@Test
	public void propertiesTest(){
		assertThat(debug, is("1"));
	}
	@Test
	public void validatorTest(){
		 /* 创建效验工厂 */  
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();   
        Validator validator = validatorFactory.getValidator();   
  
        /* 信息封装 */  
        EntityFieldsGrid infoBean = new EntityFieldsGrid();  
          
        /* 将类型装载效验 */  
        Set<ConstraintViolation<EntityFieldsGrid>> set = validator.validate(infoBean);   
        for (ConstraintViolation<EntityFieldsGrid> constraintViolation : set)   {
            assertThat(constraintViolation.getMessage(), is("不能为空"));
        }
	}
}
