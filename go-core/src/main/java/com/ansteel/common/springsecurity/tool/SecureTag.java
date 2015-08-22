package com.ansteel.common.springsecurity.tool;


import org.beetl.core.GroupTemplate;
import org.beetl.core.Context;
import org.beetl.core.ByteWriter;
import org.beetl.core.statement.Statement;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ansteel.common.springsecurity.service.SecurityUtils;

import javax.servlet.jsp.JspException;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板语法父类。  
 */
public abstract class SecureTag {
    /**
     * 传入标签的参数
     */
    protected Object[] args;
    /**
     * tempalte.render() 返回渲染结果，如本例所示
     * template.renderTo(Writer) 渲染结果输出到Writer里
     * template.renderTo(OutputStream ) 渲染结果输出到OutputStream里
     */
    protected GroupTemplate gt;
    /**
     * 包含了模板的上下文，主要提供了如下属性
     * byteWriter 输出流
     * template 模板本身
     * gt GroupTemplate
     * globalVar 该模板对应的全局变量
     * byteOutputMode 模板的输出模式，是字节还是字符
     * safeOutput 模板当前是否处于安全输出模式
     */
    protected Context ctx;
    /**
     * 当前的输出流
     */
    protected ByteWriter bw;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public GroupTemplate getGt() {
        return gt;
    }

    public void setGt(GroupTemplate gt) {
        this.gt = gt;
    }

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public ByteWriter getBw() {
        return bw;
    }

    public void setBw(ByteWriter bw) {
        this.bw = bw;
    }


    protected Authentication getAuthentication()
    {
    	if ((SecurityContextHolder.getContext() == null)
                || !(SecurityContextHolder.getContext() instanceof SecurityContext)
                || (SecurityContextHolder.getContext().getAuthentication() == null)) {
            return null;
        }
    	return SecurityContextHolder.getContext().getAuthentication();

    }

    public abstract int render() throws JspException;
}
