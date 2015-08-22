package com.ansteel.report.excelin.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.report.excelin.domain.ExcelIn;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入数据仓库。
 */
public interface ExcelInRepository extends ProjectRepository<ExcelIn, String>{

	ExcelIn findOneByName(String name);
}
