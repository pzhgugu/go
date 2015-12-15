package com.ansteel.cms.news.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.core.constant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.cms.news.core.NewsType;
import com.ansteel.cms.news.domain.News;
import com.ansteel.cms.news.domain.NewsCategory;
import com.ansteel.cms.news.domain.NewsItems;
import com.ansteel.cms.news.service.NewsCategoryService;
import com.ansteel.cms.news.service.NewsItemsService;
import com.ansteel.cms.news.service.NewsService;
import com.ansteel.common.tpl.core.IDhtmlxWidget;
import com.ansteel.common.tpl.core.ITpl;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.controller.SimpleController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;

@Controller
@RequestMapping(value = Public.ADMIN+"/news")
public class NewsController extends SimpleController {

	@Autowired
	NewsService newsService;

	@Resource(name = "singleGridTpl")
	ITpl singleGridTpl;

	@Autowired
	ViewElementService viewElementService;

	@Autowired
	TplService tplService;

	@Autowired
	NewsItemsService newsItemsService;

	@Autowired
	NewsCategoryService newsCategoryService;

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		Collection<EntityInfo> entityInfos = new ArrayList<EntityInfo>();

		// 树设置
		TreeInfo treeInfo = new TreeInfo();
		treeInfo.setTree(NewsCategory.class);
		Collection<Class> tables = new ArrayList<Class>();
		tables.add(News.class);
		treeInfo.setTables(tables);

		EntityInfo tree = new EntityInfo();
		tree.setClazz(NewsCategory.class);
		tree.setTree(treeInfo);
		entityInfos.add(tree);

		EntityInfo news = new EntityInfo();
		news.setClazz(News.class);
		news.setTree(treeInfo);
		entityInfos.add(news);

		EntityInfo newsItems = new EntityInfo();
		newsItems.setClazz(NewsItems.class);
		newsItems.setTree(treeInfo);
		entityInfos.add(newsItems);

