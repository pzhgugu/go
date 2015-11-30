package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsAttribute;
import com.ansteel.shop.goods.domain.GoodsAttributeValue;
import com.ansteel.shop.goods.repository.GoodsAttributeValueRepository;
import groovy.transform.TailRecursive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Administrator on 2015/11/27.
 */
@Service
@Transactional(readOnly = true)
public class GoodsAttributeValueServiceImpl implements GoodsAttributeValueService {

    @Autowired
    GoodsAttributeValueRepository goodsAttributeValueRepository;

    @Autowired
    GoodsAttributeService goodsAttributeService;


    @Override
    public Page<GoodsAttributeValue> findByGoodsAttribute_Id(String attId, Pageable pageable) {
        return goodsAttributeValueRepository.findByGoodsAttribute_Id(attId, pageable);
    }

    @Override
    @Transactional
    public GoodsAttributeValue save(GoodsAttributeValue baseEntity) {
        return goodsAttributeValueRepository.save(baseEntity);
    }

    @Override
    @Transactional
    public void save(GoodsAttributeValue goodsAttributeValue, String value) {
        if (StringUtils.hasText(value)) {
            GoodsAttribute goodsAttribute = goodsAttributeService.findOne(value);
            goodsAttributeValue.setGoodsAttribute(goodsAttribute);
        }
        goodsAttributeValueRepository.save(goodsAttributeValue);
    }

    @Override
    @Transactional
    public void delete(String id) {
        GoodsAttributeValue goodsAttributeValue = goodsAttributeValueRepository.findOne(id);
        goodsAttributeValueRepository.delete(goodsAttributeValue);
        this.updateGoodsAttributeByAttrValue(goodsAttributeValue.getGoodsAttribute().getId());
    }

    @Override
    @Transactional
    public void updateGoodsAttributeByAttrValue(String value) {
        GoodsAttribute goodsAttribute = goodsAttributeService.findOne(value);
        Collection<GoodsAttributeValue> goodsAttributeValueList = goodsAttribute.getGoodsAttributeValueList();
        StringBuffer sb = new StringBuffer();
        for (GoodsAttributeValue gav : goodsAttributeValueList) {
            sb.append(gav.getName() + ",");
        }
        String names = "";
        if (sb.length() > 0) {
            names = sb.substring(0, sb.length() - 1);
        }
        goodsAttribute.setAttrValue(names);
        goodsAttributeService.save(goodsAttribute);
    }
}
