package com.ansteel.dhtmlx.widget.form;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.utils.RequestUtils;
import org.springframework.util.ReflectionUtils;

import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.formoption.IFormOption;
import com.ansteel.dhtmlx.widget.formoption.SqlOption;
import com.ansteel.dhtmlx.widget.formoption.StringOption;
import com.ansteel.dhtmlx.widget.formoption.ValueListOption;
import com.ansteel.common.prentmodel.domain.FieldsForm;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单工厂类。  
 */
public class FormFactory {
	
	private static final String TYPE = "type";

	private static final String LABEL = "label";

	private static final String OPTIONS = "options";
	
	private static Map<String,Class> formMap = null;

	private static Map<String, Object> optionTypeMap = null;

	
	public Form fieldsFormToForm(FieldsForm fieldsForm, Map<String, String> nameMap,HttpServletRequest request) {
		String type = fieldsForm.getType();
		if(StringUtils.hasText(type)){
			Form form = this.getForm(type);
			if(form==null){
				throw new PageException(type+",请在formMap中设置表单类型！");
			}
			this.setFormValue(form,fieldsForm,nameMap,request);
			return form;
		}
		return null;
	}


	private void setFormValue(Form form, FieldsForm fieldsForm, Map<String, String> nameMap,HttpServletRequest request) {
		for (Class<?> clazz = form.getClass(); clazz != Form.class; clazz = clazz
				.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields){
				if(field.getName().equals(TYPE)){
					continue;
				}
				Object value = null;
				if(field.getName().equals(LABEL)){
					if(nameMap.containsKey(fieldsForm.getName())){
						value = nameMap.get(fieldsForm.getName());
					}
				}else if(field.getName().equals(OPTIONS)){
					value = this.getOptions(fieldsForm,request);			
				}else{
					Field  fieldsFormField = ReflectionUtils.findField(EntityFieldsForm.class, field.getName());
					if(fieldsFormField!=null){
						fieldsFormField.setAccessible(true);
						value = ReflectionUtils.getField(fieldsFormField, fieldsForm);
					}
				}
				if(value!=null){
					field.setAccessible(true);
					if(field.getName().equals("note")){
						String json=(String) value;
						if(StringUtils.hasText(json)){
							value=JsonUtils.objectFromJson(json, Note.class);
						}else{
							continue;
						}
					}else if(field.getName().equals("userdata")){
						String json=(String) value;
						if(StringUtils.hasText(json)){
							String contextPath = request.getContextPath();
							json = json.replaceAll("\\$\\{S_URL\\}", contextPath);
							value=JsonUtils.objectFromJson(json, Map.class);
						}else{
							continue;
						}
					}
					ReflectionUtils.setField(field,form,value);
				}
			}
		}
	}


	public List<Options> getOptions(FieldsForm fieldsForm,HttpServletRequest request) {
		String type = fieldsForm.getType();
		if (optionTypeMap == null) {
			this.initOptionType();
		}
		//if(type.equals(new Combo().getType())||type.equals(new Multiselect().getType())||type.equals(new Select().getType())||type.equals(new Checkbox().getType())){
		if (optionTypeMap.containsKey(type)) {
			Integer optionType = fieldsForm.getOptionType();
			if(optionType==null){
				optionType=0;
			}
			IFormOption formOption;
			switch (optionType) {
			case 1:
				formOption = new ValueListOption();
				break;
			case 2:
				formOption = new SqlOption(request);
				break;
			default:
				formOption = new StringOption();
				break;
			}
			return formOption.get(fieldsForm.getOptionValue());
		}
		return null;
	}



	private Form getForm(String name){
		if(formMap==null){
			this.initForm();
		}
		if(formMap.containsKey(name)){
			Class clazz= formMap.get(name);
			Form form = null;
			try {
				form=(Form) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return form;
		}
		return null;
	}

	private void initOptionType() {
		optionTypeMap = new HashMap<>();
		optionTypeMap.put(new Combo().getType(), new Combo());
		optionTypeMap.put(new Multiselect().getType(), new Multiselect());
		optionTypeMap.put(new Select().getType(), new Select());
		optionTypeMap.put(new Checkbox().getType(), new Checkbox());
		optionTypeMap.put(new InputTree().getType(), new InputTree());
	}

	private void initForm() {
		formMap = new HashMap<>();
		formMap.put(new Block().getType(), Block.class);
		formMap.put(new Btn2state().getType(), Btn2state.class);
		formMap.put(new Button().getType(), Button.class);
		formMap.put(new Calendar().getType(), Calendar.class);
		formMap.put(new Checkbox().getType(), Checkbox.class);
		formMap.put(new Colorpicker().getType(),Colorpicker.class);
		formMap.put(new Combo().getType(), Combo.class);
		formMap.put(new Container().getType(), Container.class);
		formMap.put(new Editor().getType(), Editor.class);
		formMap.put(new Fieldset().getType(), Fieldset.class);
		formMap.put(new File().getType(),  File.class);
		formMap.put(new Hidden().getType(), Hidden.class);
		formMap.put(new Input().getType(), Input.class);
		formMap.put(new Label().getType(), Label.class);
		formMap.put(new Multiselect().getType(), Multiselect.class);
		formMap.put(new Newcolumn().getType(), Newcolumn.class);
		formMap.put(new Password().getType(), Password.class);
		formMap.put(new Radio().getType(), Radio.class);
		formMap.put(new Select().getType(), Select.class);
		formMap.put(new Settings().getType(), Settings.class);
		formMap.put(new Template().getType(), Template.class);
		formMap.put(new Textarea().getType(), Textarea.class);
		formMap.put(new Upload().getType(), Upload.class);
		formMap.put(new EditArea().getType(), EditArea.class);
		formMap.put(new KindEditor().getType(), KindEditor.class);
		formMap.put(new InputTree().getType(), InputTree.class);
	}


}
