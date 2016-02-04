package com.ansteel.shop.goods.service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.GoodsOrder;
import com.ansteel.shop.goods.domain.GoodsOrderCommon;
import com.ansteel.shop.goods.repository.GoodsOrderRepository;
import com.ansteel.shop.goods.web.ShopOrderModel;
import com.ansteel.shop.goods.web.ShopOrderStoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderServiceImpl implements   GoodsOrderService {

    @Autowired
    GoodsOrderRepository goodsOrderRepository;

    @Autowired
    OrderSerialNumberService orderSerialNumberService;

    @Override
    public void createOrder(ShopOrderModel shopOrderModel) {

        List<ShopOrderStoreModel> storeList = shopOrderModel.getStoreList();

        UserInfo userInfo = UserUtils.getUserInfo();

        for(ShopOrderStoreModel sotreModel:storeList){
            String serialNumber=orderSerialNumberService.getOrderSerialNumber();
            GoodsOrder goodsOrder = new GoodsOrder();
            goodsOrder.setOrderSn(serialNumber);
            goodsOrder.setStoreId(sotreModel.getStoreId());
            goodsOrder.setStoreName(sotreModel.getStoreName());
            goodsOrder.setBuyerId(userInfo.getId());
            goodsOrder.setBuyerName(userInfo.getAlias());
            goodsOrder.setGoodsAmount(sotreModel.getStoreGoodsPrice());
            goodsOrder.setOrderAmount(sotreModel.getStorePrice());
            goodsOrder.setShippingFee(sotreModel.getFreight());
            goodsOrder.setOrderState(10);
            goodsOrder=goodsOrderRepository.save(goodsOrder);

            GoodsOrderCommon goodsOrderCommon = new GoodsOrderCommon();
            goodsOrderCommon.setOrderId(goodsOrder.getId());
            goodsOrderCommon.setStoreId(sotreModel.getStoreId());
            goodsOrderCommon.setOrderMessage(sotreModel.getMessage());
        }


    }
}
