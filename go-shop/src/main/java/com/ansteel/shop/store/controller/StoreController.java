package com.ansteel.shop.store.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.constant.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.store.domain.Store;
@Controller
@RequestMapping(value = Public.ADMIN + "/store")
public class StoreController  extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo store = new EntityInfo();
		store.setClazz(Store.class);
		entityInfos.add(store);
		return entityInfos;
	}

}
