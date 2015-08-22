package com.ansteel.common.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.common.security.domain.PermissionURL;
import com.ansteel.common.security.domain.Resources;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-20
 * 修 改 人：
 * 修改日 期：
 * 描   述：资源认证url备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class PermissionURLExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return PermissionURL.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		
		//资源节点
		Collection<Resources> dataBaseResList = baseService.findAll(Resources.class);
		Map<String, Resources> dataBaseResMap = new HashMap<String, Resources>();
		for(Resources entity :dataBaseResList){
			dataBaseResMap.put(entity.getName(), entity);
		}

		List<Resources> xmlParentList=(List<Resources>) xmlMap.get(ResourcesExecute.class);
		Map<String, Resources> parentXmlToDataBaseMap = new HashMap<String, Resources>();
		for(Resources entity :xmlParentList){
			String name = entity.getName();
			if(dataBaseResMap.containsKey(name)){
				if(StringUtils.hasText(entity.getId())){
					parentXmlToDataBaseMap.put(entity.getId(), dataBaseResMap.get(name));
				}
			}
		}
		//资源节点
				
				
		List<PermissionURL> xmlEntityList = (List<PermissionURL>) o;
		Collection<PermissionURL> dataBaseList = baseService.findAll(PermissionURL.class);
		for (PermissionURL entity : xmlEntityList) {
			PermissionURL xmlEntity =new PermissionURL();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlName = xmlEntity.getPath();
			//此节点只有ID
			String xmlParentId=entity.getResources().getId();
			Resources parentXmlToDataBaseEntity=null;
			if(StringUtils.hasText(xmlParentId)){
				if(parentXmlToDataBaseMap.containsKey(xmlParentId)){
					parentXmlToDataBaseEntity=parentXmlToDataBaseMap.get(xmlParentId);
					xmlEntity.setResources(parentXmlToDataBaseMap.get(xmlParentId));
				}else{
					xmlEntity.setResources(null);
				}
			}else{
				xmlEntity.setResources(null);
			}
			
			//确认xml中是否有数据库中的实体
			boolean isSave = true;
			for (PermissionURL dataBaseEntity : dataBaseList) {
				//数据库节点名称
				String dataBaseName = dataBaseEntity.getPath();
				if(dataBaseName.equals(xmlName)){
					//数据库父节点名称
					String dataBaseParentName = dataBaseEntity.getResources()==null?"":dataBaseEntity.getResources().getName();
					String parentXmlName = parentXmlToDataBaseEntity==null?"":parentXmlToDataBaseEntity.getName();
					if(dataBaseParentName.equals(parentXmlName)){					
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							baseService.save(getUpdateEntity(dataBaseEntity, xmlEntity));
						}
						isSave=false;
						break;
					}
				}
			}
			
			
			if (isSave) {	
				xmlEntity.setVersionPublish(xmlEntity.getVersion());
				baseService.save(xmlEntity);
			}
		}
	}

	private PermissionURL getUpdateEntity(PermissionURL databaseEntity, PermissionURL entity) {
		String oldId = databaseEntity.getId();
		Long version = databaseEntity.getVersion();
		try {
			BeanUtils.copyProperties(databaseEntity, entity);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		databaseEntity.setId(oldId);
		databaseEntity.setVersion(version);
		databaseEntity.setVersionPublish(entity.getVersion());
		return databaseEntity;
	}

}
