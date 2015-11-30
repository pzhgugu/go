package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsAttribute;
import com.ansteel.shop.goods.domain.GoodsAttributeValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2015/11/27.
 */
public interface GoodsAttributeValueRepository extends ProjectRepository<GoodsAttributeValue, String> {

    Page<GoodsAttributeValue> findByGoodsAttribute_Id(String attId, Pageable pageable);
}
