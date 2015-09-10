package com.ansteel.shop.goods.service;

import com.ansteel.core.domain.BaseEntity;
import com.ansteel.shop.goods.domain.GoodsSpec;
import com.ansteel.shop.goods.domain.GoodsType;
import com.ansteel.shop.goods.repository.GoodsSpecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/9/7.
 */
@Service
public class GoodsSpecServiceBean implements GoodsSpecService {

    @Autowired
    GoodsSpecRepository goodsSpecRepository;

    @Autowired
    GoodsTypeService goodsTypeService;

    @Override
    public Page findSelectGoodsTypes(String goodsTypesId, Pageable pageable) {
        Page page = goodsSpecRepository.findAll(pageable);
        this.querySelect(page,goodsTypesId);
        return page;
    }

    @Override
    public List findBySpName(String spName) {
       return goodsSpecRepository.findBySpName(spName);
    }

    @Override
    public GoodsSpec save(GoodsSpec goodsSpec, String goodsTypesId) {
        GoodsType goodsType= goodsTypeService.findOne(goodsTypesId);
        Assert.notNull(goodsType, goodsTypesId + "，商品类型没有找到！");
        this.addgoodsType(goodsSpec,goodsType);
        return goodsSpecRepository.save(goodsSpec);
    }

    private void addgoodsType(GoodsSpec goodsSpec, GoodsType goodsType) {
        Collection<GoodsType> list = goodsSpec.getGoodsTypes();
        GoodsType selectType=null;
        for(GoodsType g:list) {
            if(g.getId().equals(goodsType.getId())) {
                selectType=g;
            }
        }
        if(goodsSpec.getIsSelect()==1) {
            if (selectType==null) {
                list.add(goodsType);
                goodsSpec.setGoodsTypes(list);
            }
        }else{
            if(selectType!=null) {
                list.remove(selectType);
                goodsSpec.setGoodsTypes(list);
            }
        }
    }

    @Override
    public void select(List<BaseEntity> baseEntityLsit, String goodsTypesId) {
        GoodsType goodsType= goodsTypeService.findOne(goodsTypesId);
        Assert.notNull(goodsType,goodsTypesId+"，商品类型没有找到！");
        for(BaseEntity b:baseEntityLsit){
            GoodsSpec goodsSpec = (GoodsSpec) b;
            this.addgoodsType(goodsSpec,goodsType);
        }
    }

    @Override
    public void querySelect(Page page, String goodsTypesId) {
        GoodsType goodsType = goodsTypeService.findOne(goodsTypesId);
        Collection<GoodsSpec> goodsSpecsList=goodsType.getGoodsSpecs();
        if(goodsSpecsList.size()>0) {
            List<GoodsSpec> list = page.getContent();
            for (GoodsSpec goodsSpec : list) {
                int isSelect=0;
                for (GoodsSpec select : goodsSpecsList) {
                    if(goodsSpec.getId()==select.getId()){
                        isSelect=1;
                    }
                }
                goodsSpec.setIsSelect(isSelect);
            }
        }
    }
}
