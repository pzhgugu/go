package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreGoodsClass;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public interface StoreGoodsClassService {
    Page<StoreGoodsClass> findCurrentByIsParentNull(String sort, Integer curPage, int size);

    StoreGoodsClass findCurrentOne(String id);

    void deleteCurrent(String id);

    List<StoreGoodsClass> findCurrentByIsParentNull();

    void deleteCurrents(String[] ids);

    StoreGoodsClass save(StoreGoodsClass storeGoodsClass);

    StoreGoodsClass saveCurrent(StoreGoodsClass storeGoodsClass, String parentId);

    List<StoreGoodsClass> findByIsParentNull(String storeId);
}
