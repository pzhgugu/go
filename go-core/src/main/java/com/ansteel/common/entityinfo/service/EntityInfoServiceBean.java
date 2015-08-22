package com.ansteel.common.entityinfo.service;

import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.exception.PageException;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体信息服务接口实现。  
 */
@Service
public class EntityInfoServiceBean implements EntityInfoService {
	
	private static final String GET_ENTITY_INFOS = "getEntityInfos";
	
	public Object newInstance(Class<?> clazz) {
		Object object = null;
		try {
			object = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new PageException(e.toString());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new PageException(e.toString());
		}
		return object;
	}

	@Override
	public Collection<EntityInfo> getEntityInfoList(Class clazz) {
		Method method = ReflectionUtils.findMethod(clazz, GET_ENTITY_INFOS);
		Object object = newInstance(clazz);
		return (Collection<EntityInfo>) ReflectionUtils.invokeMethod(method, object);
	}

	@Override
	public EntityInfo getEntityInfo(Class clazz, String objectName) {
		Collection<EntityInfo> eis = this.getEntityInfoList(clazz);
		for(EntityInfo ei :eis){
			if(ei.getClazz().getSimpleName().equalsIgnoreCase(objectName)){
				return ei;
			}
		}
		return null;
	}
	
	@Override
	public Class getBaseEntityClass(Class clazz, String objectName) {
		EntityInfo entityInfo = this.getEntityInfo(clazz, objectName);
		if(entityInfo==null){
			throw new PageException(objectName+"实体在EntityInfo中没有找到！");
		}
		return entityInfo.getClazz();
	}

	@Override
	public BaseEntity getBaseEntity(Class clazz, String objectName) {
		Class objectClazz = this.getBaseEntityClass(clazz, objectName);
		return (BaseEntity) newInstance(objectClazz);
	}

}
