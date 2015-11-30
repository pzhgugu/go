package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsType;

import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface GoodsTypeRepository extends ProjectRepository<GoodsType,String> {
    List<GoodsType> findByGoodsClass_id(String id);
}
