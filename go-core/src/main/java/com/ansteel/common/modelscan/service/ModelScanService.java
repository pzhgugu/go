package com.ansteel.common.modelscan.service;

import java.util.List;
import java.util.Map;

import com.ansteel.common.model.domain.Models;
import com.ansteel.core.domain.EntityInfo;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型扫描接口。  
 */
public interface ModelScanService {

	/**
	 * 扫描一个实体
	 * @param entityInfo
	 * @param requestMappingName
	 * @return
	 */
	Map<Class, Models> scanModel(EntityInfo entityInfo,String requestMappingName);
	/**
	 * 扫描多个实体
	 * @param entityInfo
	 * @param requestMappingName
	 * @return
	 */
	Map<Class, Models> scanModel(List<EntityInfo> entityInfo,String requestMappingName);
	/**
	 * 扫描模型
	 * @param clazz
	 * @param requestMappingName
	 * @return
	 */
	Models scanModel(Class clazz,String requestMappingName);
}
