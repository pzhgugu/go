package com.ansteel.common.security.repository;

import com.ansteel.common.security.domain.Resources;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源持久层。  
 */
public interface ResourcesRepository extends ProjectRepository<Resources, String> {
	Resources findOneByName(String name);
}
