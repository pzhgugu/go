package com.ansteel.shop.goods.service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.core.domain.Address;
import com.ansteel.shop.core.domain.Invoice;
import com.ansteel.shop.core.service.AddressService;
import com.ansteel.shop.core.service.InvoiceService;
import com.ansteel.shop.goods.domain.*;
import com.ansteel.shop.goods.repository.GoodsOrderRepository;
import com.ansteel.shop.goods.web.ShopOrderCartModel;
import com.ansteel.shop.goods.web.ShopOrderModel;
import com.ansteel.shop.goods.web.ShopOrderStoreModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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

    @Autowired
    AddressService addressService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    GoodsOrderCommonService goodsOrderCommonService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsOrderGoodsService goodsOrderGoodsService;

    @Autowired
    GoodsOrderLogService goodsOrderLogService;

    @Autowired
    CartService cartService;

    @Override
    @Transactional
    public List<GoodsOrder> createOrder(ShopOrderModel shopOrderModel) {

        List<ShopOrderStoreModel> storeList = shopOrderModel.getStoreList();

        UserInfo userInfo = UserUtils.getUserInfo();
        //地址信息
        Address address=addressService.findOne(shopOrderModel.getAddress_id());
        Assert.notNull(address,"地址信息错误！");
        //发票信息
        String invoiceId=shopOrderModel.getInvoice_id();
        Invoice invoice=null;
        if(StringUtils.hasText(invoiceId)){
            invoice=invoiceService.findOne(invoiceId);
        }


        List<GoodsOrder> goodsOrderList = new ArrayList<>();
        for(ShopOrderStoreModel sotreModel:storeList){
            String serialNumber=orderSerialNumberService.getOrderSerialNumber();
            GoodsOrder goodsOrder = new GoodsOrder();
            goodsOrder.setOrderSn(serialNumber);
            goodsOrder.setStoreId(sotreModel.getStoreId());
            goodsOrder.setStoreName(sotreModel.getStoreName());
            goodsOrder.setBuyerId(userInfo.getId());
            goodsOrder.setBuyerName(userInfo.getAlias());
            //商品总价格
            goodsOrder.setGoodsAmount(sotreModel.getStoreGoodsPrice());
            //订单总价格
            goodsOrder.setOrderAmount(sotreModel.getStorePrice());
            //运费
            goodsOrder.setShippingFee(sotreModel.getFreight());
            goodsOrder.setOrderState(10);
            goodsOrder=goodsOrderRepository.save(goodsOrder);
            goodsOrderList.add(goodsOrder);

            //订单扩展类
            GoodsOrderCommon goodsOrderCommon = new GoodsOrderCommon();
            goodsOrderCommon.setOrderId(goodsOrder.getId());
            goodsOrderCommon.setStoreId(sotreModel.getStoreId());
            goodsOrderCommon.setOrderMessage(sotreModel.getMessage());
            goodsOrderCommon.setDaddressId(address.getId());
            goodsOrderCommon.setReciverName(address.getTrueName());
            goodsOrderCommon.setReciverProvinceId(address.getAreaId());
            //地址对象
            goodsOrderCommon.setReciverInfo(JsonUtils.jsonFromObject(address));
            //发票对象
            if(invoice!=null) {
                goodsOrderCommon.setInvoiceInfo(JsonUtils.jsonFromObject(invoice));
            }
            goodsOrderCommon=goodsOrderCommonService.save(goodsOrderCommon);

            //订单商品类
            for(ShopOrderCartModel socm:sotreModel.getCartList()){
                GoodsOrderGoods goodsOrderGoods = new GoodsOrderGoods();
                Goods goods = goodsService.findOne(socm.getGoodsId());
                Assert.notNull(goods, "商品已经失效！");
                goodsOrderGoods.setGoodsNum(socm.getNumber());
                goodsOrderGoods.setStoreId(goods.getStoreId());
                goodsOrderGoods.setGoodsId(goods.getId());
                goodsOrderGoods.setOrderId(goodsOrder.getId());
                goodsOrderGoods.setGoodsPrice(goods.getGoodsStorePrice());
                goodsOrderGoods.setGoodsImage(goods.getGoodsImage());
                goodsOrderGoods.setGoodsName(goods.getName());
                goodsOrderGoods.setBuyerId(userInfo.getId());
                goodsOrderGoods.setGoodsType(1);
                goodsOrderGoods=goodsOrderGoodsService.save(goodsOrderGoods);
                if(shopOrderModel.getIfcart()==1){
                    cartService.delete(userInfo.getId(),goods.getId());
                }
            }

            //订单日志
            GoodsOrderLog goodsOrderLog=new GoodsOrderLog();
            goodsOrderLog.setOrderId(goodsOrder.getId());
            goodsOrderLog.setLogUser(userInfo.getAlias());
            goodsOrderLog.setLogUserId(userInfo.getId());
            goodsOrderLog.setLogOrderState(10);
            goodsOrderLog.setLogRole("买家");
            goodsOrderLog.setLogMsg("提交了订单");
            goodsOrderLog=goodsOrderLogService.save(goodsOrderLog);
        }

        return  goodsOrderList;
    }
}
