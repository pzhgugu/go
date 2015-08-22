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
public class Container extends Form{

	protected Container() {
		super(DHtmlxConstants.CONTAINER);
	}

	/**
	 * •类名的字符串，用于项目的用户定义 css 类
	 */
	private String className ;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 添加图标后输入的标签 （相关事件- onInfo)
	 */
	private Boolean info ;
	/**
	 * 自动输入的高度。默认值是自动的
	 */
	private Integer inputHeight ;
	/**
	 * 自动输入的宽度。默认值是自动的
	 */
	private Integer inputWidth ;
	/**
	 * 设置输入的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft ;
	/**
	 * 设置输入的顶级绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop ;
	/**
	 * 字符串、 项目的文本标签
	 */
	private String label ;
	/**
	 * 设置标签的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelLeft ;
	/**
	 * 设置标签的顶部的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelTop ;
	/**
	 * 标识名称。用于指的项目
	 */
	private String name ;
	/**
	 * 请创建详细信息块放在输入下
	 */
	private Note note ;
	/**
	 * 设置左的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetLeft ;
	/**
	 * 设置最高的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetTop ;
	/**
	 * 左下角的标签，标签权利、 标签顶部或绝对，定义标签相对于输入的位置
	 */
	private String position;
	/**
	 * 添加图标后输入作为强制性的标号。此外，将该属性自动设置为true师傅到输入 '空' 验证规则
	 */
	private Boolean required ;
	/**
	 * •样式的字符串，指定将应用于输入的 css 样式
	 */
	private String style ;
	/**
	 * •工具提示的字符串，创建输入标签的工具提示
	 */
	private String tooltip ;
	/**
	 * 对于输入的密钥： 面值成对设置某些用户数据）
	 */
	private Map<String, Object> userdata ;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public Boolean getInfo() {
		return info;
	}
	public void setInfo(Boolean info) {
		this.info = info;
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
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getLabelLeft() {
		return labelLeft;
	}
	public void setLabelLeft(Integer labelLeft) {
		this.labelLeft = labelLeft;
	}
	public Integer getLabelTop() {
		return labelTop;
	}
	public void setLabelTop(Integer labelTop) {
		this.labelTop = labelTop;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Note getNote() {
		return note;
	}
	public void setNote(Note note) {
		this.note = note;
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
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
}
