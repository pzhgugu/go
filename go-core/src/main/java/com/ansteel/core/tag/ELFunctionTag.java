package com.ansteel.core.tag;

import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：jstl标签函数。  
 */
public class ELFunctionTag {
	/**
	 * 输出Clob字符串
	 * @param clob
	 * @return
	 */
	public static String clob(Object clob) {
		if (clob == null) {
			return "";
		}
		StringBuffer clobString = new StringBuffer();
		if (clob instanceof Clob) {
			int y;
			char ac[] = new char[4096];
			Reader reader = null;
			try {
				reader = ((Clob) clob).getCharacterStream();
				while ((y = reader.read(ac, 0, 4096)) != -1) {
					clobString.append(new String(ac, 0, y));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			clobString.append(clob.toString());
		}
		return clobString.toString();
	}
	/**
	 * 转换list的Clob
	 * @param results
	 * @return
	 */
	public static List listClob(List results) {
		if (results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				Map m = (Map) results.get(i);
				for (Object o : m.keySet()) {
					String value = "";
					try {
						value = clob(m.get(o));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					m.put(o, value);
				}
			}
		}
		return results;
	}

	/**
	 * 替换多出的字符串...
	 * @param max
	 * @param replaceChar
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String cutChar(Integer max, String replaceChar, String value)
			throws Exception {
		if (max != null && max > 0) {
			if (StringUtils.hasText(value)) {
				if (value.length() > max) {
					value = value.substring(0, max);
					if (StringUtils.hasText(value)) {
						value += replaceChar;
					}
				}
			}
		}
		return value;
	}
}
