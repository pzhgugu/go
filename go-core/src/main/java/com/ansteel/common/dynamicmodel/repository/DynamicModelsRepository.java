package com.ansteel.common.dynamicmodel.repository;

import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模型持久层。  
 */
public interface DynamicModelsRepository extends ProjectRepository<DynamicModels, String> {
	DynamicModels findOneByName(String name);
}
