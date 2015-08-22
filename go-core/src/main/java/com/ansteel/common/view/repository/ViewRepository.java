package com.ansteel.common.view.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ansteel.common.view.domain.View;
import com.ansteel.core.repository.ProjectRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图数据仓库。
 */
public interface ViewRepository extends ProjectRepository<View, String> {

	/**
	 * 根据名称获取视图
	 * @param name
	 * @return
	 */
	View findOneByName(String name);
	
	/**
	 * 根据url路径获取视图
	 * @param name
	 * @return
	 */
	View findOneByUrlPath(String name);
}
