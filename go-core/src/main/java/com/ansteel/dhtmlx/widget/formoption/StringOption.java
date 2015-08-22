package com.ansteel.dhtmlx.widget.formoption;

import java.util.ArrayList;
import java.util.List;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.widget.form.Options;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：字符串下拉解析。  
 */
public class StringOption implements IFormOption {

	@Override
	public List<Options> get(String optionValue) {

		List<Options> optionList = new ArrayList<>();
		if(StringUtils.hasText(optionValue)){
			String[] values = optionValue.split(";");
			for(String v:values){
				String[] vs = v.split("=");
				Options options = new Options();
				if(vs.length==2){
					options.setText(vs[1]);
					options.setValue(vs[0]);
					optionList.add(options);
				}else if(vs.length==1){
					options.setText(vs[0]);
					options.setValue(vs[0]);
					optionList.add(options);
				}
			}
		}
		return optionList;
	}

}
