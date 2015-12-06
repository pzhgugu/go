package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.repository.GoodsCommonRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/6.
 */
@Service
public class GoodsCommonServiceImpl implements GoodsCommonService {

    @Autowired
    GoodsCommonRepository goodsCommonRepository;
    @Autowired
    StoreService storeService;
    @Autowired
    GoodsImagesService goodsImagesService;

    @Override
    @Transactional
    public GoodsCommon save(GoodsCommon goods) {
        Store store = storeService.getCurrentStore();
        goods.setStoreId(store.getId());
        goods.setStoreName(store.getName());
        GoodsCommon newGoods=goodsCommonRepository.save(goods);
        goodsImagesService.saveDefault(newGoods.getId(), newGoods.getGoodsImage(), newGoods.getStoreId());
        return newGoods;
    }

    @Override
    public GoodsCommon findOneByStoreIdAndId(String goodsId) {
        Store store = storeService.getCurrentStore();
        return goodsCommonRepository.findOneByStoreIdAndId(store.getId(), goodsId);
    }

    @Override
    public Page<GoodsCommon> findCurrentSaleAll(final String classId, final String sortType, Integer curPage, int pageSize, final String name, final String value) {
        if (curPage == null) {
            curPage = 0;
        } else if (curPage > 0) {
            curPage = curPage - 1;
        }
        Pageable pageable = new PageRequest(curPage, pageSize);
        Store store = storeService.getCurrentStore();
        final String storeId = store.getId();
        Specification<GoodsCommon> specification = new Specification<GoodsCommon>() {
            public Predicate toPredicate(Root<GoodsCommon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (StringUtils.hasText(sortType)) {
                    query.orderBy(cb.asc(root.get(sortType)));
                } else {
                    query.orderBy(cb.asc(root.get("created")));
                }
                List<Predicate> predicate = new ArrayList<>();

                predicate.add(cb.equal(root.get("storeId"), storeId));
                predicate.add(cb.equal(root.get("goodsState"), 1));
                if (StringUtils.hasText(classId)) {
                    predicate.add(cb.equal(root.get("gcId"), classId));
                }
                if (StringUtils.hasText(value)) {
                    predicate.add(cb.equal(root.get(name), value));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        return goodsCommonRepository.find(specification, pageable);
    }

    @Override
    public void unShow(String[] goodsIdArray) {
        for (String goodsId : goodsIdArray) {
            this.unShow(goodsId);
        }
    }

    @Override
    public void unShow(String goodsId) {
        goodsCommonRepository.updateGoodsState(goodsId, 0);
    }

    @Override
    public void adEdit(String[] goodsIdArray, String adWord) {
        for (String goodsId : goodsIdArray) {
            goodsCommonRepository.updateAdWord(goodsId, adWord);
        }
    }

    @Override
    public GoodsCommon savePosition(String commonid, String plateTop, String plateBottom) {
        GoodsCommon goods = this.findOneByStoreIdAndId(commonid);
        Assert.notNull(goods, commonid + ",此商品id无效！");
        goods.setPlateidTop(plateTop);
        goods.setPlateidBottom(plateBottom);
        return this.save(goods);
    }

    @Override
    public void savePosition(String[] ids, String plateTop, String plateBottom) {
        for (String id : ids) {
            this.savePosition(id, plateTop, plateBottom);
        }
    }
}
