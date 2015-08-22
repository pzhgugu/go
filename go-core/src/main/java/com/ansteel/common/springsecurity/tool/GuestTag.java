package com.ansteel.common.springsecurity.tool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：游客模板语法。  
 */
public class GuestTag extends SecureTag {

    @Override
    public int render() throws JspException {
        return  onDoStartTag();
    }

    private static final Logger log = LoggerFactory.getLogger(GuestTag.class);

    public int onDoStartTag()
            throws JspException
    {
        if ((getAuthentication() == null) || (getAuthentication().getPrincipal() == null)) {
            if (log.isTraceEnabled()) {
                log.trace("Subject does not exist or does not have a known identity (aka 'principal').  Tag body will be evaluated.");
            }

            return 1;
        }
        if (log.isTraceEnabled()) {
            log.trace("Subject exists or has a known identity (aka 'principal').  Tag body will not be evaluated.");
        }

        return 0;
    }
}
