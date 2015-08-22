package com.ansteel.core.utils;

import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ansteel.core.controller.BaseController;

public abstract class SpringControllerTest extends SpringBaseTest{
	
	protected MockMvc mockMvc;  
	  
    @Before  
    public void setup() {  
        mockMvc = MockMvcBuilders.standaloneSetup(getBaseController()).build();         
    }  
    
    public abstract BaseController getBaseController();

}
