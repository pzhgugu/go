package com.ansteel.criteria.test;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;

import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.security.domain.Users;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.core.domain.TreeUI;
import com.ansteel.core.repository.BaseRepository;
import com.ansteel.core.utils.SpringBaseTest;

public class CriteriaApiTest extends SpringBaseTest{
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void oneTest(){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
		Root<Users> root = criteriaQuery.from(Users.class);
		root.alias("o"); 
		//criteriaQuery.multiselect(root.get("id").alias("id"));
		Predicate condition = criteriaBuilder.equal(root.get("name"), "admin");
		criteriaQuery.where(condition);
		TypedQuery<Users> typedQuery = em.createQuery(criteriaQuery);
		List result = typedQuery.getResultList();
		System.out.println(result.size());
	}
	
	@Test
	public void twoTest(){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Users> root = criteriaQuery.from(Users.class);
		root.alias("o"); 
		criteriaQuery.multiselect(root.get("id").alias("id"));
		Predicate condition = criteriaBuilder.equal(root.get("name"), "admin");
		criteriaQuery.where(condition);
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		List result = typedQuery.getResultList();
		System.out.println(result.size());
	}
	
	@Test
	public void treeModelsTest(){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		Class clazz=Models.class;
		CriteriaQuery<?> criteriaQuery = criteriaBuilder.createQuery(clazz);
		Root<?> root = criteriaQuery.from(clazz);
		root.alias("o"); 
		Predicate condition = criteriaBuilder.equal(root.get("name"), "View");
		criteriaQuery.where(condition);
		TypedQuery<?> typedQuery = em.createQuery(criteriaQuery);
		List result = typedQuery.getResultList();
		System.out.println(result.size());
	}

	@Test
	public void treeModelsJoinTest(){
		String pid="402897814d234892014d23493f8b002c";
		//String pid="";
		Class clazz=Models.class;
		
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<?> root = criteriaQuery.from(clazz);
		root.alias("o"); 
		root.join("children" , JoinType.LEFT); 

		criteriaQuery.multiselect(root.get("id").alias("id"),
				root.get("alias").alias("text"),
				root.get("parent").get("id").alias("parent"),
				root.get("displayOrder").alias("displayOrder"),
				criteriaBuilder.count(root.get("id")).alias("child"));
		
		Predicate condition;
		if(StringUtils.hasText(pid)){
			condition = criteriaBuilder.equal(root.get("parent").get("id"),pid);
		}else{
			condition = criteriaBuilder.isNull(root.get("parent"));
		}
		criteriaQuery.where(condition);
		criteriaQuery.groupBy(root.get("id"),root.get("alias"),root.get("displayOrder"),root.get("parent").get("id"));
		criteriaQuery.orderBy(criteriaBuilder.asc(root.<Integer>get("displayOrder")));
		
		TypedQuery<Tuple> typedQuery = em.createQuery(criteriaQuery);
		
		List<Tuple> result = typedQuery.getResultList();
		for(Tuple tuple:result){
			System.out.println("-------------------------------------");
			System.out.println(tuple.get("text"));
		}
		System.out.println(result.size());
	}
	
	@Test
	public void specificationsTest(){
		Specification<Models> spec = new Specification<Models>() {  
		    public Predicate toPredicate(Root<Models> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {  
		        Predicate p1 = cb.like(root.get("name").as(String.class), "%View%");  
		        /*Predicate p2 = cb.equal(root.get("uuid").as(Integer.class), um.getUuid());  
		        Predicate p3 = cb.gt(root.get("age").as(Integer.class), um.getAge());  */
		        //把Predicate应用到CriteriaQuery中去,因为还可以给CriteriaQuery添加其他的功能，比如排序、分组啥的  
		       // query.where(cb.and(p3,cb.or(p1,p2)));  
		        query.where(cb.and(p1));
		        //添加排序的功能  
		        query.orderBy(cb.desc(root.get("uuid").as(Integer.class)));  
		          
		        return query.getRestriction();  
		    }  
		};  
	}

	
	private String parentId;
	
	@Autowired
	BaseRepository baseRepository;
	
	@Test
	public void specificationsTreeTest(){
		this.parentId="402897814d234892014d23493f8b002c";
		//String pid="";
		Class clazz=Models.class;
		
		Specification<TreeEntity> spec = new Specification<TreeEntity>() {  			
		    public Predicate toPredicate(Root<TreeEntity> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {
		    	root.alias("o"); 
				root.join("children" , JoinType.LEFT); 

				query.multiselect(root.get("id").alias("id"),
						root.get("alias").alias("text"),
						root.get("parent").get("id").alias("parent"),
						root.get("displayOrder").alias("displayOrder"),
						cb.count(root.get("id")).alias("child"));
				
				Predicate condition;
				if(StringUtils.hasText(parentId)){
					condition = cb.equal(root.get("parent").get("id"),parentId);
				}else{
					condition = cb.isNull(root.get("parent"));
				}
				query.where(condition);
				query.groupBy(root.get("id"),root.get("alias"),root.get("displayOrder"),root.get("parent").get("id"));
				query.orderBy(cb.asc(root.get("displayOrder")));
		          
		        return query.getRestriction();  
		    }  
		};  
		
		baseRepository.init(clazz);
		List result =baseRepository.findTree(spec);
		//List<Tuple> result = baseRepository.tupleQuery(spec);
		
		/*for(Tuple tuple:result){
			System.out.println("-------------------------------------");
			System.out.println(tuple.get("text"));
		}*/
		System.out.println(result.size());
	}
}
