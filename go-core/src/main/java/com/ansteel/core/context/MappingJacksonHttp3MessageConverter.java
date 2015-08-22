package com.ansteel.core.context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.ansteel.dhtmlx.jsonclass.DhtmlxContext;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
/**
 * 创 建 人：gugu
 * 创建日期：2015-03-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：json返回值处理器。 
 */
public class MappingJacksonHttp3MessageConverter extends MappingJackson2HttpMessageConverter{

	@Override
	protected void writeInternal(Object object, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		if(object instanceof UDataSet){
			object=new DhtmlxContext().get((UDataSet) object);
		}
		super.writeInternal(object, outputMessage);
	}
}
