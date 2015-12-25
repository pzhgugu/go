package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.CriteriaUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.repository.GoodsRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsRepository goodsRepository;

    @Override
    @Transactional
    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Integer grossInventory(String id) {
        List<Goods> goodsList=goodsRepository.findByGoodsCommonId(id);
        Integer gross=0;
        for(Goods g:goodsList){
            Integer saleNum = g.getGoodsSaleNum()==null?0:g.getGoodsSaleNum();
            Integer inventory=(g.getGoodsStorage()-saleNum);
            Assert.isTrue(inventory>=0,g.getId()+",商品异常，售出大于库存");
            gross+=inventory;
        }
        return gross;
    }

    @Override
    public List<Goods> findByGoodsCommonId(String commonId) {
        return goodsRepository.findByGoodsCommonId(commonId);
    }

    @Override
    @Transactional
    public void delect(List<Goods> goodsList) {
        goodsRepository.delete(goodsList);
    }

    @Override
    @Transactional
    public void delect(Goods goods) {
        goodsRepository.delete(goods);
    }

    @Override
    @Transactional
    public void delectByCommonId(String commonId,String storeId) {
        goodsRepository.delectGoodsCommonId(commonId,storeId);
    }

    @Override
    public Goods findOne(String id) {
        return goodsRepository.findOne(id);
    }


}
