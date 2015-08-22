package com.ansteel.modules.reportlist.repository;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ansteel.core.utils.SpringBaseTest;
import com.ansteel.report.reportlist.repository.ReportMappedRepository;


public class ReportMappedRepositoryTest extends SpringBaseTest{

	@Autowired
	ReportMappedRepository reportMappedRepository;
	
	@Test
	public void test() {
		reportMappedRepository.count();
	}

}
