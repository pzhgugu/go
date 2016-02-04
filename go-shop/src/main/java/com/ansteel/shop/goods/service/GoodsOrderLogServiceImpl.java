package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.repository.GoodsOrderLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GoodsOrderLogServiceImpl implements  GoodsOrderLogService {

    @Autowired
    private GoodsOrderLogRepository goodsOrderLogRepository;
}
