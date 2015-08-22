package com.ansteel.dhtmlx.xml;

import java.util.ArrayList;
import java.util.List;


import javax.xml.bind.annotation.XmlRootElement;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：xml数据类解析。  
 */
@XmlRootElement
public class Data {
	
	private List<GridXml> action = new ArrayList<GridXml>();


	public List<GridXml> getAction() {
		return action;
	}


	public void setAction(List<GridXml> action) {
		this.action = action;
	}


	public void addAction(GridXml gridXml) {
		action.add(gridXml);
	}
	
	
}
