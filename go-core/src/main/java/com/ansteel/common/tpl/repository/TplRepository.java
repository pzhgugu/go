package com.ansteel.common.tpl.repository;

import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.repository.ProjectRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板数据仓库。
 */
public interface TplRepository extends ProjectRepository<Tpl,String>{

	/**
	 * 根据名称获取模板
	 * @param name
	 * @return
	 */
	Tpl findOneByName(String name);
}
