package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.Store;

import java.util.List;

public interface StoreRepository extends ProjectRepository<Store,String>{

	Store findOneByMemberId(String userId);

	List<Store> findBySellerName(String sellerName);

	List<Store> findByName(String storeName);
}
