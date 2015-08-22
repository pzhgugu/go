package com.ansteel.common.tpl.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.TreeArray;
import com.ansteel.dhtmlx.widget.form.Block;
import com.ansteel.dhtmlx.widget.form.Button;
import com.ansteel.dhtmlx.widget.form.Calendar;
import com.ansteel.dhtmlx.widget.form.Combo;
import com.ansteel.dhtmlx.widget.form.Form;
import com.ansteel.dhtmlx.widget.form.FormFactory;
import com.ansteel.dhtmlx.widget.form.Input;
import com.ansteel.dhtmlx.widget.form.Newcolumn;
import com.ansteel.dhtmlx.widget.form.Options;
import com.ansteel.dhtmlx.widget.form.Settings;
import com.ansteel.dhtmlx.widget.form.Template;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：Dhtmlx详细查询。
 */
@Service(ServiceConstants.DTHMLX_DETAIL_QUERY)
public class DhtmlxDetailQuery extends DhtmlxQuery implements IDhtmlxWidget{
	
	private static final String LABEL_LEFT_DEFAUTL = "label-left";
	private static final Integer LABEL_WIDTH_DEFAUTL = 80;
	private static final Integer INPUT_WIDTH_DEFAUTL = 200;
	private static final String TYPE = "type";
	private static final String POSITION = "position";
	
	public class QueryType {
		/**
		 * 所有查询
		 */
		List all;
		/**
		 * 默认查询
		 */
		List often;
		public List getAll() {
			return all;
		}
		public void setAll(List all) {
			this.all = all;
		}
		public List getOften() {
			return often;
		}
		public void setOften(List often) {
			this.often = often;
		}		
	}

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

