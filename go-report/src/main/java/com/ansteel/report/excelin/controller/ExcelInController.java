package com.ansteel.report.excelin.controller;

import java.util.ArrayList;
import java.util.Collection;

import com.ansteel.core.constant.Public;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ansteel.core.controller.BaseController;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.report.excelin.domain.ExcelIn;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入控制器。
 */
@Controller
@RequestMapping(value = Public.ADMIN+ "/excelin")
public class ExcelInController extends BaseController{
	
	@Override
	public Collection<EntityInfo> getEntityInfos() {	
		
		Collection<EntityInfo> eis = new ArrayList<EntityInfo>();
		EntityInfo entity = new EntityInfo();
		entity.setClazz(ExcelIn.class);
		eis.add(entity);		
		return eis;
	}
}
