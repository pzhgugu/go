package com.ansteel.shop.goods.service;

import java.util.List;

import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.domain.GoodsImages;

public interface GoodsImagesService {

	/**
	 * 保存店铺商品默认图片
	 * @param image
	 * @param storeId
	 * @return
	 */
	GoodsImages saveDefault(String goodsId,String image, String storeId);

	/**
	 * 查询当前店铺下商品的图片
	 * @param goodsId
	 * @return
	 */
	List<GoodsImages> findByGoodsIdAndStoreId(String goodsId);

	/**
	 * 获取主图
	 * @param goodsImagesList
	 * @return
	 */
	GoodsImages getDefautlGoodsImages(List<GoodsImages> goodsImagesList);

	/**
	 * 保存商品中默认图片
	 * @param goodsImagesArray
	 * @param goodsId
	 * @return
	 */
	GoodsCommon saevDefaultImage(GoodsImages[] goodsImagesArray, String goodsId);

	/**
	 * 删除原有图片，保存新的图片列表
	 * @param goodsId
	 * @param goodsImagesArray
	 */
	void save(String goodsId, GoodsImages[] goodsImagesArray);

}
