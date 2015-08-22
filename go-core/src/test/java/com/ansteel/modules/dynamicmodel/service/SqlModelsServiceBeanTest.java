package com.ansteel.modules.dynamicmodel.service;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsForm;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsQuery;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.sqlmodel.domain.SqlModels;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.RegexUtil;
import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.sqlmodel.service.SqlModelsService;

public class SqlModelsServiceBeanTest extends SpringBaseTest {
	

	@Autowired
	SqlModelsService sqlModelsService;
	
	private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

	@Test
	public void testGetFieldNames() {
		//String sql="select * from go_users";
		//Map<String,String> list=sqlModelsService.getFieldNames(sql);
		//assertThat(list.size()>0, is(true));
	}
	
	@Test
	@Transactional(readOnly=false)

	@Ignore
	//@Rollback(false)
	public void testSqlFieldInfo() {
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		Map map= new HashMap<String, Object>();
		map.put("name", "administrator");
		//这样的用法只能使用与mock对象的有返回值的方法，如果mock对象的方法是void，则需要使用expectLastCall()：
		attributes.put("S_URL", "/show/");
		
		Enumeration<String>  e=Collections.enumeration(new LinkedHashSet<String>(this.attributes.keySet()));		
		EasyMock.expect(request.getAttributeNames()).andReturn(e);
		EasyMock.expect(request.getParameterMap()).andReturn(map);
		EasyMock.expect(request.getAttribute("S_URL")).andReturn("/show/");
		EasyMock.replay(request); 
		SqlModels dynamicModels = sqlModelsService.scanSqlModels("test",request);
		assertThat(dynamicModels!=null, is(true));
		
		//进入verify阶段  
        EasyMock.verify(request); 
	}
	
	
	
}
