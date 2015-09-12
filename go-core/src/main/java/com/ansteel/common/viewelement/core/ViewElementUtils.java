package com.ansteel.common.viewelement.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsForm;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsQuery;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsGrid;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图元素工具类。
 */
public class ViewElementUtils {

	/**
	 * 得到控制器Mapping名称
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestMappingName(HttpServletRequest request,
			Class thisClass) {
		String requestMappingName = "";
		String[] names = ClassUtils.getClassRequestMapping(thisClass);
		if (names != null) {
			if (names.length == 1) {
				requestMappingName = names[0];
			} else {
				for (String name : names) {
					String p = request.getContextPath();
					if (request.getRequestURL().indexOf(
							(request.getContextPath() + name)) > 0) {
						requestMappingName = name;
						break;
					}
				}
			}
		}
		return requestMappingName;
	}

	/**
	 * 得到模型分类对象
	 * 
	 * @param modelEntity
	 * @param fieldsCategory
	 * @return
	 */
	public static EntityFieldsCategory getFieldsCategory(Models modelEntity,
			String fieldsCategory) {
		// 实体分类类型
		if (fieldsCategory.equals(TplViewConstant.DEFAULT_SHORT)) {
			fieldsCategory = Public.DEFAULE_CATEGORY_NAME;
		}
		List<EntityFieldsCategory> fcs = (List<EntityFieldsCategory>) modelEntity
				.getFieldsCategory();
		for (EntityFieldsCategory f : fcs) {
			if (f.getName().equals(fieldsCategory)) {
				return f;
			}
		}
		throw new PageException(modelEntity.getAlias() + "-" + fieldsCategory
				+ ":模型字段分类不存在！");
	}

	public static Collection<Fields> getParentFieldsList(
			Collection<EntityFields> formList) {
		Collection<Fields> list = new ArrayList<Fields>();
		for (EntityFields entity : formList) {
			Fields o = entity;
			list.add(o);
		}
		return list;
	}

	public static Collection<FieldsForm> getParentFieldsFormList(
			Collection<EntityFieldsForm> formList) {
		Collection<FieldsForm> fList = new ArrayList<FieldsForm>();
		for (EntityFieldsForm entity : formList) {
			FieldsForm o = entity;
			fList.add(o);
		}
		return fList;
	}

	public static Collection<FieldsGrid> getParentFieldsGridList(
			Collection<EntityFieldsGrid> formList) {
		Collection<FieldsGrid> list = new ArrayList<FieldsGrid>();
		for (EntityFieldsGrid entity : formList) {
			FieldsGrid o = entity;
			list.add(o);
		}
		return list;
	}

	public static Collection<FieldsQuery> getParentFieldsQueryList(
			Collection<EntityFieldsQuery> formList) {
		Collection<FieldsQuery> list = new ArrayList<FieldsQuery>();
		for (EntityFieldsQuery entity : formList) {
			FieldsQuery o = entity;
			list.add(o);
		}
		return list;
	}

	public static void setViewElement(ViewElement viewElement, Models model,
			String type, Class clazz) {
		viewElement.setModel(model);
		viewElement.setCurrentName(clazz.getName());
		viewElement.setCurrentSimpleName(clazz.getSimpleName());
		EntityFieldsCategory fc = getFieldsCategory(model, type);
		viewElement.setFieldsCategory(fc);
		viewElement.setFieldsList(getParentFieldsList(model.getFields()));
		viewElement.setFormList(getParentFieldsFormList(fc.getFieldsForm()));
		viewElement.setGridList(getParentFieldsGridList(fc.getFieldsGrid()));
		viewElement.setQueryList(getParentFieldsQueryList(fc.getFieldsQuery()));
	}

	public static void setViewElement(ViewElement viewElement,
			DynamicModels model, String categoryName) {
		viewElement.setModel(model);
		DynamicFieldsCategory dfc = getFieldsCategory(model, categoryName);
		viewElement.setFieldsCategory(dfc);
		viewElement
				.setFieldsList(getDynamicParentFieldsList(model.getFields()));
		viewElement.setFormList(getDynamicParentFieldsFormList(dfc
				.getFieldsForm()));
		viewElement.setGridList(getDynamicParentFieldsGridList(dfc
				.getFieldsGrid()));
		viewElement.setQueryList(getDynamicParentFieldsQueryList(dfc
				.getFieldsQuery()));

	}

