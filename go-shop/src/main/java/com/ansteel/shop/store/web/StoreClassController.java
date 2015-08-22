package com.ansteel.shop.store.web;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.store.domain.StoreClass;

@Controller
@RequestMapping(value = "/storeclass")
public class StoreClassController extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo storeClass = new EntityInfo();
		storeClass.setClazz(StoreClass.class);
		entityInfos.add(storeClass);
		return entityInfos;
	}

}
