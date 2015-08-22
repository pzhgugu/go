package com.ansteel.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class ScriptsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8981028602817984686L;
	
	public int doStartTag(){
		JspWriter out = pageContext.getOut();
		try {
			out.append(Resource.SCRIPT_PLACEHOLDER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}
