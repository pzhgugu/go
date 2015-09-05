package com.ansteel.shop.goods.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品类型。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_type")
public class GoodsType extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9196504916438328497L;

    /**
     * 名称
     */
    private String typeName;

    /**
     * 排序
     */
    private Integer typeSort;

    /**
     * 分类id
     */
    @ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE}, optional=true,
            fetch= FetchType.LAZY)
    @JoinColumn(name = "classId", nullable = false)
    @JsonIgnore
    private GoodsClass goodsClass;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "goodsTypes",
            fetch=FetchType.LAZY)
    @JsonIgnore
    private Collection<GoodsSpec> goodsSpecs=new ArrayList<GoodsSpec>();

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    public GoodsClass getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(GoodsClass goodsClass) {
        this.goodsClass = goodsClass;
    }

    public Collection<GoodsSpec> getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(Collection<GoodsSpec> goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }
}
