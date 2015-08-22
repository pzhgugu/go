package com.ansteel.report.backup.fun;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.core.AbstractExecuteXml;
import com.ansteel.common.backup.core.IExecuteXml;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.BeanUtils;
import com.ansteel.report.excel.domain.ExcelReport;
import com.ansteel.report.excel.domain.ExcelReportSQL;
import com.ansteel.report.excel.domain.ExcelReportTree;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-05
 * 修 改 人：
 * 修改日 期：
 * 描   述：Excel报表SQL查询语句执行。 
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW)
public class ExcelReportSQLExecute extends AbstractExecuteXml implements IExecuteXml{

	@Override
	public Class getClazz() {
		return ExcelReportSQL.class;
	}


	@Override
	public void importSave(Map<Class, Object> xmlMap, Object o, int type) {
		
		List<ExcelReportSQL> xmlEntityList = (List<ExcelReportSQL>) o;
		
		Map<String, ExcelReport> parentDataBase=this.getParentDataBase(xmlMap);
		
		this.save(parentDataBase,xmlEntityList);
	}
	
	
	
	private Map<String, ExcelReport> getParentDataBase(
			Map<Class, Object> xmlMap) {
		Map<String, ExcelReport> parentDataBase = new HashMap<>();
		//得到所有父类
		List<ExcelReport> xmlParentList=(List<ExcelReport>) xmlMap.get(ExcelReportExecute.class);
		//得到所有顶层
		List<ExcelReportTree> xmlTopList=(List<ExcelReportTree>) xmlMap.get(ExcelReportTreeExecute.class);
		Map<ExcelReport,ExcelReportTree> xmlTopNameToParent = new HashMap<>();
		for(ExcelReport e :xmlParentList){
			String topId = e.getExcelReportTree().getId();
			for(ExcelReportTree m:xmlTopList){
				if(topId.equals(m.getId())){
					xmlTopNameToParent.put(e,m);
					break;
				}
			}
		}
		//所有顶层数据库实体
		List<ExcelReportTree> dataBaseTopList = baseService.findAll(ExcelReportTree.class);
		Map<String ,Collection<ExcelReport>> dataBaseParent = new HashMap<>();
		for(ExcelReportTree entity:dataBaseTopList){
			Collection<ExcelReport> eList = entity.getExcelReportList();
			if(eList.size()>0){
				dataBaseParent.put(entity.getName(), eList);
			}else{
				logger.info(entity.getAlias());
			}
		}
		
		for(Entry<ExcelReport, ExcelReportTree> entry:xmlTopNameToParent.entrySet()){
			ExcelReport parentE = entry.getKey();
			ExcelReportTree topE = entry.getValue();
			String name = topE.getName();
			if(dataBaseParent.containsKey(name)){
				Collection<ExcelReport> dataBaseParentList = dataBaseParent.get(name);
				boolean is = false;
				for(ExcelReport e:dataBaseParentList){
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

	private void save(Map<String, ExcelReport> parentDataBase, List<ExcelReportSQL> xmlEntityList) {

		for (ExcelReportSQL entity : xmlEntityList) {
			ExcelReportSQL xmlEntity =new ExcelReportSQL();
			try {
				BeanUtils.copyProperties(xmlEntity, entity);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String xmlParentId = entity.getExcelReport().getId();
			if(parentDataBase.containsKey(xmlParentId)){
				ExcelReport parentDataBaseEntity = parentDataBase.get(xmlParentId);
				xmlEntity.setExcelReport(parentDataBaseEntity);
				//确认xml中是否有数据库中的实体
				boolean isSave = true;

				Collection<ExcelReportSQL> dataBaseEntityList = parentDataBaseEntity.getExcelReportSQLList();
				String xmlName = xmlEntity.getName();
				
				for (ExcelReportSQL dataBaseEntity : dataBaseEntityList) {						
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


	
	private ExcelReportSQL getUpdateEntity(ExcelReportSQL databaseEntity, ExcelReportSQL entity) {
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
