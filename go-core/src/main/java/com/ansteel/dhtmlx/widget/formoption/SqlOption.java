package com.ansteel.dhtmlx.widget.formoption;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.form.Options;
import com.ansteel.common.sql.service.SqlService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：sql下拉解析。  
 */
public class SqlOption implements IFormOption {
	
	HttpServletRequest request;

	public SqlOption(HttpServletRequest request) {
		this.request=request;
	}

	@Override
	public List<Options> get(String optionValue) {
		if(StringUtils.hasText(optionValue)){
			return this.runDynamicSql(optionValue);
		}
		return new ArrayList<Options>();
	}

	private List<Options> getOptions(String optionValue,List results) {
		List<Options> optionList = new ArrayList<>();
		for(Object o:results){
			Object[] array=(Object[]) o;
			if(array.length==2){
				Options options = new Options();
				options.setText(array[1]+"");
				options.setValue(array[0]+"");
				optionList.add(options);
			}else if(array.length==1){
				Options options = new Options();
				options.setText(array[0]+"");
				options.setValue(array[0]+"");
				optionList.add(options);
			}else{
				throw new PageException(optionValue+",sql字段只能为两个或者一个值,请检查！");
			}
		}
		return optionList;
	}

	/**
	 * 执行动态sql
	 * @param optionValue
	 * @return
	 */
	private List<Options> runDynamicSql(String optionValue) {
		SqlService sqlService = (SqlService)ContextHolder.getSpringBean("sqlServiceBean");
		List list = sqlService.querySqlArray(optionValue, request);
		return this.getOptions(optionValue,list);
	}

}
