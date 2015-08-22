package com.ansteel.dhtmlx.jsonclass;

import java.util.HashMap;
import java.util.Map;

import com.ansteel.core.constant.DHtmlxConstants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射上下文，用于json数据转换。  
 */
public class DhtmlxContext {
	
	private static Map<String ,UiStrategy> strategyMap =null;
	

	public Object get(UDataSet dataSet){
		UiStrategy uiStrategy = this.getStrategy(dataSet.getUi());
		if(uiStrategy==null){
			return dataSet.getResult();
		}
		return uiStrategy.get(dataSet.getResult());
		
	}
	
	public UiStrategy getStrategy(String name){
		this.initStrategy();
		if(strategyMap.containsKey(name)){
			return strategyMap.get(name);
		}
		return null;
	}

	public Map<String ,UiStrategy> initStrategy(){
		if(strategyMap==null){
			strategyMap=new HashMap<String, UiStrategy>();
			strategyMap.put(DHtmlxConstants.UI_ASYN_TREE, new TreeUiStrategy());
			strategyMap.put(DHtmlxConstants.UI_DATA, new DataUiStrategy());
			strategyMap.put(DHtmlxConstants.UI_ROWS, new RowsUiStrategy());
			strategyMap.put(DHtmlxConstants.UI_OBJECT, new ObjectUiStrategy());
		}
		return strategyMap;
	}

	
}
