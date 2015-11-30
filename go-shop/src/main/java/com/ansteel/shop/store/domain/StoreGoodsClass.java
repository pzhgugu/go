package com.ansteel.shop.store.domain;

import com.ansteel.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2015/11/10.
 */
@Entity
@Table(name = "store_goods_class")
public class StoreGoodsClass extends BaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID")
    @JsonIgnore
    private StoreGoodsClass parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Collection<StoreGoodsClass> children = new ArrayList<StoreGoodsClass>();

    @Column(name = "LAYER", columnDefinition = "INT default 0")
    private Integer layer;

    private String name;

    private Integer sequence;

    private String stcState;

    private String storeId;

    public String getStcState() {
        return stcState;
    }

    public void setStcState(String stcState) {
        this.stcState = stcState;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer() {
        if (this.getParent() != null) {
            this.layer = this.getParent().getLayer() + 1;
        } else {
            this.layer = 0;
        }
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public StoreGoodsClass getParent() {
        return parent;
    }

    public void setParent(StoreGoodsClass parent) {
        this.parent = parent;
    }

    public Collection<StoreGoodsClass> getChildren() {
        return children;
    }

    public void setChildren(Collection<StoreGoodsClass> children) {
        this.children = children;
    }

    public void addChildren(Collection<StoreGoodsClass> elements) {
        getChildren().addAll(elements);
    }

    public void addChild(StoreGoodsClass element) {
        getChildren().add(element);
    }

    public void removeChildren(Collection<StoreClass> elements) {
        for (StoreClass element : elements) {
            if (!getChildren().contains(element)) {
                getChildren().remove(elements);
            }
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getParentId() {
        if (this.getParent() == null) {
            return "";
        } else {
            return (String) this.getParent().getId();
        }
    }
}
