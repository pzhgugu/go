package com.ansteel.dhtmlx.jsonclass;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射。  
 */
public class UDataSet {
	
	private final static String UI_NAME="_ui";
	
	public UDataSet(HttpServletRequest request,String ui,Object result){
		if(StringUtils.hasText(ui)){
			this.ui=ui;
		}
		Map<String, String[]> rMap = request.getParameterMap();
		if(rMap.containsKey(UI_NAME)){
			this.ui=request.getParameter(UI_NAME);
		}

		this.result=result;
	}
	
	public UDataSet(String ui,Object result){
		if(StringUtils.hasText(ui)){
			this.ui=ui;
		}
		this.result=result;
	}
	
	private String ui;
	
	private Object result;

	public String getUi() {
		return ui;
	}

	public void setUi(String ui) {
		this.ui = ui;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
