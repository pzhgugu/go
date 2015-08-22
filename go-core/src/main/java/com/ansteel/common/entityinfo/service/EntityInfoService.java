package com.ansteel.common.entityinfo.service;

import java.util.Collection;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体信息服务接口。  
 */
public interface EntityInfoService {
	/**
	 * 通过类得到实体信息
	 * @param clazz
	 * @return
	 */
	Collection<EntityInfo> getEntityInfoList(Class clazz);
	/**
	 * 通过类，对象名称得到实体
	 * @param clazz
	 * @param entityName
	 * @return
	 */
	BaseEntity getBaseEntity(Class clazz,String objectName);
	/**
	 * 通过类，对象名称得到实体信息
	 * @param clazz
	 * @param objectName
	 * @return
	 */
	EntityInfo getEntityInfo(Class clazz,String objectName);
	/**
	 * 
	 * @param clazz
	 * @param objectName
	 * @return
	 */
	Class getBaseEntityClass(Class clazz, String objectName);
}
