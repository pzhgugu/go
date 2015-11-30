package com.ansteel.shop.goods.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.constant.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.Goods;
@Controller
@RequestMapping(value = Public.ADMIN + "/goods")
public class GoodsController  extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo entity = new EntityInfo();
		entity.setClazz(Goods.class);
		entityInfos.add(entity);
		return entityInfos;
	}

}
