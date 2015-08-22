package com.ansteel.common.tpl.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import com.ansteel.common.tpl.core.ITpl;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.domain.TplSecurity;
import com.ansteel.common.tpl.domain.TplVariable;
import com.ansteel.common.tpl.repository.TplRepository;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.cache.ICacheCallback;
import com.ansteel.core.cache.TemplateCache;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.common.view.service.ViewService;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板服务实现类。
 */
@Service
@Transactional(readOnly=true)
public class TplServiceBean implements TplService {
	
	@Autowired
	TplRepository tplRepository;
	
	@Autowired
	ViewService viewService;
	
	@Autowired
	TplSecurityService tplSecurityService;

	@Override
	public Tpl getNameToTpl(String key) {
		return tplRepository.findOneByName(key);
	}

	@Override
	public String runTpl(ViewElement viewElement, Model model) {
		Tpl template = viewElement.getTpl();
		//5、组织模板服务类（实体信息、控制器服务名称、tpl信息、视图信息、clazz）
		//6、执行tpl服务
        Map<String,Object> models = new HashMap<String,Object>();
        String moduleName = template.getModuleClass();
        Class moduleClass = null;
        try {
			moduleClass = ClassUtils.forName(moduleName,this.getClass().getClassLoader());
		} catch (ClassNotFoundException | LinkageError e) {
			throw new PageException(moduleName+",模板服务不存在！");
		}
        Object tplService =null;
        try {
			tplService= moduleClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new PageException(moduleName+":"+e.getMessage());
		}        

        Assert.isTrue(tplService instanceof ITpl,moduleName+",请实现ITpl接口！");
        models = ((ITpl) tplService).getModel(viewElement);
        //7、获取tpl模板jsp路径
        String tplPath = template.getTplPath();
        Assert.hasText(tplPath, template.getAlias()+",模板路径不能为空！");

        String jspName = template.getJspName();
        Assert.hasText(jspName, template.getAlias()+",模板jsp不能为空！");
        
        models.put(ViewModelConstant.P_TPL_PATH_JSP, tplPath+"/"+jspName);
        models.put(ViewModelConstant.P_TPL_PATH_JSP, tplPath+"/"+jspName);
        
        
        Map<String, Object> varMap = new HashMap<>();
        //模板变量
        varMap.putAll(getTplVariable(viewElement.getTpl()));
        //视图变量
        varMap.putAll(getViewVariable(viewElement.getView()));

        models.put(ViewModelConstant.P_VARIABLE, varMap);
        model.addAllAttributes(models);
		return FisUtils.page("core:widget/tpl/tpl.html");
	}
	
	@Override
	public Map<String,Object> getTplVariable(Tpl tpl){
		Map<String,Object> varMap = new HashMap<>();
		if(tpl==null){
			return varMap; 
		}
		Collection<TplVariable> varTplList =tpl.getTplVariableList();
		for(TplVariable tv:varTplList){
        	varMap.put(tv.getName(), tv.getVarValue());
        }
		Collection<TplSecurity> securityList = tpl.getTplSecurityList();
		varMap.putAll(tplSecurityService.getTplSecurityPermission(securityList));
		return varMap;
		
	}

	@Override
	public Map<String,Object> getViewVariable(View view){
		return viewService.getViewVariable(view);
		
	}

	@Override
	public Tpl getCacheTemplate(String tplName) {
		final TplServiceBean tplService = this;
		Assert.hasText(tplName, "模板名称为空！");
		TemplateCache templateCache = new TemplateCache();
		Tpl template = (Tpl) templateCache.getCache(tplName,
				new ICacheCallback() {
					@Override
					public Object get(String key) {
						return tplService.getNameToTpl(key);
					}
				});
		return template;

	}
}
