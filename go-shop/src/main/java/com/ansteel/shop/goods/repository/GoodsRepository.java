package com.ansteel.shop.goods.repository;


import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.Goods;

public interface GoodsRepository extends ProjectRepository<Goods,String>{

	Goods findOneByStoreIdAndId(String storeId, String id);

}
