package com.ansteel.common.dynamicmodel.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.ansteel.common.dynamicmodel.core.ITableSchema;
import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsCategory;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsForm;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsGrid;
import com.ansteel.common.dynamicmodel.domain.DynamicFieldsQuery;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsCategoryRepository;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsFormRepository;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsGridRepository;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsQueryRepository;
import com.ansteel.common.dynamicmodel.repository.DynamicFieldsRepository;
import com.ansteel.common.dynamicmodel.repository.DynamicModelsRepository;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.context.DefaultEditors;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.query.QueryOperator;
import com.ansteel.core.query.QueryOrWhere;
import com.ansteel.core.query.SqlQueryBuilder;
import com.ansteel.core.query.SqlQuerySettings;
import com.ansteel.core.service.SqlBaseService;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.RequestUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class DynamicModelsServiceBean implements DynamicModelsService {
	
	@Autowired
	SqlBaseService sqlBaseService;
	

	private static final int DYNAMIC_TYPE = 1;
	
	@Autowired
	ITableSchema tableSchema;
	
	@Autowired
	DynamicModelsRepository dynamicModelsRepository;
	
	@Autowired
	DynamicFieldsCategoryRepository dynamicFieldsCategoryRepository;
	
	@Autowired
	DynamicFieldsFormRepository dynamicFieldsFormRepository;
	
	@Autowired
	DynamicFieldsGridRepository dynamicFieldsGridRepository;
	
	@Autowired
	DynamicFieldsQueryRepository dynamicFieldsQueryRepository;
	
	@Autowired
	DynamicFieldsRepository dynamicFieldsRepository;

	/*@Override
	public DynamicModels getDynamicModels(String name, int type) {
		Map<String ,Object> props = new HashMap<String ,Object>();
		props.put("name", name);
		props.put("modelType", type);
		List<DynamicModels> entityList =  dynamicModelsRepository.find(props);
		if(entityList.size()>0){
			if(entityList.size()==1){
				return entityList.get(0);
			}else{
				throw new PageException("有重复数据，数据【"+name+"】");
			}
		}
		return null;
	}
*/
	@Override
	public DynamicModels getDynamicModelsById(String id, int type) {
		Map<String ,Object> props = new HashMap<String ,Object>();
		props.put("id", id);
		props.put("modelType", type);
		List<DynamicModels> entityList =  dynamicModelsRepository.find(props);
		if(entityList.size()>0){
			return entityList.get(0);
		}
		return null;
	}

	@Override
	public DynamicModels getDynamicModels(String name) {
		return dynamicModelsRepository.findOneByName(name);
	}

	@Override
	@Transactional(readOnly=false)
	public <T extends BaseEntity> void delect(Collection<T> list) {
		for(BaseEntity entity:list){
			this.delect(entity);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void delect(BaseEntity entity) {		
		Class clazz = entity.getClass();
		if(clazz==DynamicModels.class){
			DynamicModels o = (DynamicModels) entity;
			dynamicModelsRepository.delete(o);
		}else if(clazz==DynamicFieldsCategory.class){
			DynamicFieldsCategory o = (DynamicFieldsCategory) entity;
			dynamicFieldsCategoryRepository.delete(o);
		}else if(clazz==DynamicFieldsForm.class){
			DynamicFieldsForm o = (DynamicFieldsForm) entity;
			dynamicFieldsFormRepository.delete(o);
		}else if(clazz==DynamicFieldsGrid.class){
			DynamicFieldsGrid o = (DynamicFieldsGrid) entity;
			dynamicFieldsGridRepository.delete(o);
		}else if(clazz==DynamicFieldsQuery.class){
			DynamicFieldsQuery o = (DynamicFieldsQuery) entity;
			dynamicFieldsQueryRepository.delete(o);
		}else if(clazz==DynamicFields.class){
			DynamicFields o = (DynamicFields) entity;
			dynamicFieldsRepository.delete(o);
		}
	}

	@Override
	public boolean dynamicFieldsExist(DynamicFields dynamicFields) {
		/*DynamicFields dy=null;
		if(StringUtils.hasText(dynamicFields.getId())){
			String queryString = "select a from "+DynamicFields.class.getName() +" a ,"+DynamicModels.class.getName()+" b where a.name=:name and a.models.id=b.id and a.id<>:id";
			Map<String ,Object> map = new HashMap<>();
			DynamicModels models = dynamicFields.getModels();
			if(models==null){
				queryString+=" and a.models.id is null ";
			}else{
				queryString+=" and a.models.id=:pid";
				map.put("pid", models.getId());
			}
			map.put("name", dynamicFields.getName());
			map.put("id", dynamicFields.getId());
			List<BaseEntity> list = dynamicFieldsRepository.find(queryString, map);
			if(list.size()>0){
				return true;
			}
		}else{
			String queryString = "select a from "+DynamicFields.class.getName() +" a ,"+DynamicModels.class.getName()+" b where a.name=:name and a.models.id=b.id";
			Map<String ,Object> map = new HashMap<>();
			map.put("name", dynamicFields.getName());
			DynamicModels models = dynamicFields.getModels();
			if(models==null){
				queryString+=" and a.models.id is null ";
			}else{
				queryString+=" and a.models.id=:pid";
				map.put("pid", models.getId());
			}
			List<BaseEntity> list = dynamicFieldsRepository.find(queryString, map);
			if(list.size()>0){
				return true;
			}
		}*/
		
		final String name=dynamicFields.getName();
		final String id=dynamicFields.getId();
		final DynamicModels models = dynamicFields.getModels();
		Specification<DynamicFields> spec = new Specification<DynamicFields>() {  			
		    public Predicate toPredicate(Root<DynamicFields> root,  
		            CriteriaQuery<?> query, CriteriaBuilder cb) {
		    	Join modelsJoin = root.join("models" , JoinType.LEFT); 
		    	
		    	Predicate pName=cb.equal(root.get("name"),name);
		    	Predicate pId;
		    	if(StringUtils.hasText(id)){
		    		pId=cb.notEqual(root.get("id"),id);
		    	}else{
		    		pId=cb.equal(root.get("id"),modelsJoin.get("id"));
		    	}
		    	if(models!=null){
		    		query.where(cb.and(pName,pId,cb.equal(root.get("models").get("id"), models.getId())));
		    	}else{
		    		query.where(cb.and(pName,pId,cb.isNull(root.get("models"))));
		    	}
		    	return query.getRestriction(); 		    	
		    }
		};			
		List<DynamicFields> list = dynamicFieldsRepository.find(spec);
		
		if(list.size()>0){
			return true;
		}
		
		return false;
	}

	@Override
	public void setModelInfo(DynamicFields dynamicFields,
			DynamicModels dynamicModels) {
		Collection<DynamicFieldsCategory> fieldsCategoryList = dynamicModels.getFieldsCategory();
		if(!(fieldsCategoryList.size()>0)){
			fieldsCategoryList.add(getDefaultFieldsCategory(dynamicModels));
		}
		for(DynamicFieldsCategory dynamicFieldsCategory:fieldsCategoryList){
			dynamicFieldsCategory.getFieldsForm().add(getDefaultFieldsForm(dynamicFields,dynamicFieldsCategory));
			dynamicFieldsCategory.getFieldsGrid().add(getDefaultFieldsGrid(dynamicFields,dynamicFieldsCategory));
			dynamicFieldsCategory.getFieldsQuery().add(getDefaultFieldsQuery(dynamicFields, dynamicFieldsCategory));
		}
	}

	private DynamicFieldsCategory getDefaultFieldsCategory(
			DynamicModels dynamicModels) {
		DynamicFieldsCategory fieldsCategory = new DynamicFieldsCategory();
		fieldsCategory.setName(Public.DEFAULE_CATEGORY_NAME);
		fieldsCategory.setAlias(Public.DEFAULE_CATEGORY_NAME);
		fieldsCategory.setModels(dynamicModels);
		return fieldsCategory;
	}
	
	private DynamicFieldsForm getDefaultFieldsForm(DynamicFields dynamicFields,DynamicFieldsCategory fieldsCategory) {
		DynamicFieldsForm fieldsForm=new DynamicFieldsForm();
		fieldsForm.setName(dynamicFields.getName());
		fieldsForm.setDisplayOrder(dynamicFields.getDisplayOrder());
		fieldsForm.setType(DHtmlxConstants.INPUT);
		fieldsForm.setIsShow(1);
		fieldsForm.setFieldsCategory(fieldsCategory);
		return fieldsForm;
	}
	
	private DynamicFieldsGrid getDefaultFieldsGrid(DynamicFields dynamicFields,DynamicFieldsCategory fieldsCategory) {
		DynamicFieldsGrid fieldsGrid=new DynamicFieldsGrid();
		fieldsGrid.setName(dynamicFields.getName());
		fieldsGrid.setDisplayOrder(dynamicFields.getDisplayOrder());
		fieldsGrid.setFieldsCategory(fieldsCategory);
		fieldsGrid.setColAlign(Public.DEFAULE_MODELGRID_ALIGN);
		fieldsGrid.setInitWidths(Public.DEFAULE_MODELGRID_WIDTHS);
		fieldsGrid.setColSorting(Public.DEFAULE_MODELGRID_SORTING);
		fieldsGrid.setColTypes(Public.DEFAULE_MODELGRID_TYPES);		
		fieldsGrid.setIsShow(1);
		return fieldsGrid;
	}
	
	private DynamicFieldsQuery getDefaultFieldsQuery(DynamicFields dynamicFields,DynamicFieldsCategory fieldsCategory) {
		DynamicFieldsQuery fieldsQuery=new DynamicFieldsQuery();
		fieldsQuery.setName(dynamicFields.getName());
		fieldsQuery.setDisplayOrder(dynamicFields.getDisplayOrder());
		fieldsQuery.setFieldsCategory(fieldsCategory);
		fieldsQuery.setIsLock(0);
		fieldsQuery.setIsShow(1);
		return fieldsQuery;
	}

	@Override
	public void createTable(String id) {
		DynamicModels dynamicModels = getDynamicModelsById(id,DYNAMIC_TYPE);
		Assert.notNull(dynamicModels, id + ",模型没有找到,请检查模型类型是否为动态模型！");
		tableSchema.updateTable(dynamicModels.getName(), dynamicModels.getFields());
	}

	@Override
	public void createTable() {
		Collection<DynamicModels> list = dynamicModelsRepository.findAll();
		for(DynamicModels model:list){
			tableSchema.updateTable(model.getName(),model.getFields());
		}		
	}

	@Override
	public boolean save(String modelName, Map<String, String> requestMap) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		Collection<DynamicFields> list = model.getFields();
		Map<String,Object> valueMap = new HashMap<>();
		if(requestMap.containsKey("id")){
			valueMap.put("id", requestMap.get("id"));
		}
		for(DynamicFields field:list){
			String name = field.getName();
			if(requestMap.containsKey(name)){
				String value = requestMap.get(name);
				if(StringUtils.hasText(value)){
					Object v = new DefaultEditors().getValue(getTypeClass(field.getFieldType()),value);
					if(v==null){
						return false;
					}
					valueMap.put(name, v);	
				}
				
			}
		}
		tableSchema.save(model.getName(), list, valueMap);
		return true;
	}

	@Override
	public boolean save(String modelName, HttpServletRequest request) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		Collection<DynamicFields> list = model.getFields();
		Map<String,Object> valueMap = new HashMap<>();

		Map<String,String> requestMap = RequestUtils.getRequestMap(request);
		if(requestMap.containsKey("id")){
			valueMap.put("id", requestMap.get("id"));
		}
		for(DynamicFields field:list){
			String name = field.getName();
			if(requestMap.containsKey(name)){
				String value = requestMap.get(name);
				if(StringUtils.hasText(value)){
					try {
						Object v = new DefaultEditors().getValue(getTypeClass(field.getFieldType()), value);
						if (v == null) {
							return false;
						}
						valueMap.put(name, v);
					}catch (Exception e){
						for(DynamicFields d:list){
							if(d.getName().equals(name)){
								throw new PageException("["+d.getAlias()+"]输入类型错误，请重新输入！");
							}
						}
					}
				}
				
			}
		}
		tableSchema.save(model.getName(),list,valueMap);
		return true;
	}

	@Override
	public void delect(String modelName, String id) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		tableSchema.delect(model.getName(), model.getFields(), id);
	}

	@Override
	public Page loadAll(String modelName, String posStart, String count,
			String order) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		String tableName=tableSchema.getTableName(model.getName());
		SqlQuerySettings querySettings = SqlQuerySettings.create(tableName);
		return getResult(querySettings, order, posStart, count);	
	}
	
	private Page getResult(SqlQuerySettings querySettings,String order,String posStart,String count){
		this.setQuerySettingsAsc(querySettings,order);
		SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
		QueryOrWhere queryOrWhere = sqlQueryBuilder.getQueryOrWhere(querySettings);
		String countHql = sqlQueryBuilder.getCountQuerySyntax(queryOrWhere.getSyntax());
		Integer totalCount = findByCount(countHql, queryOrWhere.getWhereValueMap());
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		List resultList = find(queryOrWhere.getSyntax(), queryOrWhere.getWhereValueMap(),pageable);
		if(resultList.size()>0){
			Map<String,Object>map =(Map<String, Object>) resultList.get(0);
			if(!map.containsKey("id")){
				for(Object o:resultList){
					Map<String,Object> oMap=(Map<String, Object>) o;
					oMap.put("id", oMap.get("ID"));
				}
			}
		}
		
		return new PageImpl(resultList, pageable, totalCount);	
	}

	private void setQuerySettingsAsc(SqlQuerySettings querySettings,
			String order) {
		if(StringUtils.hasText(order)){
			querySettings.asc(order);	
		}else{
			querySettings.asc("CREATED");
		}		
	}

	private List find(String queryString, Map<String, Object> paramsValueName,
			Pageable pageable) {
		// 返回其执行了分布方法的list
		return sqlBaseService.findSqlMap(queryString,paramsValueName,pageable);
	}

	private Integer findByCount(String countHql,
			Map<String, Object> whereMap) {
		return (int) sqlBaseService.sqlCount(countHql,whereMap);
	}

	@Override
	public Page find(String modelName, String posStart, String count,
			String order, List<QueryMapping> queryList) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		String tableName=tableSchema.getTableName(model.getName());
		SqlQuerySettings querySettings = SqlQuerySettings.create(tableName);
		for(QueryMapping qm:queryList){
			if(Public.QUERY_BETWEEN.endsWith(qm.getOperator())){
				Object begin = this.getWhereValue(model.getFields(),qm.getName(),qm.getBegin());
				Object end = this.getWhereValue(model.getFields(),qm.getName(),qm.getEnd());
				QueryOperator.getQuerySettings(querySettings, qm.getName(), qm.getOperator(), begin,end);
			}else if(StringUtils.hasText(qm.getValue())){
				Object wValue = this.getWhereValue(model.getFields(),qm.getName(),qm.getValue());
				QueryOperator.getQuerySettings(querySettings, qm.getName(), qm.getOperator(), wValue);
			} 
		}
		return getResult(querySettings, order, posStart, count);	
	}
	
	@Override
	public List find(String modelName,String order, List<QueryMapping> queryList) {
		DynamicModels model=dynamicModelsRepository.findOneByName( modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		String tableName=tableSchema.getTableName(model.getName());
		SqlQuerySettings querySettings = SqlQuerySettings.create(tableName);
		for(QueryMapping qm:queryList){
			if(Public.QUERY_BETWEEN.endsWith(qm.getOperator())){
				Object begin = this.getWhereValue(model.getFields(),qm.getName(),qm.getBegin());
				Object end = this.getWhereValue(model.getFields(),qm.getName(),qm.getEnd());
				QueryOperator.getQuerySettings(querySettings, qm.getName(), qm.getOperator(), begin,end);
			}else if(StringUtils.hasText(qm.getValue())){
				Object wValue = this.getWhereValue(model.getFields(),qm.getName(),qm.getValue());
				QueryOperator.getQuerySettings(querySettings, qm.getName(), qm.getOperator(), wValue);
			} 
		}
		this.setQuerySettingsAsc(querySettings,order);
		SqlQueryBuilder sqlQueryBuilder = new SqlQueryBuilder();
		QueryOrWhere queryOrWhere = sqlQueryBuilder.getQueryOrWhere(querySettings);
		
		return sqlBaseService.findSqlMap(queryOrWhere.getSyntax(), queryOrWhere.getWhereValueMap());
	}

	private Object getWhereValue(Collection<DynamicFields> fields, String name,
			String value) {
		for(DynamicFields df:fields){
			if(df.getName().equals(name)){
				String type = df.getFieldType();
				Object wValue = new DefaultEditors().getValue(getTypeClass(df.getFieldType()), value);
				if(wValue==null){
					throw new PageException("输入类型错误！");
				}
				return wValue;
			}
		}
		return null;
	}

	private static HashMap<String, Class> defaultType;
	
	public Class getTypeClass(String type) {
		if(defaultType==null){
			defaultType = new HashMap<String,Class>();
			defaultType.put("integer", Integer.class);
			defaultType.put("long", Long.class);
			defaultType.put("short", Short.class);
			defaultType.put("float", Float.class);
			defaultType.put("double", Double.class);
			defaultType.put("string", String.class);
			defaultType.put("date", Date.class);
			defaultType.put("datetime", Date.class);
			defaultType.put("clob", String.class);
			defaultType.put("boolean", Boolean.class);
		}
		if(defaultType.containsKey(type)){
			return defaultType.get(type);			
		}
		return String.class;
	}

	@Override
	public void save(String modelName, List<Map<String, String>> valueList) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		Collection<DynamicFields> list = model.getFields();
		for(Map<String, String> map:valueList){
			Map<String,Object> valueMap = new HashMap<>();
			for(DynamicFields field:list){
				String name = field.getName();
				if(map.containsKey(name)){
					String value = map.get(name);
					if(StringUtils.hasText(value)){
						Object v = new DefaultEditors().getValue(getTypeClass(field.getFieldType()),value);
						valueMap.put(name, v);	
					}
					
				}
			}
			tableSchema.save(model.getName(),list,valueMap);
		}
	}

	@Override
	@Transactional
	public void save(DynamicFields entity) {
		dynamicFieldsRepository.save(entity);
	}

	@Override
	public DynamicFields findOne(String id) {
		return dynamicFieldsRepository.findOne(id);
	}

	@Override
	public Collection<DynamicFieldsForm> findDynamicFieldsForm(
			Map<String, Object> map) {
		return dynamicFieldsFormRepository.find(map);
	}

	@Override
	public Collection<DynamicFieldsGrid> findDynamicFieldsGrid(
			Map<String, Object> map) {
		return dynamicFieldsGridRepository.find(map);
	}

	@Override
	public Collection<DynamicFieldsQuery> findDynamicFieldsQuery(
			Map<String, Object> map) {
		return dynamicFieldsQueryRepository.find(map);
	}

	@Override
	public void clean(String modelName) {
		DynamicModels model=dynamicModelsRepository.findOneByName(modelName);
		Assert.notNull(model, modelName+",模型没有找到,请检查！");
		tableSchema.clean(model.getName(), model.getFields());
	}

}
