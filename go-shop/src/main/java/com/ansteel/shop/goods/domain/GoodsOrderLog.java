package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 订单处理历史表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_order_log")
public class GoodsOrderLog  extends BaseEntity {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 文字描述
     */
    private String logMsg;
    /**
     * 操作角色
     */
    private String logRole;
    /**
     * 操作人
     */
    private String logUser;
    /**
     * 操作人ID
     */
    private String logUserId;
    /**
     * 订单状态：0(已取消)10:未付款;20:已付款;30:已发货;40:已收货;
     */
    private Integer logOrderState;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogMsg() {
        return logMsg;
    }

    public void setLogMsg(String logMsg) {
        this.logMsg = logMsg;
    }

    public String getLogRole() {
        return logRole;
    }

    public void setLogRole(String logRole) {
        this.logRole = logRole;
    }

    public String getLogUser() {
        return logUser;
    }

    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }

    public String getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(String logUserId) {
        this.logUserId = logUserId;
    }

    public Integer getLogOrderState() {
        return logOrderState;
    }

    public void setLogOrderState(Integer logOrderState) {
        this.logOrderState = logOrderState;
    }
}
