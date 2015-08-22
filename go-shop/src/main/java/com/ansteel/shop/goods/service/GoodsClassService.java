package com.ansteel.shop.goods.service;

import java.util.List;

import com.ansteel.shop.goods.domain.GoodsClass;

public interface GoodsClassService {

	List<GoodsClass> findByParentIsNull();

	List<GoodsClass> findByParentId(String id);

	GoodsClass findOne(String id);

}
