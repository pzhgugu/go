package com.ansteel.spring.test;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.utils.SpringControllerTest;
import com.ansteel.common.admin.web.AdminController;

public class AdminControllerTest extends SpringControllerTest{
	
	@Autowired
	private AdminController adminController;
	

	@Override
	public BaseController getBaseController() {
		return adminController;
	}  
    
    @Test  
    public void testFindPageUsers() throws Exception {  
        ResultActions ra = this.mockMvc.perform(MockMvcRequestBuilders  
                .get("/admin/index"));  
        MvcResult mr = ra.andReturn();  
        String result = mr.getModelAndView().getViewName();  
        System.out.println(result); 
    }

  
}
