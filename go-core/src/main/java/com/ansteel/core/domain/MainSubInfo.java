package com.ansteel.core.domain;

import java.util.Collection;

import com.ansteel.core.exception.PageException;

/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：主从表信息类，用于实体主从表信息的加载。 
 */
public class MainSubInfo {
	

	private Collection<Class> subordinate;	
	private Class<?> principal;
	
	
	
	public Collection<Class> getSubordinate() {
		return subordinate;
	}



	public Class<?> getPrincipal() {
		return principal;
	}


	public void setSubordinate(Collection<Class> ts) {
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
		this.subordinate = ts;
	}
	
	public void setPrincipal(Class<?> principal) {
		Object o=null;
		try {
			o = principal.newInstance();
		} catch (InstantiationException e) {
			throw new PageException("设置树时，无法new对象！");
		} catch (IllegalAccessException e) {
			throw new PageException("设置树时，无法new对象！");
		}
		if(o instanceof OperEntity){
			this.principal = principal;
		}else{
			throw new PageException("请设置继承AbstractEntity！");
		}
		
	}
	
}
