package com.ansteel.dhtmlx.widget.form;

import java.util.Map;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：dhtmlx表单类。  
 */
public class Upload extends Form{

	protected Upload() {
		super(DHtmlxConstants.UPLOAD);
	}
	private String className ;
	private Boolean disabled ;
	private Boolean hidden;
	private String name ;
	private Integer inputHeight;
	private Integer inputWidth;
	private Integer offsetLeft ;
	private Integer offsetTop ;
	private Map<String, Object> userdata ;
	private String mode ;
	private Boolean titleScreen ;
	private String titleText ;
	private Boolean autoStart ;
	private Boolean autoRemove ;
	private String url ;
	private String swfPath ;
	private String swfUrl ;
	private String slXap;
	private String slUrl ;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean getHidden() {
		return hidden;
	}
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Map<String, Object> getUserdata() {
		return userdata;
	}
	public void setUserdata(Map<String, Object> userdata) {
		this.userdata = userdata;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Boolean getTitleScreen() {
		return titleScreen;
	}
	public void setTitleScreen(Boolean titleScreen) {
		this.titleScreen = titleScreen;
	}
	public String getTitleText() {
		return titleText;
	}
	public void setTitleText(String titleText) {
		this.titleText = titleText;
	}
	public Boolean getAutoStart() {
		return autoStart;
	}
	public void setAutoStart(Boolean autoStart) {
		this.autoStart = autoStart;
	}
	public Boolean getAutoRemove() {
		return autoRemove;
	}
	public void setAutoRemove(Boolean autoRemove) {
		this.autoRemove = autoRemove;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSwfPath() {
		return swfPath;
	}
	public void setSwfPath(String swfPath) {
		this.swfPath = swfPath;
	}
	public String getSwfUrl() {
		return swfUrl;
	}
	public void setSwfUrl(String swfUrl) {
		this.swfUrl = swfUrl;
	}
	public String getSlXap() {
		return slXap;
	}
	public void setSlXap(String slXap) {
		this.slXap = slXap;
	}
	public String getSlUrl() {
		return slUrl;
	}
	public void setSlUrl(String slUrl) {
		this.slUrl = slUrl;
	}
	
	
}
