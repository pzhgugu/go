package com.ansteel.shop.goods.repository;


import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.shop.goods.domain.GoodsClass;

public class GoodsClassRepositoryTest extends SpringBaseTest {
	
	@Autowired
	GoodsClassRepository goodsClassRepository;

	@Test
	@Ignore
	public void test() {
		List<GoodsClass> parentList = goodsClassRepository.findByParent_Id("402897814edda989014edddfab990000");
		System.out.println(parentList.size());
	}

}
