package com.ansteel.core.service;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据校验接口。  
 */
public interface CheckService {
	/**
	 * 名称是否重复
	 * @param entity
	 * @return
	 */
	<T extends OperEntity> boolean isNameRepeat(T entity);
	/**
	 * 名称在节点下是否重复
	 * @param entity
	 * @param nodeName
	 * @param nodeId
	 * @return
	 */
	<T extends OperEntity> boolean isNodeNameRepeat(T entity,String nodeName,String nodeId);
}
