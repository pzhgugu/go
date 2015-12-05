package com.ansteel.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：树实体，包含树的基本信息。 
 */

@MappedSuperclass
@SuppressWarnings("rawtypes")
public class TreeEntity<T extends TreeEntity> extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578233004739543979L;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	@OrderBy("displayOrder")
	@JsonIgnore
	private T parent;
	
	@OneToMany(mappedBy="parent",fetch=FetchType.LAZY)
	private Collection<T> children = new ArrayList<T>();
	
	@Column(name="LAYER" ,nullable=false,columnDefinition="INT default 0")
	private Integer layer;
	
	public Integer getLayer() {
		return layer;
	}
	public void setLayer() {
		if(this.getParent()!=null){
			this.layer=this.getParent().getLayer()+1;
		}else{
			this.layer = 0;
		}
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public void setChildren(Collection<T> children) {
		this.children = children;
	}

	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	public Collection<T> getChildren() {
		return children;
	}
	
	public void addChildren(Collection<T> elements) {
		getChildren().addAll(elements);
	}
	
	public void addChild(T element) {
		getChildren().add(element);
	}
	
	public void removeChildren(Collection<T> elements) {
		for(T element : elements) {
			if(!getChildren().contains(element)) {
				getChildren().remove(elements);
			}
		}
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getParentId() {
		if (this.getParent() == null) {
			return Constants.NULL;
		} else {
			return (String)this.getParent().getId();
		}
	}
}
