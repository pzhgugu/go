package com.ansteel.common.tpl.core;

import java.util.Map;

import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：控件设置接口。
 */
public interface IDhtmlxWidget {
	/**
	 * 构建控件设置选项
	 * @param tpl
	 * @param view
	 * @return
	 */
	Map<String, Object> structure(ViewElement viewElement);
}
