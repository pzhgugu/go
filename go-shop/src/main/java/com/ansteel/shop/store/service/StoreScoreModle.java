package com.ansteel.shop.store.service;

/**
 * 店铺评分，数据实体
 */
public class StoreScoreModle {
    /**
     * 描述相符度分数
     */
    private Double descScore;
    /**
     * 服务态度分数
     */
    private Double serviceScore;
    /**
     * 发货速度分数
     */
    private Double deliveryScore;
    /**
     * 综合分数
     */
    private Double multipleScore;

    public Double getDescScore() {
        return descScore;
    }

    public void setDescScore(Double descScore) {
        this.descScore = descScore;
    }

    public Double getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(Double serviceScore) {
        this.serviceScore = serviceScore;
    }

    public Double getDeliveryScore() {
        return deliveryScore;
    }

    public void setDeliveryScore(Double deliveryScore) {
        this.deliveryScore = deliveryScore;
    }

    public Double getMultipleScore() {
        return multipleScore;
    }

    public void setMultipleScore(Double multipleScore) {
        this.multipleScore = multipleScore;
    }
}
