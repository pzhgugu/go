package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.Goods;

public interface GoodsService {

	/**
	 * 保存商品
	 * @param goods
	 * @return
	 */
	Goods save(Goods goods);

	/**
	 * 查询当前店铺商品
	 * @param goodsId
	 * @return
	 */
	Goods findOneByStoreIdAndId(String goodsId);

}
