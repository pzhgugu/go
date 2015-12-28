package com.ansteel.impl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.interfaces.service.MakeReport;
import com.ansteel.report.makereport.service.MakeReportService;
import com.ansteel.report.excelin.service.ExcelInService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：生成报表，实现生成报表接口。 
 */
@Service
@Transactional
public class MakeReportImpl implements MakeReport {
	
	/**
	 * 自动注入生成报表服务
	 */
	@Autowired
	MakeReportService makeReportService;
	
	/**
	 * 自动注入Excel导入服务
	 */
	@Autowired
	ExcelInService excelInService;

	/**
	 * 报表展示
	 */
	@Override
	public String show(List listMap, Map<String, String> hashMap,
			String rType, String inline, HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		return makeReportService.show(listMap, hashMap, rType, inline, request, response,fileName);
	}

	/**
	 * 报表展示
	 */
	@Override
	public String show(String modelName, List listMap, String type,
			Object inline, HttpServletRequest request,
			HttpServletResponse response,String fileName) {
		return makeReportService.show(modelName,listMap, type, null, request, response,fileName);
	}

	/**
	 * Excel导入
	 */
	@Override
	public List inExcel(String modelName, String inName,
			MultipartFile file) {
		return excelInService.inExcel(modelName, inName,file);
	}

}
