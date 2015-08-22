package com.ansteel.common.tpl.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansteel.common.model.domain.Models;
import com.ansteel.common.model.repository.ModelsRepository;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：主从表模板。
 */
@Service
public class OneToManyTpl extends AbstractTpl implements ITpl {
	
	@Override
	protected Map<String, Object> loadWidget(ViewElement viewElement) {
		//得到单表模板的数据
		Map<String, Object> widgetModel=new SingleGridTpl().loadWidget(viewElement);
		
		//获取从表查询主键id名称
		List<SubInfo> subInfoList = this.getSubInofList(viewElement);	
		
		widgetModel.put(ViewModelConstant.P_SUB_INFO, JsonUtils.jsonFromObject(subInfoList));
		
		return widgetModel;
	}

	/**
	 * 获取从表信息
	 * @param viewElement
	 * @return
	 */
	private List<SubInfo> getSubInofList(ViewElement viewElement) {
		List<SubInfo> subInfoList = new ArrayList<SubInfo>();
		EntityInfo currEntityInfo = null;
		List<EntityInfo> entityInfos = viewElement.getEntityInfoList();
		for(EntityInfo ei:entityInfos){
			if(viewElement.getCurrentName().equals(ei.getClazz().getName())){
				currEntityInfo=ei;
			}
		}
		MainSubInfo mainSub = currEntityInfo.getMainSub();
		if(mainSub!=null){
			Collection<Class> subClassList = mainSub.getSubordinate();
			for(Class clazz:subClassList){
				String oneName = ClassUtils.getManyToOneName(mainSub.getPrincipal(), clazz);
				String name = clazz.getName();
				ModelService modelService=ContextHolder.getSpringBean("modelServiceBean");
				Models models = modelService.findOneByClazz(name);
				SubInfo subInfo = new SubInfo();
				subInfo.setAlias(models.getAlias());
				subInfo.setName(clazz.getSimpleName());
				subInfo.setOneName(oneName);
				subInfoList.add(subInfo);
			}
		}
		return subInfoList;
	}
}
