package com.ansteel.core.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class ScriptTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2298651716053938808L;
	
	public int doStartTag(){
		return EVAL_BODY_BUFFERED;
	}

	public int doEndTag(){
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Resource resource = (Resource) request.getAttribute(Resource.CONTEXT_ATTR_NAME);
		BodyContent body = this.getBodyContent();
		String code = body.getString();
		resource.addScriptPool(code);
		return EVAL_PAGE;
	}
}