		QueryInfo queryInfo = this.getQueryInfo(fieldsQuery,fieldsForm, nameMap, typeMap,viewElement.getRequest());
		
		
		TreeArray tree=this.getTree(queryInfo.getNameList());
		ObjectMapper objectMapper = new ObjectMapper();  
		String sTree = "";
	    try {
	    	sTree = objectMapper.writeValueAsString(tree);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    
	    QueryType queryType  = this.getForm(viewElement,queryInfo);
	    
	    String sOften = "";
	    try {
	    	sOften = objectMapper.writeValueAsString(queryType.getOften());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	    
	    String sAll = "";
	    try {
	    	sAll = objectMapper.writeValueAsString(queryType.getAll());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
	    Map<String, Object> viewModel=new HashMap<String, Object>();
	    String prefix=viewElement.getViewPrefix();
	    viewModel.put(TplUtils.getName(ViewModelConstant.P_DETAIL_QUERY_TREE, prefix),sTree);	
	    viewModel.put(TplUtils.getName(ViewModelConstant.P_DETAIL_QUERY_OFTEN, prefix),sOften);	
	    viewModel.put(TplUtils.getName(ViewModelConstant.P_DETAIL_QUERY_ALL, prefix),sAll);
		return viewModel;
	}

	private QueryType getForm(ViewElement viewElement, QueryInfo queryInfo) {
		List allList = new LinkedList();
		List oftenList = new LinkedList();		
		oftenList.add(this.getSettions(viewElement.getTpl()));
		
		Map<String, List<Options>> operatorMap = queryInfo.getOperatorMap();
		Map<String, List<Options>> valueMap = queryInfo.getValueMap();
		Map<String, Boolean> oftenMap = queryInfo.getOftenMap();
			
		List<Options> options = queryInfo.getNameList();
		for(Options opt:options){
			String value = opt.getValue();
			List<Form> blockList = new LinkedList<Form>();	
			Block block = new Block();
			block = new Block();
			block.setWidth(490);
			block.setName(value+"Block");
			
			//增加操作符，标签及为名称
			blockList.add(this.getOperator(opt.getText(),value,operatorMap));
			//增加newcolumn
			blockList.add(this.getNewColumn());
			//增加查询值
			List<Options> operator = operatorMap.get(value);
			if(operator.size()>0){
				Options o = operator.get(0);
				if(o.getValue().equals(Public.QUERY_BETWEEN)){
					this.getBetweenValue(blockList,value,queryInfo.getFormTypeMap());
				}else{
					blockList.add(this.getValue(value,valueMap));
				}
			}
			//增加newcolumn
			blockList.add(this.getNewColumn());
			//增加删除
			blockList.add(this.getDelect(value,oftenMap));
			
			block.setList(blockList);
			

			if(oftenMap.get(value)){
				oftenList.add(block);
			}
			allList.add(block);
		}
		
		
		Button button = new Button();
		button.setOffsetLeft(20);
		button.setValue("查询");
		button.setName("query");
		oftenList.add(button);
		
		QueryType queryType = new QueryType();
		queryType.setAll(allList);
		queryType.setOften(oftenList);
		return queryType;
	}

	private void getBetweenValue(List<Form> blockList, String value,
			Map<String, String> formTypeMap) {	
		if(!formTypeMap.containsKey(value)){
			throw new PageException(value+",表单类型不存在！");
		}
		
		if(formTypeMap.get(value).equals(DHtmlxConstants.CALENDAR)){
			Calendar inputBegin = new Calendar();
			inputBegin.setName(value+"_Begin");
			inputBegin.setInputWidth(98);
			blockList.add(inputBegin);
			blockList.add(this.getNewColumn());
			Calendar inputEnd = new Calendar();
			inputEnd.setName(value+"_End");
			inputEnd.setInputWidth(98);
			blockList.add(inputEnd);

			Calendar inputValue = new Calendar();
			inputValue.setName(value);
			inputValue.setHidden(true);
			blockList.add(inputValue);			
		}else{
			Input inputBegin = new Input();
			inputBegin.setName(value+"_Begin");
			inputBegin.setInputWidth(98);
			blockList.add(inputBegin);
			blockList.add(this.getNewColumn());
			Input inputEnd = new Input();
			inputEnd.setName(value+"_End");
			inputEnd.setInputWidth(98);
			blockList.add(inputEnd);
			
			Input inputValue = new Input();
			inputValue.setName(value);
			inputValue.setHidden(true);
			blockList.add(inputValue);		
		}
	}

	private Form getNewColumn() {
		Newcolumn newcolumn = new Newcolumn();
		newcolumn.setOffset(1);
		return newcolumn;
	}

	private Form getDelect(String value, Map<String, Boolean> oftenMap) {
		Template template = new Template();
		template.setName(value+"Delect");
		template.setValue(value);
		template.setFormat("delectTpl");
		template.setInputWidth(50);
		return template;
	}

	private Form getValue(String value, Map<String, List<Options>> valueMap) {
		Form form = null;
		if(valueMap.containsKey(value)){
			Combo combo  = new Combo();
			combo.setName(value);
			combo.setOptions(valueMap.get(value));
			form = combo;
		}else{
			Input input = new Input();
			input.setName(value);
			form = input;
		}
		return form;
	}

	private Form getOperator(String text,String value, Map<String, List<Options>> operatorMap) {
		Combo combo = new Combo();
		combo.setLabel(text);
		combo.setName(value+"_Operator");
		combo.setInputWidth(80);
		combo.setOptions(operatorMap.get(value));
		return combo;
	}

	/**
	 * 建筑树
	 * @param nameList
	 * @return
	 */
	private TreeArray getTree(List<Options> nameList) {
		TreeArray treeRoot=new TreeArray();
		treeRoot.setId("0");		
		List<TreeArray> items = new ArrayList<TreeArray>();
		TreeArray treeAll=new TreeArray();
		treeAll.setId("1");
		treeAll.setText("所有条件");
		treeAll.setItem(this.getItem(nameList));
		items.add(treeAll);		
		treeRoot.setItem(items);		
		return treeRoot;
	}

	/**
	 * 构造树内容
	 * @param nameList
	 * @return
	 */
	private List<TreeArray> getItem(List<Options> nameList) {
		List<TreeArray> items = new ArrayList<TreeArray>();
		for(Options option:nameList){
			TreeArray tree=new TreeArray();
			tree.setId(option.getValue());
			tree.setText(option.getText());
			items.add(tree);
		}
		return items;
	}
	
	private Settings getSettions(Tpl tpl) {
		Settings settings = new Settings();
		
		Field[] fields = settings.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(TYPE)){
				continue;
			}
			if(field.getName().equals(POSITION)){
				String position = tpl.getPosition();
				if(StringUtils.hasText(position)){
					settings.setPosition(position);
				}else{
					settings.setPosition(LABEL_LEFT_DEFAUTL);
				}
			}else if(field.getName().equals("labelWidth")){
				Integer labelWidth = tpl.getLabelWidth();
				if(labelWidth==null||labelWidth==0){
					settings.setLabelWidth(LABEL_WIDTH_DEFAUTL);
				}else{
					settings.setLabelWidth(labelWidth);
				}
			}else if(field.getName().equals("inputWidth")){
				Integer inputWidth = tpl.getInputWidth();
				if(inputWidth==null||inputWidth==0){
					settings.setInputWidth(INPUT_WIDTH_DEFAUTL);
				}else{
					settings.setInputWidth(inputWidth);
				}
			}else{
				Field  fieldsFormField = ReflectionUtils.findField(Tpl.class, field.getName());
				if(fieldsFormField!=null){
					fieldsFormField.setAccessible(true);
					Object value = ReflectionUtils.getField(fieldsFormField, tpl);
					field.setAccessible(true);
					ReflectionUtils.setField(field,settings,value);
				}
			}
		}	
		return settings;
	}


}
