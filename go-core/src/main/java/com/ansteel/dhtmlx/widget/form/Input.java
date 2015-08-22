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
public class Input extends Form{

	public Input() {
		super(DHtmlxConstants.INPUT);
	}

	public Input(String type) {
		super(type);
	}
	/**
	 * 用于项目的用户定义 css 类
	 */
	private String className;
	/**
	 * 布尔，禁用/启用该项目
	 */
	private Boolean disabled;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden;
	/**
	 * 添加图标后输入的标签 （相关事件- onInfo)
	 */
	private Boolean info;
	/**
	 * 整数或自动输入的高度。默认值是自动的
	 */
	private Integer inputHeight;
	/**
	 * 置输入的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft;
	/**
	 * 设置输入的顶级绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop;
	/**
	 * 整数或自动输入的宽度。默认值是自动的
	 */
	private Integer inputWidth;
	/**
	 * 字符串、 项目的文本标签
	 */
	private String label;
	/**
	 * •标签对齐方式为左、 右或中心，定义宽度内标签的对齐方式(left, right or center)
	 */
	private String labelAlign;
	/**
	 * DIV 标签的位置 （而不是字体大小） 的高度。默认值是自动的
	 */
	private String labelHeight;
	/**
	 * 设置标签的左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelLeft;
	/**
	 * 设置标签的顶部的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelTop;
	/**
	 * 标签的宽度。默认值是自动的
	 */
	private Integer labelWidth;
	/**
	 * 可以在输入中输入的字符的最大数目
	 */
	private Integer maxLength;
	/**
	 * 标识名称。用于指的项目
	 */
	private String name;
	/**
	 * 请注意--对象，请创建详细信息块放在输入下 
	 * 文本块的文本，字符串，则
	 * 宽度-整数、 块的宽度
	 */
	private Note note;
	/**
	 * 设置 （请参阅详细信息下面的数值数据的格式)
	 */
	private String numberFormat;
	/**
	 * 设置左的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetLeft;
	/**
	 * 设置最高的相对偏移量的项目 （输入和标签）
	 */
	private Integer offsetTop;
	/**
	 * 左下角的标签，标签权利、 标签顶部或绝对，定义标签相对于输入的位置
	 */
	private String position;
	/**
	 * 指定项的值是否可以通过在浏览器中的按钮单击更改 （同时，可以随时以编程方式更改项的值）
	 */
	private Boolean readonly;
	/**
	 * 添加图标后输入作为强制性的标号。此外，将该属性自动设置为true师傅到输入 '空' 验证规则
	 */
	private Boolean required;
	/**
	 * 整数，用于本文本区域 （而不是一个单一的输入） 的指定的高度)
	 */
	private Integer rows;
	/**
	 * 指定将应用于输入的 css 样式
	 */
	private String style;
	/**
	 * 创建输入标签的工具提示
	 */
	private String tooltip;
	/**
	 *  预定义的 rule_ 或 _custom 函数设置的验证规则。查看在"验证"一章的详细信息
	 */
	private String validate;
	/**
	 * 初始项的值，字符串，则
	 */
	private String value;
	/**
	 * 对象，对于输入的密钥： 面值成对设置某些用户数据）
	 */
	private Map<String, Object> userdata;

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

	public String getLabelHeight() {
		return labelHeight;
	}

	public void setLabelHeight(String labelHeight) {
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

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
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
	public String getNumberFormat() {
		return numberFormat;
	}

	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
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

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
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
