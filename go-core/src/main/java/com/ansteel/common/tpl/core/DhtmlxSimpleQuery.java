package com.ansteel.common.tpl.core;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：Dhtmlx简单查询。
 */
@Service(ServiceConstants.DTHMLX_SIMPLE_QUERY)
public class DhtmlxSimpleQuery extends DhtmlxQuery implements IDhtmlxWidget{

	@Override
	public Map<String, Object> structure(ViewElement viewElement) {
		List<Fields> fields = (List<Fields>) viewElement.getFieldsList();
		Map<String,String> nameMap = new LinkedHashMap<String, String>();
		Map<String,String> typeMap = new LinkedHashMap<String, String>();
		for(Fields f:fields){
			nameMap.put(f.getName(), f.getAlias());
			typeMap.put(f.getName(),f.getFieldType());
		}
		
		List<FieldsQuery> fieldsQuery =(List<FieldsQuery>) viewElement.getQueryList();
		List<FieldsForm> fieldsForm = (List<FieldsForm>) viewElement.getFormList();		
		
		QueryInfo simpleQuery = this.getQueryInfo(fieldsQuery,fieldsForm,nameMap,typeMap,viewElement.getRequest());
		
		ObjectMapper objectMapper = new ObjectMapper();  
		String sNames = "";
	    try {
	    	sNames = objectMapper.writeValueAsString(simpleQuery.getNameList());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
	    String sOperator = "";
	    try {
	    	sOperator = objectMapper.writeValueAsString(simpleQuery.getOperatorMap());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		String sValue = "";
	    try {
	    	sValue = objectMapper.writeValueAsString(simpleQuery.getValueMap());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    
		String sFormType = "";
	    try {
	    	sFormType = objectMapper.writeValueAsString(simpleQuery.getFormTypeMap());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> viewModel=new HashMap<String, Object>();
		String prefix=viewElement.getViewPrefix();
		viewModel.put(TplUtils.getName(ViewModelConstant.P_SIMPLE_QUERY_NAMES, prefix),sNames);	
		viewModel.put(TplUtils.getName(ViewModelConstant.P_SIMPLE_QUERY_OPERATOR, prefix),sOperator);
		viewModel.put(TplUtils.getName(ViewModelConstant.P_SIMPLE_QUERY_VALUES, prefix),sValue);
		viewModel.put(TplUtils.getName(ViewModelConstant.P_SIMPLE_QUERY_FORM_TYPE, prefix),sFormType);
		return viewModel;
	}

}
