package com.ansteel.core.exception;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：页面异常。  
 */
public class PageException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7068276706212560050L;
	
	public PageException(String message){
		super(message);
	}

	public PageException(String message,String type){
		super(message,type);
	}
}
