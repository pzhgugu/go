package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsSpecValue;
import com.ansteel.shop.goods.web.GoodsSpecValueModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public interface GoodsSpecValueService {
    List<GoodsSpecValue> findByStoreIdAndSpId(String spId);

    List<GoodsSpecValue> findByStoreIdAndSpId(String storeId, String spId);

    void save(GoodsSpecValueModel goodsSpecValueModel);

    void delete(String id);

    List<GoodsSpecValue> findByStoreIdOrderByStoreIdAsc(String storeId);

    GoodsSpecValue save(String gcId, String spId, String name);
}
