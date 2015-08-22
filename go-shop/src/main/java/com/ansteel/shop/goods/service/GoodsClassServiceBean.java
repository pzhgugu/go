package com.ansteel.shop.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.repository.GoodsClassRepository;

@Service
@Transactional(readOnly=true)
public class GoodsClassServiceBean implements GoodsClassService {
	
	@Autowired
	GoodsClassRepository goodsClassRepository;

	@Override
	public List<GoodsClass> findByParentIsNull() {
		return goodsClassRepository.findByParentIsNull();
	}

	@Override
	public List<GoodsClass> findByParentId(String id) {
		return goodsClassRepository.findByParent_Id(id);
	}

	@Override
	public GoodsClass findOne(String id) {
		return goodsClassRepository.findOne(id);
	}

}
