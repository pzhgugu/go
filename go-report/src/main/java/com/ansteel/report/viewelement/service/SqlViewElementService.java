package com.ansteel.report.viewelement.service;

import com.ansteel.common.viewelement.service.ViewElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/9/12.
 */
public interface SqlViewElementService {

    /**
     * 获取模板视图元素,SQL模型方式
     * @param request
     * @param response
     * @param modelType
     * @param fieldsCategory
     * @param tplName
     * @param viewName
     * @return
     */
	ViewElement getSQLViewElement(HttpServletRequest request,
			HttpServletResponse response, String modelType,
			String fieldsCategory, String tplName, String viewName);
}
