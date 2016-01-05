package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.GoodsConsult;
import org.springframework.data.domain.Page;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface GoodsConsultService {
    GoodsConsult save(GoodsConsult goodsConsult);

    Page<GoodsConsult> findByGoodsIdOrderByCreatedDesc(String goodsId, Integer curPage,int size);
}
