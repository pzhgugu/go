package com.ansteel.cms.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.cms.news.domain.NewsItems;
import com.ansteel.cms.news.repository.NewsItemsRepository;
import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;

@Service
@Transactional(readOnly=true)
public class NewsItemsServiceBean implements NewsItemsService {
	
	@Autowired
	NewsItemsRepository newsItemsRepository;
	

	@Override
	public NewsItems findOne(String id) {
		return newsItemsRepository.findOne(id);
	}

	@Override
	@Transactional
	public NewsItems save(NewsItems entity) {
		String id=entity.getId();
		if(StringUtils.hasText(id)){
			NewsItems dataBase = this.findOne(id);
		
			try {
				BeanUtils.applyIf(dataBase, entity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newsItemsRepository.save(entity);
	}

	@Override
	@Transactional
	public NewsItems backstagePublish(NewsItems entity) {
		//设置为后台发布
		entity.setOrigin(1);
		//设置发布人
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		entity.setUserName(userInfo.getAlias());
		return this.save(entity);
	}

	@Override
	@Transactional
	public void delect(String id) {
		newsItemsRepository.delete(id);
	}

	@Override
	@Transactional
	public void cream(String id, int value) {
		newsItemsRepository.cream(id,value);
	}

	@Override
	@Transactional
	public void top(String id, int value) {
		newsItemsRepository.top(id,value);
	}

	@Override
	@Transactional
	public void comment(String id, int value) {
		newsItemsRepository.comment(id,value);
	}

	@Override
	@Transactional
	public void grade(String id, int value) {
		newsItemsRepository.grade(id,value);
	}

	
}
