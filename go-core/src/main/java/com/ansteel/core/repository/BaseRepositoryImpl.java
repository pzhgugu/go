package com.ansteel.core.repository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.data.jpa.repository.query.QueryUtils.COUNT_QUERY_STRING;
import static org.springframework.data.jpa.repository.query.QueryUtils.DELETE_ALL_QUERY_STRING;
import static org.springframework.data.jpa.repository.query.QueryUtils.applyAndBind;
import static org.springframework.data.jpa.repository.query.QueryUtils.getQueryString;
import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.LockMetadataProvider;
import org.springframework.data.jpa.repository.support.PersistenceProvider;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.domain.TreeUI;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 基本数据持久层接口实现。  
 */
@Repository
@Transactional(readOnly = true)
public class BaseRepositoryImpl<T, ID extends Serializable> implements BaseRepository<T, ID> {

	private static final String CountQueryPlaceholder ="x";

	@PersistenceContext
	private EntityManager em;

	private LockMetadataProvider lockMetadataProvider;

	@Autowired
	EntityManagerFactory entityManagerFactory;

	private JpaEntityInformation<T, ?> entityInformation;

	private PersistenceProvider provider;

	private static Map<Class,JpaEntityInformation> entityMetadataMap  = new HashMap<Class, JpaEntityInformation>();

	@Override
	public void init(Class<T> clazz){
		if(entityMetadataMap.containsKey(clazz)){
			entityInformation= entityMetadataMap.get(clazz);
		}else{
			JpaEntityInformation<T, ?> entityMetadata = mock(JpaEntityInformation.class);
			when(entityMetadata.getJavaType()).thenReturn(clazz);
			when(entityMetadata.getEntityName()).thenReturn(clazz.getName());
			entityInformation= entityMetadata;
			entityMetadataMap.put(clazz, entityMetadata);
		}
		this.provider = PersistenceProvider.fromEntityManager(em);
	}

	protected Class<T> getDomainClass() {
		return entityInformation.getJavaType();
	}

