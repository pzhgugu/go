package com.ansteel.core.utils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.interfaces.service.MakeReport;

@Service
public class MakeReportImpl implements MakeReport {

	@Override
	public void show(List listMap, Map<String, String> hashMap, String rType,
			String inline, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show(String modelName, List listMap, String type,
			Object inline, HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List inExcel(String modelName, String inName, MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
