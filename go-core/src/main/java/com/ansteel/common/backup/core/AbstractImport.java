package com.ansteel.common.backup.core;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：增量备份导入虚类，用于增量备份导入的通用方法。  
 */
public abstract class AbstractImport implements IBackupXmlImport {
	
	private int type=BackupConstant.ALL_NEW;

	 @Override
    public void setType(int type) {
        this.type = type;
    }
	 
	public int getType(){
		return this.type;
	}
}
