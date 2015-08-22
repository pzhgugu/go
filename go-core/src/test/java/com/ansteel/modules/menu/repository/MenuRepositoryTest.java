package com.ansteel.modules.menu.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.menu.domain.Menu;
import com.ansteel.common.menu.repository.MenuRepository;
import com.ansteel.core.utils.SpringBaseTest;

public class MenuRepositoryTest extends SpringBaseTest{

	@Autowired
	MenuRepository menuRepository;
	
	@Test
	public void testFindByParentIsNull() {
		List<Menu> list = menuRepository.findByParentIsNull();
		System.out.println(list.size());
	}
	
	@Test
	public void testOrderBy() {
		List<Menu> list = menuRepository.findAllOrderByParentDisplayOrder();
		System.out.println(list.size());
	}

}
