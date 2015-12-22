package com.ansteel.shop.goods.repository;

import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.GoodsCommon;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Administrator on 2015/12/19.
 */
@Repository
@Transactional(readOnly = true)
public class GoodsDao  {

    @PersistenceContext
    private EntityManager em;

    public List<GoodsCommon> findTopByHot(String storeId,Integer maxResults){
        StringBuffer sb= new StringBuffer("select gc from GoodsCommon gc ");
        sb.append(" where gc.storeId=:storeId");
        sb.append(" and gc.goodsVerify=1");
        sb.append(" and gc.goodsState=1");
        sb.append(" order by gc.goodsStorage desc");
        Query query  = em.createQuery(sb.toString(), GoodsCommon.class);
        query.setParameter("storeId",storeId);
        return  query.setFirstResult(0).setMaxResults(maxResults).getResultList();
    }

    public List<GoodsCommon> findTopByHotCollect(String storeId,Integer maxResults) {
        StringBuffer sb= new StringBuffer("select gc from GoodsCommon gc ");
        sb.append(" where gc.storeId=:storeId");
        sb.append(" and gc.goodsVerify=1");
        sb.append(" and gc.goodsState=1");
        sb.append(" order by gc.goodsCollect desc");
        Query query  = em.createQuery(sb.toString(), GoodsCommon.class);
        query.setParameter("storeId",storeId);
        return  query.setFirstResult(0).setMaxResults(maxResults).getResultList();
    }
}
