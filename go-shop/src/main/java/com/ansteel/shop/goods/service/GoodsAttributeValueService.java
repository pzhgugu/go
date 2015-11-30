package com.ansteel.shop.goods.service;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.shop.goods.domain.GoodsAttributeValue;
import com.ansteel.shop.goods.domain.GoodsSpecValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2015/11/27.
 */
public interface GoodsAttributeValueService {

    Page<GoodsAttributeValue> findByGoodsAttribute_Id(String attId, Pageable pageable);

    GoodsAttributeValue save(GoodsAttributeValue goodsAttributeValue);

    void save(GoodsAttributeValue goodsAttributeValue, String value);

    void delete(String id);

    /**
     * 修改商品属性列表
     *
     * @param value
     */
    void updateGoodsAttributeByAttrValue(String value);
}
