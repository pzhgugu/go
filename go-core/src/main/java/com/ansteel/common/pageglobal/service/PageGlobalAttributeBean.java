package com.ansteel.common.pageglobal.service;

import java.util.Collection;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.context.PropertiesConfigurationLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ansteel.common.springsecurity.service.ISecurityMetadata;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.utils.RequestUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型扫描接口实现。  
 */
@Service
public class PageGlobalAttributeBean implements PageGlobalAttribute {
	
	@Value("${go_pro.attPath}")
	private String attPath;
	
	@Value("${go_pro.attWeb}")
	private String attWeb;
	
	private static String sUrl;
	
	private static String serviceUrl;
	
	@Autowired
	ISecurityMetadata securityMetadata;

	@Override
	public void set(HttpServletRequest request) {
		if(!StringUtils.hasText(sUrl)){
			sUrl=request.getContextPath();
		}
		if(!StringUtils.hasText(serviceUrl)){
			serviceUrl=RequestUtils.getServiceURL(request);
		}
		
		//请求域名
		request.setAttribute(ViewModelConstant.S_URL_SERVICE, serviceUrl);
		//请求路径
		request.setAttribute(ViewModelConstant.S_URL, sUrl);
		//静态资源请求路径
		request.setAttribute(ViewModelConstant.S_URL_R, sUrl+"/"+Public.STATIC_RES);
		//请求参数
		request.setAttribute(ViewModelConstant.S_PARAM, RequestUtils.getRequestMap(request));
		//附件物理路径
		request.setAttribute(ViewModelConstant.S_ATT_PATH,attPath);
		//附件Web路径
		request.setAttribute(ViewModelConstant.S_ATT_WEB, serviceUrl + "/" + attWeb);
		
		/*String url = RequestUtils.getServletPath(request);
		
		Collection<ConfigAttribute> configAttributeList = securityMetadata.getAttributes(url);
		System.out.println(configAttributeList.size());*/
	}

}
