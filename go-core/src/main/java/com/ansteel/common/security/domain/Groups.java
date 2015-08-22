package com.ansteel.common.security.domain;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;


//@Entity
//@Table(name=Constants.G_TABLE_PREFIX + "go_groups")
public class Groups /*extends OperEntity*/{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877752373108172276L;
	/**
	 * 组类型
	 * 1、用户，2、角色，3、组
	 */
	private String type;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
