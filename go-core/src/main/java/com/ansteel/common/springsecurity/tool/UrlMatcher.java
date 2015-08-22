package com.ansteel.common.springsecurity.tool;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：url匹配接口。  
 */
public interface UrlMatcher {
	Object compile(String paramString);
	/**
	 * url路径匹配
	 * @param paramObject
	 * @param paramString
	 * @return
	 */
	boolean pathMatchesUrl(Object paramObject, String paramString);

	String getUniversalMatchPattern();

	boolean requiresLowerCaseUrl();

}
