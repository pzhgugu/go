package com.ansteel.common.tpl.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：单表模板。
 */
@Service
public class SingleGridTpl extends AbstractTpl implements ITpl {

	@Override
	public Map<String, Object> loadWidget(ViewElement viewElement) {
		Map<String, Object> widgetModel=new HashMap<String, Object>();
		IDhtmlxWidget grid=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_GRID);
		widgetModel.putAll(grid.structure(viewElement));
		
		IDhtmlxWidget toolbar=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_TOOLBAR);
		widgetModel.putAll(toolbar.structure(viewElement));
		
		IDhtmlxWidget form=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_FORM);
		widgetModel.putAll(form.structure(viewElement));
		
		IDhtmlxWidget query=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_SIMPLE_QUERY);
		widgetModel.putAll(query.structure(viewElement));
		
		return widgetModel;
	}

}
