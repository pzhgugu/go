package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsConsult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Administrator on 2016/1/4.
 */
public interface GoodsConsultRepository  extends ProjectRepository<GoodsConsult,String> {
    Page<GoodsConsult> findByGoodsId(String goodsId, Pageable pageable);
}
