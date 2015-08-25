package com.ansteel.shop.goods.repository;


import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.Goods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GoodsRepository extends ProjectRepository<Goods,String>{

	Goods findOneByStoreIdAndId(String storeId, String id);

	/**
	 * 更新商品上下架
	 * @param goodsId
	 * @param state
	 */
	@Modifying
	@Query("update Goods g set g.goodsState = ?2 where g.id = ?1")
	int updateGoodsState(String goodsId, int state);

	/**
	 * 更新广告词
	 * @param goodsId
	 * @param adWord
	 * @return
	 */
	@Modifying
	@Query("update Goods g set g.adWord = ?2 where g.id = ?1")
	int updateAdWord(String goodsId, String adWord);
}
