package com.ansteel.shop.utils;

public class JsonImage {
	/**
	 * 文件id
	 */
	private String file_id;
	/**
	 * 文件名称
	 */
	private String file_name;
	/**
	 * 上传前名称
	 */
	private String origin_file_name;
	/**
	 * 文件路径
	 */
	private String file_path;
	
	private String instance;
	
	private Boolean state;

	public String getFile_id() {
		return file_id;
	}

	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getOrigin_file_name() {
		return origin_file_name;
	}

	public void setOrigin_file_name(String origin_file_name) {
		this.origin_file_name = origin_file_name;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}
	
	
	

}
