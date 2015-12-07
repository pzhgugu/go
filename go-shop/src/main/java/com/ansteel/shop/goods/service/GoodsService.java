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
	 * 保存商品
	 * @param goods
	 * @return
	 *//*
	Goods save(Goods goods);

	*//**
	 * 查询当前店铺商品
	 * @param goodsId
	 * @return
	 *//*
	Goods findOneByStoreIdAndId(String goodsId);

    *//**
     * 查询当前店铺的所有出售商品
     * @param classId
     * @param sortType
     * @param curPage
     * @param pageSize
     * @param name
     * @param value
     * @return
     *//*
    Page<Goods> findCurrentSaleAll(String classId, String sortType, Integer curPage, int pageSize, String name, String value);

	*//**
	 * 商品下架
	 * @param goodsIdArray
	 *//*
	void unShow(String[] goodsIdArray);
	*//**
	 * 商品下架
	 * @param goodsId
	 *//*
	void unShow(String goodsId);

	*//**
	 * 设置广告词
	 * @param goodsIdArray
	 * @param adWord
	 *//*
	void adEdit(String[] goodsIdArray, String adWord);

	*//**
	 * 修改关联版式
	 * @param commonid
	 * @param plateTop
	 * @param plateBottom
	 * @return
	 *//*
	Goods savePosition(String commonid, String plateTop, String plateBottom);

	void savePosition(String[] ids, String plateTop, String plateBottom);*/
}
