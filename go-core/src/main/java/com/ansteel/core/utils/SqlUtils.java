package com.ansteel.core.utils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql工具类。  
 */
public class SqlUtils {
	//效验  
    public static String sqlValidate(String sql) {  
    	return sql.replaceAll(".*([';]+|(--)+).*", " ");
    }  
}
