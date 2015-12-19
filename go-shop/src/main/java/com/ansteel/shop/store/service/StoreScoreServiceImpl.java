package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created by Administrator on 2015/12/17.
 */
@Service
public class StoreScoreServiceImpl implements StoreScoreService {

    @Autowired
    StoreService storeService;

    @Override
    public StoreScoreModle getStoreScore(String storeId) {
        Store store = storeService.findOne(storeId);
        return this.getStoreScore(store);
    }

    @Override
    public StoreScoreModle getStoreScore(Store store) {
        StoreScoreModle storeScoreModle=this.storeScoreCache(store.getId());
        if(storeScoreModle!=null){
            return storeScoreModle;
        }
        Assert.notNull(store,"查询店铺评分时，没有此店铺！");
        //得到店铺分类
        String storeScId=store.getScId();

        MultipleScoreModle sumScoreModle=this.getMultipleScore(storeScId);

        return this.getStoreScoreModle(store, sumScoreModle);
    }

    private StoreScoreModle getStoreScoreModle(Store store, MultipleScoreModle sumScoreModle) {
        //店铺得分-同行业平均分/同行业最高分-同行业平均分
        StoreScoreModle storeScoreModle = new StoreScoreModle();
        Double descScore=descScore = (store.getStoreDesccredit() - sumScoreModle.getDescScoreAvg()) / (sumScoreModle.getDescScoreaxMax() - sumScoreModle.getDescScoreAvg());
        if(descScore.isNaN()){
            storeScoreModle.setDescScore(0.0);
        }else {
            storeScoreModle.setDescScore(descScore);
        }
        Double deliveryScore =deliveryScore = (store.getStoreDeliverycredit() - sumScoreModle.getDeliveryScoreAvg()) / (sumScoreModle.getDeliveryScoreMax() - sumScoreModle.getDeliveryScoreAvg());
        if(deliveryScore.isNaN()){
            storeScoreModle.setDeliveryScore(0.0);
        }else {
            storeScoreModle.setDeliveryScore(deliveryScore);
        }

        Double serviceScore = (store.getStoreServicecredit() - sumScoreModle.getServiceScoreAvg()) / (sumScoreModle.getServiceScoreMax() - sumScoreModle.getServiceScoreAvg());
        if(serviceScore.isNaN()){
            storeScoreModle.setServiceScore(0.0);
        }else {
            storeScoreModle.setServiceScore(serviceScore);
        }

        Double multipleScore=multipleScore = (descScore+deliveryScore+serviceScore)/3;
        if(multipleScore.isNaN()){
            storeScoreModle.setMultipleScore(0.0);
        }else {
            storeScoreModle.setMultipleScore(multipleScore);
        }

        return storeScoreModle;
    }

    private MultipleScoreModle getMultipleScore(String storeScId) {
        return storeService.getMultipleScore(storeScId);
    }

    /**
     * 店铺评分缓存
     * @param storeId
     * @return
     */
    private StoreScoreModle storeScoreCache(String storeId) {
        return null;
    }
}
