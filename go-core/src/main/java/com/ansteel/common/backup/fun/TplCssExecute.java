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
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.domain.TplCss;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.StringUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：模板css备份实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class TplCssExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return TplCss.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		//父节点
		Collection<Tpl> dataBaseResList = baseService.findAll(Tpl.class);
		Map<String, Tpl> dataBaseResMap = new HashMap<String, Tpl>();
		for(Tpl entity :dataBaseResList){
			dataBaseResMap.put(entity.getName(), entity);
		}
		Collection<View> dataBaseTwoList = baseService.findAll(View.class);
		Map<String, View> dataBaseTwoMap = new HashMap<String, View>();
		for(View entity :dataBaseTwoList){
			dataBaseTwoMap.put(entity.getName(), entity);
		}

		List<Tpl> xmlParentList=(List<Tpl>) xmlMap.get(TplExecute.class);
		Map<String, Tpl> parentXmlToDataBaseMap = new HashMap<String, Tpl>();
		for(Tpl entity :xmlParentList){
			String name = entity.getName();
			if(dataBaseResMap.containsKey(name)){
				if(StringUtils.hasText(name)){
					parentXmlToDataBaseMap.put(entity.getId(), dataBaseResMap.get(name));
				}
			}
		}
		
		List<View> xmlTwoParentList=(List<View>) xmlMap.get(ViewExecute.class);
		Map<String, View> parentTwoXmlToDataBaseMap = new HashMap<String, View>();
		for(View entity :xmlTwoParentList){
			String name = entity.getName();
			if(dataBaseTwoMap.containsKey(name)){
				if(StringUtils.hasText(name)){
					parentTwoXmlToDataBaseMap.put(entity.getId(), dataBaseTwoMap.get(name));
				}
			}
		}
		//父节点
		

		List<TplCss> xmlEntityList = (List<TplCss>) o;
		Collection<TplCss> dataBaseList = baseService.findAll(TplCss.class);
		for (TplCss entity : xmlEntityList) {
			TplCss xmlEntity =new TplCss();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlName = xmlEntity.getName();
			//此节点只有ID

			Tpl parentXmlToDataBaseEntity=null;
			if(xmlEntity.getTpl()!=null){
				String xmlParentId=entity.getTpl().getId();
				if(StringUtils.hasText(xmlParentId)){
					if(parentXmlToDataBaseMap.containsKey(xmlParentId)){
						parentXmlToDataBaseEntity=parentXmlToDataBaseMap.get(xmlParentId);
						xmlEntity.setTpl(parentXmlToDataBaseEntity);
					}else{
						xmlEntity.setTpl(null);
					}
				}else{
					xmlEntity.setTpl(null);
				}
			}else{
				xmlEntity.setTpl(null);
			}
			
			View parentTwoXmlToDataBaseEntity=null;
			if(xmlEntity.getView()!=null){
				String xmlParentId=xmlEntity.getView().getId();
				if(StringUtils.hasText(xmlParentId)){
					if(parentTwoXmlToDataBaseMap.containsKey(xmlParentId)){
						parentTwoXmlToDataBaseEntity=parentTwoXmlToDataBaseMap.get(xmlParentId);
						xmlEntity.setView(parentTwoXmlToDataBaseEntity);
					}else{
						xmlEntity.setView(null);
					}
				}else{
					xmlEntity.setView(null);
				}
			}else{
				xmlEntity.setView(null);
			}
		
			//确认xml中是否有数据库中的实体
			boolean isSave = true;
			for (TplCss dataBaseEntity : dataBaseList) {
				//数据库节点名称
				String dataBaseName = dataBaseEntity.getName();
				if(dataBaseName.equals(xmlName)){
					//数据库父节点名称
					String dataBaseParentName = dataBaseEntity.getTpl()==null?"":dataBaseEntity.getTpl().getName();
					String parentXmlName = parentXmlToDataBaseEntity==null?"":parentXmlToDataBaseEntity.getName();
					String dataBaseTwoParentName = dataBaseEntity.getView()==null?"":dataBaseEntity.getView().getName();
					String parentTwoXmlName = parentTwoXmlToDataBaseEntity==null?"":parentTwoXmlToDataBaseEntity.getName();
					if(dataBaseParentName.equals(parentXmlName)&&dataBaseTwoParentName.equals(parentTwoXmlName)){
					
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
	
	
	
	private TplCss getUpdateEntity(TplCss databaseEntity, TplCss entity) {
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
