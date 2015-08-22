package com.ansteel.common.springsecurity.tool;

import org.beetl.core.Tag;
import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板语法基类。  
 */
public class SpringSecurityTag extends Tag {

    private static Map<String,SecureTag>  secMap = null;

    @Override
    public void render() {
        String tagName = (String) this.args[0];
        Map argsMap = (Map) args[1];

        String attr = "";

        if (argsMap.containsKey("attr")) {
            attr = (String) argsMap.get("attr");
        }



        if (StringUtils.hasText(attr)) {
            attr=attr.trim();
            SecureTag secureTag = this.getSecureTag(attr);
            if(secureTag!=null){
                secureTag.setArgs(this.args);
                secureTag.setBw(this.bw);
                secureTag.setCtx(this.ctx);
                secureTag.setGt(this.gt);
                try {
                    int iValue=secureTag.render();
                    if(iValue==1){
                        doBodyRender();
                    }
                } catch (JspException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SecureTag getSecureTag(String attr){
        if(secMap==null){
        	secMap = new HashMap<>();
        	secMap.put("principal",new PrincipalTag());
        	secMap.put("guest",new GuestTag());
        	secMap.put("user",new UserTag());
        	secMap.put("hasRole",new HasRoleTag());
            /*shiroMap.put("authenticated",new AuthenticatedTag());
            shiroMap.put("notAuthenticated",new NotAuthenticatedTag());
            shiroMap.put("lacksRole",new LacksRoleTag());
            shiroMap.put("hasAnyRole",new HasAnyRolesTag());
            shiroMap.put("hasPermission",new HasPermissionTag());
            shiroMap.put("lacksPermission",new LacksPermissionTag());*/
        }
        if(secMap.containsKey(attr)){
            return secMap.get(attr);
        }
        return null;
    }
}
