package com.ansteel.common.modelscan.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.EntityFieldsCategory;
import com.ansteel.common.model.domain.EntityFieldsForm;
import com.ansteel.common.model.domain.EntityFieldsGrid;
import com.ansteel.common.model.domain.EntityFieldsQuery;
import com.ansteel.common.model.domain.Models;
import com.ansteel.common.model.repository.ModelsRepository;
import com.ansteel.common.model.service.ModelService;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.domain.EntityInfo;
import com.ansteel.core.domain.MainSubInfo;
import com.ansteel.core.domain.TreeInfo;
import com.ansteel.dhtmlx.widget.form.Constant;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：模型扫描接口实现。  
 */
@Service
@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=false)
public class ModelScanServiceBean implements ModelScanService {
	
	private static final String UID = "serialVersionUID";

	private static final String DEFAULT_FORM = DHtmlxConstants.INPUT;

	private static final Logger logger=Logger.getLogger(ModelScanServiceBean.class);
	
	@Autowired
	ModelService modelService;

	@Autowired
	ModelsRepository modelsRepository;

	@Override
	public Map<Class, Models> scanModel(EntityInfo entityInfo,String requestMappingName) {
		Map<Class, Models> modelMap = new HashMap<Class, Models>();
		Class<?> mainClass = entityInfo.getClazz();
		Map<Class, Models> newModels = new HashMap<Class, Models>();

		Models mainModel = modelService.findOneByClazz(mainClass
				.getName());
		if (mainModel == null) {
			mainModel=this.getClassToModel(mainClass,requestMappingName);
			newModels.put(mainClass, mainModel);
		}
		modelMap.put(mainClass,mainModel);

		TreeInfo tree = entityInfo.getTree();
		if (tree != null) {
			Class<?> treeClass = tree.getTree();
			Models treeModel = modelService.findOneByClazz(treeClass
					.getName());
			if (treeModel == null) {
				if (!newModels.containsKey(treeClass)) {
					treeModel=this.getClassToModel(treeClass,requestMappingName);
					newModels.put(treeClass, treeModel);
				}
			}
			modelMap.put(treeClass,treeModel);
		}

		MainSubInfo mainSub = entityInfo.getMainSub();
		if(mainSub!=null){
			Collection<Class> subClassList = mainSub.getSubordinate();
			for (Class o : subClassList) {
				Models subModel = modelService.findOneByClazz(o.getName());
				if (subModel == null) {
					if (!newModels.containsKey(o)) {
						subModel=this.getClassToModel(o,requestMappingName);
						newModels.put(o, subModel);
					}
				}
				modelMap.put(o,subModel);
			}
		}
		if(newModels.size()>0){
			this.setModelInfo(newModels);
			this.saveModels(newModels);
		}
		return modelMap;
	}

	@Override
	public  Map<Class, Models> scanModel(List<EntityInfo> entityInfo,String requestMappingName) {
		Map<Class, Models> map = new HashMap<Class, Models>();
		for(EntityInfo o:entityInfo){
			map.putAll(this.scanModel(o,requestMappingName));
		}
		return map;
	}
	
	public Models getClassToModel(Class clazz,String requestMappingName) {
		Models model = new Models();
		model.setName(clazz.getName());
		model.setAlias(clazz.getSimpleName());
		// 模式类型（0为Javabean，1为自定义表）
		model.setModelType(0);
		model.setClazz(clazz.getName());
		model.setDisplayOrder(0);
		model.setLayer(0);
		model.setRequestMappingName(requestMappingName);
		return model;
	}
	
