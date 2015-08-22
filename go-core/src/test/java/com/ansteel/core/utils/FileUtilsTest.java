package com.ansteel.core.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FileUtilsTest {

	@Test
	public void test() {
		System.out.println(FileUtils.readClassLoaderFile("DynamicModel.xml",this.getClass()));
	}

}
