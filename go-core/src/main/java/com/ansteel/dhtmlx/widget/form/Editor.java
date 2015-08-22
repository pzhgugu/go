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
public class Editor extends Form{

	public Editor() {
		super(DHtmlxConstants.EDITOR);
	}

	public Editor(String name) {
		super(name);
	}

	/**
	 * 的用户定义的 css 类的项目
	 */
	private String className ;
	/**
	 * 禁用/启用该项目
	 */
	private Boolean disabled ;
	/**
	 *  隐藏/显示该项目。默认值-false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 添加图标后物料标签 （相关事件- onInfo)
	 */
	private Boolean info ;
	/**
	 * 输入的高度。默认值是自动的
	 */
	private Integer inputHeight ;
	/**
	 * 左侧的绝对偏移量的输入。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft ;
	/**
	 * 顶部的绝对偏移量的输入。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop ;
	/**
	 *  设置输入的宽度。默认值是自动的
	 */
	private Integer inputWidth ;
	/**
	 * 的文本标签的项目
	 */
	private String label ;
	/**
	 * •标签对齐方式-（左、 右或中心） 的定义的宽度内标签的对齐方式
	 */
	private String labelAlign ;
	/**
	 * 标签的高度。默认值是自动的
	 */
	private Integer labelHeight ;
	/**
	 * 左边的标签的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelLeft ;
	/**
	 * 顶部的标签的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelTop ;
	/**
	 *  标签的宽度。默认值是自动的
	 */
	private Integer labelWidth ;
	/**
	 * 识别名称。用于指的项目
	 */
	private String name ;
	/**
	 * 创建详细信息块放在该项目下
	 */
	private Note note ;
	/**
	 * 集的左侧的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetLeft ;
	/**
	 * 顶部的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetTop ;
	/**
	 * -（标签左、 右标签、 标签顶部或绝对） 定义标签相对于输入的位置
	 */
	private String position ;
	/**
	 * 添加标签标记作为强制性字段后的图标。此外，将该属性自动设置为 //true// 师傅到外地的 '空' 验证规则
	 */
	private Boolean required ;
	/**
	 * 创建物料标签的工具提示
	 */
	private String tooltip ;
	/**
	 * 设置的验证规则。查看在"验证"一章的详细信息•（字符串） 初始价值的项目
	 */
	private String validate ;
	/**
	 *  初始价值的项目
	 */
	private String value ;
	/**
	 * 设置为该项目的某些用户数据 （键： 值 / / 双）
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
	public Integer getInputWidth() {
		return inputWidth;
	}
	public void setInputWidth(Integer inputWidth) {
		this.inputWidth = inputWidth;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabelAlign() {
		return labelAlign;
	}
	public void setLabelAlign(String labelAlign) {
		this.labelAlign = labelAlign;
	}
	public Integer getLabelHeight() {
		return labelHeight;
	}
	public void setLabelHeight(Integer labelHeight) {
		this.labelHeight = labelHeight;
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
	public Integer getLabelWidth() {
		return labelWidth;
	}
	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
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
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	
	
}
