package com.ansteel.dhtmlx.jsonclass;

import org.springframework.data.domain.Page;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射到ui。  
 */

public class RowsUiStrategy  implements UiStrategy  {

	@Override
	public Object get(Object result) {
		Rows rows = new Rows();
		if(result instanceof Page){
			Page page = (Page) result;
			rows.setData(page.getContent());
			rows.setPos(page.getNumber()*page.getSize());
			rows.setTotal_count((int) page.getTotalElements());
			//System.out.println(page.getNumber()+"-"+page.getNumberOfElements()+"-"+page.getSize()+"-"+page.getTotalElements()+"-"+page.getTotalPages());
		}else{
			rows.setData(result);
		}
		return rows;
	}

}
