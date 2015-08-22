package com.ansteel.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.context.CustomTimestampEditor;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.common.modelscan.service.ModelScanService;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：简单控制器，实现时间格式绑定，扫描实体。 
 */
public abstract class SimpleController {

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
	protected ModelScanService modelScanService;	
	
	@RequestMapping("/model/scan")
	@ResponseBody
	public void modelScan(HttpServletRequest request){
		String requestMappingName = this.getRequestMappingName(request);
		List<EntityInfo> entityInfos = (List<EntityInfo>) this.getEntityInfos();
		modelScanService.scanModel(entityInfos,requestMappingName);
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
	
	@RequestMapping("/scan/{className}")
	@ResponseBody
	public void modelToFieldsScan(@PathClass("className")Class clazz,HttpServletRequest request){
		String requestMappingName = this.getRequestMappingName(request);
		modelScanService.scanModel(clazz,requestMappingName);
	}
	
}