	private static Collection<FieldsQuery> getDynamicParentFieldsQueryList(
			Collection<DynamicFieldsQuery> fieldsQuery) {
		Collection<FieldsQuery> list = new ArrayList<FieldsQuery>();
		for (DynamicFieldsQuery entity : fieldsQuery) {
			FieldsQuery o = entity;
			list.add(o);
		}
		return list;
	}

	private static Collection<FieldsGrid> getDynamicParentFieldsGridList(
			Collection<DynamicFieldsGrid> fieldsGrid) {
		Collection<FieldsGrid> list = new ArrayList<FieldsGrid>();
		for (DynamicFieldsGrid entity : fieldsGrid) {
			FieldsGrid o = entity;
			list.add(o);
		}
		return list;
	}

	private static Collection<FieldsForm> getDynamicParentFieldsFormList(
			Collection<DynamicFieldsForm> fieldsForm) {
		Collection<FieldsForm> fList = new ArrayList<FieldsForm>();
		for (DynamicFieldsForm entity : fieldsForm) {
			FieldsForm o = entity;
			fList.add(o);
		}
		return fList;
	}

	private static Collection<Fields> getDynamicParentFieldsList(
			Collection<DynamicFields> fields) {
		Collection<Fields> list = new ArrayList<Fields>();
		for (DynamicFields entity : fields) {
			Fields o = entity;
			list.add(o);
		}
		return list;
	}

	private static DynamicFieldsCategory getFieldsCategory(
			DynamicModels dynamicModels, String categoryName) {
		// 实体分类类型
		if (categoryName.equals(TplViewConstant.DEFAULT_SHORT)) {
			categoryName = Public.DEFAULE_CATEGORY_NAME;
		}
		Collection<DynamicFieldsCategory> fcs = dynamicModels
				.getFieldsCategory();
		for (DynamicFieldsCategory f : fcs) {
			if (f.getName().equals(categoryName)) {
				return f;
			}
		}
		throw new PageException(dynamicModels.getAlias() + "-" + categoryName
				+ ":模型字段分类不存在！");
	}

	/*public static void setViewElement(ViewElement viewElement, SqlModels model,
			String categoryName) {
		viewElement.setModel(model);
		SqlFieldsCategory dfc = getFieldsCategory(model, categoryName);
		viewElement.setFieldsCategory(dfc);
		viewElement.setFieldsList(getSqlParentFieldsList(model.getFields()));
		viewElement
				.setFormList(getSqlParentFieldsFormList(dfc.getFieldsForm()));
		viewElement
				.setGridList(getSqlParentFieldsGridList(dfc.getFieldsGrid()));
		viewElement.setQueryList(getSqlParentFieldsQueryList(dfc
				.getFieldsQuery()));
	}

	private static Collection<FieldsQuery> getSqlParentFieldsQueryList(
			Collection<SqlFieldsQuery> fieldsQuery) {
		Collection<FieldsQuery> list = new ArrayList<FieldsQuery>();
		for (SqlFieldsQuery entity : fieldsQuery) {
			FieldsQuery o = entity;
			list.add(o);
		}
		return list;
	}

	private static Collection<FieldsGrid> getSqlParentFieldsGridList(
			Collection<SqlFieldsGrid> fieldsGrid) {
		Collection<FieldsGrid> list = new ArrayList<FieldsGrid>();
		for (SqlFieldsGrid entity : fieldsGrid) {
			FieldsGrid o = entity;
			list.add(o);
		}
		return list;
	}

	private static Collection<FieldsForm> getSqlParentFieldsFormList(
			Collection<SqlFieldsForm> fieldsForm) {
		Collection<FieldsForm> fList = new ArrayList<FieldsForm>();
		for (SqlFieldsForm entity : fieldsForm) {
			FieldsForm o = entity;
			fList.add(o);
		}
		return fList;
	}

	private static Collection<Fields> getSqlParentFieldsList(
			Collection<SqlFields> fields) {
		Collection<Fields> list = new ArrayList<Fields>();
		for (SqlFields entity : fields) {
			Fields o = entity;
			list.add(o);
		}
		return list;
	}

	private static SqlFieldsCategory getFieldsCategory(SqlModels model,
			String categoryName) {
		// 实体分类类型
		if (categoryName.equals(TplViewConstant.DEFAULT_SHORT)) {
			categoryName = Public.DEFAULE_CATEGORY_NAME;
		}
		Collection<SqlFieldsCategory> fcs = model
				.getFieldsCategory();
		for (SqlFieldsCategory f : fcs) {
			if (f.getName().equals(categoryName)) {
				return f;
			}
		}
		throw new PageException(model.getAlias() + "-" + categoryName
				+ ":模型字段分类不存在！");
	}*/
}
