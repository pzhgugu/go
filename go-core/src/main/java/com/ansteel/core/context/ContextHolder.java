package com.ansteel.core.context;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 创 建 人：gugu
 * 创建日期：2015-03-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：系统上下文，用于获取系统spring上下文。 
 */
public class ContextHolder implements ApplicationContextAware{
	
	
	private ApplicationContext applicationContext = null;
	private static ContextHolder INSTANCE = null;
	
	private static final Logger logger=Logger.getLogger(ContextHolder.class);

	
	ContextHolder() {
		if(INSTANCE==null){
			INSTANCE = this;
		}
		logger.info("到了ContextHolder。");
	}
	
	public ContextHolder(ApplicationContext context) {
		if(INSTANCE==null){
			INSTANCE = this;
		}
		this.setApplicationContext(context);
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		if(INSTANCE!=null&&INSTANCE.applicationContext==null){
			INSTANCE.applicationContext =  context;
		}
	}

	
	/**
	 * 取得WebApplicationContext对象.
	 * <p>
	 * 根据web.xml中对的配置顺序,在Spring启动完成后可用.
	 * </p>
	 * 
	 * @return
	 */
	public static ApplicationContext getSpringBeanFactory() {
		return INSTANCE.applicationContext;
	}
	
	/**
	 * 根据给出的beanId来获取在Spring当中配置的bean
	 * 
	 * @param beanId
	 *            给出的beanId
	 * @return 返回找到的bean对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getSpringBean(String beanId) {
		return (T) getSpringBeanFactory().getBean(beanId);
	}
	
}
