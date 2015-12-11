package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StoreWarning;

import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public interface StoreWarningRepository extends ProjectRepository<StoreWarning,String> {
    List<StoreWarning> findByStoreId(String storeId);
}
