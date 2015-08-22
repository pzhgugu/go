package com.ansteel.modules.security.service;

import static org.junit.Assert.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.SpringDbunitTest;

public class UserControllerTest {

	@Test
	public void testMessageFormat() {
		/*String value= "402897814c026b8d014c027d3acb0000";
		Object[] arguments = { "Users", "roles", value };
		int count = new BaseEntity().findByCount(Roles.class);
		String hql = MessageFormat
				.format("select name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select 1 from {0} o inner join o.{1} k where o.id =:id and r.id=k.id) as accredit  from Roles r",
						arguments);
		Query query =new BaseEntity().getSession().createQuery(hql);
		query.setParameter("id",value);
		List resultList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for(Object o:resultList){
			System.out.println(o);
		}*/
	}

	@Test
	@Rollback(false)
	public void testResource() {
		/*Resources resources = Resources.load(Resources.class, "402897814c0bb8d1014c0bd030e000ba");
		Roles roles = Roles.load(Roles.class, "402897814c06de16014c06de26f60001");
		RolesResources rolesResources = new RolesResources();
		rolesResources.setResources(resources);
		rolesResources.setRoles(roles);
		rolesResources.save();*/
		/*String hql = "select name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select distinct rr.permission from Resources res, RolesResources rr,Roles ro where " +
				"res.id =:id and res.id=rr.resources.id and rr.roles.id=ro.id and r.id=ro.id) from Roles r";
		Query query = new BaseEntity().getSession().createQuery(hql);
		query.setParameter("id","402897814c0bb8d1014c0bd030e000ba");
		List resultList = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		for(Object o:resultList){
			Map<String,Object> map=(Map)o;
			for(Entry<String,Object> e:map.entrySet()){
				System.out.println(e.getKey()+"---"+e.getValue());
			}
		}*/
	}
}
