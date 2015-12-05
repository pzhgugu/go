package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015/10/26.
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_attribute")
public class GoodsAttribute extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goodsAttribute", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("sort")
    @JsonIgnore
    private Collection<GoodsAttributeValue> goodsAttributeValueList = new ArrayList<>();


    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, optional = true,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId", nullable = false)
    @JsonIgnore
    private GoodsType goodsType;

    public GoodsType getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(GoodsType goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 属性名称
     */
    private String attrName;

    /**
     * 属性值列
     */
    private String attrValue;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 排序
     */
    private Integer attrSort;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getAttrSort() {
        return attrSort;
    }

    public void setAttrSort(Integer attrSort) {
        this.attrSort = attrSort;
    }

    public Collection<GoodsAttributeValue> getGoodsAttributeValueList() {
        return goodsAttributeValueList;
    }

    public void setGoodsAttributeValueList(Collection<GoodsAttributeValue> goodsAttributeValueList) {
        this.goodsAttributeValueList = goodsAttributeValueList;
    }
}
