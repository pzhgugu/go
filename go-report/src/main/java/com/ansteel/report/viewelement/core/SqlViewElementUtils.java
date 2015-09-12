package com.ansteel.report.viewelement.core;

import com.ansteel.common.dynamicmodel.domain.*;
import com.ansteel.common.model.domain.*;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsGrid;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.TplViewConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ClassUtils;
import com.ansteel.report.sqlmodel.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图元素工具类。
 */
public class SqlViewElementUtils {

	public static void setViewElement(ViewElement viewElement, SqlModels model,
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
	}
}
