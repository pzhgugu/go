package com.ansteel.shop.store.service;

import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.repository.StorePlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/8/25.
 */
@Service
@Transactional(readOnly = true)
public class StorePlateServiceImpl implements StorePlateService {

    @Autowired
    StorePlateRepository storePlateRepository;

    @Autowired
    StoreService storeService;


    @Override
    @Transactional
    public StorePlate save(StorePlate storePlate) {
        storePlate.setStoreId(storeService.getCurrentStore().getId());
        return storePlateRepository.save(storePlate);
    }

    @Override
    public StorePlate findOne(String id) {
        return storePlateRepository.findOne(id);
    }

    @Override
    @Transactional
    public void deleteCurrent(String[] idArray) {
        Store store = storeService.getCurrentStore();
        for (String id : idArray) {
            this.deleteCurrent(id, store.getId());
        }
    }

    @Override
    @Transactional
    public void delete(String id) {
        storePlateRepository.delete(id);
    }

    @Override
    public void deleteCurrent(String id, String storeId) {
        StorePlate storePlate = storePlateRepository.findByIdAndStoreId(id, storeId);
        Assert.notNull(storePlate, "异常删除数据！");
        this.delete(id);
    }

    @Override
    public Page<StorePlate> findByStoreId(final String position, final String name, final String sortType, Integer curPage, int pageSize) {
        if (curPage == null) {
            curPage = 0;
        } else if (curPage > 0) {
            curPage = curPage - 1;
        }
        Pageable pageable = new PageRequest(curPage, pageSize);
        Store store = storeService.getCurrentStore();
        final String storeId = store.getId();
        Specification<StorePlate> specification = new Specification<StorePlate>() {
            public Predicate toPredicate(Root<StorePlate> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (StringUtils.hasText(sortType)) {
                    query.orderBy(cb.asc(root.get(sortType)));
                } else {
                    query.orderBy(cb.asc(root.get("created")));
                }
                List<Predicate> predicate = new ArrayList<>();

                predicate.add(cb.equal(root.get("storeId"), storeId));

                if (StringUtils.hasText(position)) {
                    predicate.add(cb.equal(root.get("platePosition"), position));
                }
                if (StringUtils.hasText(name)) {
                    predicate.add(cb.like(root.<String>get("plateName"), "%" + name + "%"));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };

        return storePlateRepository.find(specification, pageable);
    }

    @Override
    public List<StorePlate> findAllCurrentStore() {
        Store store = storeService.getCurrentStore();
        return storePlateRepository.findByStoreId(store.getId());
    }
}
