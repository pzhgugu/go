package com.ansteel.modules.security.domain;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.map.HashedMap;
import org.dbunit.database.AmbiguousTableNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.SpringDbunitTest;

public class PermissionTest  {

	/*CascadeType.REFRESH：级联刷新，当多个用户同时作操作一个实体，为了用户取到的数据是实时的，在用实体中的数据之前就可以调用一下refresh()方法！
	 
	CascadeType.REMOVE：级联删除，当调用remove()方法删除Order实体时会先级联删除OrderItem的相关数据！
	 
	CascadeType.MERGE：级联更新，当调用了Merge()方法，如果Order中的数据改变了会相应的更新OrderItem中的数据，
	 
	CascadeType.ALL：包含以上所有级联属性。*/
	
	
	@Before
	public void before() throws Exception {
		/*super.back("go_resources", "resources_back.xml");
		super.back("go_permission_url", "permission_url_back.xml");
		super.importTestDb("resources_test.xml");
		super.importTestDb("permission_url_test.xml");*/
	}

	@After
	public void after() throws Exception {
		/*super.recover("permission_url_back.xml");
		super.recover("resources_back.xml");*/
	}

	/**
	 * 新增资源URL、并关联资源
	 * @throws AmbiguousTableNameException
	 */
	@Test
	@Rollback(true)
	public void testPermissionURLToResourcesSave() throws AmbiguousTableNameException {
		/*PermissionURL permissionURL = new PermissionURL();
		permissionURL.setPath("/admin/home**");
		permissionURL.setPermissionBit(Permission.READ);
		
		Resources resource = Resources.findByNamedParamOne(Resources.class, "name", "resTest");
		assertNotNull("resource为空", resource);
		
		permissionURL.setResources(resource);
		resource.getPermissionUrls().add(permissionURL);
		
		try {
			permissionURL.save();
		} catch (Exception e) {
			assertTrue("插入permissionURL数据异常...", false);
		}
*/
	}
	
	@Test
	@Rollback(true)
	public void permissionSave(){
		/*Permission permission = new Permission();
		permission.setAlias("研发");
		permission.setPermissionBit(10);
		
		Resources resource = Resources.findByNamedParamOne(Resources.class, "name", "resTest");
		assertNotNull("resource为空", resource);
		resource.getPermissions().add(permission);
		permission.setResources(resource);
		permission.save();*/
	}
	
	@Test
	public void queryPermission(){
		/*Resources resource = Resources.findByNamedParamOne(Resources.class, "name", "resTest");
		assertNotNull("resource为空", resource);
		Map<Integer,String> permissionMap = getPermission(resource);
		
		List<PermissionURL> permissionUrls =(List<PermissionURL>) resource.getPermissionUrls();
		for(PermissionURL pUrl:permissionUrls){
			System.out.println(permissionMap.get(pUrl.getPermissionBit()));
		}*/
		
	}

	@Test
	public void getPermissionTest() {
		/*String hql = "select id as id, name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select distinct rr.permission from Resources res, RolesResources rr,Roles ro where " +
				"res.id =:id and res.id=rr.resources.id and rr.roles.id=ro.id and r.id=ro.id) as permission  from Roles r";
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", "402897814c0bb8d1014c0bd030e000ba");
		
		Resources resources = new Resources().load(Resources.class, "402897814c0bb8d1014c0bd030e000ba");
		
		
		Page page = new Page();
		page.setFirstResult(PageUtils.getFirstResult(null));
		page.setMaxResults(PageUtils.getMaxResults(null));
		List resultList = new BaseEntity().findMap(hql,map, page.getFirstResult(),
				page.getMaxResults());
		for(Object o :resultList){
			Map mapResult = (Map) o;
			Integer permission = (Integer) mapResult.get("permission");
			if(permission!=null){
				String pStr = this.getPermissionStr(permission,resources.getPermissionsMap());
				mapResult.put("permissionStr", pStr);
			}
		}
		
		
		
		Result result = new Result();
		result.setResult(resultList);
		page.setTotalCount(new BaseEntity().findByCount(Roles.class));
		result.setPage(page);*/
	}

	private String getPermissionStr(Integer permission,
			Map<Integer, String> permissionsMap) {
		StringBuffer sb = new StringBuffer();
		for(Entry<Integer,String> entry:permissionsMap.entrySet()){
			if(isPermission(permission,entry.getKey())){
				sb.append(entry.getValue());
				sb.append(",");
			}
		}
		return sb.substring(0, sb.length()-1);
	}
	

	/** 
	 * @param p 授权集合，比如10
	 * @param b 授权码 ，Permission.CREATE
	 * @return
	 */
	private boolean isPermission(Integer permission, Integer urlPermissionBit) {
		int temp = 1;
		temp <<= urlPermissionBit;
		temp &= permission;
		if (temp > 0) {
			return true;
		}
		return false;
	}
	
	@Test
	public void  isPermissionTest() {
		int m=1;
		m<<=3;
		System.out.println(m);
		
		System.out.println(isPermission(3,3));
	}

}
