package com.ansteel.common.tpl.core;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.viewelement.core.ViewElementUtils;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：左树右单表模板附从表（如Excel报表管理）。
 */
@Service
public class TreeOneToManyTpl  extends AbstractTpl implements ITpl{
	@Override
	protected Map<String, Object> loadWidget(ViewElement viewElement) {
		//得到主从模板的数据
		Map<String, Object> widgetModel=new OneToManyTpl().loadWidget(viewElement);
		
		//获取主表关联映射名称
		Class treeClass=this.getTreeCLass(viewElement);
		Class curClass=null;
		try {
			curClass = Class.forName(viewElement.getCurrentName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String oneName = ClassUtils.getManyToOneName(treeClass, curClass);
		
		//改变视图元素为树的属性
		viewElement.setViewPrefix("TREE");

		ModelService modelService=ContextHolder.getSpringBean("modelServiceBean");
		Models model = modelService.findOneByName(treeClass.getName());

		ViewElementUtils.setViewElement(viewElement, model, Public.DEFAULE_CATEGORY_NAME,treeClass);
		

		IDhtmlxWidget form=ContextHolder.getSpringBean(ServiceConstants.DTHMLX_FORM);
		widgetModel.putAll(form.structure(viewElement));
		
		widgetModel.put(ViewModelConstant.P_TREE_ONENAME,oneName);
		widgetModel.put(ViewModelConstant.P_TREE_CLASS,treeClass.getSimpleName());
		//获取树的表单编辑
		return widgetModel;
	}

	private Class getTreeCLass(ViewElement viewElement) {
		EntityInfo currEntityInfo = null;
		List<EntityInfo> entityInfos = viewElement.getEntityInfoList();
		for(EntityInfo ei:entityInfos){
			if(viewElement.getCurrentName().equals(ei.getClazz().getName())){
				currEntityInfo=ei;
			}
		}		
		return currEntityInfo.getTree().getTree();
	}

}
