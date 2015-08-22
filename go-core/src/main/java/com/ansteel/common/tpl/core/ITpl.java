package com.ansteel.common.tpl.core;

import java.util.Map;

import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板接口。
 */
public interface ITpl {
	/**
	 * 获取模型
	 * @param viewElement
	 * @return
	 */
	Map<String,Object> getModel(ViewElement viewElement);
}
