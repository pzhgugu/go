package com.ansteel.modules.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.dbunit.database.AmbiguousTableNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import com.ansteel.core.utils.SpringDbunitTest;

public class UsersTest {

	@Before
	public void before() throws Exception {
		//super.back("go_users", "user_back.xml");
		//super.back("go_roles", "roles_back.xml");
		//super.back("go_user_role", "user_role_back.xml");
		/*super.back("go_resources", "resources_back.xml");
		super.back("go_roles_resources", "roles_resources_back.xml");*/
		//super.recover("user_role_clean.xml");		
		//super.importTestDb("user_test.xml");
		//super.importTestDb("roles_test.xml");
		//super.importTestDb("user_role_test.xml");

		//super.recover("roles_resources_clean.xml");
		//super.importTestDb("resources_test.xml");
		//super.importTestDb("roles_resources_test.xml");
	}

	@After
	public void after() throws Exception {
		//super.recover("user_role_clean.xml");
		//super.recover("user_back.xml");
		//super.recover("roles_back.xml");
		//super.recover("user_role_back.xml");
		
		//super.recover("roles_resources_clean.xml");
		//super.recover("resources_back.xml");
		//super.recover("roles_resources_back.xml");
	}

	@Test
	@Rollback(true)
	public void testUsersQuery() throws Exception {
		/*Users user = new Users()
				.findByNamedParamOne(Users.class, "name", "adminTest");
		assertThat(user.getName(), is("adminTest"));*/
	}

	@Test
	@Rollback(true)
	public void testUsersSave() throws Exception {
		/*Users user = new Users();
		user.setName("administratorTest");
		user.setAlias("系统管理员测试");
		user.setPassword("1111");
		try {
			user.save();
			// 恢复数据时，由于事务有hibernate控制，数据恢复由Dbunit控制，
			// 所有需要把保存的数据先刷到数据库，Dbunit才可以覆盖这个条数据
			user.flush();
		} catch (Exception e) {
			assertTrue("插入user数据异常...", false);
		}*/

	}

	@Test
	// 让数据回滚，不写入数据库
	@Rollback(true)
	public void testRolesSave() throws Exception {
		/*Roles rolse = new Roles();
		rolse.setName("S_USER");
		rolse.setAlias("用户");
		try {
			rolse.save();
		} catch (Exception e) {
			assertTrue("插入rolse数据异常...", false);
		}*/
	}

	/**
	 * 用户关联角色
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testUsersToRoles() throws Exception {
		/*Users user =new Users()
				.findByNamedParamOne(Users.class, "name", "adminTest");
		assertThat(user.getName(), is("adminTest"));
		Roles role = new Roles().findByNamedParamOne(Roles.class, "name",
				"S_USERTEST");
		assertThat(role.getName(), is("S_USERTEST"));
		List<Roles> rolseList = new ArrayList<Roles>();
		rolseList.add(role);
		user.setRoles(rolseList);
		try {
			user.update();
			user.flush();
		} catch (Exception e) {
			assertTrue("更新user数据异常...", false);
		}

		Users userToRole =new Users().findByNamedParamOne(Users.class, "name",
				"adminTest");
		Collection<Roles> roleList = userToRole.getRoles();
		boolean is = false;
		for (Roles r : roleList) {
			if (r.getName().equals("S_USERTEST")) {
				is = true;
			}
		}

		assertTrue("关联角色数据异常...", is);

		// 清除关联
		user.getRoles().remove(role);
		try {
			user.update();
			user.flush();
		} catch (Exception e) {
			assertTrue("清除关联user数据异常...", false);
		}*/

	}

	@Test
	@Rollback(true)
	public void testResourcesRolseAdd() throws AmbiguousTableNameException {
		/*Resources resource = new Resources();
		resource.setName("resCodeTest");
		resource.setAlias("测试资源编码测试");
		resource.setType(Resources.URL);

		Roles role = Roles.findByNamedParamOne(Roles.class, "name",
				"S_USERTEST");

		RolesResources rolesResource = new RolesResources();
		rolesResource.setRoles(role);
		rolesResource.setResources(resource);

		resource.getRolesResources().add(rolesResource);
		
		try {
			resource.save();
			resource.flush();
		} catch (Exception e) {
			assertTrue("新建资源管理角色数据异常...", false);
		}*/
	}
	
	@Test
	@Rollback(true)
	public void testAddRolesToUsers() throws Exception {
		/*Users userAdmin = Users
				.findByNamedParamOne(Users.class, "name", "administrator");
		assertThat(userAdmin.getName(), is("administrator"));
		
		
		Users user = Users
				.findByNamedParamOne(Users.class, "name", "0333377");
		assertThat(user.getName(), is("0333377"));
		
		
		Roles role = new Roles();
		role.setName("ROLE_USER");
		role.setAlias("初级会员");
		role.save();
		
		Roles roleAdmin = new Roles();
		roleAdmin.setName("ROLE_ADMIN");
		roleAdmin.setAlias("管理员");
		roleAdmin.save();
		
		List<Roles> rolseList = new ArrayList<Roles>();
		rolseList.add(role);
		user.setRoles(rolseList);
		try {
			user.update();
		} catch (Exception e) {
			assertTrue("更新user数据异常...", false);
		}

		List<Roles> rolseAdminList = new ArrayList<Roles>();
		rolseAdminList.add(roleAdmin);
		userAdmin.setRoles(rolseAdminList);
		try {
			userAdmin.update();
		} catch (Exception e) {
			assertTrue("更新user数据异常...", false);
		}*/

	}


}
