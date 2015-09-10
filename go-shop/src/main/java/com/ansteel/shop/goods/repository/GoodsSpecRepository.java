package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsSpec;

import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
public interface GoodsSpecRepository extends ProjectRepository<GoodsSpec,String> {
    List<GoodsSpec> findBySpName(String spName);
}
