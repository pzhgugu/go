package com.ansteel.report.dc.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：数据收集实体。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "DataCollection_Tree")
public class DataCollectionTree extends TreeEntity<DataCollectionTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1628830489465313897L;
	/**
	 * 描述
	 */
	@Column(length=4000) 
	private String scription;
	
	@OneToMany(mappedBy = "dataCollectionTree",  cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DataCollection> dataCollectionList = new ArrayList<DataCollection>();

	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;

	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}

	public String getScription() {
		return scription;
	}

	public void setScription(String scription) {
		this.scription = scription;
	}


	public Collection<DataCollection> getDataCollectionList() {
		return dataCollectionList;
	}

	public void setDataCollectionList(Collection<DataCollection> dataCollectionList) {
		this.dataCollectionList = dataCollectionList;
	}
}
