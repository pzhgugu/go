package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StoreClass;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface StoreClassRepository extends ProjectRepository<StoreClass, String> {
    List<StoreClass> findByParentIsNullOrderByDisplayOrderDesc();
}
