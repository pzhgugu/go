package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsOrderGoods;
import com.ansteel.shop.goods.repository.GoodsOrderGoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2016/2/4.
 */
@Service
@Transactional(readOnly = true)
public class GoodsOrderGoodsServiceImpl implements  GoodsOrderGoodsService {

    @Autowired
    private GoodsOrderGoodsRepository goodsOrderGoodsRepository;

    @Override
    public GoodsOrderGoods save(GoodsOrderGoods goodsOrderGoods) {
        return goodsOrderGoodsRepository.save(goodsOrderGoods);
    }
}
