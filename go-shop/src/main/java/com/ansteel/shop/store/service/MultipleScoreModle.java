package com.ansteel.shop.store.service;

/**
 * 店铺评分综合数据模型
 */
public class MultipleScoreModle {

    public MultipleScoreModle(Double descScoreAvg, Double serviceScoreAvg, Double deliveryScoreAvg, Integer descScoreaxMax, Integer serviceScoreMax, Integer deliveryScoreMax){
        this.descScoreAvg=descScoreAvg;
        this.serviceScoreAvg=serviceScoreAvg;
        this.deliveryScoreAvg=deliveryScoreAvg;
        this.descScoreaxMax=descScoreaxMax;
        this.serviceScoreMax=serviceScoreMax;
        this.deliveryScoreMax=deliveryScoreMax;
    }
    /**
     * 描述相符度平均分
     */
    private Double descScoreAvg;

    /**
     * 描述相符度最高分
     */
    private Integer descScoreaxMax;
    /**
     * 服务态度平均分
     */
    private Double serviceScoreAvg;
    /**
     * 服务态度最高分
     */
    private Integer serviceScoreMax;
    /**
     * 发货速度平均分
     */
    private Double deliveryScoreAvg;
    /**
     * 发货速度最高分
     */
    private Integer deliveryScoreMax;

    public Double getDescScoreAvg() {
        return descScoreAvg;
    }

    public void setDescScoreAvg(Double descScoreAvg) {
        this.descScoreAvg = descScoreAvg;
    }

    public Integer getDescScoreaxMax() {
        return descScoreaxMax;
    }

    public void setDescScoreaxMax(Integer descScoreaxMax) {
        this.descScoreaxMax = descScoreaxMax;
    }

    public Double getServiceScoreAvg() {
        return serviceScoreAvg;
    }

    public void setServiceScoreAvg(Double serviceScoreAvg) {
        this.serviceScoreAvg = serviceScoreAvg;
    }

    public Integer getServiceScoreMax() {
        return serviceScoreMax;
    }

    public void setServiceScoreMax(Integer serviceScoreMax) {
        this.serviceScoreMax = serviceScoreMax;
    }

    public Double getDeliveryScoreAvg() {
        return deliveryScoreAvg;
    }

    public void setDeliveryScoreAvg(Double deliveryScoreAvg) {
        this.deliveryScoreAvg = deliveryScoreAvg;
    }

    public Integer getDeliveryScoreMax() {
        return deliveryScoreMax;
    }

    public void setDeliveryScoreMax(Integer deliveryScoreMax) {
        this.deliveryScoreMax = deliveryScoreMax;
    }
}
