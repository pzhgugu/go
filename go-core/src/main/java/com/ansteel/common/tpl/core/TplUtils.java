package com.ansteel.common.tpl.core;

import com.ansteel.core.utils.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板工具类。
 */
public class TplUtils {

	public static String getName(String name,String prefix){
		if(StringUtils.hasText(prefix)){
			return prefix+"_"+name;
		}
		return name;
	}
}
