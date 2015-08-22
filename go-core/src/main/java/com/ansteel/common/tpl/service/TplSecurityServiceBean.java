package com.ansteel.common.tpl.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.tpl.domain.TplSecurity;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.security.service.RolesResourcesService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板安全服务实现类。
 */
@Service
@Transactional(readOnly=true)
public class TplSecurityServiceBean implements TplSecurityService {
	
	private final String DefaultSecurityName ="DefaultSecurity";
	
	private final String DefaultPermissionName ="DefaultPermission";
	
	@Autowired
	RolesResourcesService rolesResourcesService;

	@Override
	public Map<String, Object> getTplSecurityPermission(
			Collection<TplSecurity> securityList) {
		Map<String,Object> varMap = new HashMap<>();
		
		for(TplSecurity entity :securityList){
			Integer isDefault = entity.getIsDefault();
			String value = entity.getVarValue();
			if(isDefault!=null&&isDefault==1){
				varMap.put(DefaultSecurityName, value);
				Object pv = this.getPermission(value);
				if(pv!=null){
					varMap.put(DefaultPermissionName, pv);
				}
			}else{
				String name = entity.getName();
				String permission = entity.getPermissionValue();
				if(StringUtils.hasText(name)&&!name.equals(DefaultSecurityName)){
					varMap.put(name, value);
				}
				if(StringUtils.hasText(permission)&&!permission.equals(DefaultPermissionName)){
					Object pv = this.getPermission(value);
					if(pv!=null){
						varMap.put(permission, pv);
					}
				}
			}
		}
		
		return varMap;
	}

	private Object getPermission(String name) {
		 Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext()
		    .getAuthentication().getAuthorities();
		 if(authorities.size()>0){
			 StringBuffer sb =new StringBuffer();			 
			 for(GrantedAuthority ga:authorities){
				 String auth = ga.getAuthority();
				 RolesResources entity= rolesResourcesService.getRolesResources(auth,name);
				 if(entity!=null){
					 Integer p=entity.getPermission();
					 if(p!=null){
						 sb.append(p);
						 sb.append(";");
					 }
				 }
			 }
			 if(sb.length()>0){
				return sb.toString();
			 }
		 }
		return null;
	}
}
