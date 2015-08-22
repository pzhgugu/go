package com.ansteel.core.domain;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：树界面查询映射，用于树实体请求映射。  
 */
public class TreeUI {
	
	public TreeUI(String id,String text,String parent,Integer displayOrder,Long  child){
		this.id=id;
		this.text=text;
		this.parent=parent;
		this.displayOrder=displayOrder;
		this.child=child;
	}
	
	private String id;
	
	private String text;
	
	private String parent;
	
	private Integer displayOrder;
	
	private Long  child;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public Long getChild() {
		return child;
	}

	public void setChild(Long child) {
		this.child = child;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	

}
