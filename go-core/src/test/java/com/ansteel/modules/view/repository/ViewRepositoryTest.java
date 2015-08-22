package com.ansteel.modules.view.repository;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.common.view.domain.View;
import com.ansteel.common.view.repository.ViewRepository;
import com.ansteel.core.utils.SpringBaseTest;

public class ViewRepositoryTest extends SpringBaseTest {
	
	@Autowired
	ViewRepository viewRepository;

	@Test
	public void test() {
		View view=viewRepository.findOneByName("Attachment");
		logger.info(view.getAlias());
	}
	
	@Test
	public void test2() {
	}

}
