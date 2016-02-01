package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.repository.GoodsOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/1/29.
 */
@Service
public class GoodsOrderServiceImpl implements   GoodsOrderService {

    @Autowired
    GoodsOrderRepository goodsOrderRepository;
}
