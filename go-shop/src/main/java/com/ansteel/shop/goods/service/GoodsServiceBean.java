package com.ansteel.shop.goods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.repository.GoodsRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

@Service
@Transactional(readOnly=true)
public class GoodsServiceBean implements GoodsService {

	@Autowired
	StoreService storeService;

	@Autowired
	GoodsRepository goodsRepository;
	
	@Autowired
	GoodsImagesService goodsImagesService;

	@Override
	@Transactional
	public Goods save(Goods goods) {
		Store store = storeService.getCurrentStore();
		goods.setStoreId(store.getId());
		goods.setStoreName(store.getName());
		Goods newGoods=goodsRepository.save(goods);
		goodsImagesService.saveDefault(newGoods.getId(),newGoods.getGoodsImage(),newGoods.getStoreId());
		return newGoods;
	}

	@Override
	public Goods findOneByStoreIdAndId(String goodsId) {
		Store store = storeService.getCurrentStore();
		return goodsRepository.findOneByStoreIdAndId(store.getId(),goodsId);
	}

}
