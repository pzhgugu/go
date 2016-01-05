package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.Goods;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GoodsService {
	/**
	 * 保存商品
	 * @param goods
	 * @return
	 */
	Goods save(Goods goods);

	/**
	 * 计算商品总库存
	 * @param id
	 * @return
	 */
	Integer grossInventory(String id);

	/**
	 *
	 * @param commonId
	 * @return
	 */
	List<Goods> findByGoodsCommonId(String commonId);

	void delect(List<Goods> goodsList);

	void delect(Goods goods);

	void delectByCommonId(String commonId,String storeId);

	Goods findOne(String id);

	List<Goods> findByGoodsCommonIdOrderByGoodsStorePriceAsc(String commonId);
}
