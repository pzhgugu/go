package com.ansteel.core.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：项目通用数据持久层。  
 */
@NoRepositoryBean
public interface ProjectRepository <T, ID extends Serializable> extends JpaRepository<T, ID> {
	/**
	 * 查询Specification方式
	 * @param spec
	 * @return
	 */
	List<Tuple> findTuple(Specification spec);
	
	/**
	 * 查询Specification方式，带分页
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page findTuple(Specification spec, Pageable pageable);

	/**
	 * 查询Specification方式，树查询
	 * @param spec
	 * @return
	 */
	List findTree(Specification<T> spec);

	/**
	 * 查询Specification方式，带分页
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<T> find(Specification<T> spec, Pageable pageable);
	/**
	 * 查询Specification方式
	 * @param spec
	 * @return
	 */
	List find(Specification<T> spec);
	/**
	 * 查询Specification方式
	 * @param spec
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List find(Specification<T> spec,Integer firstResult,Integer maxResults);
	/**
	 * 查询
	 * @param props 查询条件
	 * @return
	 */
	List find(Map<String ,Object> props);
	/**
	 * 查询带分页
	 * @param jpqlQuery 
	 * @param whereMap
	 * @param countQuery
	 * @param countMap
	 * @param pageable
	 * @return
	 */
	Page findMap(String jpqlQuery, Map<String, Object> whereMap,String countQuery, Map<String, Object> countMap,  Pageable pageable);
}
