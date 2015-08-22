package com.ansteel.report.reportlist.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.query.QuerySettings;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.report.reportlist.domain.ReportMapped;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-13
 * 修 改 人：
 * 修改日 期：
 * 描   述：报表映射服务类。
 */
public interface ReportMappedService {

	/**
	 * 保存
	 * @param typeId
	 * @param attid
	 * @param beginDate
	 * @param endDate
	 */
	void save(String typeId,String attid, String beginDate, String endDate);

	/**
	 * 删除
	 * @param reportMapped
	 */
	void delect(ReportMapped reportMapped);

	/**
	 * 根据开始时间查询报表映射
	 * @param beginDate
	 * @return
	 */
	ReportMapped findOneByRDate(String beginDate);

	/**
	 * 根据描述查询报表映射
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	ReportMapped findOneByScription(String beginDate, String endDate);

	/**
	 * 根据类型查询页面并根据RDate排序
	 * @param type
	 * @param pageable
	 * @return
	 */
	Page findByTypeOrderByRDate(String type, Pageable pageable);

	/**
	 * 根据类型查询页面并根据RDate排序
	 * @param id
	 * @param queryList
	 * @param pageable
	 * @return
	 */
	Page findByTypeOrderByRDate(String id, List<QueryMapping> queryList,
			Pageable pageable);

	/**
	 * 根据id查询报表映射
	 * @param id
	 * @return
	 */
	ReportMapped findOne(String id);

}
