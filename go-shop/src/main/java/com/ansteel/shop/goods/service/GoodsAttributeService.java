package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsAttribute;

/**
 * Created by Administrator on 2015/11/30.
 */
public interface GoodsAttributeService {
    GoodsAttribute findOne(String id);

    GoodsAttribute save(GoodsAttribute goodsAttribute);
}
