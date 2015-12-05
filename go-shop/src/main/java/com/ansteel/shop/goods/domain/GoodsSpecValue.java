package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 创 建 人：gugu
 * 创建日期：2015-09-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品规格对应值。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_spec_value")
public class GoodsSpecValue extends BaseEntity {

    /**
     * 规格值名称
     */
    private String name;
    /**
     * 所属规格id
     */
    private String spId;
    /**
     * 分类id
     */
    private String gcId;
    /**
     * 店铺id
     */
    private String storeId;
    /**
     * 规格颜色
     */
    private String spValueColor;
    /**
     * 排序
     */
    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getGcId() {
        return gcId;
    }

    public void setGcId(String gcId) {
        this.gcId = gcId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSpValueColor() {
        return spValueColor;
    }

    public void setSpValueColor(String spValueColor) {
        this.spValueColor = spValueColor;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
