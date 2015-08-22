package com.ansteel.spring.test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Ignore;
import org.junit.Test;

import com.ansteel.common.security.domain.Permission;
import com.ansteel.core.utils.RegexUtil;

public class RegexUtilTest {

	@Test
	public void testIsNullObjectArray() {
		Object[] objs = {};
		assertThat(RegexUtil.isNull(objs), is(true));
		objs = null;
		assertThat(RegexUtil.isNull(objs), is(true));
	}

	@Ignore
	@Test
	public void testIsNullInteger() {
		Integer i = null;
		assertThat(RegexUtil.isNull(i), is(true));
		i = 0;
		assertThat(RegexUtil.isNull(i), is(true));
	}

	@Test
	public void permissionTest() {
		int permission = 0;
		assertThat(1 << Permission.CREATE, is(1));
		assertThat(1 << Permission.READ, is(2));
		assertThat(1 << Permission.UPDATE, is(4));
		assertThat(1 << Permission.DELETE, is(8));

		permission |= (1 << Permission.CREATE);
		permission |= (1 << Permission.READ);
		assertThat(isPermission(permission,Permission.UPDATE),is(false));
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

}
