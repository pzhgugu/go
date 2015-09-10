package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsType;
import com.ansteel.shop.goods.repository.GoodsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2015/9/7.
 */
@Service
public class GoodsTypeServiceBean implements  GoodsTypeService {

    @Autowired
    GoodsTypeRepository goodsTypeRepository;

    @Override
    public GoodsType findOne(String id) {
        return goodsTypeRepository.findOne(id);
    }
}
