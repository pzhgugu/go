package com.ansteel.shop.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.repository.StoreRepository;

@Service
public class StoreServiceBean implements StoreService{
	
	@Autowired
	StoreRepository storeRepository;

	@Override
	public Store getCurrentStore() {
		UserInfo user = (UserInfo) SecurityContextHolder.getContext()
	    .getAuthentication()
	    .getPrincipal();
		return this.findOneByMemberId(user.getId());
	}

	@Override
	public Store findOneByMemberId(String userId) {
		return storeRepository.findOneByMemberId(userId);
	}

}
