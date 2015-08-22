package com.ansteel.common.attachment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：附件管理。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "attachment")
public class Attachment extends OperEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3140501160087386809L;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.MERGE }, optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "treeid", nullable = false)
	@JsonIgnore
	private AttachmentTree attachmentTree;
	/**
	 * 附件物理路径
	 */
	private String path;

	@Transient
	private String webPath;
	/**
	 * 附件类型（PDF，EXCL，WORD，IMAGE）
	 */
	private String type;
	/**
	 * 附件上传文件名
	 */
	private String loadFileName;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件大小
	 */
	private Long fileSize;

	private String contentType;
	
	/**
	 * 附件组
	 */
	private String groupId;
	/**
	 * 附件下载次数
	 */
	private Integer downloads;
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getDownloads() {
		return downloads;
	}

	public void setDownloads(Integer downloads) {
		this.downloads = downloads;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getWebPath() {
		return webPath;
	}

	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}

	public AttachmentTree getAttachmentTree() {
		return attachmentTree;
	}

	public void setAttachmentTree(AttachmentTree attachmentTree) {
		this.attachmentTree = attachmentTree;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoadFileName() {
		return loadFileName;
	}

	public void setLoadFileName(String loadFileName) {
		this.loadFileName = loadFileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

}
