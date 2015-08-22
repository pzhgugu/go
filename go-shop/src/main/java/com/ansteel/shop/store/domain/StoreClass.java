package com.ansteel.shop.store.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.TreeEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：店铺分类。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX +  "store_class")
public class StoreClass extends TreeEntity<StoreClass>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5436108415885057684L;

}
