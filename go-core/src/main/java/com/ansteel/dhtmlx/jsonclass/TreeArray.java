package com.ansteel.dhtmlx.jsonclass;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射到树数组。  
 */
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TreeArray {
	
	private String id;
	
	private String text;
	
	private List<TreeArray> item;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeArray> getItem() {
		return item;
	}

	public void setItem(List<TreeArray> item) {
		this.item = item;
	}

	

}
