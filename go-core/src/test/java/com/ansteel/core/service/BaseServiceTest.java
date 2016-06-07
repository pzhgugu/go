package com.ansteel.core.service;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.SpringBaseTest;

public class BaseServiceTest extends SpringBaseTest{
	
	@Autowired
	BaseService baseService;

	@Test
	@Ignore
	public void testfindByClassKeyValueOrderPageable() {
		Pageable pageable=new PageRequest(0,10);
		
		Page page = baseService.find(EntityFields.class, null, null, null, pageable);
		
		System.out.println(page.getNumber());
		
		Page page2 = baseService.find(EntityFields.class, "models.id", "402897814d234892014d23493f790026", "created", pageable);
		
		System.out.println(page2.getNumber());
	}
	
	@Test
	@Ignore
	public void testfindByClassKeyValueOrderPageableQueryMapping() {
		Pageable pageable=new PageRequest(0,10);
		
	}

}
