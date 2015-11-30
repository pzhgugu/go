package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsAttribute;
import com.ansteel.shop.goods.repository.GoodsAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/11/30.
 */
@Service
@Transactional(readOnly = true)
public class GoodsAttributeServiceImpl implements GoodsAttributeService {

    @Autowired
    GoodsAttributeRepository goodsAttributeRepository;

    @Override
    public GoodsAttribute findOne(String id) {
        return goodsAttributeRepository.findOne(id);
    }

    @Override
    @Transactional
    public GoodsAttribute save(GoodsAttribute goodsAttribute) {
        return goodsAttributeRepository.save(goodsAttribute);
    }
}
