package com.ansteel.core.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class SqlBaseRepositoryBean implements SqlBaseRepository{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List findSqlMap(String queryString, Map<String, Object> whereMap, Pageable pageable) {
		SQLQuery sqlQuery = this.getSQLQuery(queryString, whereMap);
		return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setFirstResult(pageable.getPageNumber())
				.setMaxResults(pageable.getPageSize()).list();
	}

	@Override
	public long sqlCount(String countQuery, Map<String, Object> whereMap) {
		SQLQuery sqlQuery = this.getSQLQuery(countQuery, whereMap);
		return ((Number)sqlQuery.uniqueResult()).intValue();
	}

	@Override
	public List findSqlMap(String queryString, Map<String, Object> whereMap) {
		SQLQuery sqlQuery = this.getSQLQuery(queryString, whereMap);
		return sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	private SQLQuery getSQLQuery(String queryString, Map<String, Object> whereMap){
		Query query = em.createNativeQuery(queryString);		
		SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
		for (Entry<String, Object> entry : whereMap
				.entrySet()) {
			if(queryString.indexOf(":"+entry.getKey())>-1){
				sqlQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return sqlQuery;
	}
	

	@Override
	public List findSql(String queryString, Map<String, Object> whereMap) {
		SQLQuery sqlQuery = this.getSQLQuery(queryString, whereMap);
		return sqlQuery.list();
	}
}
