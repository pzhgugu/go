package com.ansteel.common.tpl.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.domain.TplCss;
import com.ansteel.common.tpl.domain.TplJavascript;
import com.ansteel.common.tpl.domain.TplSecurity;
import com.ansteel.common.tpl.domain.TplVariable;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.service.CheckService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/tpl")
public class TplController  extends BaseController implements SaveBefore{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		MainSubInfo msi=new MainSubInfo();
		msi.setPrincipal(Tpl.class);
		Collection<Class> subs=new ArrayList<Class>();
		subs.add(TplVariable.class);
		subs.add(TplJavascript.class);
		subs.add(TplCss.class);
		subs.add(TplSecurity.class);
		msi.setSubordinate(subs);
		
		Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
		EntityInfo tpl = new EntityInfo();
		tpl.setClazz(Tpl.class);
		tpl.setMainSub(msi);
		eis.add(tpl);

		EntityInfo tplCss = new EntityInfo();
		tplCss.setClazz(TplCss.class);
		tplCss.setMainSub(msi);
		eis.add(tplCss);

		EntityInfo tplElement = new EntityInfo();
		tplElement.setClazz(TplSecurity.class);
		tplElement.setMainSub(msi);
		eis.add(tplElement);

		EntityInfo tplJavascript = new EntityInfo();
		tplJavascript.setClazz(TplJavascript.class);
		tplJavascript.setMainSub(msi);
		eis.add(tplJavascript);
		
		EntityInfo tplVariable = new EntityInfo();
		tplVariable.setClazz(TplVariable.class);
		tplVariable.setMainSub(msi);
		eis.add(tplVariable);
		return eis;
	}
	
	@Autowired
	CheckService checkService;

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity instanceof Tpl){
			Tpl o=(Tpl) entity;
			Assert.isTrue(!checkService.isNameRepeat(o),o.getName()+"名称重复请重新设置！");
		}else if(entity instanceof TplVariable){
			TplVariable o=(TplVariable) entity;
			Tpl m = o.getTpl();
			Assert.notNull(m, m.getAlias()+",模型没有找到,请检查！");
			Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",m.getId()),o.getName()+"名称重复请重新设置！");
		}if(entity instanceof TplJavascript){
			TplJavascript o=(TplJavascript) entity;
			Tpl m = o.getTpl();
			Assert.notNull(m, m.getAlias()+",模型没有找到,请检查！");
			Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",m.getId()),o.getName()+"名称重复请重新设置！");
		}if(entity instanceof TplCss){
			TplCss o=(TplCss) entity;
			Tpl m = o.getTpl();
			Assert.notNull(m, m.getAlias()+",模型没有找到,请检查！");
			Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",m.getId()),o.getName()+"名称重复请重新设置！");
		}
	}

}
