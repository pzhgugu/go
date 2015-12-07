package com.ansteel.shop.goods.repository;


import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.Goods;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends ProjectRepository<Goods,String>{

	List<Goods> findByGoodsCommonId(String id);
}
