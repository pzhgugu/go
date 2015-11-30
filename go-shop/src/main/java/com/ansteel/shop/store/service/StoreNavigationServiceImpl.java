package com.ansteel.shop.store.service;

import com.ansteel.core.utils.BeanUtils;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreNavigation;
import com.ansteel.shop.store.repository.StoreNavigationRepository;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
@Service
public class StoreNavigationServiceImpl implements StoreNavigationService {
    @Autowired
    StoreNavigationRepository storeNavigationRepository;

    @Autowired
    StoreService storeService;

    @Override
    public List<StoreNavigation> getCurrentStoreNavigation() {
        Store store = storeService.getCurrentStore();
        Assert.notNull(store, "当前店铺不存在！");
        return this.findByStoreId(store.getId());
    }

    @Override
    public List<StoreNavigation> findByStoreId(String storeId) {
        return storeNavigationRepository.findByStoreId(storeId);
    }

    @Override
    public StoreNavigation findOne(String id) {
        return storeNavigationRepository.findOne(id);
    }

    @Override
    public StoreNavigation saveCurrent(StoreNavigation storeNavigation) {
        Store store = storeService.getCurrentStore();
        Assert.notNull(store, "当前店铺不存在！");
        storeNavigation.setStoreId(store.getId());
        if (StringUtils.hasText(storeNavigation.getId())) {
            StoreNavigation dataBase = storeNavigationRepository.findOne(storeNavigation.getId());
            try {
                BeanUtils.applyIf(dataBase, storeNavigation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return storeNavigationRepository.save(dataBase);
        }
        return storeNavigationRepository.save(storeNavigation);
    }

    @Override
    public void delete(String id) {
        storeNavigationRepository.delete(id);
    }
}
