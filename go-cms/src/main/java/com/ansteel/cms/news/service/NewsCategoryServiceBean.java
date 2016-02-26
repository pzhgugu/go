package com.ansteel.cms.news.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.ansteel.common.attachment.service.FileAttachmentService;
import com.ansteel.common.attachment.service.ImageAttachmentTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.cms.news.repository.NewsCategoryRepository;
import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.core.utils.ImageUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.attachment.service.AttachmentService;

@Service
@Transactional(readOnly=true)
public class NewsCategoryServiceBean implements NewsCategoryService{
	
	@Autowired
	FileAttachmentService fileAttachmentService;

	@Autowired
	ImageAttachmentTreeService imageAttachmentTreeService;
	
	@Autowired
	NewsCategoryRepository newsCategoryRepository;
	
	@Value("${go_pro.attPath}")
	private String attPath;


	@Override
	@Transactional
	public String saveImage(InputStream inputStream, int x, int y, int width,
			int height, int size) {
		AttachmentTree attachmentTree = imageAttachmentTreeService.get();
		String outPath=imageAttachmentTreeService.getPath(attachmentTree,StringUtils.getUuid()+".jpg");
		ImageUtils.abscut(inputStream,x, y, width, height,size, attPath+outPath);
		Attachment attachment= fileAttachmentService.save(outPath, attachmentTree);
		return attachment.getId();		
	}

	@Override
	public List<NewsCategory> findAll(Sort sort) {
		return newsCategoryRepository.findAll(sort);
	}

	@Override
	public List findTree(Specification spec) {
		return newsCategoryRepository.findTree(spec);
	}

	@Override
	public NewsCategory findOneByName(String name) {
		return newsCategoryRepository.findOneByName(name);
	}

	@Override
	public NewsCategory findOne(String id) {
		return newsCategoryRepository.findOne(id);
	}

}
