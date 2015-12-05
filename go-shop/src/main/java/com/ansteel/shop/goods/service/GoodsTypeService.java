package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsType;

import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface GoodsTypeService {
    GoodsType findOne(String id);

    List<GoodsType> findByGoodsClass(String id);
}
