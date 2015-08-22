package com.ansteel.dhtmlx.jsonclass;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-08
 * 修 改 人：
 * 修改日 期：
 * 描   述：json数据映射到树ui。  
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ansteel.core.constant.ExceptionConstant;
import com.ansteel.core.domain.TreeUI;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;

public class TreeUiStrategy implements UiStrategy {

	@Override
	public Object get(Object result) {
		if(result==null){
			return getEmptyTree();
		}
		if(result instanceof List){
			try {
				List<TreeUI> r=(List<TreeUI>) result;
				if(r.size()>0){
					return getTree(r);
				}else{
					return getEmptyTree();
				}
			} catch (Exception e) {
				return result;
			}
			
		}
		return result;
	}

	private Object getTree(List<TreeUI> result) {
		Tree tree=new Tree();
		TreeUI ui = result.get(0);
		String parent = ui.getParent();
		if(StringUtils.hasText(parent)){
			tree.setId( parent);
		}else{
			tree.setId("0");
		}
		tree.setItem(result);
		return tree;
	}

	

	private Tree getEmptyTree(){
		Tree tree=new Tree();
		tree.setId("0");
		tree.setItem(new ArrayList());
		return tree;
	}
}
