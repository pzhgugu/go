package com.ansteel.shop.goods.repository;

import java.util.List;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.GoodsClass;

public interface GoodsClassRepository extends ProjectRepository<GoodsClass,String>{

	List<GoodsClass> findByParentIsNull();

	List<GoodsClass> findByParent_Id(String id);

}
