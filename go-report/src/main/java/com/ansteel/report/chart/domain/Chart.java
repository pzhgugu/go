package com.ansteel.report.chart.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-06
 * 修 改 人：
 * 修改日 期：
 * 描   述：图表实体。 
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "CHART")
public class Chart  extends OperEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6650224485163960963L;

	/**
	 * sql语句
	 */
	@Lob
	private String sqlContent;
	
	/**
	 * 编码
	 */
	@Column(length = 4000)
	private String recode;

	/**
	 * 图表类型
	 */
	private String type;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 标题颜色
	 */
	private String titleColor;
	
	/**
	 * 背景图片
	 */
	private String backgroundImage;
	
	/**
	 * 名称映射
	 */
	private String nameMapping;
	
	/**
	 * 颜色
	 */
	private String colorizeBars;
	
	/**
	 * 工具条提示
	 */
	private String tooltip;
	
	/**
	 * 值颜色
	 */
	private String barValuesColor;
	
	/**
	 * 数据方向（1、为纵向，2、为横向）
	 */
	@Column(columnDefinition="INT default 1")
	private Integer dataAspect;
	
	public Integer getDataAspect() {
		return dataAspect;
	}

	public void setDataAspect(Integer dataAspect) {
		this.dataAspect = dataAspect;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleColor() {
		return titleColor;
	}

	public void setTitleColor(String titleColor) {
		this.titleColor = titleColor;
	}

	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public String getNameMapping() {
		return nameMapping;
	}

	public void setNameMapping(String nameMapping) {
		this.nameMapping = nameMapping;
	}

	public String getColorizeBars() {
		return colorizeBars;
	}

	public void setColorizeBars(String colorizeBars) {
		this.colorizeBars = colorizeBars;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getBarValuesColor() {
		return barValuesColor;
	}

	public void setBarValuesColor(String barValuesColor) {
		this.barValuesColor = barValuesColor;
	}

	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getVersionPublish() {
		return versionPublish;
	}

	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}
	
	
}
