package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.StorePlate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/8/25.
 */
public interface StorePlateRepository extends ProjectRepository<StorePlate,String> {

    Page<StorePlate> findByStoreId(String id, Pageable pageable);

    StorePlate findByIdAndStoreId(String id, String storeId);

    List<StorePlate> findByStoreId(String id);
}
