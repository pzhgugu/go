package com.ansteel.common.security.service;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ansteel.common.security.domain.Permission;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.common.security.domain.Roles;
import com.ansteel.common.security.domain.RolesResources;
import com.ansteel.common.security.domain.Users;
import com.ansteel.common.security.repository.PermissionRepository;
import com.ansteel.common.security.repository.PermissionURLRepository;
import com.ansteel.common.security.repository.RolesRepository;
import com.ansteel.common.security.repository.RolesResourcesRepository;
import com.ansteel.common.security.repository.UsersRepository;
import com.ansteel.common.springsecurity.service.CustomFilterInvocationSecurityMetadataSource;
import com.ansteel.core.constant.ExceptionConstant;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.PageUtils;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.dhtmlx.xml.GridXml;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-15
 * 修 改 人：
 * 修改日 期：
 * 描   述：权限服务接口实现。  
 */
@Service
@Transactional
public class SecurityServiceBean implements SecurityService {
	
	private static final int DEFAULT_PERMISSION = 10;
	
	@Autowired
	PermissionRepository permissionRepository;
	
	@Autowired
	RolesService rolesService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	RolesResourcesRepository rolesResourcesRepository;
	
	@Autowired
	ResourcesService resourcesService;
	
	@Override
	public String getPermissionStr(Integer permission,
			Map<Integer, String> permissionsMap) {
		StringBuffer sb = new StringBuffer();
		for(Entry<Integer,String> entry:permissionsMap.entrySet()){
			if(Permission.isPermission(permission,entry.getKey())){
				sb.append(entry.getValue());
				sb.append(",");
			}
		}
		return sb.substring(0, sb.length()-1);
	}

	@Override
	public String getPermissionDefaultStr(Resources resources) {
		Map<Integer, String> map = resources.getPermissionsMap();
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Entry o :map.entrySet()){
			sb.append("\"");
			sb.append(o.getValue());
			sb.append("\"");
			sb.append(",");			
		}
		sb.substring(0, sb.length()-1);
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Page resLoadRole(String id, String posStart, String count) {
		String hql = "select id as id, name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select distinct rr.permission from Resources res, RolesResources rr,Roles ro where " +
				"res.id =:id and res.id=rr.resources.id and rr.roles.id=ro.id and r.id=ro.id) as permission  from Roles r";
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", id);
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		Page result = rolesService.findMap(hql, map,"select count(*) from Roles",null,pageable);
		this.setResRoleToResult(result,id);
		return result;
	}
	
	private void setResRoleToResult(Page result,String resId){
		Resources resources = resourcesService.findOne(resId);
		List resultList = (List) result.getContent();
		for(Object o :resultList){
			Map mapResult = (Map) o;
			Integer permission = (Integer) mapResult.get("permission");
			if(permission!=null){
				String pStr = this.getPermissionStr(permission,resources.getPermissionsMap());
				mapResult.put("permissionStr", pStr);
			}
		}
	}

	@Override
	public Page resQueryRole(String id, String where, String whereValue,
			String posStart, String count) {
		String hql = MessageFormat
				.format("select id as id, name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select distinct rr.permission from Resources res, RolesResources rr,Roles ro where " +
				"res.id =:id and res.id=rr.resources.id and rr.roles.id=ro.id and r.id=ro.id) as permission  from Roles r where r.{0} like :queryValue",where);;
		String countHql = MessageFormat
				.format("select count(*) from Roles r where r.{0} like :queryValue ",where);
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", id);
		map.put("queryValue", "%"+whereValue+"%");
		Map<String,Object> countMap =new HashMap<String,Object>();
		countMap.put("queryValue", "%"+whereValue+"%");
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		Page result = rolesService.findMap(hql, map,countHql,countMap,pageable);
		this.setResRoleToResult(result,id);
		return result;
	}


	@Override
	public Page userLoadRole(String id, String posStart, String count) {
		String hql = "select id as id,name as name ,alias as alias,displayOrder as displayOrder,enable as enable,(select 1 from Users o inner join o.roles k where o.id =:id and r.id=k.id) as accredit  from Roles r order by accredit desc";
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", id);	
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		return rolesService.findMap(hql, map,"select count(*) from Roles",null,pageable);
	}

	@Override
	public Page userQueryRole(String id, String where, String whereValue,
			String posStart, String count) {
		String hql = MessageFormat
				.format("select id as id,name as name ,alias as alias,displayOrder as displayOrder," +
				"enable as enable,(select 1 from Users o inner join o.roles k where o.id =:id and r.id=k.id) as accredit  " +
				"from Roles r where r.{0} like :queryValue order by accredit desc",where);
		String countHql = MessageFormat
				.format("select count(*) from Roles r where r.{0} like :queryValue ",where);
		
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("id", id);
		map.put("queryValue", "%"+whereValue+"%");
		Map<String,Object> countMap =new HashMap<String,Object>();
		countMap.put("queryValue", "%"+whereValue+"%");
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		return rolesService.findMap(hql, map,countHql,countMap,pageable);
		
	}

	@Override
	@Transactional(readOnly=false)
	public void updateUserRoles(String userId, List<BaseEntity> baseEntityLsit) {
		Users user=userService.findOne(userId);
		Assert.notNull(user, userId+",主键在用户表中不存在！");
		if(baseEntityLsit.size()>0){
			for(BaseEntity baseEntity:baseEntityLsit){				
				Roles role=(Roles) baseEntity;
				if(role.getAccredit()==1){
					user.getRoles().add(role);
				}else{
					user.getRoles().remove(role);
				}
				rolesService.save(role);
			}
		}
	}

