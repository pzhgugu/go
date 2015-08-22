package com.ansteel.common.menu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ansteel.common.menu.domain.Menu;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：菜单持久层。  
 */
public interface MenuRepository extends ProjectRepository<Menu,String>{

	List<Menu> findByParentIsNull();
	
	@Query("select m from Menu m order by layer,parent.id,displayOrder")
	List<Menu> findAllOrderByParentDisplayOrder();
}
