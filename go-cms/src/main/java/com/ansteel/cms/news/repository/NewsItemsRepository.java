package com.ansteel.cms.news.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ansteel.cms.news.domain.NewsItems;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：资讯持久层。  
 */
public interface NewsItemsRepository extends ProjectRepository<NewsItems, String> {

	/**
	 * 等级
	 * @param id
	 * @param value
	 */
	@Modifying
	@Query("update NewsItems a set a.grade = ?2 where a.id = ?1")
	void grade(String id, int value);

	/**
	 * 回复
	 * @param id
	 * @param value
	 */
	@Modifying
	@Query("update NewsItems a set a.allowreply = ?2 where a.id = ?1")
	void comment(String id, int value);

	/**
	 * 置顶
	 * @param id
	 * @param value
	 */
	@Modifying
	@Query("update NewsItems a set a.top = ?2 where a.id = ?1")
	void top(String id, int value);

	/**
	 * 精华
	 * @param id
	 * @param value
	 */
	@Modifying
	@Query("update NewsItems a set a.digest = ?2 where a.id = ?1")
	void cream(String id, int value);
	
}
