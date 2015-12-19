package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.MultipleScoreModle;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StoreRepository extends ProjectRepository<Store,String>{

	Store findOneByMemberId(String userId);

	List<Store> findBySellerName(String sellerName);

	List<Store> findByName(String storeName);

	@Query("select new com.ansteel.shop.store.service.MultipleScoreModle(avg(s.storeDesccredit),avg(s.storeServicecredit),avg(s.storeDeliverycredit),max(s.storeDesccredit),max(s.storeServicecredit),max(s.storeDeliverycredit)) from Store s where s.scId=?1")
	MultipleScoreModle getMultipleScore(String storeScId);
}
