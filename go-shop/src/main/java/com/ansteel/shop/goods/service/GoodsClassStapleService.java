package com.ansteel.shop.goods.service;

import java.util.List;

import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.GoodsClassStaple;
import com.ansteel.shop.goods.domain.JsonGoodsClass;

public interface GoodsClassStapleService {

	/**
	 * 获取当前用户常用分类
	 * @return
	 */
	List<GoodsClassStaple> getCurrentGoodsClassStaple();

	GoodsClassStaple findByOne(String id);

	/**
	 * 选择常用分类
	 * @param stapleid
	 * @return
	 */
	JsonGoodsClass selectGoodsClassStaple(String stapleid);

	void delect(String id);

	/**
	 * 检查常用分类是否有这个分类
	 * 没有则保存
	 * @param goodsClass
	 * @return
	 */
	GoodsClassStaple checkSaveStaple(GoodsClass goodsClass);

}