	/**
	 * 实体写到字段信息中
	 * 
	 * @param objectClass
	 * @param model
	 * @return
	 */
	public Collection<EntityFields> entityTOFields(Models model,Class key) {
		Collection<EntityFields> fields = new ArrayList<EntityFields>();
		int step = 0;
		for (Class<?> clazz = key; clazz != Object.class; clazz = clazz
				.getSuperclass()) {

			Field[] fieldlist = clazz.getDeclaredFields();
			for (int i = 0; i < fieldlist.length; i++) {
				EntityFields fd = new EntityFields();
				Field fld = fieldlist[i];
				String fieldName = fld.getName();
				if (!(UID).equals(fieldName)) {
					logger.info("--------字段名称-------：" + fld.getName());
					logger.info("字段类型：" + fld.getType());

					Boolean isTransient = false;
					Annotation[] annotation = fld.getAnnotations();
					if (annotation.length > 0) {
						for (int j = 0; j < annotation.length; j++) {
							logger.info("注解类型："
									+ annotation[j].annotationType());
							if (annotation[j].annotationType() == Transient.class) {
								isTransient = true;
							} else if (annotation[j].annotationType() == Column.class) {
								Column column = (Column) annotation[j];
								fd.setFieldPrecision(column.precision());
								if (column.nullable()) {
									fd.setFieldNullable(1);
								} else {
									fd.setFieldNullable(0);
								}

								fd.setFieldScale(column.scale());
								if (column.unique()) {
									fd.setFieldUnique(1);
								} else {
									fd.setFieldUnique(0);
								}

								fd.setFieldLength(column.length());
							} else if (annotation[j].annotationType() == OneToMany.class) {
								OneToMany otm = (OneToMany) annotation[j];
								fd.setRelation(otm.getClass().getSimpleName());
								fd.setRelationCascade(otm.cascade().toString());
								fd.setRelationFetch(otm.fetch().name());
								fd.setRelationMappedBy(otm.mappedBy());
							} else if (annotation[j].annotationType() == OneToOne.class) {
								OneToOne otm = (OneToOne) annotation[j];
								fd.setRelation(otm.getClass().getSimpleName());
								fd.setRelationCascade(otm.cascade().toString());
								fd.setRelationFetch(otm.fetch().name());
								fd.setRelationMappedBy(otm.mappedBy());
							} else if (annotation[j].annotationType() == ManyToMany.class) {
								ManyToMany otm = (ManyToMany) annotation[j];
								fd.setRelation(otm.getClass().getSimpleName());
								fd.setRelationCascade(otm.cascade().toString());
								fd.setRelationFetch(otm.fetch().name());
								fd.setRelationMappedBy(otm.mappedBy());
							} else if (annotation[j].annotationType() == OrderBy.class) {
								OrderBy ob = (OrderBy) annotation[j];
								fd.setRelationOrderBy(ob.value());
							}

						}
					}
					if (!isTransient) {
						logger.info("-------------------------操作字段----------------------");
						fd.setName(fieldName);
						fd.setAlias(fieldName);
						fd.setFieldType(fld.getType().getSimpleName());

						Type fc = fld.getGenericType();// 如果是集合得到GenericType
						if (fc == null) {
							fd.setTypeClass(fld.getType().getName());
						} else {
							// 如果是泛型类型
							if (fc instanceof ParameterizedType) {
								ParameterizedType pt = (ParameterizedType) fc;
								Type[] atas = pt.getActualTypeArguments();
								if (atas.length > 0) {
									Type type = atas[0];
									if (type instanceof Class) {
										fd.setTypeClass(((Class) type)
												.getName());
									} else if (type instanceof TypeVariable) {
										GenericDeclaration gd = ((TypeVariable) type)
												.getGenericDeclaration();
										fd.setTypeClass(((Class) gd).getName());
									}

								}
							}
						}
						/**
						 * 设置默认值
						 */
						fd.setModels(model);
						fd.setDisplayOrder(step);
						step += 5;
						fd.setIsInvented(0);
						fields.add(fd);
					}
				}

			}
		}
		return fields;

	}

	public void setModelInfo(Map<Class, Models> newModels) {
		//Map<Class, Model> newModels = getNewModel(entityInfo);
		this.modelToField(newModels);
		this.defaultFieldsCategory(newModels);
		//return newModels;
	}

	
	public void saveModels(Map<Class,Models> newModels) {
		for(Class clazz:newModels.keySet()){
			Models models = newModels.get(clazz);
			modelsRepository.save(models);
		}
	}

	private void defaultFieldsCategory(Map<Class, Models> newModels) {
		for (Class key : newModels.keySet()) {
			Models model = newModels.get(key);			
			this.defaultFieldsCategory(key, model);
		}

	}
	
	private void defaultFieldsCategory(Class key,Models model) {		
		EntityFieldsCategory fieldsCategory=this.setFieldsCategory(model);
		fieldsCategory.setModels(model);
		List<EntityFieldsGrid> fieldsGridList=this.setFieldsGrid(model,fieldsCategory);
		List<EntityFieldsForm> fieldsFormList=this.setFieldsForm(model,fieldsCategory);
		List<EntityFieldsQuery> fieldsQueryList=this.setFieldsQuery(model,fieldsCategory);	
		
		
		fieldsCategory.setFieldsGrid(fieldsGridList);
		fieldsCategory.setFieldsForm(fieldsFormList);
		fieldsCategory.setFieldsQuery(fieldsQueryList);
		List<EntityFieldsCategory> fieldsCategoryList=new ArrayList<EntityFieldsCategory>();
		fieldsCategoryList.add(fieldsCategory);
		model.setFieldsCategory(fieldsCategoryList);
	}

	private List<EntityFieldsQuery> setFieldsQuery(Models model,EntityFieldsCategory fieldsCategory) {
		List<EntityFields> fieldsList=(List<EntityFields>) model.getFields();
		List<EntityFieldsQuery> fieldsQueryList=new ArrayList<EntityFieldsQuery>();
		for(EntityFields field:fieldsList){
			fieldsQueryList.add(this.getFieldsQuery(field, fieldsCategory));
		}
		return fieldsQueryList;
	}
	
	private  EntityFieldsQuery getFieldsQuery(EntityFields field,EntityFieldsCategory fieldsCategory){
		EntityFieldsQuery fieldsQuery=new EntityFieldsQuery();
		fieldsQuery.setName(field.getName());
		fieldsQuery.setDisplayOrder(field.getDisplayOrder());
		fieldsQuery.setFieldsCategory(fieldsCategory);
		fieldsQuery.setIsLock(0);
		fieldsQuery.setIsShow(1);
		return fieldsQuery;
	}

