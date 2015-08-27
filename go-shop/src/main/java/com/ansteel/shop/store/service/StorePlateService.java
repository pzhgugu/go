package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StorePlate;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2015/8/25.
 */
public interface StorePlateService {
    /**
     * 保存版式
     * @param storePlate
     */
    StorePlate save(StorePlate storePlate);

    /**
     * 查询版式
     * @param id
     * @return
     */
    StorePlate findOne(String id);

    /**
     * 查询所有
     * @param sortType
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<StorePlate> findAll(String sortType, Integer curPage, int pageSize);

    void delete(String[] idArray);

    void delete(String id);
}
