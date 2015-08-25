package com.ansteel.core.utils;

public class FisUtils {
	
	public static String SPLIT=":";

	public static String page(String id){
		//cms:index
		if(id.indexOf(SPLIT)>-1){

			String[] iArray = id.split(SPLIT);
			if(iArray.length!=2){
				return id;
			}
			StringBuffer sb=new StringBuffer();
			sb.append("WEB-INF/");
			sb.append(iArray[0]);
			sb.append("/");
			sb.append(iArray[1]);
			return sb.toString();
		}
		return id;
	}
	
	public static String path(String id){
		//cms:index
		if(id.indexOf(SPLIT)>-1){

			String[] iArray = id.split(SPLIT);
			if(iArray.length!=2){
				return id;
			}
			StringBuffer sb=new StringBuffer("/");
			sb.append("WEB-INF/");
			sb.append(iArray[0]);
			sb.append("/");
			sb.append(iArray[1]);
			return sb.toString();
		}
		return id;
	}
}
