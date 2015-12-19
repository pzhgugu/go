package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsCommon;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Administrator on 2015/12/6.
 */
public interface GoodsCommonRepository extends ProjectRepository<GoodsCommon,String> {
    GoodsCommon findOneByStoreIdAndId(String id, String goodsId);

    /**
     * 更新商品上下架
     * @param goodsId
     * @param state
     */
    @Modifying
    @Query("update GoodsCommon g set g.goodsState = ?2 where g.id = ?1")
    void updateGoodsState(String goodsId, int state);

    /**
     * 更新广告词
     * @param goodsId
     * @param adWord
     * @return
     */
    @Modifying
    @Query("update GoodsCommon g set g.adWord = ?2 where g.id = ?1")
    void updateAdWord(String goodsId, String adWord);

}
