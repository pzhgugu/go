package com.ansteel.dhtmlx.widget.form;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Options {
	private List<Form> list	;
	private Boolean selected ;
	private String text ;
	private String value ;
	public Boolean getSelected() {
		return selected;
	}
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<Form> getList() {
		return list;
	}
	public void setList(List<Form> list) {
		this.list = list;
	}
	
}
