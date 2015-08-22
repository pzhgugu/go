package com.ansteel.core.context;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ansteel.common.backup.core.IAutoPublish;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：web容器监听。 
 */
public class StartContextListener implements ServletContextListener {
	
	private static final Logger logger=Logger.getLogger(StartContextListener.class);
			
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	logger.info("到了contextInitialized！");
    	/*ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext());
    	new ContextHolder(applicationContext);
    	IAutoPublish autoPublish = ContextHolder.getSpringBean("autoPublish");
    	autoPublish.publish();
    	InitService initService = ContextHolder.getSpringBean("initServiceBean");
    	initService.run();*/
    	
    	
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	logger.info("到了contextDestroyed！");
    }
}
