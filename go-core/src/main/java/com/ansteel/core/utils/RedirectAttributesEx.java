package com.ansteel.core.utils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：跳转数据传递工具类。  
 */
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RedirectAttributesEx {

	private HttpServletRequest request;

	private Map<String, Object> attribute = new HashMap<String, Object>();

	private String PREFIX = "REDIRECT_SESSION_";

	public RedirectAttributesEx(HttpServletRequest request) {
		this.request = request;
	}

	public void setAttribute(String key, Object value) {
		request.getSession().setAttribute(PREFIX + key, value);
	}

	public Object getAttribute(String key) {
		key = PREFIX + key;
		if (attribute.containsKey(key)) {
			return attribute.get(key);
		} else {
			Object value = null;
			Enumeration<String> e = request.getSession().getAttributeNames();
			while (e.hasMoreElements()) {
				if (e.nextElement().equals(key)) {
					value = request.getSession().getAttribute(key);
					request.getSession().removeAttribute(key);
					attribute.put(key, value);
				}
			}
			return value;
		}
	}

	public Map<String, Object> getAttributes() {
		Object value = null;
		Enumeration<String> e = request.getSession().getAttributeNames();
		while (e.hasMoreElements()) {
			String key = e.nextElement();
			if (key.contains(PREFIX)) {
				value = request.getSession().getAttribute(key);
				request.getSession().removeAttribute(key);
				attribute.put(key.replaceFirst(PREFIX, ""), value);
			}
		}
		return attribute;
	}

}
