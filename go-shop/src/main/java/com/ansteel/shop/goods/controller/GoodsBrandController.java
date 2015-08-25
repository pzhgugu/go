package com.ansteel.shop.goods.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsBrand;
@Controller
@RequestMapping(value = "/goodsbrand")
public class GoodsBrandController  extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo entity = new EntityInfo();
		entity.setClazz(GoodsBrand.class);
		entityInfos.add(entity);
		return entityInfos;
	}

}
