package com.ansteel.shop.store.service;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.PageableUtils;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.repository.StoreGoodsClassRepository;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Administrator on 2015/11/22.
 */
@Service
public class StoreGoodsClassServiceImpl implements StoreGoodsClassService {
    @Autowired
    StoreGoodsClassRepository storeGoodsClassRepository;

    @Autowired
    StoreService storeService;

    @Override
    public Page<StoreGoodsClass> findCurrentByIsParentNull(String sort, Integer curPage, int size) {
        final Store store = storeService.getCurrentStore();
        Pageable pageable = PageableUtils.getPageable(sort, curPage, size);

        Specification<StoreGoodsClass> spec = new Specification<StoreGoodsClass>() {
            public Predicate toPredicate(Root<StoreGoodsClass> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.where(cb.isNull(root.get("parent")), cb.equal(root.get("storeId"), store.getId()));
                return query.getRestriction();
            }
        };
        return storeGoodsClassRepository.find(spec, pageable);

    }

    @Override
    public StoreGoodsClass findCurrentOne(String id) {
        Store store = storeService.getCurrentStore();
        StoreGoodsClass storeGoodsClass = storeGoodsClassRepository.findOne(id);
        Assert.notNull(storeGoodsClass, id + ",没找到，异常删除！");
        if (store.getId().equals(storeGoodsClass.getStoreId())) {
            return storeGoodsClass;
        } else {
            return null;
        }
    }

    @Override
    public void deleteCurrent(String id) {
        Store store = storeService.getCurrentStore();
        StoreGoodsClass storeGoodsClass = storeGoodsClassRepository.findOne(id);
        Assert.notNull(storeGoodsClass, id + ",没找到，异常删除！");
        if (store.getId().equals(storeGoodsClass.getStoreId())) {
            if (storeGoodsClass.getChildren().size() > 0) {
                throw new PageException("异常删除：请先删除子记录！");
            }
            storeGoodsClassRepository.delete(storeGoodsClass);
        } else {
            throw new PageException("异常删除：只能删除当前店铺记录！");
        }
    }

    @Override
    public List<StoreGoodsClass> findCurrentByIsParentNull() {
        Store store = storeService.getCurrentStore();
        return storeGoodsClassRepository.findByParentIsNullAndStoreId(store.getId());

    }

    @Override
    public void deleteCurrents(String[] ids) {
        for (String s : ids) {
            this.deleteCurrent(s);
        }
    }

    @Override
    public StoreGoodsClass save(StoreGoodsClass storeGoodsClass) {
        StoreGoodsClass storeGoodsClassName = this.findOneByName(storeGoodsClass.getName());
        if (storeGoodsClassName == null || storeGoodsClassName.getId().equals(storeGoodsClass.getId())) {
            return storeGoodsClassRepository.save(storeGoodsClass);
        } else {
            throw new PageException(storeGoodsClass.getName() + ",名称已经存在！");
        }
    }

    @Override
    public StoreGoodsClass saveCurrent(StoreGoodsClass storeGoodsClass, String parentId) {
        StoreGoodsClass parent = null;
        if (StringUtils.hasText(parentId)) {
            parent = storeGoodsClassRepository.findOne(parentId);
        }
        storeGoodsClass.setParent(parent);
        Store store = storeService.getCurrentStore();
        storeGoodsClass.setStoreId(store.getId());
        return this.save(storeGoodsClass);
    }

    private StoreGoodsClass findOneByName(String name) {
        List<StoreGoodsClass> list = storeGoodsClassRepository.findByName(name);
        if (list.size() > 0) {
            if (list.size() != 1) {
                throw new PageException(name + ",名称多条记录！");
            }
            return list.get(0);
        } else {
            return null;
        }
    }
}
