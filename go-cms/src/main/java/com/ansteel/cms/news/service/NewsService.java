package com.ansteel.cms.news.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ansteel.cms.news.core.NewsType;
import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.core.utils.QueryMapping;

public interface NewsService {

	/**
	 * 
	 * @param categoryId 分类id
	 * @param type 资讯类型(发件箱、待审箱、垃圾箱)
	 * @param order
	 * @param pageable
	 * @return
	 */
	Page findByCategoryIdAndNewType(String categoryId, NewsType type, Pageable pageable);

	Page find(Integer type, String pid, String order,
			List<QueryMapping> queryList, Pageable pageable);

	List treeQuery(String pid);

	void operation(String type, String[] ids);

	/**
	 * 审核
	 * @param id
	 * @param value
	 */
	void approve(String[] ids, int value);

	void delect(String[] ids);

	/**
	 * 精华
	 * @param ids
	 * @param value
	 */
	void cream(String[] ids, int value);

	/**
	 * 置顶
	 * @param ids
	 * @param value
	 */
	void top(String[] ids, int value);

	/**
	 * 评论
	 * @param ids
	 * @param value
	 */
	void comment(String[] ids, int value);

	/**
	 * 等级
	 * @param ids
	 * @param value
	 */
	void grade(String[] ids, int value);

	void category(String type, String[] ids);


}
