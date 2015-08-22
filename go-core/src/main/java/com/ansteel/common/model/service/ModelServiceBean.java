package com.ansteel.common.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.model.repository.ModelsRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体模型服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class ModelServiceBean implements ModelService {
	
	@Autowired
	ModelsRepository modelsRepository;

	@Override
	public Models findOneByClazz(String name) {
		Models model = modelsRepository.findOneByClazz(name);
		return model;
	}

	@Override
	public Models findOneByName(String name) {
		return modelsRepository.findOneByName(name);
	}

	@Override
	public Models findOne(String id) {
		return modelsRepository.findOne(id);
	}

	@Override
	public List<Models> findAll() {
		return modelsRepository.findAll();
	}

}
