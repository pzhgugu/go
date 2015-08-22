package com.ansteel.core.utils;
import java.io.Reader;
import java.sql.Clob;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：jstl标签函数。  
 */
public class ELFunctionTag {
    public String clob(Object clob) {
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
}
