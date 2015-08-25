package com.ansteel.cms.news.web;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ansteel.cms.news.core.NewsType;
import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.cms.news.domain.NewsItems;
import com.ansteel.cms.news.service.NewsCategoryService;
import com.ansteel.cms.news.service.NewsItemsService;
import com.ansteel.cms.news.service.NewsService;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;

@Controller
@RequestMapping(value = Public.CLIENT+"/newshow")
public class NewShowController {
	/**
	 * 当前分类
	 */
	private static final String P_CURRENT_NEWSCATEGORY = "P_CURRENT_NEWSCATEGORY";

	/**
	 * 当前父分类
	 */
	private static final String P_PARENT_NEWSCATEGORY = "P_PARENT_NEWSCATEGORY";

	/**
	 * 当前子分类
	 */
	private static final String P_CHILD_NEWSCATEGORY = "P_CHILD_NEWSCATEGORY";

	/**
	 * 当前分类记录分页
	 */
	private static final String P_PAGE_NEWS = "P_PAGE_NEWS";

	private static final String P_NEWSITEMS = "P_NEWSITEMS";

	@Autowired
	NewsService newsService;
	
	@Autowired
	NewsCategoryService newsCategoryService;
	
	@Autowired
	NewsItemsService newsItemsService;
	
	@RequestMapping("/list")
	public String list(Model model,
			@RequestParam(value = "type", required = false) String type,// 资讯分类（即叶子节点）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "order", required = false) String order,// 排序字段名
			HttpServletRequest request,
			HttpServletResponse response) {
		Pageable pageable = PageUtils.getPageable(order, posStart);
		if(!StringUtils.hasText(type)){
			throw new PageException("你访问的页面不存在！");
		}
		//获取分类
		NewsCategory newsCategory = newsCategoryService.findOneByName(type);
		Assert.notNull(newsCategory, type+"此分类不存在，请检查！");
		NewsCategory newsCategoryParent = newsCategory.getParent();
		Collection<NewsCategory> newsCategoryChild = newsCategoryParent.getChildren();
		Page page=newsService.findByCategoryIdAndNewType(newsCategory.getId(), NewsType.publish, pageable);
		
		model.addAttribute(P_CURRENT_NEWSCATEGORY, newsCategory);
		model.addAttribute(P_PARENT_NEWSCATEGORY, newsCategoryParent);
		model.addAttribute(P_CHILD_NEWSCATEGORY, newsCategoryChild);
		model.addAttribute(P_PAGE_NEWS, page);
		return FisUtils.page("cms:pages/shopnews/news.html");
		
	}
	
	@RequestMapping("/view/{id}")
	public String view(Model model,
			@PathVariable(value = "id") String id,
			HttpServletRequest request,
			HttpServletResponse response) {
		NewsItems newsItems = newsItemsService.findOne(id);
		NewsCategory newsCategory=newsCategoryService.findOne(newsItems.getCategoryId());
		NewsCategory newsCategoryParent = newsCategory.getParent();
		Collection<NewsCategory> newsCategoryChild = newsCategoryParent.getChildren();
		
		model.addAttribute(P_CURRENT_NEWSCATEGORY, newsCategory);
		model.addAttribute(P_PARENT_NEWSCATEGORY, newsCategoryParent);
		model.addAttribute(P_CHILD_NEWSCATEGORY, newsCategoryChild);
		model.addAttribute(P_NEWSITEMS, newsItems);
		return FisUtils.page("cms:pages/shopnews/view.html");
		
	}

}
