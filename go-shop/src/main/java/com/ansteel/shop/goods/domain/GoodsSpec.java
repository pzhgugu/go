package com.ansteel.shop.goods.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品规格,与商品表一对多。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_spec")
public class GoodsSpec extends BaseEntity{
	/*`spec_id`	int(11)	NOT NULL	auto_increment	备注:’商品规格索引id',
	`goods_id`	int(11)	NOT NULL	备注:’商品id',
	`spec_name`	varchar(255)	NOT NULL	备注:’规格名称',
	`spec_goods_price`	decimal(10,2)	NOT NULL	备注:’规格商品价格',
	`spec_goods_storage`	int(11)	NOT NULL	备注:’规格商品库存',
	`spec_salenum`	int(11)	NOT NULL	default	'0'	备注:’售出数量',
	`spec_goods_color`	varchar(20)	NOT NULL	备注:’规格商品颜色',
	`spec_goods_serial`	varchar(50)	NOT NULL	备注:’规格商品编号',
	`spec_goods_spec`	text	NOT NULL	备注:’商品规格序列化',
*/
}
