package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreWarning;

/**
 * Created by Administrator on 2015/12/11.
 */
public interface StoreWarningService {
    StoreWarning findCurrentStore();

    StoreWarning save(Integer value);
}
