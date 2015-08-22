package com.ansteel.modules.backup.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.backup.core.IXmlVersion;
import com.ansteel.core.utils.RegexUtil;
import com.ansteel.core.utils.SpringBaseTest;

public class XmlVersionTest extends SpringBaseTest{
	
	@Autowired
	IXmlVersion xmlVersion;

	@Test
	public void testGetVersion() {
		xmlVersion.versionUpdate("123");
		assertThat(xmlVersion.getVersion(), is("123"));
	}

}
