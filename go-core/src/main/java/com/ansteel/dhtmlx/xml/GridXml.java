package com.ansteel.dhtmlx.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：xml表格类解析。  
 */
public class GridXml {
	private String type;
	private String tid;
	private String sid;

	public String getType() {
		return type;
	}
	@XmlAttribute
	public void setType(String type) {
		this.type = type;
	}

	public String getTid() {
		return tid;
	}
	@XmlAttribute
	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getSid() {
		return sid;
	}
	@XmlAttribute
	public void setSid(String sid) {
		this.sid = sid;
	}
	
	
}
