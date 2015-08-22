package com.ansteel.common.tpl.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.form.Block;
import com.ansteel.dhtmlx.widget.form.Button;
import com.ansteel.dhtmlx.widget.form.Calendar;
import com.ansteel.dhtmlx.widget.form.Combo;
import com.ansteel.dhtmlx.widget.form.Form;
import com.ansteel.dhtmlx.widget.form.Hidden;
import com.ansteel.dhtmlx.widget.form.Input;
import com.ansteel.dhtmlx.widget.form.Label;
import com.ansteel.dhtmlx.widget.form.Newcolumn;
import com.ansteel.dhtmlx.widget.form.Options;
import com.ansteel.dhtmlx.widget.form.Settings;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsQuery;
import com.ansteel.common.viewelement.service.ViewElement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：Dhtmlx顶部查询。
 */
@Service(ServiceConstants.DTHMLX_TOP_QUERY)
public class DhtmlxTopQuery extends DhtmlxQuery implements IDhtmlxWidget{

	private static final Integer BLOCK_WIDTH_DEFAULT = 1300;

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
		
		Map<String, List<Options>> operatorMap = queryInfo.getOperatorMap();
		
		Tpl tpl = viewElement.getTpl();
		if(tpl==null){
			tpl=this.getDefaultTpl();
		}
		//每个表单占用几行
		Integer columnNumber = tpl.getColumnNumber();
		if(columnNumber==null||columnNumber==0){
			columnNumber = 4;
		}

		
		List list = new LinkedList();
		
		
		list.add(this.getSettions(tpl));
		
		List<Form> blockList = null;
		Block block = null;
		//当前行
		int number =0;
		
		List<Options> options = queryInfo.getNameList();
		Map<String, List<Options>> valueMap = queryInfo.getValueMap();
		for(Options opt:options){
			String value = opt.getValue();
			if(number%columnNumber==0){
				block = new Block();
				block.setWidth(tpl.getBlockWidth()==null?BLOCK_WIDTH_DEFAULT:tpl.getBlockWidth());
				blockList	= new LinkedList<Form>();
			}

			//增加查询值
			List<Options> operator = operatorMap.get(value);
			if(operator.size()>0){
				Options o = operator.get(0);
				if(o.getValue().equals(Public.QUERY_BETWEEN)){
					this.getBetweenValue(blockList,opt.getText(),value,queryInfo.getFormTypeMap());					
				}else{
					blockList.add(this.getValue(opt.getText(),value,valueMap,queryInfo.getFormTypeMap()));
				}
				Hidden input = new Hidden();
				input.setName(value+"_options");
				input.setValue(o.getValue());
				blockList.add(input);
			}
			if((number+1)%columnNumber!=0){
				Newcolumn newcolumn = new Newcolumn();
				newcolumn.setOffset(10);
				blockList.add(newcolumn);
			}
			if(number%columnNumber==0){
				block.setList(blockList);
				list.add(block);
			}
			number++;
		}
		
		
		/*Block blockButton = new Block();
		blockButton.setName("blockButton");
		blockButton.setWidth(800);
		LinkedList<Form> blockButtonList = new LinkedList<Form>();
		Button button = new Button();
		button.setValue("查询");
		button.setName("query");
		blockButtonList.add(button);
		blockButtonList.add(new Newcolumn());
		Button buttonClean  = new Button();
		buttonClean.setValue("重置");
		buttonClean.setName("reset");
		blockButtonList.add(buttonClean);
		blockButton.setList(blockButtonList);
		
		list.add(blockButton);*/
		
		ObjectMapper objectMapper = new ObjectMapper();  
		String pForm = "";
	    try {
	    	pForm = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> viewModel=new HashMap<String, Object>();
	    String prefix=viewElement.getViewPrefix();
		viewModel.put(TplUtils.getName(ViewModelConstant.P_TOP_FORM, prefix),pForm);	
		return viewModel;
	}
	
