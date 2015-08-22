package com.ansteel.core.exception;

import java.util.Locale;

import com.ansteel.core.context.ContextHolder;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：持久层异常。  
 */
public class DaoException extends PageException{

	public DaoException(String message) {
		super(ContextHolder.getSpringBeanFactory().getMessage("Exception.Dao",null,Locale.CHINESE)+message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
