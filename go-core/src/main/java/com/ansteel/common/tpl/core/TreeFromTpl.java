package com.ansteel.common.tpl.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：左树右表格模板。
 */
@Service
public class TreeFromTpl extends AbstractTpl implements ITpl {

	@Override
	public Map<String, Object> loadWidget(ViewElement viewElement) {
		Map<String, Object> widgetModel=new HashMap<String, Object>();
		
		IDhtmlxWidget form=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_FORM);
		widgetModel.putAll(form.structure(viewElement));
		widgetModel.put(ViewModelConstant.P_TREE_CLASS,viewElement.getCurrentSimpleName());
		return widgetModel;
	}

}
