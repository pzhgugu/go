package com.ansteel.shop.goods.repository;

import java.util.List;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsClassStaple;

public interface GoodsClassStapleRepository  extends ProjectRepository<GoodsClassStaple,String>{

	List<GoodsClassStaple> findByStoreId(String id);

	/**
	 * 查询商品分类是否在常用分类中
	 * @param gcid3
	 * @param StoreId
	 * @return
	 */
	GoodsClassStaple findOneByGcId3AndStoreId(String gcid3,String StoreId);

}
