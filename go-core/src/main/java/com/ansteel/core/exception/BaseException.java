package com.ansteel.core.exception;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：基础异常。  
 */
public class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1814525679916270394L;
	
	protected BaseException(String message){
		super(message);
	}
	
	protected BaseException(String message,String type){
		super(type+message);
	}

}
