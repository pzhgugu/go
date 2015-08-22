package com.ansteel.modules.backup.core;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ansteel.common.backup.core.IAutoPublish;
import com.ansteel.common.backup.core.IDataXmlEport;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.SpringBaseTest;

public class DataXmlEportTest extends SpringBaseTest {
	
	@Value("${go_pro.autoPublish_path}")
	public String publishPath;
	
	@Autowired
	IDataXmlEport dataXmlEport;
	
	@Autowired
	IAutoPublish autoPublish;

	@Test
	public void testRegister() {
		assertThat(dataXmlEport.getRegister().size()>0, is(true));
	}
	
	@Test
	@Ignore
	public void testBackupAll() {
		assertThat(StringUtils.hasText(publishPath), is(true));
		String path=publishPath+"/"+Public.AUTO_PUBLISH_XML;
	    dataXmlEport.backupAll(path);
	}
	
	@Test
	@Rollback(true)
	@Ignore
	public void testAutoPublish(){
		 autoPublish.publish();
	}

}
