package com.ansteel.shop.goods.controller;

import java.util.*;

import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.goods.domain.GoodsClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.goods.domain.GoodsType;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/goodstype")
public class GoodsTypeController  extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {

		//树设置
		TreeInfo treeInfo=new TreeInfo();
		treeInfo.setTree(GoodsClass.class);
		Collection<Class> tables=new ArrayList<Class>();
		tables.add(GoodsType.class);
		treeInfo.setTables(tables);


		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();

		EntityInfo goodsClass = new EntityInfo();
		goodsClass.setClazz(GoodsClass.class);
		goodsClass.setTree(treeInfo);
		entityInfos.add(goodsClass);

		EntityInfo goodsType = new EntityInfo();
		goodsType.setClazz(GoodsType.class);
		goodsType.setTree(treeInfo);
		entityInfos.add(goodsType);

		return entityInfos;
	}

	@RequestMapping("/spec")
	public String spec(Model model,
					   @RequestParam("id") String typeId,
					   HttpServletRequest request,
					   HttpServletResponse response) {


		return FisUtils.page("shop:pages/admin/goods/spec.html");
	}

	@RequestMapping("/brand")
	public String brand(Model model,
						@RequestParam("id") String typeId,
					   HttpServletRequest request,
					   HttpServletResponse response) {


		return FisUtils.page("shop:pages/admin/goods/brand.html");
	}

	@RequestMapping("/attribute")
	public String attribute(Model model,
							@RequestParam("id") String typeId,
					   HttpServletRequest request,
					   HttpServletResponse response) {


		return FisUtils.page("shop:pages/admin/goods/attribute.html");
	}
}
