package com.ansteel.common.backup.core;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份常量。  
 */
public class BackupConstant {
	 /**
     * 导入所有新信息（有重复的报错）
     */
    public static final int ALL_NEW = 1;
    /**
     * 导入所有（有重复更新）
     */
    public static final int ALL_UPDATE = 2;
    /**
     * 导入选择的（有重复的报错）
     */
    public static final int SELECT_NEW = 3;
    /**
     * 导入选择的（有重复更新）
     */
    public static final int SELECT_UPDATE = 4;

    /**
     * 导入不同版本
     */
    public static final int ALL_VERSION_NEW=5;

    /**
     * 导入不同版本
     */
    public static final int ALL_VERSION_UPDATE=6;


    public static final String MESSAGE="Message";

    public static final String CLASS="Class";

    public static final String EXECUTE="Execute";


    public static final String DATA_ROW="DataRow";

    public static final String FILE_PATH="filePath";

    public static final String ROOT = "Root";

    public static final String NAME_FIELD = "name";

    public static final String PARENT_FIELD =  "parent";

    /**
     * 错误编码
     */
    public static final String  REPEAT = "已经存在";
	public static final String ID_FIELD = "id";
    public static String VERSION="VERSION";
}
