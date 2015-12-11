package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreWarning;
import com.ansteel.shop.store.repository.StoreWarningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
@Service
@Transactional(readOnly = true)
public class StoreWarningServiceImpl implements StoreWarningService {

    @Autowired
    StoreWarningRepository storeWarningRepository;


    @Autowired
    StoreService storeService;

    @Override
    public StoreWarning findCurrentStore() {
        Store store = storeService.getCurrentStore();
      return this.findCurrentStore(store.getId());
    }

    @Override
    @Transactional
    public StoreWarning save(Integer value) {
        Store store = storeService.getCurrentStore();
        StoreWarning storeWarning=this.findCurrentStore(store.getId());
        if(storeWarning==null){
            storeWarning=new StoreWarning();
        }
        storeWarning.setWarningValue(value);
        storeWarning.setStoreId(store.getId());
        return storeWarningRepository.save(storeWarning);
    }

    private StoreWarning findCurrentStore(String storeId) {
        List<StoreWarning> list=storeWarningRepository.findByStoreId(storeId);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
