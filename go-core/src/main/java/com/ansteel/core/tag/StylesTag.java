package com.ansteel.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class StylesTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6012385708226095164L;
	
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		try {
			out.append(Resource.STYLE_PLACEHOLDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
