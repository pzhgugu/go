package com.ansteel.common.springsecurity.tool;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：url过滤类。  
 */
public class AntUrlPathMatcher implements UrlMatcher  {

	private boolean requiresLowerCaseUrl;
	private PathMatcher pathMatcher;

	public AntUrlPathMatcher() {
		this(true);
	}

	public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = true;
		this.pathMatcher = new AntPathMatcher();

		this.requiresLowerCaseUrl = requiresLowerCaseUrl;
	}

	@Override
	public Object compile(String paramString) {
		if (this.requiresLowerCaseUrl) {
			return paramString.toLowerCase();
		}
		return paramString;
	}

	public boolean isRequiresLowerCaseUrl() {
		return requiresLowerCaseUrl;
	}

	public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = requiresLowerCaseUrl;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	@Override
	public boolean pathMatchesUrl(Object path, String url) {
		if (("/**".equals(path)) || ("**".equals(path))) {
			return true;
		}
		return this.pathMatcher.match((String) path, url);

	}

	@Override
	public String getUniversalMatchPattern() {
		return "/**";
	}

	@Override
	public boolean requiresLowerCaseUrl() {
		return this.requiresLowerCaseUrl;
	}

	public String toString() {
		return super.getClass().getName() + "[requiresLowerCase='"
				+ this.requiresLowerCaseUrl + "']";
	}

}
