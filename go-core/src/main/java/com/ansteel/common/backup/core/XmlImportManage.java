package com.ansteel.common.backup.core;


import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DataType;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导入管理实现。  
 */
@Service
public class XmlImportManage implements IXmlImportManage {


	private String VERSION;

	@Override
	public Map<Class, Object> importXmlVersion(InputStream is,
			List<Class> listClass, String version) {
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(is);
		} catch (DocumentException e) {
			throw new PageException(e.toString());
		}
		Element rootElement = document.getRootElement();
		String xmlVersion = rootElement.attributeValue(BackupConstant.VERSION);
		if (xmlVersion == null) {
			throw new PageException("xml的version为空，请检查！");
		}
		if (xmlVersion.equals(version)) {
			return null;
		}
		VERSION = xmlVersion;
		Map<Class, Object> sortMap = new LinkedHashMap<Class, Object>();
		for (Iterator iter = rootElement.elementIterator(); iter.hasNext();) {
			Element message = (Element) iter.next();
			if (message.getName().equals(BackupConstant.MESSAGE)) {
				String className = message.attributeValue(BackupConstant.CLASS);
				String seviceNamer = message
						.attributeValue(BackupConstant.EXECUTE);
				Class clazz = null;
				try {
					clazz = Class.forName(className);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}

				IExecuteXml executeXml = BackupUtils
						.getExecuteXmlName(seviceNamer);
				List<BaseEntity> entityList = this.xmlToBean(clazz, message,
						executeXml);
				sortMap.put(executeXml.getClass(), entityList);
			}
		}
		return sortMap;
	}

	public List<BaseEntity> xmlToBean(Class<?> clazzBase, Element root,
			IExecuteXml executeXml) {
		List<BaseEntity> es = new ArrayList<BaseEntity>();
		List messages = root.elements();
		for (Iterator iter = messages.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			List datarows = null;
			if (element.getName().equals(BackupConstant.DATA_ROW)) {
				BaseEntity entity = null;
				try {
					entity = (BaseEntity) clazzBase.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				datarows = element.elements();

				for (Iterator piter = datarows.iterator(); piter.hasNext();) {
					Element rows = (Element) piter.next();
					String rName = rows.getName();
					String value = rows.getText();
					this.xmlToField(rName, value, entity, executeXml);
				}
				es.add(entity);
			}
		}
		if (es.size() > 0) {
			if (es.get(0) instanceof TreeEntity) {
				for (Iterator iter = messages.iterator(); iter.hasNext();) {
					Element element = (Element) iter.next();
					List datarows = null;
					if (element.getName().equals(BackupConstant.DATA_ROW)) {
						datarows = element.elements();

						TreeEntity currentEntity = null;
						TreeEntity parentEntity = null;
						for (Iterator piter = datarows.iterator(); piter
								.hasNext();) {
							Element rows = (Element) piter.next();
							String rName = rows.getName();
							String value = rows.getText();
							if (rName.equals(BackupConstant.ID_FIELD)) {
								for (BaseEntity baseEntity : es) {
									if (baseEntity.getId().equals(value)) {
										currentEntity = (TreeEntity) baseEntity;
									}
								}
							}
							if (rName.equals(BackupConstant.PARENT_FIELD)) {
								for (BaseEntity baseEntity : es) {
									if (baseEntity.getId().equals(value)) {
										parentEntity = (TreeEntity) baseEntity;
									}
								}
							}
						}
						if (currentEntity != null && parentEntity != null) {
							currentEntity.setParent(parentEntity);
						}
					}
				}
			}
		}
		return es;

	}

	public void xmlToField(String key, String value, BaseEntity entity,
			IExecuteXml executeXml) {
		for (Class<?> clazz = entity.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field f : fields) {
				/*
				 * if (key.equalsIgnoreCase("version")) { continue; }
				 */
				
				if (f.getName().equals(key)) {
					boolean isStatic = Modifier.isStatic(f.getModifiers());
					// 静态字段不设置
					if (!isStatic) {
						// 只加入基本类型，不包括集合类型
						if (DataType.PRIMITIVE_DATA_TYPES.contains(f.getType())) {
							this.setField(f, value, entity);
						}else{
							this.setParentField(f, value, entity);
						}
					}
				}
			}
		}
	}
	
	
	public void setParentField(Field field, String value, BaseEntity entity){
		if (DataType.COLLECTION_DATA_TYPES.contains(field.getType())) {
			return ;
		}		
		/*if(entity instanceof EntityFields){
			System.out.println("-------------------");
		}*/
		field.setAccessible(true);
		
		if((entity instanceof BaseEntity)&&!(entity instanceof TreeEntity)){
			Object prentEntity=null;
			try {
				prentEntity=field.getType().newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(StringUtils.hasText(value)){
				BaseEntity baseEntity = (BaseEntity) prentEntity;
				baseEntity.setId(value);
				try {
					field.set(entity, baseEntity);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setField(Field field, String value, BaseEntity entity) {
		field.setAccessible(true);

		if (StringUtils.hasText(value)) {
			Object valueObject = null;
			try {
				Class<?> type = field.getType();
				if (type == int.class || type == Integer.class) {
					valueObject = Integer.valueOf(value).intValue();
				} else if (type == Boolean.class || type == boolean.class) {
					valueObject = Boolean.valueOf(value).booleanValue();
				} else if (type == Double.class || type == double.class) {
					valueObject = Double.valueOf(value).doubleValue();
				} else if (type == Float.class || type == float.class) {
					valueObject = Float.valueOf(value).floatValue();
				} else if (type == long.class || type == Long.class) {
					valueObject = Long.valueOf(value).longValue();
				} else if (type == Date.class) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					try {
						valueObject = sdf.parse(value);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					valueObject = value;
				}
				field.set(entity, valueObject);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public String getVersion() {		
		return VERSION;
	}

}
