package com.ansteel.common.backup.core;

import java.util.List;
import java.util.Map;

import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导出接口。  
 */
public interface IExecuteXml {
	 /**
     * 导出所有查询实体
     * @return
     */
	List<BaseEntity> getAllRecode() ;
	
	Class getClazz();
	
	void importSave(Map<Class, Object> xmlMap, Object o,int type);
}
