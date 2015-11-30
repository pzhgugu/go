package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreClass;
import com.ansteel.shop.store.repository.StoreClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/10/28.
 */
@Service
public class StoreClassServiceImpl implements StoreClassService {

    @Autowired
    StoreClassRepository storeClassRepository;

    @Override
    public List<StoreClass> findByParentIsNull() {
        return storeClassRepository.findByParentIsNullOrderByDisplayOrderDesc();
    }
}
