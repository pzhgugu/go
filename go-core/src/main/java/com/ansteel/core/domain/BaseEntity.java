package com.ansteel.core.domain;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：基础实体类，包含版本、主键、创建人、创建时间、更新人、更新时间。 
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
@JsonInclude(Include.NON_EMPTY) 
public class BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4326200893701262621L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	private String id;
	@Version
	private Long version;
	/**
	 * 创建时间
	 */

	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
	private Date created;
	/**
	 * 修改时间
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")  
	private Date updated;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 最好修改人
	 */
	private String lastUpdater;
	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLastUpdater() {
		return lastUpdater;
	}

	public void setLastUpdater(String lastUpdater) {
		this.lastUpdater = lastUpdater;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Override
	public int hashCode() {
    	return new HashCodeBuilder().append(getId()).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (!(other.getClass()== this.getClass()))
			return false;
		BaseEntity casOther =  (BaseEntity) other;
		return new EqualsBuilder().append(getId(), casOther.getId()).isEquals();
	}
}
