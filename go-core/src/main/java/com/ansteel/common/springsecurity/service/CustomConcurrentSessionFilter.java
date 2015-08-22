package com.ansteel.common.springsecurity.service;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.ansteel.core.context.ContextHolder;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：自定义session拦截器，用于控制单点登录，url跳转。  
 */
@SuppressWarnings("deprecation")
public class CustomConcurrentSessionFilter extends GenericFilterBean {

	// ~ Instance fields
	// ================================================================================================

	private SessionRegistry sessionRegistry;
	private String expiredUrl;
	private LogoutHandler[] handlers = new LogoutHandler[] { new SecurityContextLogoutHandler() };
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	public SingleSignOn getSingleSignOn(){
		return ContextHolder.getSpringBean("singleSignOnServic");
	}

	// ~ Methods
	// ========================================================================================================

	/**
	 * @deprecated Use constructor which injects the <tt>SessionRegistry</tt>.
	 */
	public CustomConcurrentSessionFilter() {
	}

	public CustomConcurrentSessionFilter(SessionRegistry sessionRegistry) {
		this(sessionRegistry, null);
	}

	public CustomConcurrentSessionFilter(SessionRegistry sessionRegistry,
			String expiredUrl) {
		this.sessionRegistry = sessionRegistry;
		this.expiredUrl = expiredUrl;
	}

	@Override
	public void afterPropertiesSet() {
		Assert.notNull(sessionRegistry, "SessionRegistry required");
		Assert.isTrue(
				expiredUrl == null || UrlUtils.isValidRedirectUrl(expiredUrl),
				expiredUrl + " isn't a valid redirect URL");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		HttpSession session = request.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry
					.getSessionInformation(session.getId());

			if (info != null) {
				if (info.isExpired()) {
					// Expired - abort processing
					doLogout(request, response);

					String targetUrl = determineExpiredUrl(request, info);

					if (targetUrl != null) {
						redirectStrategy.sendRedirect(request, response,
								targetUrl);

						return;
					} else {
						response.getWriter()
								.print("This session has been expired (possibly due to multiple concurrent "
										+ "logins being attempted as the same user).");
						response.flushBuffer();
					}

					return;
				} else {
					// Non-expired - update last request date/time
					sessionRegistry.refreshLastRequest(info.getSessionId());
				}
			} else {
				this.runSingleSignOn(request, response);
			}
		} else {
			this.runSingleSignOn(request, response);
		}

		chain.doFilter(request, response);
	}
	
	public void runSingleSignOn(HttpServletRequest request,HttpServletResponse response){
		SingleSignOn singleSignOn=getSingleSignOn();
		if(singleSignOn!=null){
			singleSignOn.on(request,response);
		}
	}

	protected String determineExpiredUrl(HttpServletRequest request,
			SessionInformation info) {
		return expiredUrl;
	}

	private void doLogout(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		for (LogoutHandler handler : handlers) {
			handler.logout(request, response, auth);
		}
	}

	/**
	 * @deprecated use constructor injection instead
	 */
	@Deprecated
	public void setExpiredUrl(String expiredUrl) {
		this.expiredUrl = expiredUrl;
	}

	/**
	 * @deprecated use constructor injection instead
	 */
	@Deprecated
	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}

	public void setLogoutHandlers(LogoutHandler[] handlers) {
		Assert.notNull(handlers);
		this.handlers = handlers;
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}
}
