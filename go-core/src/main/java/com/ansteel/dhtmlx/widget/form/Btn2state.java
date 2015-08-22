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
public class Btn2state extends Form {

	protected Btn2state() {
		super(DHtmlxConstants.BTN2STATE);
	}

	/**
	 * 检查-定义将最初选中复选框。默认值是false
	 */
	private Boolean checked ;
	/**
	 * 用于项目的用户定义 css 类
	 */
	private String className ;
	/**
	 * 它允许设置个人寻找一个特定的项目实例 （请参阅详细信息如下)
	 */
	private String cssName ;
	/**
	 * 禁用/启用该项目
	 */
	private Boolean disabled ;
	/**
	 * 隐藏/显示该项目。默认值- false （该项显示）
	 */
	private Boolean hidden ;
	/**
	 * 添加图标后输入的标签 （相关事件- onInfo)
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
	 * 该项目的文本标签
	 */
	private String label;
	/**
	 * •标签对齐方式为左、 右或中心，定义宽度内标签的对齐方式(left, right or center)
	 */
	private String labelAlign ;
	/**
	 * DIV 标签的位置 （而不是字体大小） 的高度。默认值是自动的
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
	 * 定义嵌套元素的数组
	 */
	private List<Form> list	= new ArrayList<Form>();
	/**
	 * 名称的字符串，标识名称。用于指的项目
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
	 * 标签左、 右标签、 标签顶部或绝对，定义相对于复选框标签的位置
	 */
	private String position ;
	/**
	 * 添加图标后输入作为强制性的标号。此外，将该属性自动设置为true师傅到输入"空"验证规则
	 */
	private Boolean required ;
	/**
	 * true / false 或 0/1，指定项的值是否可以通过在浏览器中的按钮单击更改 （同时，可以随时以编程方式更改项的值）
	 */
	private Boolean readonly ;
	/**
	 * 工具提示的字符串，创建输入标签的工具提示
	 */
	private String tooltip ;
	/**
	 * •值-初始项的值，字符串，则
	 */
	private String value ;
	/**
	 * 设置一些输入 （键： 值对） 的用户数据
	 */
	private Map<String, Object> userdata ;
	
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getCssName() {
		return cssName;
	}
	public void setCssName(String cssName) {
		this.cssName = cssName;
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
	public List<Form> getList() {
		return list;
	}
	public void setList(List<Form> list) {
		this.list = list;
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
	public Boolean getReadonly() {
		return readonly;
	}
	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
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
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	
	
}
