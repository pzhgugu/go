package com.ansteel.common.springsecurity.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：自定义记住密码服务。  
 */
public class CustomTokenBasedRememberMeServices extends TokenBasedRememberMeServices{
	

    public static final int DEFAULT_TOKEN_LENGTH = 16;
    
    private int tokenLength = DEFAULT_TOKEN_LENGTH;
    
    private SecureRandom random;
    
    @Deprecated
    public CustomTokenBasedRememberMeServices() {
        random = new SecureRandom();
    }

	
	@Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request,
            HttpServletResponse response) {

		List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		auths.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
    	UserInfo principal = new UserInfo("gugu","1234567", true, true, true, true, auths);

        return principal;
    }
	
	public void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[] {token.getSeries(), token.getTokenValue()}, getTokenValiditySeconds(), request, response);
    }
	
	 public String generateTokenData() {
	        byte[] newToken = new byte[tokenLength];
	        random.nextBytes(newToken);
	        return new String(Base64.encode(newToken));
	    }
	 

}
