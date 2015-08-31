package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StorePlate;
import org.springframework.data.domain.Page;

import java.util.List;

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
     * 删除当前店铺ids
     * @param idArray
     */
    void deleteCurrent(String[] idArray);

    /**
     * 删除id
     * @param id
     */
    void delete(String id);

    /**
     * 删除当前店铺id
     * @param id
     * @param storeId
     */
    void deleteCurrent(String id, String storeId);

    /**
     * 查询当前店铺
     * @param position 位置
     * @param name 名称
     * @param sortType
     * @param curPage
     * @param pageSize
     * @return
     */
    Page<StorePlate> findByStoreId(String position, String name, String sortType, Integer curPage, int pageSize);


    /**
     * 查询当前店铺关联的版式
     * @return
     */
    List<StorePlate> findByStoreId();
}
