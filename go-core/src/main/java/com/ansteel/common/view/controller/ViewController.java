package com.ansteel.common.view.controller;

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
import com.ansteel.common.view.domain.View;
import com.ansteel.common.view.domain.ViewTree;
import com.ansteel.common.view.service.ViewService;
import com.ansteel.core.controller.BaseController;
import com.ansteel.core.controller.SaveBefore;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.CheckService;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/view")
public class ViewController  extends BaseController implements SaveBefore{

	@Override
	public Collection<EntityInfo> getEntityInfos() {
		
		//树设置
		TreeInfo treeInfo=new TreeInfo();
		treeInfo.setTree(ViewTree.class);
		Collection<Class> tables=new ArrayList<Class>();
		tables.add(View.class);
		treeInfo.setTables(tables);
		
		//主从表设置
		MainSubInfo msi=new MainSubInfo();
		msi.setPrincipal(View.class);
		Collection<Class> subs=new ArrayList<Class>();
		subs.add(TplVariable.class);
		subs.add(TplJavascript.class);
		subs.add(TplCss.class);
		subs.add(TplSecurity.class);
		msi.setSubordinate(subs);
		
		Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
		EntityInfo viewTree = new EntityInfo();
		viewTree.setClazz(ViewTree.class);
		viewTree.setTree(treeInfo);
		eis.add(viewTree);
		
		EntityInfo view = new EntityInfo();
		view.setClazz(View.class);
		view.setMainSub(msi);
		view.setTree(treeInfo);
		eis.add(view);

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
	
	@Autowired
	ViewService viewService;

	@Override
	public <T extends BaseEntity> void SaveCheck(T entity) {
		if(entity instanceof View){
			View o=(View) entity;			
			Assert.isTrue(!checkService.isNameRepeat(o),o.getName()+"名称重复请重新设置！");
			View urlView = viewService.findOneByUrlPath(o.getUrlPath());
			if(urlView!=null&&!urlView.getId().equals(o.getId())){
				throw new PageException(o.getUrlPath()+"url映射已经存在，请检查！");
			}
		}else if(entity instanceof TplVariable){
			TplVariable o=(TplVariable) entity;
			Tpl m = o.getTpl();
			View v = o.getView();
			if(m==null){
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",v.getId()),o.getName()+"名称重复请重新设置！");
			}else{
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"view",m.getId()),o.getName()+"名称重复请重新设置！");
			}
			
		}if(entity instanceof TplJavascript){
			TplJavascript o=(TplJavascript) entity;
			Tpl m = o.getTpl();
			View v = o.getView();
			if(m==null){
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",v.getId()),o.getName()+"名称重复请重新设置！");
			}else{
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"view",m.getId()),o.getName()+"名称重复请重新设置！");
			}
		}if(entity instanceof TplCss){
			TplCss o=(TplCss) entity;
			Tpl m = o.getTpl();
			View v = o.getView();
			if(m==null){
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"tpl",v.getId()),o.getName()+"名称重复请重新设置！");
			}else{
				Assert.isTrue(!checkService.isNodeNameRepeat(o,"view",m.getId()),o.getName()+"名称重复请重新设置！");
			}
		}
	}

}
