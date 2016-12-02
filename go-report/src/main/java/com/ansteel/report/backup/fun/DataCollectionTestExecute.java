package com.ansteel.report.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ansteel.report.dc.domain.DataCollection;
import com.ansteel.report.dc.domain.DataCollectionTest;
import com.ansteel.report.dc.domain.DataCollectionTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-06
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表测试执行。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class DataCollectionTestExecute  extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return DataCollectionTest.class;
	}

	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		List<DataCollectionTest> xmlEntityList = (List<DataCollectionTest>) o;
		
		Map<String, DataCollection> parentDataBase=this.getParentDataBase(xmlMap);
		
		this.save(parentDataBase,xmlEntityList);
	}
	
	
	
	
	private Map<String, DataCollection> getParentDataBase(
			Map<Class, Object> xmlMap) {
		Map<String, DataCollection> parentDataBase = new HashMap<>();
		//得到所有父类
		List<DataCollection> xmlParentList=(List<DataCollection>) xmlMap.get(DataCollectionExecute.class);
		//得到所有顶层
		List<DataCollectionTree> xmlTopList=(List<DataCollectionTree>) xmlMap.get(DataCollectionTreeExecute.class);
		Map<DataCollection,DataCollectionTree> xmlTopNameToParent = new HashMap<>();
		for(DataCollection e :xmlParentList){
			String topId = e.getDataCollectionTree().getId();
			for(DataCollectionTree m:xmlTopList){
				if(topId.equals(m.getId())){
					xmlTopNameToParent.put(e,m);
					break;
				}
			}
		}
		//所有顶层数据库实体
		List<DataCollectionTree> dataBaseTopList = baseService.findAll(DataCollectionTree.class);
		Map<String ,Collection<DataCollection>> dataBaseParent = new HashMap<>();
		for(DataCollectionTree entity:dataBaseTopList){
			Collection<DataCollection> eList = entity.getDataCollectionList();
			if(eList.size()>0){
				dataBaseParent.put(entity.getName(), eList);
			}else{
				logger.info(entity.getAlias());
			}
		}
		
		for(Entry<DataCollection, DataCollectionTree> entry:xmlTopNameToParent.entrySet()){
			DataCollection parentE = entry.getKey();
			DataCollectionTree topE = entry.getValue();
			String name = topE.getName();
			if(dataBaseParent.containsKey(name)){
				Collection<DataCollection> dataBaseParentList = dataBaseParent.get(name);
				boolean is = false;
				for(DataCollection e:dataBaseParentList){
					 if(parentE.getName().equals(e.getName())){
						 parentDataBase.put(parentE.getId(),e);
						 is=true;
						 break;
					 }
				 }
				if(!is){
					throw new PageException("数据异常！");
				}
			}else{
				throw new PageException("数据异常！");
			}
			
		}
		return parentDataBase;
	}

	private void save(Map<String, DataCollection> parentDataBase, List<DataCollectionTest> xmlEntityList) {

		for (DataCollectionTest entity : xmlEntityList) {
			DataCollectionTest xmlEntity =new DataCollectionTest();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlParentId = entity.getDataCollection().getId();
			if(parentDataBase.containsKey(xmlParentId)){
				DataCollection parentDataBaseEntity = parentDataBase.get(xmlParentId);
				xmlEntity.setDataCollection(parentDataBaseEntity);
				//确认xml中是否有数据库中的实体
				boolean isSave = true;

				Collection<DataCollectionTest> dataBaseEntityList = parentDataBaseEntity.getDataCollectionTestList();
				String xmlName = xmlEntity.getName();
				
				for (DataCollectionTest dataBaseEntity : dataBaseEntityList) {
					String dataBaseName = dataBaseEntity.getName();
					if(xmlName.equals(dataBaseName)){
						if (dataBaseEntity.getVersionPublish() != xmlEntity.getVersion()) {
							baseService.save(getUpdateEntity(dataBaseEntity, xmlEntity));
						}
						isSave=false;
						break;
					}						
				}
				
				if (isSave) {	
					xmlEntity.setVersionPublish(xmlEntity.getVersion());
					baseService.save(xmlEntity);
				}
			}else{
				throw new PageException(xmlParentId+",不存在,数据异常！");
			}
		}	
	}
	
	private DataCollectionTest getUpdateEntity(DataCollectionTest databaseEntity, DataCollectionTest entity) {
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
