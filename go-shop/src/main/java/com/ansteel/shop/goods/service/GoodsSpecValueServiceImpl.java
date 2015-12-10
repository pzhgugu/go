package com.ansteel.shop.goods.service;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.GoodsSpec;
import com.ansteel.shop.goods.domain.GoodsSpecValue;
import com.ansteel.shop.goods.repository.GoodsSpecValueRepository;
import com.ansteel.shop.goods.web.GoodsSpecValueModel;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/24.
 */
@Service
@Transactional(readOnly = true)
public class GoodsSpecValueServiceImpl implements GoodsSpecValueService {

    @Autowired
    GoodsSpecValueRepository goodsSpecValueRepository;

    @Autowired
    StoreService storeService;

    @Override
    public List<GoodsSpecValue> findByStoreIdAndSpId(String spId) {
        Store store = storeService.getCurrentStore();
        return goodsSpecValueRepository.findByStoreIdAndSpId(store.getId(), spId);
    }

    @Override
    public List<GoodsSpecValue> findByStoreIdAndSpId(String storeId, String spId) {
        return goodsSpecValueRepository.findByStoreIdAndSpId(storeId, spId);
    }

    @Override
    @Transactional
    public void save(GoodsSpecValueModel goodsSpecValueModel) {
        Store store = storeService.getCurrentStore();
        List<GoodsSpecValue> goodsSpecValueDataBaseList = this.findByStoreIdAndSpId(store.getId(), goodsSpecValueModel.getSpId());
        Map<String, GoodsSpecValue> map = new HashMap<>();
        for (GoodsSpecValue v : goodsSpecValueDataBaseList) {
            map.put(v.getId(), v);
        }
        List<GoodsSpecValue> goodsSpecValueList = goodsSpecValueModel.getSpecValues();
        for (GoodsSpecValue v : goodsSpecValueList) {
            String id = v.getId();
            v.setStoreId(store.getId());
            v.setGcId(goodsSpecValueModel.getGcId());
            v.setSpId(goodsSpecValueModel.getSpId());
            if (map.containsKey(id)) {
                GoodsSpecValue dEntity = map.get(id);
                try {
                    BeanUtils.applyIf(dEntity, v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goodsSpecValueRepository.save(dEntity);
            } else {
                goodsSpecValueRepository.save(v);
            }
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        Store store = storeService.getCurrentStore();
        GoodsSpecValue goodsSpecValue = goodsSpecValueRepository.findOne(id);
        if (store.getId().equals(goodsSpecValue.getStoreId())) {
            goodsSpecValueRepository.delete(goodsSpecValue);
        } else {
            throw new PageException(id + "，异常删除！");
        }
    }

    @Override
    public List<GoodsSpecValue> findByStoreIdOrderByStoreIdAsc(String storeId) {
        return goodsSpecValueRepository.findByStoreIdOrderByStoreIdAsc(storeId);
    }

    @Override
    @Transactional
    public GoodsSpecValue save(String gcId, String spId, String name) {
        GoodsSpecValue goodsSpecValue = new GoodsSpecValue();
        Store store = storeService.getCurrentStore();
        goodsSpecValue.setStoreId(store.getId());
        goodsSpecValue.setSpId(spId);
        goodsSpecValue.setGcId(gcId);
        goodsSpecValue.setName(name);
        goodsSpecValue.setSort(255);
        return goodsSpecValueRepository.save(goodsSpecValue);
    }

    @Override
    public List<GoodsSpecValue> findById(String[] spvIdArray) {
        List<GoodsSpecValue> list = new ArrayList<>();
        for(String id :spvIdArray){
            list.add(goodsSpecValueRepository.findOne(id));
        }
        return list;
    }

    @Override
    public List<GoodsSpecValue> findByCurrentStoreIdAndGcId(String gcId) {
        Store store = storeService.getCurrentStore();
        return goodsSpecValueRepository.findByStoreIdAndGcId(store.getId(),gcId);
    }


}
