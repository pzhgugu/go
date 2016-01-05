package com.ansteel.shop.goods.service;

import com.ansteel.common.springsecurity.service.UserInfo;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.GoodsConsult;
import com.ansteel.shop.goods.repository.GoodsConsultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Administrator on 2016/1/4.
 */
@Service
@Transactional(readOnly = true)
public class GoodsConsultServiceImpl implements GoodsConsultService {

    @Autowired
    GoodsConsultRepository goodsConsultRepository;

    @Override
    @Transactional
    public GoodsConsult save(GoodsConsult goodsConsult) {
        UserInfo userInfo=UserUtils.getUserInfo();
        if(userInfo!=null) {
            goodsConsult.setMemberId(userInfo.getUsername());
            goodsConsult.setMemberName(userInfo.getAlias());
        }else{
            goodsConsult.setMemberId("0");
            goodsConsult.setMemberName("游客");
        }
        return goodsConsultRepository.save(goodsConsult);
    }

    @Override
    public Page<GoodsConsult> findByGoodsIdOrderByCreatedDesc(String goodsId, Integer curPage, int size) {
        Sort sort =new Sort(Sort.Direction.DESC, "created");
        Pageable pageable = new PageRequest(curPage,size,sort);
        return goodsConsultRepository.findByGoodsId(goodsId,pageable);
    }
}
