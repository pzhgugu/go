package com.ansteel.common.tpl.domain;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-17
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "tpl")
public class Tpl extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1624423892211131614L;
	/**
	 *  描述
	 */
	@Column(length = 4000)
	private String scription;
	/**
	 * 模块全名称
	 */
	private String moduleClass;
	/**
	 * 模板路径
	 */
	private String tplPath;
	/**
	 * 模板jsp名称
	 */
	private String jspName;	
	/**
	 * 所占列数
	 */
	private Integer columnNumber;	
	/**
	 * 是否表格编辑
	 */
	private Integer isTableEditor;
	
	/**
	 * 项内容（数）的偏移量。对于块项目唯一
	 */
	private Number blockOffset ;
	/**
	 * 设置输入的高度。默认值是自动
	 */
	private Integer inputHeight ;
	/**
	 * 设置输入的宽度。默认值是自动
	 */
	private Integer inputWidth ;
	/**
	 * （左，右或中心）(left, right or center) 的标签定义的宽度内的对准
	 */
	private String labelAlign ;
	/**
	 * （整数或自动）设置标签的高度。默认值是自动
	 */
	private String labelHeight ;
	/**
	 * （整数或自动）设置标签的宽度。默认值是自动
	 */
	private Integer labelWidth ;
	/**
	 * （整数或自动）设置的细节的宽度方框（其被放置在输入下）
	 */
	private Integer noteWidth ;
	/**
	 * （整数）设置在左侧的相对项的偏移（两个输入和标签）
	 */
	private Integer offsetLeft ;
	/**
	 * （整数）设置顶相对项的偏移（两个输入和标签）
	 */
	private Integer offsetTop ;
	/**
	 * （标签左，标签右，标记顶或绝对）(label-left, label-right, label-top or absolute) 定义相对于输入标签的位置
	 */
	private String position ;
	/**
	 * 表单块宽度
	 */
	private Integer blockWidth ;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	
	
	public Integer getBlockWidth() {
		return blockWidth;
	}

	public void setBlockWidth(Integer blockWidth) {
		this.blockWidth = blockWidth;
	}

	public Number getBlockOffset() {
		return blockOffset;
	}

	public void setBlockOffset(Number blockOffset) {
		this.blockOffset = blockOffset;
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

	public Integer getLabelWidth() {
		return labelWidth;
	}

	public void setLabelWidth(Integer labelWidth) {
		this.labelWidth = labelWidth;
	}

	public Integer getNoteWidth() {
		return noteWidth;
	}

	public void setNoteWidth(Integer noteWidth) {
		this.noteWidth = noteWidth;
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

	public Integer getIsTableEditor() {
		return isTableEditor;
	}

	public void setIsTableEditor(Integer isTableEditor) {
		this.isTableEditor = isTableEditor;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tpl")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<TplCss> tplCssList = new ArrayList<TplCss>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tpl")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<TplUrl> tplUrlList = new ArrayList<TplUrl>();
	
	@OneToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER, mappedBy = "tpl")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<TplSecurity> tplSecurityList=new ArrayList<TplSecurity>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tpl")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<TplVariable> tplVariableList=new ArrayList<TplVariable>();
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "tpl")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<TplJavascript> javaScriptList=new ArrayList<TplJavascript>();

	public Collection<TplVariable> getTplVariableList() {
		return tplVariableList;
	}

	public void setTplVariableList(Collection<TplVariable> tplVariableList) {
		this.tplVariableList = tplVariableList;
	}
	
	public Collection<TplSecurity> getTplSecurityList() {
		return tplSecurityList;
	}
	public void setTplSecurityList(Collection<TplSecurity> tplSecurityList) {
		this.tplSecurityList = tplSecurityList;
	}
	public Collection<TplJavascript> getJavaScriptList() {
		return javaScriptList;
	}

	public void setJavaScriptList(Collection<TplJavascript> javaScriptList) {
		this.javaScriptList = javaScriptList;
	}

	public Collection<TplCss> getTplCssList() {
		return tplCssList;
	}

	public void setTplCssList(Collection<TplCss> tplCssList) {
		this.tplCssList = tplCssList;
	}

	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	


	public String getTplPath() {
		return tplPath;
	}

	public void setTplPath(String tplPath) {
		this.tplPath = tplPath;
	}

	public String getJspName() {
		return jspName;
	}

	public void setJspName(String jspName) {
		this.jspName = jspName;
	}

	public Collection<TplUrl> getTplUrlList() {
		return tplUrlList;
	}

	public void setTplUrlList(Collection<TplUrl> tplUrlList) {
		this.tplUrlList = tplUrlList;
	}

	public String getModuleClass() {
		return moduleClass;
	}

	public void setModuleClass(String moduleClass) {
		this.moduleClass = moduleClass;
	}

	
	
}
