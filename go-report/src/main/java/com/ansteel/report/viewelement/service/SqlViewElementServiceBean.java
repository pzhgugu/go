package com.ansteel.report.viewelement.service;

import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.common.view.service.ViewService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.report.sqlmodel.domain.SqlModels;
import com.ansteel.report.sqlmodel.service.SqlModelsService;
import com.ansteel.report.viewelement.core.SqlViewElementUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/9/12.
 */
@Service
public class SqlViewElementServiceBean implements  SqlViewElementService {

    @Autowired
    SqlModelsService sqlModelsService;

    @Autowired
    TplService tplService;

    @Autowired
    ViewService viewService;

	@Override
	public ViewElement getSQLViewElement(HttpServletRequest request,
			HttpServletResponse response, String modelType,
			String categoryName, String tplName, String viewName) {
		ViewElement viewElement = new ViewElement();
		viewElement.setRequest(request);
		viewElement.setResponse(response);
		viewElement.setCurrentName(modelType);
		viewElement.setCurrentSimpleName(modelType);

		SqlModels sqlModels = sqlModelsService
				.getSqlModels(modelType);
        SqlViewElementUtils.setViewElement(viewElement, sqlModels,
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
