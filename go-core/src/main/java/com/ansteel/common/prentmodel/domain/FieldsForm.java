package com.ansteel.common.prentmodel.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型字段表单父类。  
 */
@MappedSuperclass
public class FieldsForm extends BaseEntity{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -9111887024776809313L;
	
	
	
	/**
	 * 机器名（英文）
	 */
	private String name;
	/**
	 *  排序
	 */
	private Integer displayOrder;
	/**
	 * 是否显示(为0，则视图不会运算这个字段)
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isShow;
	
	/**
	 * 表单类型
	 * @return
	 */
	private String type;
	/**
	 * 选项类型（0字符串、1值集、2SQL）
	 */

	@Column(columnDefinition = "int default 0")
	private Integer optionType;
	/**
	 * 选项值
	 */
	@Column(length=4000)
	private String optionValue;
	/**
	 * input/texteara
	 * 使用行数
	 */
	@Column(name="go_rows")
	private Integer rows ;
	
	/**
	 * 表单控件所占列数
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer columnForm ;
	
	/**
	 * input/texteara
	 * 宽度
	 */
	private Integer inputWidth;
	
	private Integer inputLeft;
	
	private Integer offsetLeft ;
	/**
	 * 输入的高度。默认值是自动的
	 */
	private Integer inputHeight ;
	
	private String userdata ;
	/**
	 * 验证
	 */
	@Column(name="go_validate")
	private String validate;
	/**
	 * 必输
	 */
	private Boolean required ;
	
	/**
	 * 添加图标后物料标签 （相关事件- onInfo)
	 */
	private Boolean info ;
	
	private String tooltip;
	
	private String note;


	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Boolean getInfo() {
		return info;
	}
	public void setInfo(Boolean info) {
		this.info = info;
	}
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	public String getValidate() {
		return validate;
	}
	public void setValidate(String validate) {
		this.validate = validate;
	}
	public Integer getOffsetLeft() {
		return offsetLeft;
	}
	public void setOffsetLeft(Integer offsetLeft) {
		this.offsetLeft = offsetLeft;
	}
	public Integer getInputLeft() {
		return inputLeft;
	}
	public void setInputLeft(Integer inputLeft) {
		this.inputLeft = inputLeft;
	}
	public Integer getInputWidth() {
		return inputWidth;
	}
	public void setInputWidth(Integer inputWidth) {
		this.inputWidth = inputWidth;
	}
	public Integer getColumnForm() {
		return columnForm;
	}
	public void setColumnForm(Integer columnForm) {
		this.columnForm = columnForm;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getInputHeight() {
		return inputHeight;
	}
	public void setInputHeight(Integer inputHeight) {
		this.inputHeight = inputHeight;
	}
	public String getUserdata() {
		return userdata;
	}
	public void setUserdata(String userdata) {
		this.userdata = userdata;
	}
	
}
