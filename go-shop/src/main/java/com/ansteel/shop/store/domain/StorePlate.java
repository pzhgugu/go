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
 * 描   述：店铺版式。
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "store_plate")
public class StorePlate  extends BaseEntity {
    /**
     * 关联板式名称
     */
    public String plateName;
    /**
     * 关联板式位置 1顶部，0底部
     */
    public String platePosition;
    /**
     * 关联板式内容
     */
    public String plateContent;
    /**
     * 店铺id
     */
    private String storeId;

    public String getPlateName() {
        return plateName;
    }

    public void setPlateName(String plateName) {
        this.plateName = plateName;
    }

    public String getPlatePosition() {
        return platePosition;
    }

    public void setPlatePosition(String platePosition) {
        this.platePosition = platePosition;
    }

    public String getPlateContent() {
        return plateContent;
    }

    public void setPlateContent(String plateContent) {
        this.plateContent = plateContent;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
