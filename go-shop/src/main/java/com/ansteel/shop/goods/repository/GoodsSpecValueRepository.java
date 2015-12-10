package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsSpecValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public interface GoodsSpecValueRepository extends ProjectRepository<GoodsSpecValue, String> {
    List<GoodsSpecValue> findByStoreIdAndSpId(String storeId, String spId);

    List<GoodsSpecValue> findByStoreIdOrderByStoreIdAsc(String storeId);

    List<GoodsSpecValue> findByStoreIdAndGcId(String id,String gcId);
}
