package com.ansteel.report.reportlist.service;

import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ansteel.common.attachment.service.FileAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.core.constant.Public;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.query.HqlQueryBuilder;
import com.ansteel.core.query.QueryOrWhere;
import com.ansteel.core.query.QuerySettings;
import com.ansteel.core.utils.CriteriaUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.report.reportlist.domain.ReportMapped;
import com.ansteel.report.reportlist.repository.ReportMappedRepository;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-13
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表映射服务实现类。
 */
@Transactional(readOnly=true)
@Service
public class ReportMappedServiceBean implements ReportMappedService {
	
	@Autowired
	ReportMappedRepository reportMappedRepository;
	
	@Autowired
	FileAttachmentService fileAttachmentService;

	@Override
	@Transactional(readOnly=false)
	public void save(String typeId,String attid, String beginDate, String endDate) {
		ReportMapped reportMapped = new ReportMapped();
		reportMapped.setAttPath(attid);
		reportMapped.setrDate(beginDate);
		reportMapped.setType(typeId);
		if(StringUtils.hasText(endDate)){
			reportMapped.setScription(beginDate+"@"+endDate);
		}
		reportMappedRepository.save(reportMapped);
		
	}

	@Override
	public ReportMapped findOne(String id) {
		return reportMappedRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void delect(ReportMapped reportMapped) {
		String path = reportMapped.getAttPath();
		if(StringUtils.hasText(path)){
			fileAttachmentService.delete(path);
		}
		reportMappedRepository.delete(reportMapped);
	}

	@Override
	public ReportMapped findOneByRDate(String beginDate) {
		return reportMappedRepository.findOneByRDate(beginDate);
	}

	@Override
	public ReportMapped findOneByScription(String beginDate, String endDate) {
		return reportMappedRepository.findOneByScription(beginDate+"@"+endDate);
	}

	@Override
	public Page findByTypeOrderByRDate(final String type, Pageable pageable) {
		Specification spec = CriteriaUtils.getSpecification(ReportMapped.class, "type", type, "rDate");
		return reportMappedRepository.find(spec, pageable);
	}

	@Override
	public Page findByTypeOrderByRDate(String type, List<QueryMapping> queryList,
			Pageable pageable) {
		Specification spec = CriteriaUtils.getSpecification(ReportMapped.class, "type", type, "rDate",queryList);
		return reportMappedRepository.find(spec, pageable);
	}


}
