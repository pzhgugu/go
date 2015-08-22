package com.ansteel.common.menu.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.beetl.service.BeetlService;
import com.ansteel.common.menu.domain.Menu;
import com.ansteel.common.menu.repository.MenuRepository;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.springsecurity.service.SecurityUtils;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.query.QuerySettings;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.security.service.SecurityService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：菜单服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class MenuServiceBean implements MenuService {
	
	@Autowired
	BeetlService beetlService;
	
	@Autowired
	SecurityService securityService;
	
	@Autowired
	MenuRepository menuRepository;

	@Override
	public List<Menu> getMenu(HttpServletRequest request) {
		List<Menu> mList = new ArrayList<Menu>();
		Collection<Menu> menus = menuRepository.findAllOrderByParentDisplayOrder();
		for(Menu m:menus){
			if(checkMenu(m)){
				m.setUrl(beetlService.outContent(m.getUrl(), request));
				mList.add(m);
			}
		}
		return mList;
	}
	
	/**
	 * 递归菜单树
	 * @return
	 */
	public List<Menu> getRecursionMenu() {
		List<Menu> mList = new ArrayList<Menu>();
		Collection<Menu> menus = menuRepository.findByParentIsNull();
		for(Menu m:menus){
			this.checkChildrenMenu(m);
			if(m!=null){
				mList.add(m);
			}
		}
		return mList;
	}

	private void checkChildrenMenu(Menu m) {
		Collection<Menu> cs = m.getChildren();
		if(cs.size()>0){
			for(Menu child:cs){
				if(checkMenu(child)){
					checkChildrenMenu(child);
				}else{
					cs.remove(child);
				}
			}
		}
		if(!checkMenu(m)){
			m=null;
		}
	}

	private boolean checkMenu(Menu m) {
		if(m.getIsStop()!=null&&m.getIsStop()==1){
			return false;
		}
		if(!this.checkSecurity(m)){
			return false;
		}
		return true;
		
	}

	private boolean checkSecurity(Menu m) {
		String resourcesName = m.getResourcesName();
		if(StringUtils.hasText(resourcesName)){
			resourcesName=resourcesName.trim();
			String[] rArray = resourcesName.split(";");
			for(String r:rArray){
				Resources resources=securityService.getResources(r);
				if(checkResources(resources)){
					return true;
				}
			}
			return false;
		}
		return true;
	}
	
	private boolean checkResources(Resources resources){
		if(resources!=null){
			Collection<RolesResources> rrList=resources.getRolesResources();
			//游客anonymousUser
			UserDetails userDetail = SecurityUtils.getUserDetail();
			if(userDetail==null){
				for(RolesResources rr:rrList){
					Roles role=rr.getRoles();
					if(role.getName().equals("anonymousUser")){
						return true;
					}
				}
			}else{
				return checkPermission(rrList);
			}
		}
		return false;		
	}
	
	private boolean checkPermission(Collection<RolesResources> rrList){
		Collection<? extends GrantedAuthority> auths = SecurityUtils.getUserDetail().getAuthorities();
		Object[] ats = auths.toArray();
		
		for(RolesResources rr:rrList){
			Integer permission = rr.getPermission();
			if(permission!=null){
				Roles role=rr.getRoles();
				for(int i=0; i<ats.length; i++){
				   GrantedAuthority o = (GrantedAuthority)ats[i];
				   if(role.getName().equals(o.getAuthority())){
					   return true;
				   }
				}
			}
		}
		return false;
	}

}
