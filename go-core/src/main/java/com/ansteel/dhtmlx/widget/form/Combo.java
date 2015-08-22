package com.ansteel.dhtmlx.widget.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class Combo extends Form {

	public Combo() {
		super(DHtmlxConstants.COMBO);
	}

	/**
	 * 用于项目的用户定义 css 类
	 */
	private String className ;
	/**
	 * 允许从数据库中加载项的选择选项。请参阅详细信息在这里
	 */
	private String connector ;
	/**
	 * 允许将复选框或图像添加到组合的项目
	 */
	private String comboType ;
	/**
	 * 禁用/启用该项目
	 */
	private Boolean disabled ;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 添加图标后物料标签 （相关事件- onInfo)
	 */
	private Boolean info ;
	/**
	 * 设置输入的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft ;
	/**
	 * 设置输入的顶级绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop ;
	/**
	 * 整数或自动输入的宽度。默认值是自动的
	 */
	private Integer inputWidth ;
	/**
	 * 过滤-布尔，开关上的筛选模式 （当用户开始键入，组合表明匹配搜索结果的）。默认值是false
	 */
	private Boolean filtering ;
	/**
	 * 字符串、 项目的文本标签
	 */
	private String label ;
	/**
	 * •标签对齐方式为左、 右或中心，定义宽度内标签的对齐方式
	 */
	private String labelAlign ;
	/**
	 * 标签的高度。默认值是自动的
	 */
	private Integer labelHeight ;
	/**
	 * 设置标签的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelLeft ;
	/**
	 * 设置标签的顶部的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelTop ;
	/**
	 * 标签的宽度。默认值是自动的
	 */
	private Integer labelWidth ;
	/**
	 * 标识名称。用于指的项目
	 */
	private String name ;
	/**
	 * 请创建详细信息块放在该项目下
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
	 * 指定项目的选择选项
	 */
	private List<Options> options = new ArrayList<>();
	/**
	 * 左下角的标签，标签权利、 标签顶部或绝对，定义标签相对于输入的位置
	 */
	private String position ;
	/**
	 *  true / false 或 0/1，指定是否可以通过单击按钮在浏览器中更改项的值 （同时，可以随时以编程方式更改值）
	 */
	private Boolean readonly ;
	/**
	 * 添加标签标记作为强制性字段后的图标。此外，将该属性自动设置为true师傅到外地的 '空' 验证规则
	 */
	private Boolean required ;
	/**
	 * •工具提示的字符串，创建物料标签的工具提示
	 */
	private String tooltip ;
	/**
	 * 预定义规则 * 或 * 自定义函数，设置的验证规则。查看在"验证"一章的详细信息
	 */
	private String validate ;
	/**
	 * 初始项的值，字符串，则
	 */
	private String value;
	/**
	 * 请设置该项目的一些用户数据键： 值 * 对）
	 */
	private Map<String, Object> userdata ;
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getConnector() {
		return connector;
	}
	public void setConnector(String connector) {
		this.connector = connector;
	}
	public String getComboType() {
		return comboType;
	}
	public void setComboType(String comboType) {
		this.comboType = comboType;
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
	public Boolean getFiltering() {
		return filtering;
	}
	public void setFiltering(Boolean filtering) {
		this.filtering = filtering;
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
	public Boolean getReadonly() {
		return readonly;
	}
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
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
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	public List<Options> getOptions() {
		return options;
	}
	public void setOptions(List<Options> options) {
		this.options = options;
	}
	
	
}
