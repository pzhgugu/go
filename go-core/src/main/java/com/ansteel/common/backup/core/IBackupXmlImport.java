package com.ansteel.common.backup.core;

import java.util.List;
import java.util.Map;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份接口。  
 */
public interface IBackupXmlImport {

    /**
     * 导入xml
     * @param
     * @return
     */
    int importXml(Map<Class, Object> xmlMap);

    /**
     * 设置导出类型
     * @param type
     */
    void setType(int type);
}
