package com.ansteel.common.menu.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.common.menu.domain.Menu;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：菜单服务接口。  
 */
public interface MenuService {

	List<Menu> getMenu(HttpServletRequest request);

}
