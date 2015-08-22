package com.ansteel.core.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ansteel.common.backup.core.IAutoPublish;
import com.ansteel.common.backup.service.InitService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：系统启动拦截器，用于系统启动时的拦截器，只会在系统启动时运行一次。 
 */
public class InstantiationTracingBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	IAutoPublish autoPublish;
	
	@Autowired
	InitService initService; 
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext applicationContext = event.getApplicationContext();
		if(applicationContext.getParent() == null){			
	    	new ContextHolder(applicationContext);
	    	autoPublish.publish();
	    	initService.run();
	     }
	}

}
