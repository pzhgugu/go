package com.ansteel.dhtmlx.widget.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Note {
	
	private String text ;
	
	private Integer width ;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	
}
