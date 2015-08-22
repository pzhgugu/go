package com.ansteel.common.tpl.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.viewelement.core.ViewElementUtils;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：复杂左树右单表附从表模板（如模型管理界面）。
 */
@Service
public class TreeTwoOneToManyTpl  extends AbstractTpl implements ITpl{
	
	private static final String TWO = "two";
	

	@Override
	protected Map<String, Object> loadWidget(ViewElement viewElement) {
		//获取树主从
		Map<String, Object> widgetModel=new TreeOneToManyTpl().loadWidget(viewElement);
		//自定义字段页面元素
		String prefix = "TWO";
		viewElement.setViewPrefix(prefix);
		
		HttpServletRequest request = viewElement.getRequest();
		Class twoClaSS= null;
		if(request.getParameterMap().containsKey(TWO)){
			String twoname = request.getParameter(TWO);
			for(EntityInfo entityInfo:viewElement.getEntityInfoList()){
				if(entityInfo.getClazz().getSimpleName().equalsIgnoreCase(twoname)){
					twoClaSS=entityInfo.getClazz();
					break;
				}
			}
		}else{
			throw new PageException("此模板需设置tow实体名称！");
		}
		Assert.notNull(twoClaSS, "tow实体名称在EntityInfo中没有设置！");
		ModelService modelService=ContextHolder.getSpringBean("modelServiceBean");
		Models model=modelService.findOneByName(twoClaSS.getName());
		
		
		ViewElementUtils.setViewElement(viewElement,model,TplViewConstant.DEFAULT_SHORT,twoClaSS);
		widgetModel.put(prefix+"_"+TplViewConstant.P_CLASS_NAME, viewElement.getCurrentSimpleName());
		
		widgetModel.putAll(new SingleGridTpl().loadWidget(viewElement));
		return widgetModel;
	}

}
