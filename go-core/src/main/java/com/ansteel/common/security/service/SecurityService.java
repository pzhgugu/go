package com.ansteel.common.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.ansteel.common.security.domain.Permission;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.dhtmlx.xml.Data;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：权限服务接口。  
 */
public interface SecurityService {
	/**
	 * 获取认证对应权限的字符串
	 * @param permission
	 * @param permissionsMap
	 * @return
	 */
	String getPermissionStr(Integer permission,
			Map<Integer, String> permissionsMap);
	/**
	 * 获取认证默认字符串
	 * @param resources
	 * @return
	 */
	String getPermissionDefaultStr(Resources resources);
	/**
	 * 通过资源id获取角色信息（包括授权）
	 * @param id
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page resLoadRole(String id,String posStart,String count);
	/**
	 * 通过用户id获取角色信息（包含授权）
	 * @param id
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page userLoadRole(String id,String posStart,String count);
	/**
	 * 查询id用户下某个角色
	 * @param id
	 * @param where
	 * @param whereValue
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page userQueryRole(String id,String where,String whereValue,String posStart,String count);
	/**
	 * 用户关联角色
	 * @param userId
	 * @param baseEntityLsit
	 */
	void updateUserRoles(String userId,List<BaseEntity> baseEntityLsit);
	/**
	 * 用户关联角色(表格编辑返回xml所需信息)
	 * @param userId
	 * @param baseEntityLsit
	 */
	Data updateUserRolesData(String userId,List<BaseEntity> baseEntityLsit);
	/**
	 * 通过角色id获取用户(包括授权)
	 * @param id
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page roleLoadUser(String id,String posStart,String count);
	/**
	 * 查询id角色下某个用户
	 * @param id
	 * @param where
	 * @param whereValue
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page roleQueryUser(String id,String where,String whereValue,String posStart,String count);
	/**
	 * 角色关联用户(表格编辑返回xml所需信息)
	 * @param roleId
	 * @param baseEntityLsit
	 * @return
	 */
	Data updateRoleUser(String roleId,List<BaseEntity> baseEntityLsit);
	/**
	 * 资源关联角色(表格编辑返回xml所需信息)
	 * @param resId 资源id
	 * @param baseEntityLsit
	 * @return
	 */
	Data updateResourceRole(String resId, List<BaseEntity> baseEntityLsit);
	/**
	 * 查询资源id下某个角色
	 * @param value
	 * @param where
	 * @param whereValue
	 * @param posStart
	 * @param count
	 * @return
	 */
	Page resQueryRole(String value, String where, String whereValue,
			String posStart, String count);
	/**
	 * 保存验证类型，自动加验证编码（从10开始累加）
	 * @param entity
	 * @param value
	 */
	void savePermission(Permission entity, String value);
	
	/**
	 * 刷新权限
	 */
	void refresh();

	Resources getResources(String resourcesName);
}
