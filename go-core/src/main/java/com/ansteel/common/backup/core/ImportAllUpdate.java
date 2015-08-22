package com.ansteel.common.backup.core;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.backup.fun.EntityFieldsCategoryExecute;
import com.ansteel.common.backup.fun.EntityFieldsFormExecute;
import com.ansteel.common.backup.fun.ModelsExecute;
import com.ansteel.core.context.ContextHolder;
import com.ansteel.core.utils.StringUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导入所有数据实现。  
 */
@Service
@Transactional
public class ImportAllUpdate extends AbstractImport implements IBackupXmlImport {

	protected final Log logger = LogFactory.getLog(getClass());
	
	 @Override
    public int importXml(Map<Class, Object> xmlMap) {
		for(Entry<Class, Object> entry:xmlMap.entrySet()){
        	String beanId = StringUtils.toLowerCaseFirstOne(entry.getKey().getSimpleName());
        	logger.info("-------"+beanId+"----------");
        	IExecuteXml executeXml=ContextHolder.getSpringBean(beanId);
            executeXml.importSave(xmlMap,entry.getValue(),this.getType());
        }
        return 1;
    }

}
