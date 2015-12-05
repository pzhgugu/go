package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.Store;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface StoreService {

	/**
	 * 获取当前用户的店铺
	 * @return
	 */
	Store getCurrentStore();

	/**
	 * 通过用户id ，获取店铺
	 * @param userId
	 * @return
	 */
	Store findOneByMemberId(String userId);

	/**
	 * 店主卖家用户名
	 *
	 * @param sellerName
	 * @return
	 */
	List<Store> findBySellerName(String sellerName);

	/**
	 * 店铺名称
	 *
	 * @param storeName
	 * @return
	 */
	List<Store> findByStoreName(String storeName);

	Store getCurrentStore(String userId);

	Store save(Store store);

	Store update(Store store, MultipartFile storeLabel, MultipartFile storeBanner);

	Store update(Store store);
}
