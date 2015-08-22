package com.ansteel.core.controller;

import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：保存更新接口，用于保存更新前需调用的方法。 
 */
public interface SaveBefore {

	<T extends BaseEntity> void SaveCheck(T entity);
}
