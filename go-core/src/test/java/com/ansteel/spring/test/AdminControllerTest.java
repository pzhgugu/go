package com.ansteel.spring.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.utils.SpringControllerTest;

public class AdminControllerTest extends SpringControllerTest{
    @Override
    public BaseController getBaseController() {
        return null;
    }
	
	/*@Autowired
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
*/
  
}
