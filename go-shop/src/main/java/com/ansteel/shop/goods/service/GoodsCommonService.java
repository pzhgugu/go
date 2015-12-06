package com.ansteel.shop.goods.service;


import com.ansteel.shop.goods.domain.GoodsCommon;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2015/12/6.
 */
public interface GoodsCommonService {
    /**
     * 保存商品
     * @param goods
     * @return
     */
    GoodsCommon save(GoodsCommon goods);

    /**
     * 查询当前店铺商品
     * @param goodsId
     * @return
     */
    GoodsCommon findOneByStoreIdAndId(String goodsId);

    /**
     * 查询当前店铺的所有出售商品
     * @param classId
     * @param sortType
     * @param curPage
     * @param pageSize
     * @param name
     * @param value
     * @return
     */
    Page<GoodsCommon> findCurrentSaleAll(String classId, String sortType, Integer curPage, int pageSize, String name, String value);

    /**
     * 商品下架
     * @param goodsIdArray
     */
    void unShow(String[] goodsIdArray);
    /**
     * 商品下架
     * @param goodsId
     */
    void unShow(String goodsId);

    /**
     * 设置广告词
     * @param goodsIdArray
     * @param adWord
     */
    void adEdit(String[] goodsIdArray, String adWord);

    /**
     * 修改关联版式
     * @param commonid
     * @param plateTop
     * @param plateBottom
     * @return
     */
    GoodsCommon savePosition(String commonid, String plateTop, String plateBottom);

    void savePosition(String[] ids, String plateTop, String plateBottom);
}
