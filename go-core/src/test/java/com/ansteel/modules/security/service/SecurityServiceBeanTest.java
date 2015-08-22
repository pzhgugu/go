package com.ansteel.modules.security.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.repository.RolesRepository;
import com.ansteel.core.utils.SpringBaseTest;

public class SecurityServiceBeanTest extends SpringBaseTest {
	
	@Autowired
	RolesRepository rolesRepository;

	@Test
	public void test() {
		/*String hql = "select id as id, name as name ,alias as alias,displayOrder as displayOrder,
		 * enable as enable,(select distinct rr.permission 
		 * from Resources res, RolesResources rr,Roles ro where res.id =:id 
		 * and res.id=rr.resources.id and rr.roles.id=ro.id and r.id=ro.id) as permission  
		 * from Roles r";
		*/
		Pageable pageable=new PageRequest(0,10);
		final String id="";
		Specification<Tuple> spec = new Specification<Tuple>() {  
		    public Predicate toPredicate(Root<Tuple> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {  
		    	
		    	/*Subquery<RolesResources> sRes = query.subquery(RolesResources.class);
		    	Root<Resources> resourcesRoot = sRes.from(Resources.class);
		    	Join rolesResourcesFrom =resourcesRoot.join("rolesResources");
		    	Join rolesFrom = rolesResourcesFrom.join("roles");
		    	sRes.select(rolesFrom.get("id"));
		    	sRes.distinct(true);
		    	sRes.where(cb.equal(root.get("id"),sRes));*/
		    	//CriteriaQuery<RolesResources> rrQuery = cb.createQuery(RolesResources.class);
		    	Root<RolesResources> rrFrom = query.from(RolesResources.class);
		        query.multiselect(root.get("id"),root.get("name"),root.get("displayOrder"),root.get("enable"),rrFrom.get("id").alias("rrId"));
		        query.where(cb.equal(root.get("id"), rrFrom.get("roles").get("id")));
		        return query.getRestriction();  
		    }  
		};  
		
		 Page page = rolesRepository.findTuple(spec,pageable);
		System.out.println(page.getNumber());
	}

}
