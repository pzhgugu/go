package com.ansteel.common.viewelement.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsCategory;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsGrid;
import com.ansteel.common.prentmodel.domain.FieldsQuery;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-14
 * 修 改 人：
 * 修改日 期：
 * 描   述：视图元素信息类。
 */
public class ViewElement {
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	/**
	 * 模型字段分类
	 */
	private FieldsCategory fieldsCategory;
	/**
	 * 模型
	 */
	private TreeEntity model;
	
	/**
	 * 请求控制器名称
	 */
	private String requestMappingName;
	
	private String currentName;
	
	private String currentSimpleName;
	
	Collection<FieldsForm> formList = new ArrayList<FieldsForm>();
	
	Collection<FieldsGrid> gridList =  new ArrayList<FieldsGrid>();
	
	Collection<FieldsQuery> queryList =  new ArrayList<FieldsQuery>();
	
	Collection<Fields> fieldsList =  new ArrayList<Fields>();	
	
	public Collection<Fields> getFieldsList() {
		return fieldsList;
	}

	public void setFieldsList(Collection<Fields> fieldsList) {
		this.fieldsList = fieldsList;
	}

	public Collection<FieldsForm> getFormList() {
		return formList;
	}

	public void setFormList(Collection<FieldsForm> formList) {
		this.formList = formList;
	}

	public Collection<FieldsGrid> getGridList() {
		return gridList;
	}

	public void setGridList(Collection<FieldsGrid> gridList) {
		this.gridList = gridList;
	}

	public Collection<FieldsQuery> getQueryList() {
		return queryList;
	}

	public void setQueryList(Collection<FieldsQuery> queryList) {
		this.queryList = queryList;
	}

	/**
	 * 当前class
	 */
	//private Class<?> presentClazz;
	
	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public String getCurrentSimpleName() {
		return currentSimpleName;
	}

	public void setCurrentSimpleName(String currentSimpleName) {
		this.currentSimpleName = currentSimpleName;
	}

	/**
	 * 实体信息集合
	 */
	private List<EntityInfo> entityInfoList=new ArrayList<EntityInfo>();
	
	/**
	 * 模板
	 */
	private Tpl tpl;
	/**
	 * 视图
	 */
	private View view;	
	
	/**
	 * 视图名称前缀
	 */
	private String viewPrefix;
	
	public String getViewPrefix() {
		return viewPrefix;
	}

	public void setViewPrefix(String viewPrefix) {
		this.viewPrefix = viewPrefix;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Tpl getTpl() {
		return tpl;
	}

	public void setTpl(Tpl tpl) {
		this.tpl = tpl;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getRequestMappingName() {
		return requestMappingName;
	}

	public void setRequestMappingName(String requestMappingName) {
		this.requestMappingName = requestMappingName;
	}

	public List<EntityInfo> getEntityInfoList() {
		return entityInfoList;
	}

	public void setEntityInfoList(List<EntityInfo> entityInfoList) {
		this.entityInfoList = entityInfoList;
	}

	/*public Class<?> getPresentClazz() {
		return presentClazz;
	}

	public void setPresentClazz(Class<?> presentClazz) {
		this.presentClazz = presentClazz;
	}*/

	public FieldsCategory getFieldsCategory() {
		return fieldsCategory;
	}

	public void setFieldsCategory(FieldsCategory fieldsCategory) {
		this.fieldsCategory = fieldsCategory;
	}

	public TreeEntity getModel() {
		return model;
	}

	public void setModel(TreeEntity model) {
		this.model = model;
	}
	
}
