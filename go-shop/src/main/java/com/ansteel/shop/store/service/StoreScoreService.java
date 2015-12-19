package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.Store;

/**
 * 店铺评分
 */
public interface StoreScoreService {

    /**
     * 获取店铺评分
     * @param storeId
     * @return
     */
    StoreScoreModle getStoreScore(String storeId);

    StoreScoreModle getStoreScore(Store store);
}
