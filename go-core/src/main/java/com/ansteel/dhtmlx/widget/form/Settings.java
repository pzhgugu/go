package com.ansteel.dhtmlx.widget.form;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class Settings extends Form{

	public Settings() {
		super(DHtmlxConstants.SETTINGS);
	}
	/**
	 * 项内容（数）的偏移量。对于块项目唯一
	 */
	private Number blockOffset ;
	/**
	 * 设置输入的高度。默认值是自动
	 */
	private Integer inputHeight ;
	/**
	 * 设置输入的宽度。默认值是自动
	 */
	private Integer inputWidth ;
	/**
	 * （左，右或中心）(left, right or center) 的标签定义的宽度内的对准
	 */
	private String labelAlign ;
	/**
	 * （整数或自动）设置标签的高度。默认值是自动
	 */
	private String labelHeight ;
	/**
	 * （整数或自动）设置标签的宽度。默认值是自动
	 */
	private Integer labelWidth ;
	/**
	 * （整数或自动）设置的细节的宽度方框（其被放置在输入下）
	 */
	private Integer noteWidth ;
	/**
	 * （整数）设置在左侧的相对项的偏移（两个输入和标签）
	 */
	private Integer offsetLeft ;
	/**
	 * （整数）设置顶相对项的偏移（两个输入和标签）
	 */
	private Integer offsetTop ;
	/**
	 * （标签左，标签右，标记顶或绝对）(label-left, label-right, label-top or absolute) 定义相对于输入标签的位置
	 */
	private String position ;
	
	public Number getBlockOffset() {
		return blockOffset;
	}
	public void setBlockOffset(Number blockOffset) {
		this.blockOffset = blockOffset;
	}
	public Integer getInputHeight() {
		return inputHeight;
	}
	public void setInputHeight(Integer inputHeight) {
		this.inputHeight = inputHeight;
	}
	public Integer getInputWidth() {
		return inputWidth;
	}
	public void setInputWidth(Integer inputWidth) {
		this.inputWidth = inputWidth;
	}
	public String getLabelAlign() {
		return labelAlign;
	}
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}
	public String getLabelHeight() {
		return labelHeight;
	}
	public void setLabelHeight(String labelHeight) {
		this.labelHeight = labelHeight;
	}
	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}
	public Integer getNoteWidth() {
		return noteWidth;
	}
	public void setNoteWidth(Integer noteWidth) {
		this.noteWidth = noteWidth;
	}
	public Integer getOffsetLeft() {
		return offsetLeft;
	}
	public void setOffsetLeft(Integer offsetLeft) {
		this.offsetLeft = offsetLeft;
	}
	public Integer getOffsetTop() {
		return offsetTop;
	}
	public void setOffsetTop(Integer offsetTop) {
		this.offsetTop = offsetTop;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
