package com.ansteel.common.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ansteel.common.security.domain.Roles;

public interface RolesService {

	Roles save(Roles roles);

	long count();

	Page findMap(String queryString, Map<String, Object> whereMap,String countQuery, Map<String, Object> countMap,Pageable pageable);

	Roles findOne(String id);

	Collection<Roles> findAll();

	Roles findOneByName(String name);

	void delect(String id);

}
