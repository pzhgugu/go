package com.ansteel.common.viewelement.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.domain.EntityInfo;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板视图元素服务类。
 */
public interface ViewElementService {

	/**
	 * 获取模板视图元素
	 * @param request
	 * @param response
	 * @param clazz
	 * @param fieldsCategory
	 * @param tplName
	 * @param viewName
	 * @param entityInfos
	 * @return
	 */
	ViewElement getViewElement(HttpServletRequest request,
			HttpServletResponse response,Class clazz,
			String fieldsCategory,String tplName,String viewName,
			Collection<EntityInfo> entityInfos,Class thisClass);
	/**
	 * 自定义模板获取视图元素
	 * @param request
	 * @param response
	 * @param clazz
	 * @param fieldsCategory
	 * @param entityInfos
	 * @return
	 */
	ViewElement getViewElement(HttpServletRequest request,
			HttpServletResponse response,Class clazz,String fieldsCategory,
			Collection<EntityInfo> entityInfos,Class thisClass);

	/**
	 * 通过视图名称得到视图对象（设置了缓存）
	 * @param viewName
	 * @return
	 */
	//View getCacheView(String viewName, String url);
	/**
	 * 通过模板名称得到模板对象
	 * @param tplName
	 * @return
	 */
	//Tpl getCacheTemplate(String tplName);
	/**
	 * 得到模型对象，如果不存在则扫描
	 * @param clazz
	 * @param requestMappingName
	 * @param entityInfos
	 * @return
	 */
	Models getModelAndScan(Class clazz,String requestMappingName,Collection<EntityInfo> entityInfos);
	/**
	 * 获取模板视图元素,动态模型方式
	 * @param request
	 * @param response
	 * @param modelType
	 * @param fieldsCategory
	 * @param tplName
	 * @param viewName
	 * @return
	 */
	ViewElement getDynamicViewElement(HttpServletRequest request,
			HttpServletResponse response, String modelType,
			String fieldsCategory, String tplName, String viewName);

}
