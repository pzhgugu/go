package com.ansteel.shop.store.service;

import com.ansteel.shop.store.domain.StoreGrade;
import com.ansteel.shop.store.repository.StoreGradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2015/11/20.
 */
@Service
public class StoreGradeServiceImpl implements StoreGradeService {

    @Autowired
    StoreGradeRepository storeGradeRepository;

    @Override
    public List<StoreGrade> findAll() {
        return storeGradeRepository.findAll();
    }

    @Override
    public StoreGrade findOne(String id) {
        return storeGradeRepository.findOne(id);
    }
}
