package com.ansteel.core.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Tuple;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 基本数据持久层接口。  
 */
public interface BaseRepository<T, ID extends Serializable> {
	/**
	 * 类初始化
	 * @param clazz
	 */
	void init(Class<T> clazz);

	/**
	 * 查询所有
	 * @return
	 */
	List<T> findAll();

	/**
	 * 查询所有带排序
	 * @param sort
	 * @return
	 */
	List<T> findAll(Sort sort);

	/**
	 * 查询id集合
	 * @param ids
	 * @return
	 */
	List<T> findAll(Iterable<String> ids);

	/**
	 * 保存实体集合
	 * @param entities
	 * @return
	 */
	<S extends T> List<S> save(Iterable<S> entities);

	/**
	 * 刷新持久层
	 */
	void flush();

	/**
	 * 保存并且刷新
	 * @param entity
	 * @return
	 */
	T saveAndFlush(T entity);

	/**
	 * 删除集合
	 * @param entities
	 */
	void deleteInBatch(Iterable<T> entities);

	/**
	 * 删除所有
	 */
	void deleteAllInBatch();

	/**
	 * 通过id获取实体
	 * @param id
	 * @return
	 */
	T getOne(String id);

	/**
	 * 查询带分页
	 * @param pageable
	 * @return
	 */
	Page<T> findAll(Pageable pageable);

	/**
	 * 保存实体
	 * @param entity
	 * @return
	 */
	<S extends T> S save(S entity);

	/**
	 * 查询
	 * @param id
	 * @return
	 */
	T findOne(String id);

	boolean exists(String id);

	/**
	 * 获取记录数量
	 * @return
	 */
	long count();

	/**
	 * 删除id记录
	 * @param id
	 */
	void delete(String id);

	/**
	 * 删除实体
	 * @param entity
	 */
	void delete(T entity);

	/**
	 * 删除实体集
	 * @param entities
	 */
	void delete(Iterable<T> entities);

	/**
	 * 删除所有
	 */
	void deleteAll();

	/**
	 * 查询Specification方式
	 * @param spec
	 * @return
	 */
	List<Tuple> tupleQuery(Specification<T> spec);

	/**
	 * 查询Specification方式
	 * @param spec
	 * @return
	 */
	List findTree(Specification<T> spec);

	/**
	 * 查询Specification方式,带分页
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

}
