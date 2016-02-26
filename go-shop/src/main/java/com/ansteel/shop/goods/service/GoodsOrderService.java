package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsOrder;
import com.ansteel.shop.goods.web.ShopOrderModel;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public interface GoodsOrderService {
    /**
     * 生成订单
     * @param shopOrderModel
     */
    List<GoodsOrder> createOrder(ShopOrderModel shopOrderModel);
}
