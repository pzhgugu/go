package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.repository.GoodsCommonRepository;
import com.ansteel.shop.goods.web.GoodsAttrModel;
import com.ansteel.shop.goods.web.GoodsModel;
import com.ansteel.shop.goods.web.GoodsSpecValueSelectListModel;
import com.ansteel.shop.goods.web.GoodsSpecValueStockModel;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/12/6.
 */
@Service
public class GoodsCommonServiceImpl implements GoodsCommonService {

    @Autowired
    GoodsCommonRepository goodsCommonRepository;
    @Autowired
    StoreService storeService;
    @Autowired
    GoodsImagesService goodsImagesService;
    @Autowired
    GoodsService goodsService;

    @Override
    @Transactional
    public GoodsCommon save(GoodsCommon goods) {
        Store store = storeService.getCurrentStore();
        goods.setStoreId(store.getId());
        goods.setStoreName(store.getName());
        GoodsCommon newGoods=goodsCommonRepository.save(goods);
        goodsImagesService.saveDefault(newGoods.getId(), newGoods.getGoodsImage(), newGoods.getStoreId());
        return newGoods;
    }

    @Override
    public GoodsCommon findOneByStoreIdAndId(String goodsId) {
        Store store = storeService.getCurrentStore();
        return goodsCommonRepository.findOneByStoreIdAndId(store.getId(), goodsId);
    }

