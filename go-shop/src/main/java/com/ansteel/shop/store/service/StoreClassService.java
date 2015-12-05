package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreClass;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
public interface StoreClassService {
    List<StoreClass> findByParentIsNull();
}
