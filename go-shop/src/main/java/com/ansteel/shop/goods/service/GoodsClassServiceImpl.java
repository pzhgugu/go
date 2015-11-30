package com.ansteel.shop.goods.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.repository.GoodsClassRepository;
import org.springframework.util.Assert;

@Service
@Transactional(readOnly=true)
public class GoodsClassServiceImpl implements GoodsClassService {
	
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

	@Override
	public List<GoodsClass> findByLayer(int layer) {
		return goodsClassRepository.findByLayer(layer);
	}

	@Override
	public Collection<GoodsClass> findChildren(String id) {
		GoodsClass goodsClass = this.findOne(id);
		Assert.notNull(goodsClass, id + ",记录不存在！");
		return goodsClass.getChildren();
	}

	@Override
	public List<GoodsClass> findAll() {
		return goodsClassRepository.findAll();
	}

}
