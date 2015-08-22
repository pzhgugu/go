package com.ansteel.dhtmlx.widget.form;

import java.util.ArrayList;
import java.util.List;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class Block extends Form{
	
	public Block() {
		super(DHtmlxConstants.BLOCK);
		// TODO Auto-generated constructor stub
	}	
	/**
	 * 左侧的项目内容的偏移量（默认20）
	 */
	private Number blockOffset;
	/**
	 * 用户定义的CSS类模块的项目
	 */
	private String className;
	/**
	 * 禁用/启用块的物品
	 */
	private Boolean disabled;
	/**
	 * 隐藏/显示项目。默认值为 - *假*（该项目显示）
	 */
	private Boolean hidden;
	/**
	 * 设置左绝对偏移
	 */
	private Integer inputLeft;
	/**
	 * 设定输入的绝对偏移的顶部。
	 */
	private Integer inputTop;
	/**
	 * 识别名称。用于参考项目
	 */
	private String name;
	/**
	 * 定义嵌套元件的阵列
	 */
	private List<Form> list	= new ArrayList<Form>();
	/**
	 * 设置左相对项目的偏移量
	 */
	private Integer offsetLeft;
	/**
	 * 设置上相对项目的偏移量
	 */
	private Integer offsetTop;
	/**
	 * 位置：label-left, label-right, label-top or absolute
	 */
	private String position	;
	/**
	 * 块的宽度
	 */
	private Integer width;
	public Number getBlockOffset() {
		return blockOffset;
	}
	public void setBlockOffset(Number blockOffset) {
		this.blockOffset = blockOffset;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public Integer getInputLeft() {
		return inputLeft;
	}
	public void setInputLeft(Integer inputLeft) {
		this.inputLeft = inputLeft;
	}
	public Integer getInputTop() {
		return inputTop;
	}
	public void setInputTop(Integer inputTop) {
		this.inputTop = inputTop;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Form> getList() {
		return list;
	}
	public void setList(List<Form> list) {
		this.list = list;
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
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
}
