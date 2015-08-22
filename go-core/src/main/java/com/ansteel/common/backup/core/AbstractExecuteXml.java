package com.ansteel.common.backup.core;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.BaseService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份虚类，用于增量备份的通用方法。  
 */
public abstract class AbstractExecuteXml {
		
	protected final Log logger = LogFactory.getLog(getClass());
	

	@Autowired
	protected BaseService baseService;
		
	public abstract Class getClazz();

    private Class getCheckClazz(){
        Class clazz=null;
        try {
            clazz=this.getClazz();
        } catch (Exception e) {
            throw new PageException("请构造虚类getClazz()方法");
        }
        if(clazz==null){
            throw new PageException("class不能为空！");
        }
        return clazz;
    }
    
    public  List<BaseEntity> getAllRecode() {
    	Class clzz = getCheckClazz();
    	getBaseService();
    	Assert.notNull(baseService, "baseService服务没有注入成功!");
    	try {
    		return (List<BaseEntity>) baseService.findAll(clzz);
		} catch (Exception e) {
			throw new PageException(clzz+"，没有这个实体！");
		}
        
	}
    
    private void getBaseService(){
    	if(baseService==null){
    		baseService = ContextHolder.getSpringBean("baseServiceBean");
    	}
    }
    
    public  BaseEntity getRecode(Class entityClass,String key){
    	getBaseService();
        return baseService.findOne(entityClass, key);
    }
}
