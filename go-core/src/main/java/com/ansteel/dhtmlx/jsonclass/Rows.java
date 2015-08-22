package com.ansteel.dhtmlx.jsonclass;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射带分页。  
 */
public class Rows {
	
	private int total_count;
	
	public int getTotal_count() {
		return total_count;
	}

	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	
	private int pos;	
	
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}
	
	private Object data ;


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	

	
	
	

}
