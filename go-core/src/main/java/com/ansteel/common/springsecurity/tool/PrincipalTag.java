package com.ansteel.common.springsecurity.tool;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户名模板语法。  
 */
public class PrincipalTag extends SecureTag{
    private static final Logger log = LoggerFactory.getLogger(PrincipalTag.class);

    private String property;


    public String getProperty()
    {
        return this.property;
    }

    public void setProperty(String property)
    {
        this.property = property;
    }

    public int onDoStartTag() throws JspException
    {
       String strValue = null;

        if (getAuthentication() != null)
        {
            Object principal = getAuthentication().getPrincipal();

            if (principal != null) {
                if (this.property == null)
                    strValue = principal.toString();
                else {
                    strValue = getPrincipalProperty(principal, this.property);
                }

            }

        }

        if (strValue != null)
            try {
                this.bw.writeString(strValue);
            } catch (IOException e) {
                throw new JspTagException("Error writing [" + strValue + "] to JSP.", e);
            }
        return 0;
    }


    private String getPrincipalProperty(Object principal, String property) throws JspTagException
    {
        String strValue = null;
        try
        {
            BeanInfo bi = Introspector.getBeanInfo(principal.getClass());

            boolean foundProperty = false;
            PropertyDescriptor[] arr$ = bi.getPropertyDescriptors(); int len$ = arr$.length; for (int i$ = 0; i$ < len$; ++i$) { PropertyDescriptor pd = arr$[i$];
            if (pd.getName().equals(property)) {
                Object value = pd.getReadMethod().invoke(principal, (Object[])null);
                strValue = String.valueOf(value);
                foundProperty = true;
                break;
            }
        }

            if (!(foundProperty)) {
                String message = "Property [" + property + "] not found in principal of type [" + principal.getClass().getName() + "]";
                if (log.isErrorEnabled())
                    log.error(message);

                throw new JspTagException(message);
            }
        }
        catch (Exception e) {
            String message = "Error reading property [" + property + "] from principal of type [" + principal.getClass().getName() + "]";
            if (log.isErrorEnabled())
                log.error(message, e);

            throw new JspTagException(message, e);
        }

        return strValue;
    }

    @Override
    public int render() {
        this.setProperty(null);
        Map argsMap = (Map) args[1];
        if (argsMap.containsKey("property")) {
            this.setProperty((String) argsMap.get("property"));
        }
        try {
            this.onDoStartTag();
        } catch (JspException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
