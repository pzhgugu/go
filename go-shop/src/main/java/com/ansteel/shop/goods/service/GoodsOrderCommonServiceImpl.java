package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsOrderCommon;
import com.ansteel.shop.goods.repository.GoodsOrderCommonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/4.
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderCommonServiceImpl implements  GoodsOrderCommonService {

    @Autowired
    GoodsOrderCommonRepository goodsOrderCommonRepository;

    @Override
    public GoodsOrderCommon save(GoodsOrderCommon goodsOrderCommon) {
        return goodsOrderCommonRepository.save(goodsOrderCommon);
    }
}
