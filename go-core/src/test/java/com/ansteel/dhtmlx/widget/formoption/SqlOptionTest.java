package com.ansteel.dhtmlx.widget.formoption;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import javax.servlet.http.HttpServletRequest;


import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.ansteel.core.utils.SpringDbunitTest;
import com.ansteel.dhtmlx.widget.form.Options;

public class SqlOptionTest {


	@Test
	@Ignore
	public void testSqlRun() {
		SqlOption sqlOption = new SqlOption(null);
		String sql = "select name,alias from go_users";
		List<Options> value = sqlOption.get(sql);
		assertNotNull(value);
	}
	
	@Test
	@Ignore
	public void testGet() {	
		Map<String, Object> attributes = new LinkedHashMap<String, Object>();
		Enumeration<String>  e=Collections.enumeration(new LinkedHashSet<String>(attributes.keySet()));		
		
		
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		//EasyMock.expect(request.getParameter("username")).andReturn("administrator");
		Map map= new HashMap<String, Object>();
		map.put("username", "administrator");
		attributes.put("S_URL", "/show/");
		//这样的用法只能使用与mock对象的有返回值的方法，如果mock对象的方法是void，则需要使用expectLastCall()：
		EasyMock.expect(request.getAttributeNames()).andReturn(e);
		EasyMock.expect(request.getParameterMap()).andReturn(map);
		EasyMock.replay(request); 
		SqlOption sqlOption = new SqlOption(request);
		String sql = "select name,alias from go_users where name='${username}'";		
		List<Options> value = sqlOption.get(sql);
		assertNotNull(value);
		//进入verify阶段  
        EasyMock.verify(request); 
	}

}