		return entityInfos;
	}

	@RequestMapping("/container")
	public String container(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ViewElement viewElement = viewElementService.getViewElement(request,
				response, News.class, TplViewConstant.DEFAULT_SHORT,
				this.getEntityInfos(), this.getClass());

		model.addAllAttributes(singleGridTpl.getModel(viewElement));
		List<NewsCategory> newsCategorys = newsCategoryService
				.findAll(new Sort("displayOrder"));
		model.addAttribute("P_NEWCATEGORYS", newsCategorys);
		return FisUtils.page("cms:pages/news/newscontainer.html");
	}

	@RequestMapping("/publishInfo")
	public String publishInfo(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ViewElement viewElement = viewElementService.getViewElement(request,
				response, NewsItems.class, TplViewConstant.DEFAULT_SHORT,
				this.getEntityInfos(), this.getClass());

		Tpl tpl = new Tpl();
		tpl.setColumnNumber(2);
		tpl.setOffsetLeft(50);
		tpl.setOffsetTop(6);
		tpl.setBlockWidth(800);
		tpl.setInputWidth(230);

		viewElement.setTpl(tpl);

		// 构建字段表单元素
		IDhtmlxWidget form = ContextHolder
				.getSpringBean(ServiceConstants.DTHMLX_FORM);
		model.addAllAttributes(form.structure(viewElement));

		return FisUtils.page("cms:pages/news/publishInfo.html");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String news(HttpServletRequest request,
			HttpServletResponse response, Model model) {

		model.addAttribute(ViewModelConstant.P_TREE_CLASS,
				NewsCategory.class.getSimpleName());
		model.addAttribute(TplViewConstant.P_TPL_MODULES, "/admin/news");

		return FisUtils.page("cms:pages/news/news.html");
	}

	/**
	 * REST风格创建
	 * 
	 * @param entity
	 * @param request
	 * @param response
	 * @param result
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void save(@Valid NewsItems entity, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {
		if (result.hasErrors()) {
			ExceprionUtils.BindingResultError(result);
		}
		String ip = RequestUtils.getIP(request);
		entity.setPostip(ip);
		newsItemsService.backstagePublish(entity);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delect/{id}", method = RequestMethod.GET)
	@ResponseBody
	public void delect(@PathVariable String id, HttpServletRequest request,
			HttpServletResponse response) {
		newsItemsService.delect(id);
	}

	@RequestMapping(value = "/find/{id}")
	@ResponseBody
	public UDataSet findOne(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		NewsItems newsItems = newsItemsService.findOne(id);
		return new UDataSet(request, DHtmlxConstants.UI_DATA, newsItems);
	}

	@RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UDataSet message(@PathVariable String id,
			HttpServletRequest request, HttpServletResponse response) {
		NewsItems newsItems = newsItemsService.findOne(id);
		String message = newsItems.getMessage();
		if (message.length() > 100) {
			message = message.substring(0, 100) + "......";
		}
		return new UDataSet(request, DHtmlxConstants.UI_OBJECT, message);
	}

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public @ResponseBody
	UDataSet load(
			@RequestParam(value = "_pid", required = false) String value,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "_type", required = false) String type,// 排序字段名
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "_order", required = false) String order,// 排序字段名
			HttpServletRequest request, HttpServletResponse response) {
		NewsType newsType = this.getNewsType(request, type);
		Pageable pageable = PageUtils.getPageable(order, posStart);
		Page page = newsService.findByCategoryIdAndNewType(value, newsType,
				pageable);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
	}

	private NewsType getNewsType(HttpServletRequest request, String type) {
		NewsType newsType = NewsType.publish;
		Map<String, String[]> m = request.getParameterMap();
		if (StringUtils.hasText(type)) {
			try {
				newsType = NewsType.valueOf(type);
			} catch (Exception e) {
				throw new PageException(type + ",未知资讯类型！");
			}

		}
		return newsType;
	}

	@RequestMapping("/loadTree")
	@ResponseBody
	public UDataSet loadTree(
			@RequestParam(value = "id", required = false) String pid,
			HttpServletRequest request, HttpServletResponse response) {
		if (pid != null && pid.equals("0")) {
			pid = null;
		}
		List result = newsService.treeQuery(pid);

		return new UDataSet(DHtmlxConstants.UI_ASYN_TREE, result);
	}

	@RequestMapping("/queryPage")
	public @ResponseBody
	UDataSet queryPageAjax(
			@RequestParam(value = "_type", required = false) String type,// 过滤字段名（一般用于主从表）
			@RequestParam(value = "_pid", required = false) String pid,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "_order", required = false) String order,// 排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request, HttpServletResponse response) {
		Pageable pageable = null;
		if (StringUtils.hasText(posStart)) {
			pageable = new PageRequest(PageUtils.getTotalPages(posStart),
					PageUtils.getMaxResults());
		}
		NewsType newsType = this.getNewsType(request, type);
		Page page = newsService.find(newsType.getValue(), pid, order,
				queryList, pageable);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
	}

	@RequestMapping("/a/queryDetailPage/{className}")
	public @ResponseBody
	UDataSet queryDetailPageAjax(
			@RequestParam(value = "_type", required = false) String type,// 过滤字段名（一般用于主从表）
			@RequestParam(value = "_pid", required = false) String pid,// 过滤字段值（一般用于主从表）
			@RequestParam(value = "posStart", required = false) String posStart,// 分页当前记录行
			@RequestParam(value = "count", required = false) String count,// 分页记录行
			@RequestParam(value = "_order", required = false) String order,// 排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request, HttpServletResponse response) {
		Pageable pageable = null;
		if (StringUtils.hasText(posStart)) {
			pageable = new PageRequest(PageUtils.getTotalPages(posStart),
					PageUtils.getMaxResults());
		}
		NewsType newsType = this.getNewsType(request, type);
		Page page = newsService.find(newsType.getValue(), pid, order,
				queryList, pageable);
		return new UDataSet(request, DHtmlxConstants.UI_ROWS, page);
	}

	@RequestMapping("/tpl/{tplName}/{className}/{fieldsCategory}")
	public String tpl(@PathVariable("tplName") String tplName,
			@PathClass("className") Class clazz,
			@PathVariable("fieldsCategory") String fieldsCategory,
			@RequestParam(value = "_view", required = false) String viewName,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {

		ViewElement viewElement = viewElementService.getViewElement(request,
				response, clazz, fieldsCategory, tplName, viewName,
				this.getEntityInfos(), this.getClass());
		return tplService.runTpl(viewElement, model);

	}

	
	@RequestMapping("/operation/{type}")
	public @ResponseBody
	void operation(@PathVariable("type") String type,
			@RequestParam("sId[]") String[] ids, HttpServletRequest request,
			HttpServletResponse response) {
		newsService.operation(type,ids);
	}
	
	@RequestMapping("/category/{type}")
	public @ResponseBody
	void category(@PathVariable("type") String type,
			@RequestParam("sId[]") String[] ids, HttpServletRequest request,
			HttpServletResponse response) {
		newsService.category(type,ids);
	}
	
}
