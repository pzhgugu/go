package com.ansteel.core.tag;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.ansteel.core.utils.StringUtils;

public class RequireOutTag extends BodyTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2298651716053938808L;
	
	private String id;
	
	private String iid;
	
	public int doStartTag(){
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Resource resource = (Resource) request.getAttribute(Resource.CONTEXT_ATTR_NAME);
		try {
			Map<String, Object> info = resource.getInfo(id);
			StringBuffer buffer = new StringBuffer();
			
			String type = (String) info.get("type");
			String uri = (String) info.get("uri");
			
			if(type.equals("js")){
                buffer.append("<script type=\"text/javascript\"");
                if(StringUtils.hasText(iid)){
                	buffer.append(" id='").append(iid).append("'");
                }
                buffer.append(" src=\"").append(uri).append("\"></script>\n");

            } else if(type.equals("css")){
                buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"").append(uri).append("\"/>\n");
            }
			JspWriter out = pageContext.getOut();
			out.write(buffer.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	

	public String getIid() {
		return iid;
	}


	public void setIid(String iid) {
		this.iid = iid;
	}


	public void setId(String id) {
		this.id = id;
	}

}
