package com.ansteel.common.view.service;

import java.util.Map;

import com.ansteel.common.view.domain.View;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图服务类。
 */
public interface ViewService {

	/**
	 * 根据key获取视图
	 * @param key
	 * @return
	 */
	View getNameToView(String key);

	/**
	 * 根据key获取视图
	 * @param key
	 * @return
	 */
	View findOneByUrlPath(String urlPath);
	


	/**
	 * 获取视图变量
	 * @param view
	 * @return
	 */
	Map<String, Object> getViewVariable(View view);
	/**
	 * 通过视图名称得到视图对象（设置了缓存）
	 * @param viewName
	 * @return
	 */
	View getCacheView(String viewName, String url);

}
