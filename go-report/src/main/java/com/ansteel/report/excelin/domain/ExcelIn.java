package com.ansteel.report.excelin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel导入实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "ExcelIn")
public class ExcelIn  extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2497003398267524869L;
	
	@Column(length = 4000)
	private String recode;
	
	private String sheetName;
	
	@Column( columnDefinition = "int default 1") 
	private Integer isLoop;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public Integer getIsLoop() {
		return isLoop;
	}

	public void setIsLoop(Integer isLoop) {
		this.isLoop = isLoop;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}
	
	

}
