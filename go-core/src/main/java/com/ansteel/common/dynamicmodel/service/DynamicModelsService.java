package com.ansteel.common.dynamicmodel.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsForm;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsQuery;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.utils.QueryMapping;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模服务接口。  
 */
public interface DynamicModelsService {
	
	public static final int SQL_TYPE =3;

	//DynamicModels getDynamicModels(String name, int type);

	DynamicModels getDynamicModelsById(String id, int sqlType);

	DynamicModels getDynamicModels(String name);

	<T extends BaseEntity> void delect(Collection<T> formList);

	void delect(BaseEntity entity);

	boolean dynamicFieldsExist(DynamicFields dynamicFields);

	void setModelInfo(DynamicFields dynamicFields, DynamicModels dynamicModels);

	void createTable(String id);

	void createTable();
	/**
	 * 保存动态模型
	 * @param modelType
	 * @param valueMap
	 */
	boolean save(String modelName, Map<String, String> valueMap);

	void delect(String modelName, String id);

	Page loadAll(String modelName, String posStart, String count, String order);

	Page find(String modelName, String posStart, String count,
			String order, List<QueryMapping> queryList);

	boolean save(String modelName, HttpServletRequest request);

	List find(String modelName, String order, List<QueryMapping> queryList);

	void save(String modelName, List<Map<String, String>> valueList);

	void save(DynamicFields dynamicFields);

	DynamicFields findOne(String id);

	Collection<DynamicFieldsForm> findDynamicFieldsForm(Map<String, Object> map);

	Collection<DynamicFieldsGrid> findDynamicFieldsGrid(Map<String, Object> map);

	Collection<DynamicFieldsQuery> findDynamicFieldsQuery(
			Map<String, Object> map);

	void clean(String modelName);
}
