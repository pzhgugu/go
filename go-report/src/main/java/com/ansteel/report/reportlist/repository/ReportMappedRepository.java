package com.ansteel.report.reportlist.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.reportlist.domain.ReportMapped;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表映射仓库。
 */
public interface ReportMappedRepository  extends ProjectRepository<ReportMapped, String>  {
	
	ReportMapped findOneByRDate(String rDate);
	
	ReportMapped findOneByScription(String scription);
}
