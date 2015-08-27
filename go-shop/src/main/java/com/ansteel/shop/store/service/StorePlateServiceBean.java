package com.ansteel.shop.store.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.repository.StorePlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/8/25.
 */
@Service
@Transactional(readOnly = true)
public class StorePlateServiceBean implements StorePlateService {

    @Autowired
    StorePlateRepository storePlateRepository;

    @Autowired
    StoreService storeService;


    @Override
    @Transactional
    public StorePlate save(StorePlate storePlate) {
        storePlate.setStoreId(storeService.getCurrentStore().getId());
        return storePlateRepository.save(storePlate);
    }

    @Override
    public StorePlate findOne(String id) {
        return storePlateRepository.findOne(id);
    }

    @Override
    public Page<StorePlate> findAll(String sortType, Integer curPage, int pageSize) {
        if(curPage==null){
            curPage=0;
        }else if(curPage>0){
            curPage=curPage-1;
        }
        Pageable pageable =null;
        if(StringUtils.hasText(sortType)){
            Sort sort =new Sort(Sort.Direction.DESC, sortType);
            pageable = new PageRequest(curPage,pageSize,sort);
        }else {
            pageable = new PageRequest(curPage,pageSize);
        }
        return storePlateRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void delete(String[] idArray) {
        for(String id:idArray){
            this.delete(id);
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        storePlateRepository.delete(id);
    }
}
