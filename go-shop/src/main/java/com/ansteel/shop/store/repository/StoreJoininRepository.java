package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StoreJoinin;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface StoreJoininRepository extends ProjectRepository<StoreJoinin, String> {

    StoreJoinin findOneByMemberId(String userId);

    List<StoreJoinin> findBySellerName(String sellerName);

    /**
     * 店铺名称
     *
     * @param storeName
     * @return
     */
    List<StoreJoinin> findByStoreName(String storeName);
}
