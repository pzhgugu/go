package com.ansteel.common.tpl.service;

import java.util.Map;

import org.springframework.ui.Model;

import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.view.domain.View;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板服务类。
 */
public interface TplService {

	/**
	 * 根据名称获取模板
	 * @param key
	 * @return
	 */
	Tpl getNameToTpl(String key);

	/**
	 * 运行模板
	 * @param viewElement
	 * @param model
	 * @return
	 */
	String runTpl(ViewElement viewElement,Model model);

	/**
	 * 获取模板变量
	 * @param tpl
	 * @return
	 */
	Map<String, Object> getTplVariable(Tpl tpl);

	/**
	 * 获取视图变量
	 * @param view
	 * @return
	 */
	Map<String, Object> getViewVariable(View view);
	/**
	 * 通过模板名称得到模板对象
	 * @param tplName
	 * @return
	 */
	Tpl getCacheTemplate(String tplName);
}
