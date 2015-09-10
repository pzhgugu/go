package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsBrand;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface GoodsBrandRepository extends ProjectRepository<GoodsBrand,String> {
    GoodsBrand findOneByBrandName(String brandName);
}
