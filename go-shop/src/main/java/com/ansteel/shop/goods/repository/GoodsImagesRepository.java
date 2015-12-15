package com.ansteel.shop.goods.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsImages;

public interface GoodsImagesRepository extends ProjectRepository<GoodsImages,String>{

	@Query("select m from GoodsImages m where m.storeId=?1 and m.goodsId=?2 order by isDefault DESC,goodsImageSort")
	List<GoodsImages> findByStoreIdAndGoodsId(String storeId,String goodsId);

	@Modifying
	@Query("delete GoodsImages g where g.goodsId = ?1 and g.storeId = ?2")
	void delectGoodsId(String goodsId,String storeId);
}
