package com.ansteel.common.springsecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.security.domain.Permission;
import com.ansteel.common.security.domain.PermissionURL;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.service.RolesService;
import com.ansteel.common.springsecurity.tool.AntUrlPathMatcher;
import com.ansteel.common.springsecurity.tool.UrlMatcher;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：认证判断接口实现类。  
 */
@Service
@Transactional(readOnly=true)
public class SecurityMetadata implements ISecurityMetadata {
	
	@Autowired
	RolesService rolesService;

	public Map<String, Collection<ConfigAttribute>> loadResourceDefine() {
		Map<String, Collection<ConfigAttribute>> resourceMap = new HashMap<String, Collection<ConfigAttribute>>();

		// 查询所有角色
		Collection<Roles> roles = rolesService.findAll();
		for (Roles r : roles) {
			// 角色名:ROLE_XXXX--value
			Collection<RolesResources> rolesResources = r.getRolesResources();
			for (RolesResources rr : rolesResources) {
				Resources res = rr.getResources();
				//rr.getPermission()等于空，说明这个资源没有授权
				if (res.getType().equals(Resources.URL)&&rr.getPermission()!=null) {
					Collection<PermissionURL> permissionUrls = res
							.getPermissionUrls();
					for (PermissionURL pUrl : permissionUrls) {
						String url = pUrl.getPath();
						CustomSecurityConfig ca = new CustomSecurityConfig(r.getName());
						// 加入认证
						ca.setPermission(rr.getPermission());
						// 加入资源名称，用于页面授权
						ca.setResName(res.getName());
						// 加入url认证位
						ca.setUrlPermissionBit(pUrl.getPermissionBit());
						Collection<ConfigAttribute> atts = null;
						if (resourceMap.containsKey(url)) {
							// 获得原来的集合
							atts = resourceMap.get(url);
						} else {
							// 首次添加
							atts = new ArrayList<ConfigAttribute>();
						}
						atts.add(ca);
						resourceMap.put(url, atts);
					}

				}
			}
		}
		return resourceMap;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) {
		String url = ((FilterInvocation) object).getRequestUrl();
		return this.getAttributes(url);
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(String url) {
		UrlMatcher urlMatcher = new AntUrlPathMatcher();
		Map<String, Collection<ConfigAttribute>> rMap = CustomFilterInvocationSecurityMetadataSource.resourceMap;

		Collection<ConfigAttribute> configAttributeList = new ArrayList<ConfigAttribute>();
		Iterator<String> ite = rMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			// 当前路径匹配资源集合
			if (urlMatcher.pathMatchesUrl(resURL, url)) {
				configAttributeList.addAll(rMap.get(resURL));
			}
		}
		return configAttributeList;
	}

	
	

}
