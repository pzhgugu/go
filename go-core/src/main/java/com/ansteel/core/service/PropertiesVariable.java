package com.ansteel.core.service;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.context.PropertiesConfigurationLoader;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：配置变量获取接口。  
 */
public class PropertiesVariable  extends Variable  {
    @Override
    public Map<String, Object> init() {
        Map<String,Object> map = new HashMap<String,Object>();
        Properties properties=PropertiesConfigurationLoader.getGoProperties();
        if(properties!=null){
	        Enumeration e=properties.propertyNames();
	        while (e.hasMoreElements()) {
	            String key = (String) e.nextElement();
	            map.put(key,properties.getProperty(key));
	        }
        }
        return map;
    }
}
