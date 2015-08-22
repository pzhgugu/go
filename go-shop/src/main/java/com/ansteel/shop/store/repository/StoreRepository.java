package com.ansteel.shop.store.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.store.domain.Store;

public interface StoreRepository extends ProjectRepository<Store,String>{

	Store findOneByMemberId(String userId);

}
