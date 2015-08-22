package com.ansteel.dhtmlx.widget.form;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class KindEditor extends Editor{
	/**
	 * 整数，用于本文本区域 （而不是一个单一的输入） 的指定的高度)
	 */
	private Integer rows;
	
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public KindEditor() {
		super(DHtmlxConstants.KINDEDITOR);
	}
}
