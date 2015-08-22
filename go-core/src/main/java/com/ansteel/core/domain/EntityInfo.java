package com.ansteel.core.domain;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：实体信息类，用于实体模板信息的加载。 
 */
public class EntityInfo {

	private Class<?> clazz;
	private String[] fields;
	private TreeInfo tree;
	private MainSubInfo mainSub;
	
	public TreeInfo getTree() {
		return tree;
	}
	public void setTree(TreeInfo tree) {
		this.tree = tree;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	public MainSubInfo getMainSub() {
		return mainSub;
	}
	public void setMainSub(MainSubInfo mainSub) {
		this.mainSub = mainSub;
	}
	
}
