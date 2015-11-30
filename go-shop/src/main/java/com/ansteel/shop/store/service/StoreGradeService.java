package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreGrade;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
public interface StoreGradeService {
    List<StoreGrade> findAll();

    StoreGrade findOne(String id);
}
