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
public class Radio extends Form{

	protected Radio() {
		super(DHtmlxConstants.RADIO);
	}

	private Boolean checked ;
	private String className ;
	private Boolean disabled ;
	private Boolean hidden ;
	private Boolean info ;
	private Integer inputLeft ;
	private Integer inputTop ;
	private String label ;
	private String labelAlign ;
	private Integer labelHeight ;
	private Integer labelLeft ;
	private Integer labelTop ;
	private Integer labelWidth ;
	private List<Form> list	= new ArrayList<Form>();
	private String name ;
	private Note note ;
	private Integer offsetLeft ;
	private Integer offsetTop ;
	private String position ;
	private Boolean readonly ;
	private Boolean required ;
	private String tooltip ;
	private String value ;
	private Map<String, Object> userdata;
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
