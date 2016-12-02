package com.ansteel.report.dc.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据收集SQL实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "DataCollection_SQL")
public class DataCollectionSQL extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5993819751153475897L;

	/**
	 * sql语句
	 */
	@Lob
	private String sqlContent;


    @Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "pid", nullable = true)
	@JsonIgnore
	DataCollection dataCollection = new DataCollection();

	public String getSqlContent() {
		return sqlContent;
	}

	public void setSqlContent(String sqlContent) {
		this.sqlContent = sqlContent;
	}

	public Long getVersionPublish() {
		return versionPublish;
	}

	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}

	public DataCollection getDataCollection() {
		return dataCollection;
	}

	public void setDataCollection(DataCollection dataCollection) {
		this.dataCollection = dataCollection;
	}
}
