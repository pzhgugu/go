package com.ansteel.common.menu.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.common.menu.domain.Menu;
import com.ansteel.common.menu.service.MenuService;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.security.service.ResourcesService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：菜单控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/menu")
public class MenuController  extends BaseController implements SaveBefore{
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	ResourcesService resourcesService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo treeEntityInfo = new EntityInfo();
		treeEntityInfo.setClazz(Menu.class);
		entityInfos.add(treeEntityInfo);
		return entityInfos;
	}
	
	@Autowired
	CheckService checkService;

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity instanceof Menu){
			Menu o=(Menu) entity;
			String rName = o.getResourcesName();
			if(StringUtils.hasText(rName)){
				String[] rArray = rName.trim().split(";");
				for(String r:rArray){
					Assert.notNull(resourcesService.findOneByName(r),"资源名称没有找到，请重新输入！");
				}
			}
			Assert.isTrue(!checkService.isNameRepeat(o),o.getName()+"名称重复请重新设置！");
		}
	}

}
