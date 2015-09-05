package com.ansteel.shop.goods.domain;

import javax.persistence.*;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;

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
    /**
     * 规格名称
     */
    private String spName;

    /**
     * 排序
     */
    private Integer spSort;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch=FetchType.LAZY)
    @JoinTable(name = Constants.G_TABLE_PREFIX + "type_spec",
            joinColumns = {@JoinColumn(name="type_id")},
            inverseJoinColumns = { @JoinColumn(name = "spec_id") })
    @JsonIgnore
    private Collection<GoodsType> goodsTypes=new ArrayList<GoodsType>();

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }


    public Collection<GoodsType> getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(Collection<GoodsType> goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public Integer getSpSort() {
        return spSort;
    }

    public void setSpSort(Integer spSort) {
        this.spSort = spSort;
    }
}
