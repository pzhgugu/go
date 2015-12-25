package com.ansteel.shop.goods.service;


import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.web.GoodsModel;
import org.springframework.data.domain.Page;

import java.util.List;

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
     * 查询当前店铺的商品
     * @param classId 店铺分类
     * @param sortType 排序类型
     * @param curPage 当前页
     * @param pageSize 分页大小
     * @param name 查询字段名称
     * @param value 查询值
     * @param goodsState 商品状态
     * @param goodsVerify 商品审核
     * @return
     */
    Page<GoodsCommon> query(String classId, String sortType, Integer curPage, int pageSize, String name, String value,Integer goodsState,Integer goodsVerify);

    /**
     * 商品下架
     * @param goodsIdArray
     */
    void isShow(String[] goodsIdArray,Integer status);
    /**
     * 商品下架
     * @param goodsId
     */
    void isShow(String goodsId,Integer status);

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

    GoodsCommon saveGoodsCommonAndGodds(GoodsCommon goodsCommon, GoodsModel goodsModel);

    void delect(String[] goodsCommonIds);

    /**
     * 店铺推荐商品
     * @param storeId
     * @return
     */
    List<GoodsCommon> findTop20ByGoodsCommend(String storeId);

    List<GoodsCommon> findTop20ByNew(String storeId);

    /**
     * 热销商品
     * @param storeId
     * @return
     */
    List<GoodsCommon> findTop5ByHot(String storeId);

    List<GoodsCommon> findTop5ByHotCollect(String storeId);
    /**
     * 查询店铺商品
     * @param storeId
     * @param key
     * @param keyword
     * @param stcId
     * @param order
     * @param curPage
     * @return
     */
    Page<GoodsCommon> queryStoreGoods(String storeId, String key, String keyword, String stcId, String order, Integer curPage, int pageSize);

    GoodsCommon findOne(String id);
}
