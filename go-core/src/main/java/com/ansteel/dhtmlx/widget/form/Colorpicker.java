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
public class Colorpicker extends Form{

	protected Colorpicker() {
		super(DHtmlxConstants.COLORPICKER);
	}
	/**
	 * 类名的字符串，用于项目的用户定义 css 类
	 */
	private String className ;
	/**
	 * 字符串，颜色拾取器版本的位置 （右/底部）
	 */
	private String cpPosition ;
	/**
	 * 禁用/启用该项目
	 */
	private Boolean disabled;
	/**
	 * 设置自定义颜色 （在 customColors 参数中指定） 的项目。默认值为false
	 */
	private Boolean enableCustomColors;
	/**
	 * 字符串、 一个逗号分隔的列表，如果 enableCustomColors 参数设置为true将在颜色拾取器版本中使用的自定义颜色
	 */
	private String customColors ;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 添加图标后输入的标签 （相关事件- onInfo)
	 */
	private Boolean info ;
	/**
	 * •文件夹的路径的 url，颜色拾取器版本的图像的相对路径 （dhtmlxColorPicker，代码库，imgs /)
	 */
	private String imagePath ;
	/**
	 * 设置输入的高度。默认值是自动的
	 */
	private Integer inputHeight ;
	/**
	 * 输入左绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputLeft ;
	/**
	 * 则顶部的绝对偏移量的输入。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer inputTop ;
	/**
	 * 设置输入的宽度。默认值是自动的
	 */
	private Integer inputWidth ;
	/**
	 * 字符串、 项目的文本标签
	 */
	private String label ;
	/**
	 * •标签对齐方式为左、 右或中心，定义宽度内标签的对齐方式
	 */
	private String labelAlign;
	/**
	 * 标签的高度。默认值是自动的
	 */
	private Integer labelHeight ;
	/**
	 * 标签的左侧的绝对偏移量。该属性被应用只当位置被设置为"绝对"
	 */
	private Integer labelLeft ;
	/**
	 * 标签的顶部的绝对偏移量。该属性被应用只当位置被设置为"绝对"
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
	private Integer offsetTop;
	/**
	 * 左下角的标签，标签权利、 标签顶部或绝对，定义标签相对于输入的位置
	 */
	private String position ;
	/**
	 * true / false * 或 * 0/1，指定项的值是否可以更改通过键入的输入 （你是仍然能够通过单击颜色拾取器版本图标设置的值）。可以用来排除这种可能性的输入不正确的值
	 */
	private Boolean readonly;
	/**
	 * 添加图标后输入作为强制性的标号。此外，将该属性自动设置为true师傅到输入 '空' 验证规则
	 */
	private Boolean required ;
	/**
	 * •工具提示的字符串，创建输入标签的工具提示
	 */
	private String tooltip ;
	/**
	 * 预定义规则 * 或 * 自定义函数，设置的验证规则。查看在"验证"一章的详细信息
	 */
	private String validate ;
	/**
	 * 初始项的值，字符串，则
	 */
	private String value ;
	/**
	 * 对于输入键： 值 * 成对设置某些用户数据）
	 */
	private Map<String, Object> userdata ;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCpPosition() {
		return cpPosition;
	}
	public void setCpPosition(String cpPosition) {
		this.cpPosition = cpPosition;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean getEnableCustomColors() {
		return enableCustomColors;
	}
	public void setEnableCustomColors(Boolean enableCustomColors) {
		this.enableCustomColors = enableCustomColors;
	}
	public String getCustomColors() {
		return customColors;
	}
	public void setCustomColors(String customColors) {
		this.customColors = customColors;
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
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
