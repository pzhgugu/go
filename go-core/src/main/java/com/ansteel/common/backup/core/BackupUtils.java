package com.ansteel.common.backup.core;

import org.springframework.util.StringUtils;

import com.ansteel.core.exception.PageException;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份解析xml工具类。  
 */
public class BackupUtils {

	public static IExecuteXml getExecuteXml(Class clazz) {
		IExecuteXml executeService = null;
		try {
			executeService = (IExecuteXml) clazz.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return executeService;
	}

	public static IExecuteXml getExecuteXmlName(String seviceNamer) {
		IExecuteXml executeService = null;
		if (StringUtils.hasText(seviceNamer)) {
			Class clazz = null;
			try {
				clazz = Class.forName(seviceNamer);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				executeService = (IExecuteXml) clazz.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return executeService;
		} else {
			// executeService = new DefaultExecuteXml();
			throw new PageException("请指定xml解析接口服务！");
		}
	}

}
