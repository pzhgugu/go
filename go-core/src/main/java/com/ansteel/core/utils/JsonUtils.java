package com.ansteel.core.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json转换工具类。  
 */
public class JsonUtils {
	private static final Log log = LogFactory.getLog(JsonUtils.class);
	

	public static String jsonFromObject(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, object);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			log.error("Unable to serialize to json: " + object, e);
			return null;
		}
		return writer.toString();
	}
	
	public static Object objectFromJson(File file, Class klass) {
		ObjectMapper mapper = new ObjectMapper();
		Object object;
		try {
			object = mapper.readValue(file, klass);
		} catch (RuntimeException e) {
			log.error("Runtime exception during deserializing "
					+ klass.getSimpleName() + " from "
					+ StringUtils.abbreviate(file.getPath(), 80));
			throw e;
		} catch (Exception e) {
			log.error("Exception during deserializing " + klass.getSimpleName()
					+ " from " + StringUtils.abbreviate(file.getPath(), 80));
			return null;
		}
		return object;
	}

	/*
	 * public static User userFromJson(String json) { return (User)
	 * objectFromJson(json, User.class); }
	 */

	public static Object objectFromJson(String json, Class klass) {
		ObjectMapper mapper = new ObjectMapper();
		Object object;
		try {
			object = mapper.readValue(json, klass);
		} catch (RuntimeException e) {
			log.error("Runtime exception during deserializing "
					+ klass.getSimpleName() + " from "
					+ StringUtils.abbreviate(json, 80));
			throw e;
		} catch (Exception e) {
			log.error("Exception during deserializing " + klass.getSimpleName()
					+ " from " + StringUtils.abbreviate(json, 80));
			return null;
		}
		return object;
	}
	
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) { 
		ObjectMapper mapper = new ObjectMapper();
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}  
	
	public static List readValue(String json,Class clazz){
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = getCollectionType(ArrayList.class, clazz); 
		try {
			List list =  (List)mapper.readValue(json, javaType);
			return list;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	public static String jsonCallback(String callback, Object o) {
		String jsonObject = JsonUtils.jsonFromObject(o);
		return callback + "(" + jsonObject + ")";
	}
}