	@Override
	@Transactional(readOnly=false)
	public Data updateUserRolesData(String userId,
			List<BaseEntity> baseEntityLsit) {
		Data data = new Data();
		Users user=userService.findOne(userId);
		Assert.notNull(user, userId+",主键在用户表中不存在！");
		if(baseEntityLsit.size()>0){
			for(BaseEntity baseEntity:baseEntityLsit){				
				Roles role=(Roles) baseEntity;
				if(role.getAccredit()==1){
					user.getRoles().add(role);
				}else{
					user.getRoles().remove(role);
				}
				rolesService.save(role);
				data.addAction(this.getGridXml(baseEntity.getId()));
			}			
		}
		return data;
	}

	@Override
	public Page roleLoadUser(String id, String posStart, String count) {
		String hql = "select id as id,name as name ,alias as alias,displayOrder as displayOrder,enable as enable," +
				"(select 1 from Users o inner join o.roles k where k.id =:id and r.id=o.id) as accredit  " +
				"from Users r order by accredit desc";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		return rolesService.findMap(hql, map,"select count(*) from Users",null,pageable);
		
	}

	@Override
	public Page roleQueryUser(String id, String where, String whereValue,
			String posStart, String count) {
		String hql = MessageFormat
				.format("select id as id,name as name ,alias as alias,displayOrder as displayOrder,enable as enable," +
				"(select 1 from Users o inner join o.roles k where k.id =:id and r.id=o.id) as accredit  " +
				"from Users r where r.{0} like :queryValue order by accredit desc",
						where);
		String countHql = MessageFormat
				.format("select count(*) from Users r where r.{0} like :queryValue ",
						where);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("queryValue", "%" + whereValue + "%");
		Map<String,Object> countMap =new HashMap<String,Object>();
		countMap.put("queryValue", "%"+whereValue+"%");
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		return rolesService.findMap(hql, map,countHql,countMap,pageable);
	}
	
	@Override
	@Transactional(readOnly=false)
	public Data updateRoleUser(String roleId, List<BaseEntity> baseEntityLsit) {
		Data data = new Data();
		Roles role = rolesService.findOne(roleId);
		Assert.notNull(role, roleId + ",主键在用户表中不存在！");
		if (baseEntityLsit.size() > 0) {
			for (BaseEntity baseEntity : baseEntityLsit) {
				Users user = (Users) baseEntity;
				if (user.getAccredit() == 1) {
					user.getRoles().add(role);
				} else {
					user.getRoles().remove(role);
				}
				userService.save(user);
				data.addAction(this.getGridXml(baseEntity.getId()));
			}
		}

		return data;
	}

	private GridXml getGridXml(String id) {
		GridXml gridXml = new GridXml();
		gridXml.setType("update");
		gridXml.setTid(id);
		gridXml.setSid(id);
		return gridXml;
	}

	@Override
	public Data updateResourceRole(String resId, List<BaseEntity> baseEntityLsit) {
		Data data = new Data();
		Resources resource = resourcesService.findOne(resId);
		Assert.notNull(resource, resId + ",主键在资源表中不存在！");
		if (baseEntityLsit.size() > 0) {
			for (BaseEntity baseEntity : baseEntityLsit) {
				Roles role = (Roles) baseEntity;
				String pStr = role.getPermissionStr();
				RolesResources rolesResource =this.getRolesResources(resource,role);
				if(StringUtils.hasText(pStr)){
					int permission=this.getPermissionStrToInt(pStr,resource);
					rolesResource.setPermission(permission);
					
				}else{
					rolesResource.setPermission(null);
				}
				rolesResourcesRepository.save(rolesResource);
				data.addAction(this.getGridXml(baseEntity.getId()));
			}
		}
		this.refresh();
		return data;
	}

	private RolesResources getRolesResources(Resources resource, Roles role) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("resources.id", resource.getId());
		map.put("roles.id", role.getId());
		List<RolesResources> rolesResources=rolesResourcesRepository.find(map);
		if(rolesResources.size()>1){
			throw new PageException("业务有重复,请检查资源："+resource.getId()+",角色"+role.getId(),
					ExceptionConstant.E_ERROR);
		}
		if(rolesResources.size()==1){
			return rolesResources.get(0);
		}
		RolesResources rolesResource = new RolesResources();
		rolesResource.setResources(resource);
		rolesResource.setRoles(role);
		return rolesResource;
	}

	private int getPermissionStrToInt(String pStr,Resources resource) {
		String[] sArray = pStr.split(",");
		Map<String, Integer> pm = resource.getPermissionsMap2();
		int permission=0;
		for(String sp:sArray){
			if(pm.containsKey(sp)){
				int temp = 1;
				Integer p = pm.get(sp);
				temp<<=p;
				permission |=temp;
			}else{
				throw new PageException(sp+"没有此授权认证！",
						ExceptionConstant.E_ERROR);
			}
		}
		return permission;
	}

	@Override
	@Transactional(readOnly=false)
	public void savePermission(Permission entity, String id) {
		if(entity.getId()==null){
			Resources resources = resourcesService.findOne(id);
			Collection<Permission> ps = resources.getPermissions();
			int temp=DEFAULT_PERMISSION;
			for(Permission p:ps){
				if(p.getPermissionBit()>=temp){
					temp=p.getPermissionBit()+1;
				}
			}
			
			entity.setPermissionBit(temp);
			entity.setResources(resources);
			permissionRepository.save(entity);
		}else{
			permissionRepository.save(entity);
		}
	}

	@Override
	public void refresh() {
		new CustomFilterInvocationSecurityMetadataSource().refresh();
	}

	@Override
	public Resources getResources(String resourcesName) {
		return resourcesService.findOneByName(resourcesName);
	}

	

}
