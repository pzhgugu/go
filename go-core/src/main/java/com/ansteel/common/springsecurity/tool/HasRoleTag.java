package com.ansteel.common.springsecurity.tool;


import java.util.Collection;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：角色控制模板语法。  
 */
public class HasRoleTag extends SecureTag {
	
	protected boolean hasRole(String name)
    {
		if(getAuthentication()!=null&&StringUtils.hasText(name)){
			String[] nameArray = name.split(",");
			Collection<? extends GrantedAuthority> gas = getAuthentication().getAuthorities();
			for(GrantedAuthority ga:gas){
				for(String n:nameArray){
					if(ga.getAuthority().equals(n)){
						return true;
					}
				}
			}
		}
        return false;
    }

	@Override
	public int render() throws JspException {
		 Map argsMap = (Map) args[1];
        if (argsMap.containsKey("name")) {
           String name =(String) argsMap.get("name");
            if(this.hasRole(name)){
                return 1;
            }
        }
        return 0;
	}

}
