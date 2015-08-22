package com.ansteel.common.springsecurity.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;

import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.Users;
import com.ansteel.core.exception.PageException;
import com.ansteel.common.security.service.UserService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：在这个类中，你就可以从数据库中读入用户的密码，角色信息，是否锁定，账号是否过期等。  
 */
public class EntityUserDetailsService implements UserDetailsService{
	
	private static final Logger logger=Logger.getLogger(EntityUserDetailsService.class);
	/**
	 * 开启角色授权
	 */
	private boolean enableAuthorities = true;
	/**
	 * 启用用户组授权
	 */
	private boolean enableGroups;
	
	protected final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	
	@Autowired
	UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Users users = loadUsersByUsername(username);
		if (users == null) {
            logger.debug("Query returned no results for user '" + username + "'");
            throw new AccessDeniedException(messages.getMessage("用户认证", new Object[]{username}, "用户 {0} 没有发现"));
        }


        Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

        if (enableAuthorities) {
            dbAuthsSet.addAll(loadUserAuthorities(users));
        }

        if (enableGroups) {
            dbAuthsSet.addAll(loadGroupAuthorities(users));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);

        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new AccessDeniedException(
                    messages.getMessage("用户认证",
                            new Object[] {username}, "用户 {0} 没有 权限"));
        }

        return createUserDetails(users, dbAuths);
	}

	private UserDetails createUserDetails(Users user,
			List<GrantedAuthority> dbAuths) {
		UserInfo userInfo =new UserInfo(user.getName(), user.getPassword(), true, true, true, true, dbAuths);
		userInfo.setAlias(user.getAlias());
		userInfo.setLoginTime(new Date());
		userInfo.setId(user.getId());
		return userInfo;
	}



	private Collection<? extends GrantedAuthority> loadGroupAuthorities(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	private Collection<? extends GrantedAuthority> loadUserAuthorities(Users user) {
		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		Collection<Roles> roles = user.getRoles();
		for(Roles r:roles){
			auths.add(new GrantedAuthorityImpl(r.getName()));
		}
		return auths;
	}

	private Users loadUsersByUsername(String username) {	
		return userService.findOneByName(username);
	}

}
