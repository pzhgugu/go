package com.ansteel.shop.goods.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY) 
public class JsonGoodsClass {

	private String gc_id;
	
	private String gc_name;
	
	private String type_id;
	
	private String one;
	
	private String two;
	
	private String three;
	
	private boolean done;

	private String gc_parent_id;

	private Integer gc_sor;

	public String getGc_parent_id() {
		return gc_parent_id;
	}

	public void setGc_parent_id(String gc_parent_id) {
		this.gc_parent_id = gc_parent_id;
	}

	public Integer getGc_sor() {
		return gc_sor;
	}

	public void setGc_sor(Integer gc_sor) {
		this.gc_sor = gc_sor;
	}

	public String getTwo() {
		return two;
	}

	public void setTwo(String two) {
		this.two = two;
	}

	public String getThree() {
		return three;
	}

	public void setThree(String three) {
		this.three = three;
	}

	public String getOne() {
		return one;
	}

	public void setOne(String one) {
		this.one = one;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getGc_id() {
		return gc_id;
	}

	public void setGc_id(String gc_id) {
		this.gc_id = gc_id;
	}

	public String getGc_name() {
		return gc_name;
	}

	public void setGc_name(String gc_name) {
		this.gc_name = gc_name;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	
	
}
