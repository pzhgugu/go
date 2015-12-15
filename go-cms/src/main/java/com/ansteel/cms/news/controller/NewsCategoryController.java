package com.ansteel.cms.news.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.cms.news.service.NewsCategoryService;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.dhtmlx.jsonclass.UDataSet;


@Controller
@RequestMapping(value = Public.ADMIN+"/newscategory")
public class NewsCategoryController  extends BaseController{
	
	@Autowired
	NewsCategoryService newsCategoryService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos= new ArrayList<EntityInfo>();
		EntityInfo treeEntityInfo = new EntityInfo();
		treeEntityInfo.setClazz(NewsCategory.class);
		entityInfos.add(treeEntityInfo);
		return entityInfos;
	}

	@RequestMapping("/image/save")
	public @ResponseBody UDataSet imageSave(
			@RequestParam(value = "doImage", required = false) MultipartFile file,
			@RequestParam("size")Double size,
			@RequestParam("x")Double x,
			@RequestParam("y")Double y,
			@RequestParam("w")Double width,
			@RequestParam("h")Double height,
			HttpServletRequest request,
			HttpServletResponse response){
		InputStream is =null;
		try {
			is = file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new PageException("读取图片错误！");
		}
		String id = newsCategoryService.saveImage(is,x.intValue(), y.intValue(), width.intValue(), height.intValue(),size.intValue());
		return new UDataSet(request,DHtmlxConstants.UI_DATA,id);
	}
	
}
