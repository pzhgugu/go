package com.ansteel.shop.store.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.shop.store.domain.StoreGrade;

@Controller
@RequestMapping(value = "/storegrade")
public class StoreGradeController extends BaseController{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo storeGrade = new EntityInfo();
		storeGrade.setClazz(StoreGrade.class);
		entityInfos.add(storeGrade);
		return entityInfos;
	}

}
