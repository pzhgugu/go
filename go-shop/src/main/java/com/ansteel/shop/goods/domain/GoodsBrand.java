package com.ansteel.shop.goods.domain;

import javax.persistence.*;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.OperEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Formula;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 创 建 人：gugu
 * 创建日期：2015-06-11
 * 修 改 人：
 * 修改日 期：
 * 描   述：商品品牌。  
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_brand")
public class GoodsBrand extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4640970260992318311L;

    /**
     * 品牌名称
     */
    private String brandName;

	/**
	 * 类别ID
	 */
	private String classId;
	/**
	 * 类别名称
	 */
	@Formula("(select s.name from "+Constants.G_TABLE_PREFIX +"goods_class s where s.ID =CLASSID)")
	private String className;
	/**
	 * logo
	 */
	private String logoImage;
	/**
	 * 推荐，0为否，1为是，默认为0
	 */
	private Integer recommend;
	/**
	 * 店铺ID
	 */
	private String storeId;
    /**
     * 排序
     */
    private Integer brandSort;
	/**
	 * 品牌申请，0为申请中，1为通过，默认为1，申请功能是会员使用，系统后台默认为1
	 */
	private Integer brandApply;

	/**
	 * 当前类型是否关联品牌
	 */
	@Transient
	private Integer isSelect;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch= FetchType.LAZY)
    @JoinTable(name = Constants.G_TABLE_PREFIX + "type_brand",
            joinColumns = {@JoinColumn(name="type_id")},
            inverseJoinColumns = { @JoinColumn(name = "brand_id") })
    @JsonIgnore
    private Collection<GoodsType> goodsTypes=new ArrayList<GoodsType>();

    public Collection<GoodsType> getGoodsTypes() {
        return goodsTypes;
    }

    public void setGoodsTypes(Collection<GoodsType> goodsTypes) {
        this.goodsTypes = goodsTypes;
    }

    public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getLogoImage() {
		return logoImage;
	}
	public void setLogoImage(String logoImage) {
		this.logoImage = logoImage;
	}
	public Integer getRecommend() {
		return recommend;
	}
	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public Integer getBrandApply() {
		return brandApply;
	}
	public void setBrandApply(Integer brandApply) {
		this.brandApply = brandApply;
	}

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getBrandSort() {
        return brandSort;
    }

    public void setBrandSort(Integer brandSort) {
        this.brandSort = brandSort;
	}

	public Integer getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(Integer isSelect) {
		this.isSelect = isSelect;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
}
