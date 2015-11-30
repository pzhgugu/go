package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Administrator on 2015/11/26.
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_attribute_value")
public class GoodsAttributeValue extends BaseEntity {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "attId", nullable = false)
    @JsonIgnore
    GoodsAttribute goodsAttribute;

    private String name;

    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public GoodsAttribute getGoodsAttribute() {
        return goodsAttribute;
    }

    public void setGoodsAttribute(GoodsAttribute goodsAttribute) {
        this.goodsAttribute = goodsAttribute;
    }
}
