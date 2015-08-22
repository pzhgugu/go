package com.ansteel.core.domain;

import java.util.Collection;

import com.ansteel.core.exception.PageException;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-10
 * 修 改 人：
 * 修改日 期：
 * 描   述：树信息类，用于实体树信息的加载。  
 */
public class TreeInfo {
	

	private Collection<Class> tables;	
	private Class<?> tree;
	
	public Collection<Class> getTables() {
		return tables;
	}
	public void setTables(Collection<Class> ts) {
		for(Class tree:ts){
			Object o=null;
			try {
				o = tree.newInstance();
			} catch (InstantiationException e) {
				throw new PageException("设置表时，无法new对象！");
			} catch (IllegalAccessException e) {
				throw new PageException("设置表时，无法new对象！");
			}
			if(!(o instanceof BaseEntity)){				
				throw new PageException("请设置表继承BaseEntity！");
			}
		}
		this.tables = ts;
	}
	public Class<?> getTree() {
		return tree;
	}
	public void setTree(Class<?> tree) {
		Object o=null;
		try {
			o = tree.newInstance();
		} catch (InstantiationException e) {
			throw new PageException("设置树时，无法new对象！");
		} catch (IllegalAccessException e) {
			throw new PageException("设置树时，无法new对象！");
		}
		if(o instanceof TreeEntity){
			this.tree = tree;
		}else{
			throw new PageException("请设置树继承AbstractTreeEntity！");
		}
		
	}
	
}
