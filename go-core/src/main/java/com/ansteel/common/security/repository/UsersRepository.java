package com.ansteel.common.security.repository;

import com.ansteel.common.security.domain.Users;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：用户持久层。  
 */
public interface UsersRepository extends ProjectRepository<Users, String> {

	  Users findOneByName(String name);
}
