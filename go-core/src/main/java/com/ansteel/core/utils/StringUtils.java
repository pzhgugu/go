package com.ansteel.core.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ansteel.dhtmlx.widget.form.Options;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：org.springframework.util.StringUtils工具类扩展。  
 */
public class StringUtils extends org.springframework.util.StringUtils {
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 去除回车换行制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	public static String join(Object[] array, String string) {
		if (array == null) {
			return null;
		}
		int arraySize = array.length;
		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0]
				.toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(string);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	public static Map<String, String> getOptionsSplit(String recode) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(StringUtils.hasText(recode)){
			String[] values = recode.split(";");
			for(String v:values){
				String[] vs = v.split("=");
				if(vs.length==2){
					String key = vs[0].trim();
					if(StringUtils.hasText(key)){
						map.put(key,vs[1].trim());
					}
				}
			}
		}
		return map;
	}
	

	public static Map<String, String> getOptionsSplitUpper(String recode) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if(StringUtils.hasText(recode)){
			String[] values = recode.split(";");
			for(String v:values){
				String[] vs = v.split("=");
				if(vs.length==2){
					String key = vs[0].trim();
					if(StringUtils.hasText(key)){
						map.put(key.toUpperCase(),vs[1].trim());
					}
				}
			}
		}
		return map;
	}
}