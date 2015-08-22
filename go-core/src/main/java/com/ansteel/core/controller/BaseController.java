package com.ansteel.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.tpl.core.ITpl;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.cache.ICacheCallback;
import com.ansteel.core.cache.TemplateCache;
import com.ansteel.core.cache.ViewCache;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.CustomTimestampEditor;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.modelscan.service.ModelScanService;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.common.view.service.ViewService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：基础控制器，用于绑定时间格式，扫描实体入口，模板统一入口。 
 */
public abstract class BaseController extends GlobalBaseController {

    public static final java.lang.String CREATE = "/create";
    public static final java.lang.String UPDATE = "/update";
    public static final java.lang.String READ = "/read";
    public static final java.lang.String DELETE = "/delete";
    public static final java.lang.String QUERY = "/query";

    @InitBinder
    protected void initBinder(HttpServletRequest request,
                              ServletRequestDataBinder binder) throws Exception {
        //对于需要转换为Date类型的属性，使用DateEditor进行处理
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);

		SimpleDateFormat datetimeFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		datetimeFormat.setLenient(false);

		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
				dateFormat, true));
		binder.registerCustomEditor(java.sql.Timestamp.class,
				new CustomTimestampEditor(datetimeFormat, true));
    }
    
    /**
	 * 实体信息获取类
	 * @return
	 */
	public abstract Collection<EntityInfo> getEntityInfos();
	

	@Autowired
	protected ViewElementService viewElementService;
	@Autowired
	protected ModelScanService modelScanService;
	

	@Autowired
	TplService tplService;
	
	
	@RequestMapping("/model/scan")
	@ResponseBody
	public void modelScan(HttpServletRequest request){
		String requestMappingName = this.getRequestMappingName(request);
		List<EntityInfo> entityInfos = (List<EntityInfo>) this.getEntityInfos();
		modelScanService.scanModel(entityInfos,requestMappingName);
	}
	
	@RequestMapping("/model/scan/{className}")
	@ResponseBody
	public void modelToFieldsScan(@PathClass("className")Class clazz,HttpServletRequest request){
		String requestMappingName = this.getRequestMappingName(request);
		modelScanService.scanModel(clazz,requestMappingName);
	}
	
	private String getRequestMappingName(HttpServletRequest request) {
		String requestMappingName = "";
		String[] names = ClassUtils.getClassRequestMapping(this.getClass());
        if(names!=null){
        	if(names.length==1){
        		requestMappingName=names[0];
        	}else{
		        for(String name :names){
		        	String p = request.getContextPath();
		        	if(request.getRequestURL().indexOf((request.getContextPath()+name))>0){
		        		requestMappingName=name;
		        		break;
		        	}
		        }
        	}
        }
		return requestMappingName;
	}
	
	@RequestMapping("/tpl/{tplName}/{className}/{fieldsCategory}")
	public String tpl(@PathVariable("tplName")String tplName,
			@PathClass("className")Class clazz,
			@PathVariable("fieldsCategory")String fieldsCategory,
			@RequestParam(value="_view",required = false) String viewName,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		
		ViewElement viewElement = viewElementService.getViewElement(
				request, response, clazz, 
				fieldsCategory, tplName, viewName, 
				this.getEntityInfos(),this.getClass());
		return tplService.runTpl(viewElement, model);
		
	}
}
