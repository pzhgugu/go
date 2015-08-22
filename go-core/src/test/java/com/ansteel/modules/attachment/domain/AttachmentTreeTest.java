package com.ansteel.modules.attachment.domain;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsCategoryRepository;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.utils.SpringBaseTest;

public class AttachmentTreeTest extends SpringBaseTest{
	
	@Autowired
	BaseService baseService;
	
	@Autowired
	DynamicFieldsCategoryRepository permissionRepository;
	
	@Test
	public void testUserCount(){
		logger.info(permissionRepository.count());
	}

	@Test
	@Rollback(false)
	public void test() {
		AttachmentTree tree = new AttachmentTree();
		tree.setName("1");
		tree.setAlias("一");
		tree.setLayer(0);
		baseService.save(tree);
		/*AttachmentTree tree2 = new AttachmentTree();
		tree2.setName("2");
		tree2.setParent(tree);
		tree2.setLayer(0);
		baseService.save(tree2);*/
	}

}
