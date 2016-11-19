package com.ansteel.core.constant;
/**
 * 创 建 人：gugu
 * 创建日期：2015-03-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：基本常量。 
 */
public class Constants {
	/**
	 * 数据库表前缀
	 */
	public final static String G_TABLE_PREFIX="go_r_";
	/**
	 * 动态建表前缀
	 */
	public static final String DYNAMIC_TABLE_FIX = "goshow_";
	/**
	 * 动态字段前缀
	 */
	public static final String DYNAMIC_FIELD_FIX = "GO_";
	/**
	 * 模板路径名
	 */
	public final static String G_TEMPLATE="template";
	/**
	 * 模板路径名后缀
	 */
	public final static String G_TEMPLATE_SUFFIX=".html";
	/**
	 * 空标示
	 */
	public static final String NULL = "null";
	/**
	 * 模块服务前缀
	 */
	public static final String SERVICE_PREFIX = "com.go.modules.";
	/**
	 * 模块服务后缀
	 */
	public static final String SERVICE_SUFFIX = ".service";
	/**
	 * javabean后缀
	 */
	public static final String BEAN_SUFFIX = ".domain";
	/**
	 * 模块服务类后缀
	 */
	public static final String SERVICE_BEAN_SUFFIX = "ServiceBean";
	/**
	 * 模块服务接口类后缀
	 */
	public static final String SERVICE_INTERFACE_SUFFIX = "Service";
	/**
	 * 动态建模服务名称
	 */
	public static final String SERVICE_DYNAMIC_MODEL = "DynamicModel";
	/**
	 * 默认排序值
	 */
	public static final int G_ORDER_VALUE = 1000;
	/**
	 * 获取实体信息方法名称
	 */
	public static final String G_ENTITY_INFO = "getEntityInfos";
	/**
	 * spring模板变量
	 */
	public static final String URI_T_V="org.springframework.web.servlet.HandlerMapping.uriTemplateVariables";
	/**
	 * 主键名称
	 */
	public static final String BEAN_KEY="id";
	/**
	 * 返回路径
	 */
	public static final String REDIRECT="redirect:";
	/**
	 * 报表输出类型
	 */
	public static final String REPORT_OUT_TYPE="rtype";
	
	public static final String REPORT_OUT_EXCEL="EXCEL";
	
	public static final String REPORT_OUT_HTML="HTML";
	
	public static final String REPORT_OUT_PDF="PDF";
	
	public static final String REPORT_OUT_BROWSEN="BROWSEN";
	/**
	 * go-home文件夹路径
	 */
	public static final String GO_HOME="/WEB-INF/go-home";
	/**
	 * go_pro.properties配置名称
	 */
	public static final String GO_PROPERTIES="/go_pro.properties";
	
	
	public static final int ZERO = 0;
	
	public static final String WIDGET_TYPE_TREE="tree";
	
	/**
	 * 排序字段名
	 */
	public static final String DISPLAY_ORDER = "displayOrder";
	/**
	 * 默认分页记录数
	 */
	public static final int DEFAULT_MAX_RESULTS = 20;
}