	private List<EntityFieldsForm> setFieldsForm(Models model,EntityFieldsCategory fieldsCategory) {
		List<EntityFields> fieldsList=(List<EntityFields>) model.getFields();		
		List<EntityFieldsForm> fieldsFormList=new ArrayList<EntityFieldsForm>();
		for(EntityFields field:fieldsList){
			fieldsFormList.add(this.getFieldsForm(field, fieldsCategory));
		}
		return fieldsFormList;
	}
	
	private EntityFieldsForm getFieldsForm(EntityFields field,EntityFieldsCategory fieldsCategory){
		EntityFieldsForm fieldsForm=new EntityFieldsForm();
		fieldsForm.setName(field.getName());
		fieldsForm.setDisplayOrder(field.getDisplayOrder());
		fieldsForm.setFieldsCategory(fieldsCategory);
		fieldsForm.setType(DEFAULT_FORM);
		fieldsForm.setIsShow(1);
		return fieldsForm;
	}

	private List<EntityFieldsGrid> setFieldsGrid(Models model,EntityFieldsCategory fieldsCategory) {
		List<EntityFields> fieldsList=(List<EntityFields>) model.getFields();
		List<EntityFieldsGrid> fieldsGridList=new ArrayList<EntityFieldsGrid>();
		for(EntityFields field:fieldsList){
			fieldsGridList.add(this.getFieldsGrid(field, fieldsCategory));
		}
		return fieldsGridList;
	}
	
	private EntityFieldsGrid getFieldsGrid(EntityFields field,EntityFieldsCategory fieldsCategory){
		EntityFieldsGrid fieldsGrid=new EntityFieldsGrid();
		fieldsGrid.setName(field.getName());
		fieldsGrid.setDisplayOrder(field.getDisplayOrder());
		fieldsGrid.setFieldsCategory(fieldsCategory);
		fieldsGrid.setColAlign(Public.DEFAULE_MODELGRID_ALIGN);
		fieldsGrid.setInitWidths(Public.DEFAULE_MODELGRID_WIDTHS);
		fieldsGrid.setColSorting(Public.DEFAULE_MODELGRID_SORTING);
		fieldsGrid.setColTypes(Public.DEFAULE_MODELGRID_TYPES);		
		fieldsGrid.setIsShow(1);
		return fieldsGrid;
	}

	private EntityFieldsCategory setFieldsCategory(Models model) {
		EntityFieldsCategory fieldsCategory = new EntityFieldsCategory();
		fieldsCategory.setName(Public.DEFAULE_CATEGORY_NAME);
		fieldsCategory.setAlias(Public.DEFAULE_CATEGORY_NAME);
		return fieldsCategory;
	}

	private void modelToField(Map<Class, Models> newModels) {
		for (Class key : newModels.keySet()) {
			Models o = newModels.get(key);
			Collection<EntityFields> fields = this.entityTOFields(o,key);
			o.setFields(fields);
		}
	}

	@Override
	public  Models scanModel(Class clazz,String requestMappingName) {
		Models mainModel = modelService.findOneByClazz(clazz
				.getName());
		if (mainModel == null) {
			Models o = this.getClassToModel(clazz,requestMappingName);
			Collection<EntityFields> fields = this.entityTOFields(o,clazz);
			o.setFields(fields);
			this.defaultFieldsCategory(clazz, o);
			return modelsRepository.save(o);
		}else{
			Collection<EntityFields> oldFields = mainModel.getFields();
			Map<String,EntityFields> oldMap = new HashMap<>();
			for(EntityFields f: oldFields){
				oldMap.put(f.getName(), f);
			}
			Collection<EntityFields> newFields = this.entityTOFields(mainModel,clazz);
			Map<String,EntityFields> newMap = new HashMap<>();
			for(EntityFields f: newFields){
				newMap.put(f.getName(), f);
			}
			for(String key:newMap.keySet()){
				if(!oldMap.containsKey(key)){
					EntityFields field = newMap.get(key);
					oldFields.add(field);
					Collection<EntityFieldsCategory> fcs = mainModel.getFieldsCategory();
					for(EntityFieldsCategory fc:fcs){
						Collection<EntityFieldsForm> ffs = fc.getFieldsForm();
						ffs.add(this.getFieldsForm(field, fc));						
						if(ffs.size()>0){
							fc.setFieldsForm(ffs);
						}
						Collection<EntityFieldsGrid> fgs = fc.getFieldsGrid();
						fgs.add(this.getFieldsGrid(field, fc));						
						if(fgs.size()>0){
							fc.setFieldsGrid(fgs);
						}
						Collection<EntityFieldsQuery> fqs = fc.getFieldsQuery();
						fqs.add(this.getFieldsQuery(field, fc));
						if(fqs.size()>0){
							fc.setFieldsQuery(fqs);
						}
					}
				}
			}
			return modelsRepository.save(mainModel);
		}
	}
}
