package com.ansteel.common.security.service;

import com.ansteel.common.security.domain.Resources;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源服务接口。  
 */
public interface ResourcesService {
	Resources findOneByName(String name);

	Resources save(Resources resources);

	Resources findOne(String id);

	void delect(String id);
}
