package com.ansteel.common.model.repository;


import com.ansteel.common.model.domain.Models;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型持久层。  
 */
public interface ModelsRepository extends ProjectRepository<Models, String> {
	/**
	 * 通过类名称查询模型
	 * @param className
	 * @return
	 */
	Models findOneByClazz(String className);
	/**
	 * 通过名称查询模型
	 * @param name
	 * @return
	 */
	Models findOneByName(String name);

}
