package com.ansteel.common.tpl.core;

import java.util.Map;

import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：左树右单表模板。
 */
public class TreeGridTpl extends AbstractTpl implements ITpl {

	@Override
	protected Map<String, Object> loadWidget(ViewElement viewElement) {
		//得到主从模板的数据
		Map<String, Object> widgetModel=new TreeOneToManyTpl().loadWidget(viewElement);
		return widgetModel;
	}

}
