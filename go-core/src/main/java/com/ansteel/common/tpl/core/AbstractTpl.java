package com.ansteel.common.tpl.core;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.domain.TplCss;
import com.ansteel.common.tpl.domain.TplJavascript;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.common.beetl.service.BeetlService;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：抽象模板类。
 */
public abstract class AbstractTpl {

	protected Map<String, Object> viewModelMap = new HashMap<String, Object>();
	
	private BeetlService getBeetlService(){
		return ContextHolder.getSpringBean("beetlServiceBean");
	}
	
	public Map<String, Object> getModel(ViewElement viewElement) {
		return init(viewElement);
	}


	/**
	 * 加载控件
	 */
	protected abstract Map<String, Object> loadWidget(ViewElement serviceInfo);
	/**
	 * 模板管理类
	 * 
	 * @param tplManage
	 * @return
	 */
	public Map<String, Object> init(ViewElement viewElement) {
		viewModelMap.put(TplViewConstant.P_TPL_MODULES, viewElement.getRequestMappingName());
		//viewModelMap.put(TplViewConstant.P_CLASS_NAME, viewElement.getPresentClazz().getSimpleName());
		viewModelMap.put(TplViewConstant.P_CLASS_NAME, viewElement.getCurrentSimpleName());
		viewModelMap.put(TplViewConstant.P_CLASS, viewElement.getCurrentName());
		
		Map<String, Object> widgetMap = this.loadWidget(viewElement);
		if (widgetMap != null) {
			viewModelMap.putAll(widgetMap);
		}

		Map<String, Object> styleMap = this.getStyle(viewElement);
		if (styleMap != null) {
			viewModelMap.putAll(styleMap);
		}

		Map<String, Object> javaScriptMap = this.getJavascript(viewElement);
		if (javaScriptMap != null) {
			viewModelMap.putAll(javaScriptMap);
		}


		Map<String, Object> buttonMap = this.getButton(viewElement);
		if (buttonMap != null) {
			viewModelMap.putAll(buttonMap);
		}

		Map<String, Object> variableMap = this.getVariable(viewElement);
		if (variableMap != null) {
			viewModelMap.putAll(variableMap);
		}

		return viewModelMap;
	}

	private Map<String, Object> getVariable(ViewElement viewElement) {
		// TODO Auto-generated method stub
		return null;
	}

	private Map<String, Object> getButton(ViewElement viewElement) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 获取样式信息
	 */
	protected Map<String, Object> getStyle(ViewElement viewElement){
		
		Map<String, String> cssPath = new LinkedHashMap<String, String>();
		Tpl tpl = viewElement.getTpl();
		if(tpl!=null){
			Collection<TplCss> tplList = tpl.getTplCssList();
			for(TplCss tplCss:tplList){
				if(tplCss.getIsStop()!=1){
					cssPath.put(tplCss.getName(), tplCss.getUrlPath());
				}
			}
		}
		
		View view = viewElement.getView();
		if(view!=null){
			Collection<TplCss> viewList = view.getTplCssList();
			for(TplCss tplCss:viewList){
				if(tplCss.getIsStop()!=1){
					cssPath.put(tplCss.getName(), tplCss.getUrlPath());
				}
			}
		}
		

		Map<String, Object> cssMap = new HashMap<String, Object>();
		//String messageFormat="<link rel=\"stylesheet\" type=\"text/css\"	href=\"{0}\" />\n";
		//String css = this.getPath(messageFormat,cssPath,viewElement.getRequest());
		cssMap.put(TplViewConstant.P_CSS_LINK, cssPath);
		return cssMap;
		
	}

	/**
	 * 获取Javascript
	 */
	protected Map<String, Object> getJavascript(ViewElement viewElement){
		//顶部
		Map<String, String> firstMap = new LinkedHashMap<String, String>();
		//底部
		Map<String, String> afterMap = new LinkedHashMap<String, String>();
		
		Tpl tpl = viewElement.getTpl();
		if(tpl!=null){
			Collection<TplJavascript> tplList = tpl.getJavaScriptList();
			for(TplJavascript tplJavascript:tplList){
				if(tplJavascript.getIsStop()!=1){
					if(tplJavascript.getPagePlace()==1){
						firstMap.put(tplJavascript.getName(), tplJavascript.getUrlPath());
					}else{
						afterMap.put(tplJavascript.getName(), tplJavascript.getUrlPath());
					}
				}
			}
		}
		
		View view = viewElement.getView();
		if(view!=null){
			Collection<TplJavascript> viewList = view.getJavaScriptList();
			for(TplJavascript tplJavascript:viewList){
				if(tplJavascript.getIsStop()!=1){
					if(tplJavascript.getPagePlace()==1){
						firstMap.put(tplJavascript.getName(), tplJavascript.getUrlPath());
					}else{
						afterMap.put(tplJavascript.getName(), tplJavascript.getUrlPath());
					}
				}
			}
		}
		
		//String messageFormat="<script type=\"text/javascript\" src=\"{0}\"> </script>\n";
		Map<String, Object> jsMap = new HashMap<String, Object>();
		///String firstStr = this.getPath(messageFormat,firstMap,viewElement.getRequest());
		//String afterStr = this.getPath(messageFormat,afterMap,viewElement.getRequest());
		jsMap.put(TplViewConstant.P_JAVASCRIPT_FIRST, firstMap);
		jsMap.put(TplViewConstant.P_JAVASCRIPT_AFTER, afterMap);
		return jsMap;
		
	}


	private String getPath(String messageFormat,Map<String, String> map,HttpServletRequest request) {
		StringBuffer sb = new StringBuffer();
		for(Entry<String, String> entry:map.entrySet()){
			String jsTemp = MessageFormat
					.format(messageFormat,this.getBeetlService().outContent(entry.getValue(),request));
			sb.append(jsTemp);
		}
		return sb.toString();
	}

}
