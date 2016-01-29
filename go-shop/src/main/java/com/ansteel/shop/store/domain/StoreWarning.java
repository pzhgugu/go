package com.ansteel.shop.store.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 创 建 人：gugu
 * 创建日期：2015-08-25
 * 修 改 人：
 * 修改日 期：
 * 描   述：店铺警告。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "store_warning")
public class StoreWarning  extends BaseEntity {

    /**
     * 店铺id
     */
    private String storeId;
    /**
     * 警告值
     */
    private Integer warningValue;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public Integer getWarningValue() {
        return warningValue;
    }

    public void setWarningValue(Integer warningValue) {
        this.warningValue = warningValue;
    }
}
