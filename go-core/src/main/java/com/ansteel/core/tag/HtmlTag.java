package com.ansteel.core.tag;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class HtmlTag extends BodyTagSupport {
	
	private String mapDir = "/";
	private Resource resource;
	private String ngApp="";

	/**
	 * 
	 */
	private static final long serialVersionUID = -612383582047754887L;
	
	public int doStartTag() throws JspException{
		JspWriter out = pageContext.getOut();
		try {
			if (ngApp!=null&&!ngApp.equals("")){
				out.append("<html ng-app='" +ngApp+"'>" );
			}else {
				out.append("<html>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String path = request.getSession().getServletContext().getRealPath(mapDir);
		resource = new Resource(path);
		request.setAttribute(Resource.CONTEXT_ATTR_NAME, resource);
		return EVAL_BODY_BUFFERED;
	}
	
	public int doEndTag(){
		BodyContent body = this.getBodyContent();
		String html = body.getString() + "</html>";
		html = resource.replace(html);
		JspWriter out = pageContext.getOut();
		try {
			out.write(html);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public String getMapDir() {
		return mapDir;
	}

	public void setMapDir(String mapDir) {
		this.mapDir = mapDir;
	}

	public String getNgApp() {
		return ngApp;
	}

	public void setNgApp(String ngApp) {
		this.ngApp = ngApp;
	}
}