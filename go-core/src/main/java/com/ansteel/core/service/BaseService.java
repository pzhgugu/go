package com.ansteel.core.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.ansteel.core.utils.QueryMapping;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：通用服务层接口。  
 */
public interface BaseService {
	
	<T extends BaseEntity> List<T>  findAll(Class<T> clazz);
	
	<T extends BaseEntity> List<T>  findAll(Class<T> clazz,Sort sort);

	<T extends BaseEntity> List<T>  findAll(Class<T> clazz,Iterable<String> ids);

	<T extends BaseEntity> List<T> save(List<T> entities);

	<T extends BaseEntity> void flush(Class<T> clazz);

	<T extends BaseEntity> T  saveAndFlush(T entity);

	<T extends BaseEntity> void deleteInBatch(List<T> entities);

	<T extends BaseEntity> void deleteAllInBatch(Class<T> clazz);

	<T extends BaseEntity> T getOne(Class<T> clazz,String id);

	<T extends BaseEntity> Page<T> findAll(Class<T> clazz,Pageable pageable);

	<T extends BaseEntity> T save(T entity);

	<T extends BaseEntity>  T findOne(Class<T> clazz,String id);

	<T extends BaseEntity> boolean exists(Class<T> clazz,String id);

	<T extends BaseEntity> long count(Class<T> clazz);

	<T extends BaseEntity> void delete(Class<T> clazz,String id);

	<T extends BaseEntity> void delete(T entity);

	<T extends BaseEntity> void delete(List<T> entities);

	<T extends BaseEntity> void deleteAll(Class<T> clazz);
	/**
	 * 查询树节点
	 * @param clazz
	 * @param pid
	 * @return
	 */
	List treeQuery(Class clazz,final String pid);

	/**
	 * 分页查询
	 * @param clazz 查询类class
	 * @param key 父类名称
	 * @param value 父类名称值
	 * @param order 排序字段
	 * @param pageable 分页
	 * @return
	 */
	Page find(Class clazz, String key, String value, String order,
			Pageable pageable);

	/**
	 * 分页查询
	 * @param clazz
	 * @param key
	 * @param value
	 * @param order
	 * @param queryList ajax传递表单查询映射
	 * @param pageable
	 * @return
	 */
	Page find(Class clazz, String key, String value, String order,
			List<QueryMapping> queryList, Pageable pageable);

	/**
	 * 查询返回map list
	 * @param clazz
	 * @param key
	 * @param value
	 * @param order
	 * @param queryList
	 * @return
	 */
	List findMap(Class clazz, String key, String value, String order,
			List<QueryMapping> queryList);

	<T extends BaseEntity> List<T> findAll(Class clazz, String key, String value, String order);

	List<OperEntity> findByNameNode(Class clazz,
			String nameValue, String nodeName, String nodeId);

	<T extends OperEntity> T findOneByName(Class clazz, String name);
	
}
