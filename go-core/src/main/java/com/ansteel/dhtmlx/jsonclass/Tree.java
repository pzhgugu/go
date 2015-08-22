package com.ansteel.dhtmlx.jsonclass;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import com.ansteel.core.domain.TreeUI;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射到树。  
 */
public class Tree {
	
	private String id;
	
	private List<TreeUI> item;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<TreeUI> getItem() {
		return item;
	}

	public void setItem(List<TreeUI> item) {
		this.item = item;
	}
	
	

}
