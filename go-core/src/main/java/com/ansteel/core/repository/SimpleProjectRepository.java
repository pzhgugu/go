package com.ansteel.core.repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.domain.TreeUI;
import com.ansteel.core.utils.CriteriaUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：项目通用数据持久层接口。  
 */
@Repository
@Transactional(readOnly = true)
public class SimpleProjectRepository<T, ID extends Serializable> extends
		SimpleJpaRepository<T, ID> implements ProjectRepository<T, ID> {

	private final EntityManager em;

	public SimpleProjectRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.em = em;
	}

	public SimpleProjectRepository(
			final JpaEntityInformation<T, ?> entityInformation,
			final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.em = entityManager;
	}

	private <S> Root<T> applySpecificationToCriteria(Specification<T> spec,
			CriteriaQuery<S> query) {

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
	
	@Override
	public List findTuple(Specification spec) {
		TypedQuery<Tuple> query = this.getTupleQuery(spec);
		return query.getResultList();
	}

	@Override
	public Page findTuple(Specification spec, Pageable pageable) {
		TypedQuery<Tuple> query = this.getTupleQuery(spec);		
		return pageable == null ? new PageImpl<Tuple>(query.getResultList()) : readPageTuple(query, pageable, spec);
	}
	private static Long executeCountQuery(TypedQuery<Long> query) {
		Assert.notNull(query);
		List totals = query.getResultList();
		Long total = Long.valueOf(0L);

		Long element;
		for(Iterator var3 = totals.iterator(); var3.hasNext(); total = Long.valueOf(total.longValue() + (element == null?0L:element.longValue()))) {
			element = (Long)var3.next();
		}

		return total;
	}

	private PageImpl<Tuple> readPageTuple(TypedQuery<Tuple> query,
			Pageable pageable, Specification spec) {
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		Long total = executeCountQuery(this.getCountQuery(spec));
		List<Tuple> content = total > pageable.getOffset() ? query.getResultList() : Collections.<Tuple> emptyList();

		return new PageImpl<Tuple>(content, pageable, total);
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
	public List find(Specification<T> spec,Integer firstResult,Integer maxResults) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(getDomainClass());
		Root<T> root = applySpecificationToCriteria(spec, query);
		query.select(root);
		TypedQuery<T> tq = em.createQuery(query);
		tq.setFirstResult(firstResult);
		tq.setMaxResults(maxResults);
		return tq.getResultList();
	}

	@Override
	public Page<T> find(Specification<T> spec, Pageable pageable) {
		return this.findAll(spec, pageable);
	}

	@Override
	public List find(final Map<String, Object> props) {
		Specification<T> spec = new Specification<T>() {
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				int size = props.size();
				if (size > 0) {
					Predicate[] pArray = new Predicate[size];
					int i = 0;
					for (Entry<String, Object> entry : props.entrySet()) {
						Path<Object> path=CriteriaUtils.getPathByKey(root, entry.getKey());
						pArray[i] = cb.equal(path,entry.getValue());
						i++;
					}
					query.where(cb.and(pArray));
				}
				return query.getRestriction();
			}
		};
		return this.find(spec);
	}

	@Override
	public Page findMap(String jqlQuery, Map<String, Object> whereMap,String countQuery, Map<String, Object> countMap,  Pageable pageable) {
		Long total  = this.total(countQuery,countMap);		
		Query query = getQuery(jqlQuery, whereMap);
		List content = query.setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize())
				.unwrap(org.hibernate.Query.class).
				setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();  
		return new PageImpl(content, pageable, total);
	}
	
	private Long total(String countQueryString, Map<String, Object> countMap) {
		TypedQuery<Long> countQuery = em.createQuery(countQueryString, Long.class);
		if(countMap!=null){
			for (Entry<String, Object> entry : countMap
					.entrySet()) {
				if(countQueryString.indexOf(":"+entry.getKey())>-1){
					countQuery.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		return countQuery.getSingleResult();
	}

	private Query getQuery(String queryString, Map<String, Object> whereMap){
		Query query = em.createQuery(queryString);
		if(whereMap!=null){
			for (Entry<String, Object> entry : whereMap
					.entrySet()) {
				if(queryString.indexOf(":"+entry.getKey())>-1){
					query.setParameter(entry.getKey(), entry.getValue());
				}
			}
		}
		return query;
	}

}
