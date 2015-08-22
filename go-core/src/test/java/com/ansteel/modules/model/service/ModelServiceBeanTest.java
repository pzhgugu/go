package com.ansteel.modules.model.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.core.repository.BaseRepository;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;

public class ModelServiceBeanTest extends SpringBaseTest{
	@Autowired
	protected BaseService baseService;
	
	@Autowired
	ModelService modelService;
	
	@Test
	public void test() {
		Models models = modelService.findOneByClazz("com.ansteel.modules.model.domain.EntityFieldsGrid");
		logger.info(models.getAlias());
	}
	
	@Autowired
	BaseRepository baseRepository;

	@Test
	public void testSql(){
		List<Models> list = baseService.findAll(Models.class);
		List<Models> list1 = modelService.findAll();
		for(Models entity:list){
			Collection<EntityFieldsCategory> eList = entity.getFieldsCategory();
			
			if(eList.size()>0){
			}else{
				System.out.println(eList);
			}
		}
		
		for(Models entity:list1){
			Collection<EntityFieldsCategory> eList = entity.getFieldsCategory();
			
			if(eList.size()>0){
			}else{
				System.out.println(eList);
			}
		}
	}
}
