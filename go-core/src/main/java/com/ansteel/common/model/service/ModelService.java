package com.ansteel.common.model.service;

import java.util.List;

import com.ansteel.common.model.domain.Models;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型服务接口。  
 */
public interface ModelService {

	Models findOneByClazz(String name);

	Models findOneByName(String name);

	Models findOne(String id);

	List<Models> findAll();

}
