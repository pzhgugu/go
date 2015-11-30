package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsType;
import com.ansteel.shop.goods.repository.GoodsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
@Service
public class GoodsTypeServiceImpl implements GoodsTypeService {

    @Autowired
    GoodsTypeRepository goodsTypeRepository;

    @Override
    public GoodsType findOne(String id) {
        return goodsTypeRepository.findOne(id);
    }

    @Override
    public List<GoodsType> findByGoodsClass(String id) {
        return goodsTypeRepository.findByGoodsClass_id(id);
    }
}
