package com.ansteel.shop.goods.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsClass;
@Controller
@RequestMapping(value = "/goodsclass")
public class GoodsClassController  extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo tree = new EntityInfo();
		tree.setClazz(GoodsClass.class);
		entityInfos.add(tree);
		return entityInfos;
	}

}
