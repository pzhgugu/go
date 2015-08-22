package com.ansteel.dhtmlx.widget.form;

import java.util.Map;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class Button extends Form{

	public Button() {
		super(DHtmlxConstants.BUTTON);
	}
	/**
	 * 类名的字符串、 一个用户定义的 css 类项目
	 */
	private String className ;
	/**
	 * 禁用/启用该项目
	 */
	private Boolean disabled ;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 设置控件的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft ;
	/**
	 * 设置控件的顶部的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop ;
	/**
	 * 标识名称。用于指的项目
	 */
	private String name ;
	/**
	 * 设置左的相对偏移量的项目
	 */
	private Integer offsetLeft ;
	/**
	 * 设置项目的顶尖相对偏移量
	 */
	private Integer offsetTop ;
	/**
	 * 标签左、 右标签、 标签顶部或绝对，定义相对于按钮标签的位置。不为按钮定义了标签
	 */
	private String position;
	/**
	 * •工具提示的字符串，创建控件标签的工具提示
	 */
	private String tooltip ;
	/**
	 * •值的字符串，按钮的文本标签
	 */
	private String value ;
	/**
	 * 该按钮的宽度
	 */
	private Integer width ;
	/**
	 * 设置该控件的一些用户数据的关键： 面值对）
	 */
	private Map<String, Object> userdata ;
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
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	
	
}
