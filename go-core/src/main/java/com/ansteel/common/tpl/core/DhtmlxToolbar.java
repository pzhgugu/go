package com.ansteel.common.tpl.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-18
 * 修 改 人：
 * 修改日 期：
 * 描   述：Dhtmlx工具条。
 */
@Service(ServiceConstants.DTHMLX_TOOLBAR)
public class DhtmlxToolbar implements IDhtmlxWidget {

	@Override
	public Map<String, Object> structure(ViewElement viewElement) {
		Map<String, Object> viewModel=new HashMap<String, Object>();
		return viewModel;
	}

}
