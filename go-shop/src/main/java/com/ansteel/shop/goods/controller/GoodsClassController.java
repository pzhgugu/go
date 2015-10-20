package com.ansteel.shop.goods.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.shop.goods.service.GoodsClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsClass;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/goodsclass")
public class GoodsClassController  extends BaseController{

	@Autowired
	GoodsClassService goodsClassService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo tree = new EntityInfo();
		tree.setClazz(GoodsClass.class);
		entityInfos.add(tree);
		return entityInfos;
	}

	@RequestMapping("/get/alias/{id}")
	public
	@ResponseBody
	String getAlias(@PathVariable("id") String id,//排序字段名
					HttpServletRequest request, HttpServletResponse response) {

		GoodsClass goodsClass = goodsClassService.findOne(id);
		Assert.notNull(goodsClass, id + "不存在！");
		return goodsClass.getAlias();
	}

}
