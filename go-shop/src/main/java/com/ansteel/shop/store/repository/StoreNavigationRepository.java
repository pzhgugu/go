package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StoreNavigation;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public interface StoreNavigationRepository extends ProjectRepository<StoreNavigation, String> {
    List<StoreNavigation> findByStoreId(String storeId);
}
