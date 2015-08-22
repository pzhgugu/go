package com.ansteel.cms.news.service;

import com.ansteel.cms.news.domain.NewsItems;

public interface NewsItemsService {

	NewsItems findOne(String id);

	NewsItems save(NewsItems entity);

	/**
	 * 后台发布资讯
	 * @param entity
	 * @return
	 */
	NewsItems backstagePublish(NewsItems entity);

	void delect(String id);

	void cream(String id, int value);

	void top(String id, int value);

	void comment(String id, int value);

	void grade(String id, int value);

}
