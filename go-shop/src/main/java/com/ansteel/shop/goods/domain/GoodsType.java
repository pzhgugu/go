package com.ansteel.shop.goods.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品类型。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_type")
public class GoodsType extends OperEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9196504916438328497L;

}