	private Form getValue(String text,String value, Map<String, List<Options>> valueMap,Map<String, String> formTypeMap) {
		Form form = null;
		if(valueMap.containsKey(value)){
			Combo combo  = new Combo();
			combo.setName(value);
			combo.setLabel(text);
			combo.setOptions(valueMap.get(value));
			form = combo;
		}else if(formTypeMap.get(value).equals(DHtmlxConstants.CALENDAR)){
			Calendar input = new Calendar();
			input.setName(value+"_Begin");
			input.setLabel(text);
			form = input;
		}else{
			Input input = new Input();
			input.setLabel(text);
			input.setName(value);
			form = input;
		}

		return form;
	}
	
	private void getBetweenValue(List<Form> blockList, String text,String value,
			Map<String, String> formTypeMap) {	
		if(!formTypeMap.containsKey(value)){
			throw new PageException(value+",表单类型不存在！");
		}
		
		if(formTypeMap.get(value).equals(DHtmlxConstants.CALENDAR)){
			Calendar inputBegin = new Calendar();
			inputBegin.setName(value+"_Begin");
			inputBegin.setLabel(text);
			inputBegin.setInputWidth(98);
			blockList.add(inputBegin);
			blockList.add(this.getNewColumn());
			Calendar inputEnd = new Calendar();
			inputEnd.setName(value+"_End");
			inputEnd.setInputWidth(98);
			blockList.add(inputEnd);		
		}else{
			Input inputBegin = new Input();
			inputBegin.setName(value+"_Begin");
			inputBegin.setLabel(text);
			inputBegin.setInputWidth(98);
			blockList.add(inputBegin);
			blockList.add(this.getNewColumn());
			Input inputEnd = new Input();
			inputEnd.setName(value+"_End");
			inputEnd.setInputWidth(98);
			blockList.add(inputEnd);
		}
	}
	
	private Form getNewColumn() {
		Newcolumn newcolumn = new Newcolumn();
		newcolumn.setOffset(1);
		return newcolumn;
	}
	
	
	private Tpl getDefaultTpl() {
		Tpl tpl =new Tpl();
		tpl.setColumnNumber(DHtmlxConstants.COLUMN_NUMBER_DEFAULT);
		tpl.setPosition(DHtmlxConstants.LABEL_LEFT_DEFAUTL);
		tpl.setLabelWidth(DHtmlxConstants.LABEL_WIDTH_DEFAUTL);
		tpl.setInputWidth(DHtmlxConstants.INPUT_WIDTH_DEFAUTL);
		tpl.setBlockWidth(DHtmlxConstants.BLOCK_WIDTH_DEFAULT);
		return tpl;
	}

	private Settings getSettions(Tpl tpl) {
		Settings settings = new Settings();
		
		Field[] fields = settings.getClass().getDeclaredFields();
		for(Field field:fields){
			if(field.getName().equals(DHtmlxConstants.TYPE)){
				continue;
			}
			if(field.getName().equals(DHtmlxConstants.POSITION)){
				String position = tpl.getPosition();
				if(StringUtils.hasText(position)){
					settings.setPosition(position);
				}else{
					settings.setPosition(DHtmlxConstants.LABEL_LEFT_DEFAUTL);
				}
			}else if(field.getName().equals("labelWidth")){
				Integer labelWidth = tpl.getLabelWidth();
				if(labelWidth==null||labelWidth==0){
					settings.setLabelWidth(DHtmlxConstants.LABEL_WIDTH_DEFAUTL);
				}else{
					settings.setLabelWidth(labelWidth);
				}
			}else if(field.getName().equals("inputWidth")){
				Integer inputWidth = tpl.getInputWidth();
				if(inputWidth==null||inputWidth==0){
					settings.setInputWidth(DHtmlxConstants.INPUT_WIDTH_DEFAUTL);
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
		settings.setLabelAlign("right");
		
		return settings;
	}
}
