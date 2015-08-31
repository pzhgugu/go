package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.CriteriaUtils;
import com.ansteel.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.repository.GoodsRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class GoodsServiceBean implements GoodsService {

	@Autowired
	StoreService storeService;

	@Autowired
	GoodsRepository goodsRepository;
	
	@Autowired
	GoodsImagesService goodsImagesService;

	@Override
	@Transactional
	public Goods save(Goods goods) {
		Store store = storeService.getCurrentStore();
		goods.setStoreId(store.getId());
		goods.setStoreName(store.getName());
		Goods newGoods=goodsRepository.save(goods);
		goodsImagesService.saveDefault(newGoods.getId(),newGoods.getGoodsImage(),newGoods.getStoreId());
		return newGoods;
	}

	@Override
	public Goods findOneByStoreIdAndId(String goodsId) {
		Store store = storeService.getCurrentStore();
		return goodsRepository.findOneByStoreIdAndId(store.getId(),goodsId);
	}

    @Override
    public Page<Goods> findCurrentSaleAll(final String classId, final String sortType, Integer curPage, int pageSize,final String name, final String value) {
        if(curPage==null){
            curPage=0;
        }else if(curPage>0){
            curPage=curPage-1;
        }
        Pageable pageable = new PageRequest(curPage,pageSize);
        Store store = storeService.getCurrentStore();
        final String storeId=store.getId();
        Specification<Goods> specification= new Specification<Goods>() {
            public Predicate toPredicate(Root<Goods> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(StringUtils.hasText(sortType)){
                    query.orderBy(cb.asc(root.get(sortType)));
                }else{
                    query.orderBy(cb.asc(root.get("created")));
                }
                List<Predicate> predicate = new ArrayList<>();

                predicate.add(cb.equal(root.get("storeId"), storeId));
                predicate.add(cb.equal(root.get("goodsState"), 1));
                if(StringUtils.hasText(classId)){
                    predicate.add(cb.equal(root.get("gcId"), classId));
                }
                if(StringUtils.hasText(value)){
                    predicate.add(cb.equal(root.get(name), value));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        return goodsRepository.find(specification,pageable);
    }

    @Override
    @Transactional
    public void unShow(String[] goodsIdArray) {
        for(String goodsId:goodsIdArray){
            this.unShow(goodsId);
        }
    }

    @Override
    @Transactional
    public void unShow(String goodsId) {
        goodsRepository.updateGoodsState(goodsId,0);
    }

    @Override
    public void adEdit(String[] goodsIdArray, String adWord) {
        for(String goodsId:goodsIdArray){
            goodsRepository.updateAdWord(goodsId,adWord);
        }
    }

    @Override
    @Transactional
    public Goods savePosition(String commonid, String plateTop, String plateBottom) {
        Goods goods = this.findOneByStoreIdAndId(commonid);
        Assert.notNull(goods,commonid+",此商品id无效！");
        goods.setPlateidTop(plateTop);
        goods.setPlateidBottom(plateBottom);
        return this.save(goods);
    }

    @Override
    @Transactional
    public void savePosition(String[] ids, String plateTop, String plateBottom) {
        for(String id:ids){
            this.savePosition(id,plateTop,plateBottom);
        }
    }

}
