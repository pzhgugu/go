package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreNavigation;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public interface StoreNavigationService {
    List<StoreNavigation> getCurrentStoreNavigation();

    List<StoreNavigation> findByStoreId(String storeId);

    StoreNavigation findOne(String id);

    StoreNavigation saveCurrent(StoreNavigation storeNavigation);

    void delete(String id);
}
