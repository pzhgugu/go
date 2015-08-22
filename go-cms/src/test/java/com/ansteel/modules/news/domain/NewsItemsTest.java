package com.ansteel.modules.news.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.cms.news.domain.NewsItems;
import com.ansteel.cms.news.repository.NewsItemsRepository;
import com.ansteel.core.utils.SpringBaseTest;

public class NewsItemsTest extends SpringBaseTest{
	
	@Autowired
	NewsItemsRepository newsItemsRepository;

	@Test
	@Rollback(false)
	public void test() {
		
		NewsItems newsItems = new NewsItems();
		newsItems.setSubject("标题");
		newsItems.setPassword("1234");
		System.out.println("-----------------------------------");
		newsItemsRepository.save(newsItems);
		
	}

}
