package com.ansteel.modules.news.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.cms.news.domain.News;
import com.ansteel.cms.news.repository.NewsRepository;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.SpringBaseTest;

public class NewsRepositoryTest extends SpringBaseTest{
	
	@Autowired
	NewsRepository newsRepository;

	@Test
	@Rollback(false)
	public void testSave() {
		News news=new News();
		news.setCategoryId("1111");
		news.setNewType(1);
		news.setSubject("yige");
		
		newsRepository.save(news);
	}

	@Test
	public void testFindByCategoryIdAndNewType(){
		Page<News> page = newsRepository.findByCategoryIdAndNewType("1111", 1, new PageRequest(0,20));
		page.getSize();
	}
}
