package com.ansteel.shop.goods.service;

import java.util.Collection;
import java.util.List;

import com.ansteel.shop.goods.domain.GoodsClass;

public interface GoodsClassService {

	List<GoodsClass> findByParentIsNull();

	List<GoodsClass> findByParentId(String id);

	GoodsClass findOne(String id);

	List<GoodsClass> findByLayer(int layer);

	/**
	 * 查询id下面的所有子记录
	 *
	 * @param id
	 * @return
	 */
	Collection<GoodsClass> findChildren(String id);

	List<GoodsClass> findAll();
}
