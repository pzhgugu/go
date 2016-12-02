package com.ansteel.core.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.ansteel.core.constant.Constants;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-25
 * 修 改 人：
 * 修改日 期：
 * 描   述： 一个工具类，作为各种查询条件的工厂。  
 */
public class PageUtils {
	
	/*public static int getFirstResult(String posStart){
		int firstResult=0;
		if(StringUtils.hasText(posStart)){
			firstResult = Integer.valueOf(posStart).intValue();
		}
		return firstResult;
	}*/
	
	public static int getMaxResults(){	
		return Constants.DEFAULT_MAX_RESULTS;
	}

	public static int getTotalPages(String posStart) {
		return getTotalPages(posStart,Constants.DEFAULT_MAX_RESULTS);
	}

	public static int getTotalPages(String posStart,int maxResults) {
		int totalPages=0;
		if(StringUtils.hasText(posStart)){
			totalPages = Integer.valueOf(posStart).intValue()/maxResults;
		}
		return totalPages;
	}
	
	public static Pageable getPageable(String order,String posStart,int maxResults){
		Pageable pageable = null;
		if (StringUtils.hasText(order)) {
			Sort sort = new Sort(order);
			pageable = new PageRequest(PageUtils.getTotalPages(posStart),
					maxResults, sort);
		} else {
			pageable = new PageRequest(PageUtils.getTotalPages(posStart),
					maxResults);
		}
		return pageable;
	}
	
	public static Pageable getPageable(String order,String posStart){
		return getPageable(order, posStart, PageUtils.getMaxResults());
	}

}
