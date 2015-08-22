package com.ansteel.cms.news.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.ansteel.cms.news.domain.NewsCategory;

public interface NewsCategoryService {

	/**
	 * 保存图片，返回附件id
	 * @param inputStream 输入流
	 * @param x 
	 * @param y
	 * @param width
	 * @param height
	 * @param size 大小比例
	 * @return
	 */
	String saveImage(InputStream inputStream, int x, int y,
			int width, int height, int size);
	
	List<NewsCategory> findAll(Sort sort);

	List findTree(Specification spec);

	NewsCategory findOneByName(String name);

	NewsCategory findOne(String id);

}
