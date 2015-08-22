package com.ansteel.cms.news.service;

import javax.servlet.ServletRequest;

import org.springframework.stereotype.Service;

import com.ansteel.core.tag.IBlockService;

@Service("topBlockService")
public class TopBlockService implements IBlockService{

	@Override
	public void doStart(ServletRequest request) {
		request.setAttribute("P_USER", "游客");
	}

}
