package com.ansteel.report.dc.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
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
@Table(name = Constants.G_TABLE_PREFIX + "DataCollection")
public class DataCollection extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2088917057418650317L;
	
	/**
	 * 是否启用
	 */
	@Column( columnDefinition = "int default 1") 
	private Integer isStart;

	/**
	 * 报表类型 day=日报;month=月报;year=年报;region=区间报表;
	 */
	private String type;
	
	@Column(name = "VERSION_PUBLISH", columnDefinition="INT default 0")
	private Long versionPublish;
		
	public Long getVersionPublish() {
		return versionPublish;
	}
	public void setVersionPublish(Long versionPublish) {
		this.versionPublish = versionPublish;
	}	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getIsStart() {
		return isStart;
	}

	public void setIsStart(Integer isStart) {
		this.isStart = isStart;
	}

	
	

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "tree_id")
	@JsonIgnore
	private DataCollectionTree dataCollectionTree = new DataCollectionTree();


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dataCollection")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DataCollectionSQL> dataCollectionSQLList = new ArrayList<DataCollectionSQL>();



	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dataCollection")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("displayOrder")
	@JsonIgnore
	private Collection<DataCollectionTest> dataCollectionTestList = new ArrayList<DataCollectionTest>();


	public DataCollectionTree getDataCollectionTree() {
		return dataCollectionTree;
	}

	public void setDataCollectionTree(DataCollectionTree dataCollectionTree) {
		this.dataCollectionTree = dataCollectionTree;
	}

	public Collection<DataCollectionSQL> getDataCollectionSQLList() {
		return dataCollectionSQLList;
	}

	public void setDataCollectionSQLList(Collection<DataCollectionSQL> dataCollectionSQLList) {
		this.dataCollectionSQLList = dataCollectionSQLList;
	}

	public Collection<DataCollectionTest> getDataCollectionTestList() {
		return dataCollectionTestList;
	}

	public void setDataCollectionTestList(Collection<DataCollectionTest> dataCollectionTestList) {
		this.dataCollectionTestList = dataCollectionTestList;
	}
}
