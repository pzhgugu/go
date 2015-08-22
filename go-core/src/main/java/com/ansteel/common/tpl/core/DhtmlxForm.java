package com.ansteel.common.tpl.core;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.form.Block;
import com.ansteel.dhtmlx.widget.form.Button;
import com.ansteel.dhtmlx.widget.form.Form;
import com.ansteel.dhtmlx.widget.form.FormFactory;
import com.ansteel.dhtmlx.widget.form.Newcolumn;
import com.ansteel.dhtmlx.widget.form.Settings;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：表单模板模块。
 */
@Service(ServiceConstants.DTHMLX_FORM)
public class DhtmlxForm implements IDhtmlxWidget{


	@Override
	public Map<String, Object> structure(ViewElement viewElement) {
		Map<String, Object> viewModel=new HashMap<String, Object>();
		viewModel.putAll(this.initForm(viewElement));		
		return viewModel;
	}

	private Map<String, Object> initForm(
			ViewElement viewElement) {
		HttpServletRequest request = viewElement.getRequest();
		List<Fields> fields = (List<Fields>) viewElement.getFieldsList();
		Map<String,String> nameMap = new LinkedHashMap<String, String>();
		for(Fields f:fields){
			nameMap.put(f.getName(), f.getAlias());
		}

	
		List<FieldsForm> fieldsForm = (List<FieldsForm>) viewElement.getFormList();
		Tpl tpl = viewElement.getTpl();
		if(tpl==null){
			tpl=this.getDefaultTpl();
		}
		//每个表单占用几行
		Integer columnNumber = tpl.getColumnNumber();
		if(columnNumber==null||columnNumber==0){
			columnNumber = DHtmlxConstants.COLUMN_NUMBER_DEFAULT;
		}

		
		List list = new LinkedList();
		
		
		list.add(this.getSettions(tpl));
		
		
		
		FormFactory formFactory = new FormFactory();

		List<Form> blockList = null;
		Block block = null;
		//当前行
		int number =0;
		for(int i=0;i<fieldsForm.size();i++){
			FieldsForm ffEntity = fieldsForm.get(i);
			Integer isShow = ffEntity.getIsShow();
			//表单所占行数
			int column = ffEntity.getColumnForm()==null?1:ffEntity.getColumnForm();
			if(isShow!=null&&isShow==1){
				Form f = formFactory.fieldsFormToForm(ffEntity,nameMap,request);
				
				if(f!=null){
					if(f.getType().equals(DHtmlxConstants.HIDDEN)){
						list.add(f);
						continue;
					}
					if(number%columnNumber==0){
						block = new Block();
						block.setWidth(tpl.getBlockWidth()==null?DHtmlxConstants.BLOCK_WIDTH_DEFAULT:tpl.getBlockWidth());
						blockList	= new LinkedList<Form>();
					}
					blockList.add(f);
					if((number+1)%columnNumber!=0){
						Newcolumn newcolumn = new Newcolumn();
						newcolumn.setOffset(10);
						blockList.add(newcolumn);
					}
					if(number%columnNumber==0){
						block.setList(blockList);
						list.add(block);
						


						Block blockInster = new Block();
						blockInster.setName("block_"+i);
						blockInster.setWidth(tpl.getBlockWidth()==null?DHtmlxConstants.BLOCK_WIDTH_DEFAULT:tpl.getBlockWidth());						
						list.add(blockInster);
					}

					if(column>1){
						number+=column-1;
					}
					number++;
				}
				
			}
		}
		
		Button button = new Button();
		Integer offsetLeft = tpl.getOffsetLeft();
		if(offsetLeft!=null){
			button.setOffsetLeft(offsetLeft+20);
		}else{
			button.setOffsetLeft(20);
		}
		button.setValue("保存");
		button.setName("save");
		button.setClassName("button_save");
		list.add(button);
		
		ObjectMapper objectMapper = new ObjectMapper();  
		String pForm = "";
	    try {
	    	pForm = objectMapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> viewModel=new HashMap<String, Object>();
	    String prefix=viewElement.getViewPrefix();
		viewModel.put(TplUtils.getName(ViewModelConstant.P_FORM, prefix),pForm);	
		return viewModel;
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
		return settings;
	}

}
