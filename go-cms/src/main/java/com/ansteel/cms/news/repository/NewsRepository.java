package com.ansteel.cms.news.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ansteel.cms.news.domain.News;
import com.ansteel.core.repository.ProjectRepository;

public interface NewsRepository extends ProjectRepository<News, String> {

	Page<News> findByCategoryIdAndNewType(String categoryId, Integer newType,
			Pageable pageable);
	
	/*@Query("from News a where a.categoryId = ?1 and newType = ?2")
	Page<News> findByCategoryIdAndNewType(String categoryId, Integer newType,
			Pageable pageable);*/

	/**
	 * 审核
	 * 
	 * @param id
	 * @param value
	 */
	@Modifying
	@Query("update News a set a.newType = ?2 where a.id = ?1")
	void approve(String id, int value);

	@Modifying
	@Query("update News a set a.categoryId = ?2 where a.id = ?1")
	void category(String id, String categoryId);
}
