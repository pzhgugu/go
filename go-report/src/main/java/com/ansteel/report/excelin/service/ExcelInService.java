package com.ansteel.report.excelin.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ansteel.report.excelin.domain.ExcelIn;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入服务。
 */
public interface ExcelInService {

	/**
	 * 根据名称获取Excel导入实体
	 * @param name
	 * @return
	 */
	ExcelIn getExcelInByName(String name);

	/**
	 * Excel导入
	 * @param modelName
	 * @param inName
	 * @param file
	 * @return
	 */
	List inExcel(String modelName, String inName, MultipartFile file);

}
