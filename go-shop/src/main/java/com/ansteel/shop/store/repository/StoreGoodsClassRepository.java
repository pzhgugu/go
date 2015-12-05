package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StoreGoodsClass;

import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
public interface StoreGoodsClassRepository extends ProjectRepository<StoreGoodsClass, String> {
    List<StoreGoodsClass> findByParentIsNullAndStoreId(String id);

    List<StoreGoodsClass> findByName(String name);
}
