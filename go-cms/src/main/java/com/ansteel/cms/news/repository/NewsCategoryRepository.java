package com.ansteel.cms.news.repository;

import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.core.repository.ProjectRepository;

public interface NewsCategoryRepository extends ProjectRepository<NewsCategory,String>{

	NewsCategory findOneByName(String name);

}
