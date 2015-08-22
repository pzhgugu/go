package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.Store;


public interface StoreService {

	/**
	 * 获取当前用户的店铺
	 * @return
	 */
	Store getCurrentStore();

	/**
	 * 通过用户id ，获取店铺
	 * @param id
	 * @return
	 */
	Store findOneByMemberId(String userId);

}
