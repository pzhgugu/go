package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.web.ShopOrderModel;

/**
 * Created by Administrator on 2016/1/29.
 */
public interface GoodsOrderService {
    /**
     * 生成订单
     * @param shopOrderModel
     */
    void createOrder(ShopOrderModel shopOrderModel);
}
