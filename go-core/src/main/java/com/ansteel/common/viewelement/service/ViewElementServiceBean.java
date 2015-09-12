package com.ansteel.common.viewelement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.view.domain.View;
import com.ansteel.common.viewelement.core.ViewElementUtils;
import com.ansteel.core.cache.ICacheCallback;
import com.ansteel.core.cache.TemplateCache;
import com.ansteel.core.cache.ViewCache;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.dynamicmodel.service.DynamicModelsService;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.modelscan.service.ModelScanService;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsCategory;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsGrid;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.common.view.service.ViewService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图元素服务实现类。
 */
@Service
@Transactional(readOnly = true)
public class ViewElementServiceBean implements ViewElementService {

	@Autowired
	ModelService modelService;

	@Autowired
	ModelScanService modelScanService;

	@Autowired
	TplService tplService;

	@Autowired
	ViewService viewService;

	@Autowired
	DynamicModelsService dynamicModelsService;

	@Override
	public ViewElement getViewElement(HttpServletRequest request,
			HttpServletResponse response, Class clazz, String fieldsCategory,
			String tplName, String viewName,
			Collection<EntityInfo> entityInfos, Class thisClass) {

		ViewElement viewElement = this.getViewElement(request, response, clazz,
				fieldsCategory, entityInfos, thisClass);

		// 3、得到tpl信息
		Tpl template = tplService.getCacheTemplate(tplName);
		Assert.notNull(template, "没有找到[" + tplName + "]模板");
		viewElement.setTpl(template);

		// 4、得到视图信息
		viewElement.setView(viewService.getCacheView(viewName,
				request.getServletPath()));

		return viewElement;

	}

	/*public View getCacheView(String viewName, String url) {

		ViewCache viewCache = new ViewCache();
		if (StringUtils.hasText(viewName)) {
			View view = (View) viewCache.getCache(viewName,
					new ICacheCallback() {
						@Override
						public Object get(String key) {
							return viewService.getNameToView(key);
						}
					});
			return view;
		} else {
			View view = (View) viewCache.getCache(url, new ICacheCallback() {
				@Override
				public Object get(String key) {
					return viewService.getUrlToView(key);
				}
			});
			return view;
		}
	}*/

	/*public Tpl getCacheTemplate(String tplName) {
		Assert.hasText(tplName, "模板名称为空！");
		TemplateCache templateCache = new TemplateCache();
		Tpl template = (Tpl) templateCache.getCache(tplName,
				new ICacheCallback() {
					@Override
					public Object get(String key) {
						return tplService.getNameToTpl(key);
					}
				});
		return template;

	}*/

	public Models getModelAndScan(Class clazz, String requestMappingName,
			Collection<EntityInfo> entityInfos) {
		Models modelEntity = this.getModels(clazz);
		// Assert.notNull(modelEntity, "模型为空，请检查！");
		if (modelEntity == null) {
			for (EntityInfo o : entityInfos) {
				if (o.getClazz() == clazz) {
					Map<Class, Models> map = modelScanService.scanModel(o,
							requestMappingName);
					return map.get(clazz);
				}
			}
		}
		return modelEntity;

	}

	private Models getModels(Class clazz) {
		return modelService.findOneByClazz(clazz.getName());
	}

	@Override
	public ViewElement getViewElement(HttpServletRequest request,
			HttpServletResponse response, Class clazz, String fieldsCategory,
			Collection<EntityInfo> entityInfos, Class thisClass) {
		ViewElement viewElement = new ViewElement();
		viewElement.setRequest(request);
		viewElement.setResponse(response);
		// 2、获取控制器服务名称
		String requestMappingName = ViewElementUtils.getRequestMappingName(
				request, thisClass);
		Assert.hasText(requestMappingName, "请设置服务RequestMapping的值！");
		viewElement.setRequestMappingName(requestMappingName);

		// 1、得到实体信息
		viewElement.setEntityInfoList((List<EntityInfo>) entityInfos);

		// 获取模型
		Models modelEntity = this.getModelAndScan(clazz, requestMappingName,
				entityInfos);
		ViewElementUtils.setViewElement(viewElement, modelEntity,
				fieldsCategory, clazz);
		return viewElement;
	}

	@Override
	public ViewElement getDynamicViewElement(HttpServletRequest request,
			HttpServletResponse response, String modelType,
			String categoryName, String tplName, String viewName) {
		ViewElement viewElement = new ViewElement();
		viewElement.setRequest(request);
		viewElement.setResponse(response);
		viewElement.setCurrentName(modelType);
		viewElement.setCurrentSimpleName(modelType);

		DynamicModels dynamicModels = dynamicModelsService
				.getDynamicModels(modelType);
		ViewElementUtils.setViewElement(viewElement, dynamicModels,
				categoryName);

		// 3、得到tpl信息
		Tpl template = tplService.getCacheTemplate(tplName);
		Assert.notNull(template, "没有找到[" + tplName + "]模板");
		viewElement.setTpl(template);

		// 4、得到视图信息
		viewElement.setView(viewService.getCacheView(viewName,
				request.getServletPath()));
		return viewElement;
	}

}