    @Override
    public Page<GoodsCommon> findCurrentSaleAll(final String classId, final String sortType, Integer curPage, int pageSize, final String name, final String value) {
        if (curPage == null) {
            curPage = 0;
        } else if (curPage > 0) {
            curPage = curPage - 1;
        }
        Pageable pageable = new PageRequest(curPage, pageSize);
        Store store = storeService.getCurrentStore();
        final String storeId = store.getId();
        Specification<GoodsCommon> specification = new Specification<GoodsCommon>() {
            public Predicate toPredicate(Root<GoodsCommon> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (StringUtils.hasText(sortType)) {
                    query.orderBy(cb.asc(root.get(sortType)));
                } else {
                    query.orderBy(cb.asc(root.get("created")));
                }
                List<Predicate> predicate = new ArrayList<>();

                predicate.add(cb.equal(root.get("storeId"), storeId));
                predicate.add(cb.equal(root.get("goodsState"), 1));
                if (StringUtils.hasText(classId)) {
                    predicate.add(cb.equal(root.get("gcId"), classId));
                }
                if (StringUtils.hasText(value)) {
                    predicate.add(cb.equal(root.get(name), value));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        return goodsCommonRepository.find(specification, pageable);
    }

    @Override
    @Transactional
    public void unShow(String[] goodsIdArray) {
        for (String goodsId : goodsIdArray) {
            this.unShow(goodsId);
        }
    }

    @Override
    @Transactional
    public void unShow(String goodsId) {
        goodsCommonRepository.updateGoodsState(goodsId, 0);
    }

    @Override
    @Transactional
    public void adEdit(String[] goodsIdArray, String adWord) {
        for (String goodsId : goodsIdArray) {
            goodsCommonRepository.updateAdWord(goodsId, adWord);
        }
    }

    @Override
    @Transactional
    public GoodsCommon savePosition(String commonid, String plateTop, String plateBottom) {
        GoodsCommon goods = this.findOneByStoreIdAndId(commonid);
        Assert.notNull(goods, commonid + ",此商品id无效！");
        goods.setPlateidTop(plateTop);
        goods.setPlateidBottom(plateBottom);
        return this.save(goods);
    }

    @Override
    @Transactional
    public void savePosition(String[] ids, String plateTop, String plateBottom) {
        for (String id : ids) {
            this.savePosition(id, plateTop, plateBottom);
        }
    }

    @Override
    @Transactional
    public GoodsCommon saveGoodsCommonAndGodds(GoodsCommon goodsCommon, GoodsModel goodsModel) {
        //设置店铺
        Store store = storeService.getCurrentStore();
        goodsCommon.setStoreId(store.getId());
        goodsCommon.setStoreName(store.getName());

        //设置属性列表
        List<GoodsAttrModel> attrList=goodsModel.getAttrList();
        if(attrList!=null&&attrList.size()>0) {
            goodsCommon.setGoodsAttr(JsonUtils.jsonFromObject(attrList));
        }

        //店铺分类
        String sgcateIdList = goodsModel.getSgcateIdList();
        goodsCommon.setGoodsStcids(sgcateIdList);

        //发布时间
        if(goodsCommon.getGoodsState()!=null&&goodsCommon.getGoodsState()==1){
            //上架时间
            goodsCommon.setGoodsSelltime(new Date());
        }else{
            //上架时间设置
        }

        //规格选择-规格名称
        List<GoodsSpecValueSelectListModel> gsvslList = goodsModel.getGsvslList();
        if(gsvslList!=null&&gsvslList.size()>0){
            goodsCommon.setSpecName(JsonUtils.jsonFromObject(gsvslList));
        }


        GoodsCommon dataBaseGoodsCommon =null;
                //判断规格是否每个品种都选择，是否有库存配置列表
        Boolean isAllSelect=this.specIsAllSelect(gsvslList);
        if(isAllSelect){
            //保存库存列表
            List<GoodsSpecValueStockModel> stockList = goodsModel.getStockList();
            goodsCommon.setSpecValue(JsonUtils.jsonFromObject(stockList));

            dataBaseGoodsCommon = goodsCommonRepository.save(goodsCommon);
            //保存Goods表，商品表
            this.save(dataBaseGoodsCommon,stockList);
        }else{
            dataBaseGoodsCommon = goodsCommonRepository.save(goodsCommon);
            this.saveGoods(dataBaseGoodsCommon);
        }
        return dataBaseGoodsCommon;
    }

    private void save(GoodsCommon goodsCommon, List<GoodsSpecValueStockModel> stockList) {
        Goods goods = this.setGoods(goodsCommon);
        goods.setGoodsCommonId(goodsCommon.getId());
        for(GoodsSpecValueStockModel gsvsm:stockList){
            //库存
            Integer stock = gsvsm.getStock();
            if(stock!=null&&stock>0) {
                Goods goodsNew = null;
                try {
                    goodsNew = (Goods) BeanUtils.cloneBean(goods);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                String[] specNameArray = gsvsm.getSpecName();
                StringBuffer nameSB = new StringBuffer(goodsNew.getName());
                for (String name : specNameArray) {
                    nameSB.append(" " + name);
                }
                goodsNew.setName(nameSB.toString());
                //商品价格
                goodsNew.setGoodsStorePrice(gsvsm.getPrice());
                goodsNew.setGoodsSpec(JsonUtils.jsonFromObject(gsvsm));
                //库存
                goodsNew.setGoodsStorage(stock);
                //商家货号
                goodsNew.setGoodsSerial(gsvsm.getSku());
                //是否添加颜色ID？？？
                goodsNew.setColorId(gsvsm.getColorId());
                goodsService.save(goodsNew);
            }
        }
    }

    private void saveGoods(GoodsCommon goodsCommon) {
        Goods goods = this.setGoods(goodsCommon);
        goodsService.save(goods);
    }

    private Goods setGoods(GoodsCommon goodsCommon) {
        Goods goods = new Goods();
        try {
            BeanUtils.applyIf(goods,goodsCommon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        goods.setId(null);
        return goods;
    }

    private Boolean specIsAllSelect(List<GoodsSpecValueSelectListModel> gsvslList) {
        for(GoodsSpecValueSelectListModel gsslm:gsvslList){
            String[] spvIds = gsslm.getSpvId();
            if(spvIds==null||spvIds.length<1){
                return false;
            }
        }
        return true;
    }
}
