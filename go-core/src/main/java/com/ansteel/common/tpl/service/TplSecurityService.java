package com.ansteel.common.tpl.service;

import java.util.Collection;
import java.util.Map;

import com.ansteel.common.tpl.domain.TplSecurity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板安全服务类。
 */
public interface TplSecurityService {

	/**
	 * 获取模板安全许可
	 * @param securityList
	 * @return
	 */
	Map<String,Object> getTplSecurityPermission(
			Collection<TplSecurity> securityList);

}
