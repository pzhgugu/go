package com.ansteel.common.tpl.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.Operator;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.form.FormFactory;
import com.ansteel.dhtmlx.widget.form.Options;
import com.ansteel.dhtmlx.widget.formoption.IFormOption;
import com.ansteel.dhtmlx.widget.formoption.OperatorOption;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsQuery;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：Dhtmlx查询。
 */
public class DhtmlxQuery {
	/**
	 * 得到查询信息
	 * @param cuntentFieldsCategory 当前模型分类
	 * @param nameMap 模型名称映射
	 * @param typeMap 字段类型，用于判断默认操作符
	 * @return
	 */
	public QueryInfo getQueryInfo(List<FieldsQuery> fieldsQuery,List<FieldsForm> fieldsForm,
			Map<String, String> nameMap,Map<String, String> typeMap,HttpServletRequest request) {
		QueryInfo queryInfo = this.getQueryInfo(fieldsQuery,nameMap,typeMap,request);		
		this.setValueMapAndFormType(queryInfo,fieldsForm,request);
		return queryInfo;
	}
	
	private QueryInfo getQueryInfo(List<FieldsQuery> fieldsQuery,
			Map<String, String> nameMap,Map<String, String> typeMap,HttpServletRequest request) {
		QueryInfo queryInfo = new QueryInfo();
		List<Options>  options = new ArrayList<Options>();
		Map<String, List<Options>> operatorMap=new LinkedHashMap<String, List<Options>>();
		Map<String, Boolean> oftenMap=new LinkedHashMap<String, Boolean>();
		for(FieldsQuery f:fieldsQuery){
			if(1==f.getIsShow()){
				options.add(this.getNameOption(f,nameMap));
				String name = f.getName();
				operatorMap.put(name, this.getOperatorMap(f,typeMap));
				if(f.getIsOften()!=null&&1==f.getIsOften()){
					oftenMap.put(name, true);
				}else{
					oftenMap.put(name, false);
				}
			}
		}
		queryInfo.setNameList(options);
		queryInfo.setOperatorMap(operatorMap);
		queryInfo.setOftenMap(oftenMap);
		return queryInfo;
	}
	
	private void setValueMapAndFormType(QueryInfo queryInfo,List<FieldsForm> fieldsForm,HttpServletRequest request) {
		Map<String, List<Options>> optionMap=new LinkedHashMap<String, List<Options>>();
		Map<String, String> formTypeMap=new LinkedHashMap<String, String>();
		FormFactory formFactory = new FormFactory();
		for(FieldsForm f:fieldsForm){
			List<Options>  options = formFactory.getOptions(f,request);
			if(options!=null&&options.size()>0){
				//加入空选项
				List<Options>  optionEmpty = new ArrayList<Options>();
				Options option = new Options();
				option.setText("");
				option.setValue("");
				optionEmpty.add(option);
				optionEmpty.addAll(options);
				optionMap.put(f.getName(), optionEmpty);
			}
			formTypeMap.put(f.getName(), f.getType());
		}
		queryInfo.setValueMap(optionMap);
		queryInfo.setFormTypeMap(formTypeMap);
	}

	private Map<String, List<Options>> getValueMap(List<FieldsForm> fieldsForm,HttpServletRequest request) {
		Map<String, List<Options>> optionMap=new LinkedHashMap<String, List<Options>>();
		FormFactory formFactory = new FormFactory();
		for(FieldsForm f:fieldsForm){
			List<Options>  options = formFactory.getOptions(f,request);
			if(options!=null&&options.size()>0){
				//加入空选项
				List<Options>  optionEmpty = new ArrayList<Options>();
				Options option = new Options();
				option.setText("");
				option.setValue("");
				optionEmpty.add(option);
				optionEmpty.addAll(options);
				optionMap.put(f.getName(), optionEmpty);
			}
		}
		return optionMap;
	}

	

	private List<Options> getOperatorMap(FieldsQuery f,Map<String, String> typeMap) {
		IFormOption formOption = new OperatorOption();
		List<Options> options = formOption.get(f.getOperatorCode());
		if(options.size()>0){
			return options;
		}else{
			String name = f.getName();
			if(typeMap.containsKey(name)){
				if(!StringUtils.hasText(typeMap.get(name))){
					throw new PageException("请设置【"+name+"】字段数据类型！");
				}
				if(typeMap.get(name).equalsIgnoreCase(Public.STRING)){
					Options likeOption = new Options();
					likeOption.setText(Operator.LIKE.getAlias());
					likeOption.setValue(Operator.LIKE.getCode());
					options.add(likeOption);
				}
			}
			Options option = new Options();
			option.setText(Operator.EQ.getAlias());
			option.setValue(Operator.EQ.getCode());
			options.add(option);
			return options;
		}
	}

	private Options getNameOption(FieldsQuery f,Map<String, String> nameMap) {
		String name = f.getName();
		Options option = new Options();
		option.setText(nameMap.get(name));
		option.setValue(f.getName());
		return option;
	}
	

}