	public void setLockMetadataProvider(LockMetadataProvider lockMetadataProvider) {
		this.lockMetadataProvider = lockMetadataProvider;
	}

	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}
	private TypedQuery<T> applyLockMode(TypedQuery<T> query) {

		LockModeType type = lockMetadataProvider == null ? null : lockMetadataProvider.getLockModeType();
		return type == null ? query : query.setLockMode(type);
	}

	protected TypedQuery<T> getQuery(Specification<T> spec, Sort sort) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());

		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		return applyLockMode(em.createQuery(query));
	}

	private String getDeleteAllQueryString() {
		return getQueryString(DELETE_ALL_QUERY_STRING, entityInformation.getEntityName());
	}

	public Page<T> findAll(Specification<T> spec, Pageable pageable) {

		TypedQuery<T> query = getQuery(spec, pageable);
		return pageable == null ? new PageImpl<T>(query.getResultList()) : readPage(query, pageable, spec);
	}

	protected TypedQuery<T> getQuery(Specification<T> spec, Pageable pageable) {

		Sort sort = pageable == null ? null : pageable.getSort();
		return getQuery(spec, sort);
	}

	protected Page<T> readPage(TypedQuery<T> query, Pageable pageable, Specification<T> spec) {

		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Long total = QueryUtils.executeCountQuery(getCountQuery(spec));
		List<T> content = total > pageable.getOffset() ? query.getResultList() : Collections.<T> emptyList();

		return new PageImpl<T>(content, pageable, total);
	}

	protected TypedQuery<Long> getCountQuery(Specification<T> spec) {

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);

		Root<T> root = applySpecificationToCriteria(spec, query);
		//解决sqlservice查询报错
		List<Order> o =new ArrayList<>();
		query.orderBy(o);
		if (query.isDistinct()) {
			query.select(builder.countDistinct(root));
		} else {
			query.select(builder.count(root));
		}

		return em.createQuery(query);
	}

	private String getCountQueryString() {

		String countQuery = String.format(COUNT_QUERY_STRING, CountQueryPlaceholder, "%s");
		return getQueryString(countQuery, entityInformation.getEntityName());
	}


	@Override
	public List<T> findAll() {
		return getQuery(null, (Sort) null).getResultList();
	}

	@Override
	public  List<T> findAll(Sort sort) {
		return getQuery(null, sort).getResultList();
	}

	@Override
	@Transactional
	public void flush() {
		em.flush();
	}

	@Override
	@Transactional
	public  T saveAndFlush(T entity) {
		T result = save(entity);
		flush();

		return result;
	}

	@Override
	@Transactional
	public void deleteAllInBatch() {
		em.createQuery(getDeleteAllQueryString()).executeUpdate();
	}

	@Override
	public T getOne(String id) {
		Assert.notNull(id, "The given id must not be null!");
		return em.getReference(getDomainClass(), id);
	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		if (null == pageable) {
			return new PageImpl<T>(findAll());
		}

		return findAll(null, pageable);
	}

	@Override
	@Transactional
	public <S extends T> S save(S entity) {

		if (entityInformation.isNew(entity)) {
			em.persist(entity);
			return entity;
		} else {
			return em.merge(entity);
		}
	}

	@Override
	public T findOne(String id) {
		Assert.notNull(id, "The given id must not be null!");

		LockModeType type = lockMetadataProvider == null ? null : lockMetadataProvider.getLockModeType();
		Class<T> domainType = getDomainClass();

		return type == null ? em.find(domainType, id) : em.find(domainType, id, type);
	}

	@Override
	public boolean exists(String id) {

		Assert.notNull(id, "The given id must not be null!");

		if (entityInformation.getIdAttribute() != null) {

			String placeholder = CountQueryPlaceholder;
			String entityName = entityInformation.getEntityName();
			Iterable<String> idAttributeNames = entityInformation.getIdAttributeNames();
			String existsQuery = QueryUtils.getExistsQueryString(entityName, placeholder, idAttributeNames);

			TypedQuery<Long> query = em.createQuery(existsQuery, Long.class);

			if (entityInformation.hasCompositeId()) {
				for (String idAttributeName : idAttributeNames) {
					query.setParameter(idAttributeName, entityInformation.getCompositeIdAttributeValue(id, idAttributeName));
				}
			} else {
				query.setParameter(idAttributeNames.iterator().next(), id);
			}

			return query.getSingleResult() == 1L;
		} else {
			return findOne(id) != null;
		}
	}

	@Override
	public long count() {
		return em.createQuery(getCountQueryString(), Long.class).getSingleResult();
	}

	@Override
	@Transactional
	public void delete(String id) {
		Assert.notNull(id, "The given id must not be null!");

		T entity = findOne(id);

		if (entity == null) {
			throw new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!",
					entityInformation.getJavaType(), id), 1);
		}

		delete(entity);
	}

	@Override
	@Transactional
	public void delete(T entity) {
		Assert.notNull(entity, "The entity must not be null!");
		em.remove(em.contains(entity) ? entity : em.merge(entity));
	}

	@Override
	@Transactional
	public void deleteAll() {
		for (T element : findAll()) {
			delete(element);
		}
	}


	@Override
	public List<T> findAll(Iterable<String> ids) {
		if (ids == null || !ids.iterator().hasNext()) {
			return Collections.emptyList();
		}

		return getQuery(new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<?> path = root.get(entityInformation.getIdAttribute());
				return path.in(cb.parameter(Iterable.class, "ids"));
			}
		}, (Sort) null).setParameter("ids", ids).getResultList();
	}

	@Override
	@Transactional
	public <S extends T> List<S> save(Iterable<S> entities) {

		List<S> result = new ArrayList<S>();

		if (entities == null) {
			return result;
		}

		for (S entity : entities) {
			result.add(save(entity));
		}

		return result;
	}

	@Override
	@Transactional
	public void deleteInBatch(Iterable<T> entities) {
		Assert.notNull(entities, "The given Iterable of entities not be null!");

		if (!entities.iterator().hasNext()) {
			return;
		}

		applyAndBind(getQueryString(DELETE_ALL_QUERY_STRING, entityInformation.getEntityName()), entities, em)
				.executeUpdate();
	}

	@Override
	@Transactional
	public void delete(Iterable<T> entities) {
		Assert.notNull(entities, "The given Iterable of entities not be null!");

		for (T entity : entities) {
			delete(entity);
		}
	}

	@Override
	public List<Tuple> tupleQuery(Specification<T> spec) {
		TypedQuery<Tuple> typedQuery = this.getTupleQuery(spec);
		return typedQuery.getResultList();

	}

	protected TypedQuery<Tuple> getTupleQuery(Specification<T> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createTupleQuery();
		Assert.notNull(query);
		Root<T> root = query.from(getDomainClass());

		if (spec != null) {
			Predicate predicate = spec.toPredicate(root, query, builder);
			if (predicate != null) {
				query.where(predicate);
			}
		}
		return em.createQuery(query);
	}

	@Override
	public List findTree(Specification<T> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<TreeUI> query = builder.createQuery(TreeUI.class);

		Root<T> root = applySpecificationToCriteria(spec, query);
		TypedQuery<TreeUI> typedQuery = em.createQuery(query);
		return typedQuery.getResultList();
	}

	@Override
	public List find(Specification<T> spec) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());
		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);
		TypedQuery<T> tq = em.createQuery(query);
		return tq.getResultList();
	}

	@Override
	public Page<T> find(Specification<T> spec, Pageable pageable) {
		return this.findAll(spec, pageable);
	}

}
