package com.ansteel.core.exception;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：管理界面异常。  
 */
public class AdminException extends BaseException {

	public AdminException(String message) {
		super(message);
	}

	public AdminException(String message,String type) {
		super(message,type);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -6297475886430775279L;

}
