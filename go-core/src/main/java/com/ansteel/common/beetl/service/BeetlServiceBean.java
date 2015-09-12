package com.ansteel.common.beetl.service;

import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.SqlUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板语法beetl接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class BeetlServiceBean implements BeetlService {

	private static final Logger logger = Logger
			.getLogger(BeetlServiceBean.class);

	Map<String, Class> registerFunction;

	Map<String, Class> registerTag;

	public Map<String, Class> getRegisterFunction() {
		return registerFunction;
	}

	public void setRegisterFunction(Map<String, Class> registerFunction) {
		this.registerFunction = registerFunction;
	}

	public Map<String, Class> getRegisterTag() {
		return registerTag;
	}

	public void setRegisterTag(Map<String, Class> registerTag) {
		this.registerTag = registerTag;
	}

	/**
	 * 防止SQL注入
	 * @param request
	 * @return
	 */
	private Map<String, Object> getRequestMap(HttpServletRequest request) {
		Map<String, Object> rMap = RequestUtils.getRequestMap(request);
		for (Entry<String, Object> entry : rMap.entrySet()) {
			rMap.put(entry.getKey(), SqlUtils.sqlValidate((String)entry.getValue()));
		}
		return rMap;
	}

	@Override
	public String outSQLQuery(String optionValue, HttpServletRequest request) {
		return this.outContent(optionValue,request);
	}
	
	

	@Override
	public String outContent(String content, Map<String, Object> rMap) {

		if (StringUtils.hasText(content)) {
			Template template = this.getTemplate(content);
			for (Entry<String,Object> entry:rMap.entrySet()) {
				template.binding(entry.getKey(), entry.getValue());
			}
			return this.getOutPut(template);
		}

		return null;
	}


	private Template getTemplate(String content) {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = null;
		try {
			cfg = Configuration.defaultConfiguration();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		GroupTemplate group = new GroupTemplate(resourceLoader, cfg);// 使用空构造函数创建一个模板

		this.registerFunction(group);

		this.registerTag(group);

		return group.getTemplate(content);
	}

	public String getOutPut(Template template) {
		String output = null;
		try {
			output = template.render();
		} catch (Exception e) {
			// TODO: handle exception
		}// 渲染输出模板引擎输出字符串
		return output;
	}

	private void registerTag(GroupTemplate group) {
		if (registerTag != null) {
			for (Entry<String, Class> tagReg : registerTag.entrySet()) {
				group.registerTag(tagReg.getKey(), tagReg.getValue());
			}
		}
	}

	private void registerFunction(GroupTemplate group) {
		if (registerFunction != null) {
			for (Entry<String, Class> funReg : registerFunction.entrySet()) {
				Class clazz = funReg.getValue();
				Object o = null;
				try {
					o = clazz.newInstance();
					group.registerFunctionPackage(funReg.getKey(), o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String outContent(String content, HttpServletRequest request) {
		Map<String, Object> rMap = this.getRequestMap(request);
		if (StringUtils.hasText(content)) {
			Template template = this.getTemplate(content);
			
			Enumeration<String> enumeration = request.getAttributeNames();
			for (; enumeration.hasMoreElements();) {
				Object o = enumeration.nextElement();
				template.binding((String) o, request.getAttribute((String) o));
			}
			
			for (Entry<String,Object> entry:rMap.entrySet()) {
				template.binding(entry.getKey(), entry.getValue());
			}
			return this.getOutPut(template);
		}
		return null;
	}

	@Override
	public String outContent(String content, HttpServletRequest request,
			Map<String, Object> sMap) {
		if (StringUtils.hasText(content)) {
			Template template = this.getTemplate(content);
			Enumeration<String> enumeration = request.getAttributeNames();
			for (; enumeration.hasMoreElements();) {
				Object o = enumeration.nextElement();
				template.binding((String) o, request.getAttribute((String) o));
			}
			Map<String, Object> rMap = this.getRequestMap(request);
			for (Entry<String,Object> entry:rMap.entrySet()) {
				template.binding(entry.getKey(), entry.getValue());
			}
			if (sMap != null) {
				for (Entry<String, Object> entry : sMap.entrySet()) {
					template.binding(entry.getKey(), entry.getValue());
				}
			}
			return this.getOutPut(template);
		}

		return null;
	}

}
