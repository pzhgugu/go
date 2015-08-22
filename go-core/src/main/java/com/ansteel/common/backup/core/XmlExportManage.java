package com.ansteel.common.backup.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DataType;
import com.ansteel.core.utils.FileUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导出管理实现。  
 */
@Service
public class XmlExportManage implements IXmlExportManage {

	private static final String SESSION_FACTORY = "sessionFactory";
	
	private static final String SERIAL_VERSION_UID = "serialVersionUID";

	@Override
	public String exportMapToXml(Map<Class, Object> map, String filePath) {

		Document document = DocumentHelper.createDocument();
		Element root = document.addElement(BackupConstant.ROOT);
		root.addAttribute(BackupConstant.VERSION, UUID.randomUUID().toString());
		for (Entry<Class, Object> entry : map.entrySet()) {
			List<BaseEntity> entitys = (List<BaseEntity>) entry.getValue();
			if (entitys != null && entitys.size() > 0) {
				Element message = root.addElement(BackupConstant.MESSAGE);
				message.addAttribute(BackupConstant.CLASS, entitys.get(0)
						.getClass().getName());
				message.addAttribute(BackupConstant.EXECUTE, entry.getKey()
						.getName());
				for (BaseEntity o : entitys) {
					Element dataRow = message
							.addElement(BackupConstant.DATA_ROW);
					this.fieldElement(dataRow, o);
				}
			}

		}

		OutputFormat format = OutputFormat.createPrettyPrint();
		// 此参数为true则去除回车换行
		format.setTrimText(false);
		format.setEncoding("UTF-8");// 设置XML文件的编码格式
		XMLWriter writer = null;// 声明写XML的对象

		try {
			FileUtils.createNewFile(filePath);
			FileOutputStream fos = new FileOutputStream(filePath);
			writer = new XMLWriter(fos, format);
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			throw new PageException("xml导出错误：" + e.getMessage());
		}
		return filePath;

	}

	public void fieldElement(Element dataRow, BaseEntity o) {
		for (Class<?> clazz = o.getClass(); clazz != Object.class; clazz = clazz
				.getSuperclass()) {
			this.fieldElement(dataRow, clazz, o);
		}
	}

	public void fieldElement(Element dataRow, Class<?> clazz, BaseEntity o) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field f : fields) {
			boolean isStatic = Modifier.isStatic(f.getModifiers());
			// 静态字段不设置
			if (isStatic) {
				continue;
			}
			if(f.getName().equals(SESSION_FACTORY)){
				continue;
			}
			if(f.getName().equals(SERIAL_VERSION_UID)){
				continue;
			}
			if (DataType.COLLECTION_DATA_TYPES.contains(f.getType())) {
				continue;
			}
			Element fEl = dataRow.addElement(f.getName());
			f.setAccessible(true);
			Object value = null;
			try {
				value = f.get(o);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!DataType.PRIMITIVE_DATA_TYPES.contains(f.getType())) {
				if(value instanceof BaseEntity){
					BaseEntity parent = (BaseEntity) value;
					if (parent != null) {
						try {
							value = parent.getId();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						value = "";
					}
				}
			}
			if (value == null) {
				fEl.addText("");
			} else {
				fEl.addText(value + "");
			}
		}
	}

}
