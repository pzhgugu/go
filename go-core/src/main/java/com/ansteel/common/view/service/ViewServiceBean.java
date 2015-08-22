package com.ansteel.common.view.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.tpl.domain.TplSecurity;
import com.ansteel.common.tpl.domain.TplVariable;
import com.ansteel.common.view.domain.View;
import com.ansteel.common.view.repository.ViewRepository;
import com.ansteel.core.cache.ICacheCallback;
import com.ansteel.core.cache.ViewCache;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.tpl.service.TplSecurityService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图服务实现类。
 */
@Service
@Transactional(readOnly=true)
public class ViewServiceBean implements ViewService {
	
	@Autowired
	ViewRepository viewRepository;
	
	@Autowired
	TplSecurityService tplSecurityService;

	@Override
	public View getNameToView(String key) {
		return viewRepository.findOneByName(key);		
	}

	@Override
	public View findOneByUrlPath(String key) {
		return viewRepository.findOneByUrlPath(key);
	}

	@Override
	public View getCacheView(String viewName, String url) {
		final ViewService viewService=this;
		ViewCache viewCache = new ViewCache();
		if (StringUtils.hasText(viewName)) {
			View view = (View) viewCache.getCache(viewName,
					new ICacheCallback() {
						@Override
						public Object get(String key) {
							return viewService.getNameToView(key);
						}
					});
			return view;
		} else {
			View view = (View) viewCache.getCache(url, new ICacheCallback() {
				@Override
				public Object get(String key) {
					return viewService.findOneByUrlPath(key);
				}
			});
			return view;
		}
	}

	@Override
	public Map<String,Object> getViewVariable(View view){
		Map<String,Object> varMap = new HashMap<>();
		if(view==null){
			return varMap; 
		}
		Collection<TplVariable> varTplList =view.getTplVariableList();
		for(TplVariable tv:varTplList){
        	varMap.put(tv.getName(), tv.getVarValue());
        }
		Collection<TplSecurity> securityList = view.getTplSecurityList();
		varMap.putAll(tplSecurityService.getTplSecurityPermission(securityList));
		return varMap;
		
	}

}
