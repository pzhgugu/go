package com.ansteel.modules.springsecurity.service;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.ansteel.core.utils.SpringBaseTest;

public class SsoPasswordEncoderTest extends SpringBaseTest{

	
	
	
	@Resource(name="passwordEncoder")
	PasswordEncoder passwordEncoder;
	@Test
	public void test() {
		boolean is = passwordEncoder.isPasswordValid("b59c67bf196a4758191e42f76670ceba", "1111", null);
		Assert.assertTrue(is);
	}

}
