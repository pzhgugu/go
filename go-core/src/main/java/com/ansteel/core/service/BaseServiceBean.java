package com.ansteel.core.service;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：通用服务层接口实现。  
 */

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.repository.BaseRepository;
import com.ansteel.core.utils.CriteriaUtils;
import com.ansteel.core.utils.MapUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.StringUtils;

@Service
@Transactional(readOnly = true)
public class BaseServiceBean implements BaseService{
	
	@Autowired
	BaseRepository baseRepository;
	
	public BaseRepository init(Class clazz){
		baseRepository.init(clazz);
		return baseRepository;
	}

	@Override
	public<T extends BaseEntity> List<T> findAll(Class<T> clazz) {
		return init(clazz).findAll();
	}

	@Override
	public <T extends BaseEntity>  List<T> findAll(Class<T> clazz, Sort sort) {
		return init(clazz).findAll(sort);
	}

	@Override
	public <T extends BaseEntity>  List<T> findAll(Class<T> clazz, Iterable<String> ids) {
		return init(clazz).findAll(ids);
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  List<T> save(List<T> entities) {
		if(entities.size()>0){
			return init(entities.get(0).getClass()).save(entities);
		}
		return entities;
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void flush(Class<T> clazz) {
		init(clazz).flush();
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  T saveAndFlush(T entity) {
		return (T) init(entity.getClass()).saveAndFlush(entity);
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void deleteInBatch(List<T> entities) {
		if(entities.size()>0){
			init(entities.get(0).getClass()).deleteInBatch(entities);
		}
	}

	@Override
	@Transactional
	public <T extends BaseEntity> void deleteAllInBatch(Class<T> clazz) {
		init(clazz).deleteAllInBatch();
	}

	@Override
	public <T extends BaseEntity> T getOne(Class<T> clazz, String id) {
		return (T) init(clazz).getOne(id);
	}

	@Override
	public <T extends BaseEntity>  Page<T> findAll(Class<T> clazz, Pageable pageable) {
		return init(clazz).findAll(pageable);
	}

	@Override
	@Transactional
	public <T extends BaseEntity> T save(T entity) {
		return (T) init(entity.getClass()).save(entity);
	}

	@Override
	public <T extends BaseEntity> T findOne(Class<T> clazz, String id) {
		return (T) init(clazz).findOne(id);
	}

	@Override
	public <T extends BaseEntity>  boolean exists(Class<T> clazz, String id) {
		return init(clazz).exists(id);
	}

	@Override
	public <T extends BaseEntity>  long count(Class<T> clazz) {
		return init(clazz).count();
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void delete(Class<T> clazz, String id) {
		init(clazz).delete(id);
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void delete(T entity) {
		BaseRepository e = init(entity.getClass());
		try {
			 BaseEntity s = (BaseEntity) e.findOne(entity.getId());
			 e.delete(s);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void delete(List<T> entities) {
		if(entities.size()>0){
			init(entities.get(0).getClass()).delete(entities);
		}
	}

	@Override
	@Transactional
	public <T extends BaseEntity>  void deleteAll(Class<T> clazz) {
		init(clazz).deleteAll();
	}


	@Override
	public List treeQuery(Class clazz, final String parentId) {
		Specification spec = CriteriaUtils.getTreeSpecification(clazz, parentId);
		return init(clazz).findTree(spec);
	}


	@Override
	public Page find(final Class clazz,final String key, final String value,final String order,
			Pageable pageable) {
		Specification spec = CriteriaUtils.getSpecification(clazz, key, value, order);		
		return init(clazz).find(spec,pageable);
	}

	@Override
	public Page find(final Class clazz, final String key, final String value, final String order,
			final List<QueryMapping> queryList, Pageable pageable) {
		Specification spec = CriteriaUtils.getSpecification(clazz, key, value, order, queryList);
		return init(clazz).find(spec,pageable);
	}

	@Override
	public List findMap(final Class clazz, final String key,final  String value,final  String order,
			final List<QueryMapping> queryList) {
		Specification spec =CriteriaUtils.getSpecification(clazz, key, value, order, queryList);
		List list=init(clazz).find(spec);
		try {
			return (List) MapUtils.toMapList(list);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			e.printStackTrace();
			throw new PageException(e.getMessage());
		}
	}

	@Override
	public List<BaseEntity> findAll(Class clazz, String key, String value,
			String order) {
		Specification<BaseEntity> spec = CriteriaUtils.getSpecification(clazz, key, value, order);
		return init(clazz).find(spec);
	}

	@Override
	public List<OperEntity> findByNameNode(Class clazz, final String nameValue,
			final String nodeName,final String nodeId) {
		Specification<BaseEntity> spec = new Specification<BaseEntity>() {  			
		    public Predicate toPredicate(Root<BaseEntity> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {
		    	Predicate namePredicate = cb.equal(root.get("name"), nameValue);
		    	Predicate nodePredicate;
		    	if(StringUtils.hasText(nodeId)){
		    		nodePredicate=cb.equal(root.get(nodeName).get("id"), nodeId);
				}else{
					nodePredicate=cb.isNull(root.get(nodeName).get("id"));
				}
		    	query.where(cb.and(namePredicate,nodePredicate));
		    	return query.getRestriction(); 		    	
		    }
		};
		return init(clazz).find(spec);
	}

	@Override
	public <T extends OperEntity> T findOneByName(Class clazz, final String name) {
		Specification<BaseEntity> spec = new Specification<BaseEntity>() {  			
		    public Predicate toPredicate(Root<BaseEntity> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {
		    	query.where(cb.equal(root.get("name"), name));
		    	return query.getRestriction(); 		    	
		    }
		};
		List entityList = init(clazz).find(spec);
		int size = entityList.size();
		if(size>0){
			if(size==1){
				return (T) entityList.get(0);
			}else{
				throw new PageException(name+",数据超过1条请检查！");
			}
		}
		return null;
	}

	
}
