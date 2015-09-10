package com.ansteel.shop.goods.service;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.shop.goods.domain.GoodsSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface GoodsSpecService {
    /**
     * 获取商品规格并选择商品类型
     * @param goodsTypesId
     * @param pageable
     * @return
     */
    Page findSelectGoodsTypes(String goodsTypesId, Pageable pageable);

    List findBySpName(String spName);

    /**
     * 更新商品规格
     * @param goodsSpec
     * @param goodsTypesId
     */
    GoodsSpec save(GoodsSpec goodsSpec, String goodsTypesId);

    /**
     * 商品规格关联商品类型
     * @param baseEntityLsit
     * @param goodsTypesId
     */
    void select(List<BaseEntity> baseEntityLsit, String goodsTypesId);

    void querySelect(Page page, String goodsTypesId);
}
