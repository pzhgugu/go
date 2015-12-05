package com.ansteel.shop.goods.service;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.core.utils.*;
import com.ansteel.shop.goods.domain.GoodsBrand;
import com.ansteel.shop.goods.domain.GoodsType;
import com.ansteel.shop.goods.repository.GoodsBrandRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
@Service
@Transactional(readOnly = true)
public class GoodsBrandServiceImpl implements GoodsBrandService {

    @Autowired
    AttachmentService attachmentService;

    @Autowired
    GoodsTypeService goodsTypeService;

    @Autowired
    GoodsBrandRepository goodsBrandRepository;

    @Autowired
    StoreService storeService;

    @Override
    @Transactional
    public String saveAttachment(MultipartFile file, GoodsBrand goodsBrand) {
        Attachment attachment = null;
        if (StringUtils.hasText(goodsBrand.getId())) {
            String aId = goodsBrand.getLogoImage();
            if (StringUtils.hasText(aId)) {
                attachment = attachmentService.getAttachmentById(aId);
            }
        }

        if (attachment == null) {
            // 获取exclTpl附件目录
            attachment = attachmentService.saveAttachment(file);
        } else {
            attachment = attachmentService.saveAttachment(attachment, file);
        }
        goodsBrand.setLogoImage(attachment.getId());
        return attachment.getId();
    }

    @Override
    public boolean isNameRepeat(GoodsBrand goodsBrand) {
        GoodsBrand goodsBrandNew = goodsBrandRepository.findOneByBrandName(goodsBrand.getBrandName());
        if (goodsBrandNew == null) {
            return false;
        }
        if (goodsBrand.getId() != null && goodsBrand.getId().equals(goodsBrandNew.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public List<GoodsBrand> loadRelationBrand(String url, String typeId) {
        List<GoodsBrand> goodsBrandList = goodsBrandRepository.findAll();
        return this.getRelationGoodsBrand(goodsBrandList, url, typeId);
    }

    private List<GoodsBrand> getRelationGoodsBrand(List<GoodsBrand> goodsBrandList, String url, String typeId) {
        GoodsType goodsType = goodsTypeService.findOne(typeId);
        Collection<GoodsBrand> selectGoodsBrandList = goodsType.getGoodsBrands();
        for (GoodsBrand goodsBrand : goodsBrandList) {
            //加载图片
            String logoImage = goodsBrand.getLogoImage();
            if (StringUtils.hasText(logoImage)) {
                String attUrl = url + "/att/download/" + logoImage;
                goodsBrand.setLogoImage(attUrl);
            }

            //加载是否选中
            int isSelect = 0;
            for (GoodsBrand gb : selectGoodsBrandList) {
                if (gb.getId().equals(goodsBrand.getId())) {
                    isSelect = 1;
                    break;
                }
            }
            goodsBrand.setIsSelect(isSelect);
        }
        return goodsBrandList;
    }

    @Override
    public List<GoodsBrand> queryRelationBrand(String url, String typeId, List<QueryMapping> queryList) {
        Specification spec = CriteriaUtils.getSpecification(GoodsBrand.class, null, null, null, queryList);
        List<GoodsBrand> goodsBrandList = goodsBrandRepository.find(spec);
        return this.getRelationGoodsBrand(goodsBrandList, url, typeId);
    }

    @Override
    @Transactional
    public void updateBrandRelation(String typeId, String id, String value) {
        GoodsBrand goodsBrand = goodsBrandRepository.findOne(id);
        Collection<GoodsType> goodsTypeList = goodsBrand.getGoodsTypes();
        GoodsType goodsTypeBrand = null;
        for (GoodsType goodsType : goodsTypeList) {
            if (typeId.equals(goodsType.getId())) {
                goodsTypeBrand = goodsType;
            }
        }
        if (value != null && value.equals("1")) {
            if (goodsTypeBrand == null) {
                goodsTypeList.add(goodsTypeService.findOne(typeId));
                goodsBrand.setGoodsTypes(goodsTypeList);
            }
        } else {
            if (goodsTypeBrand != null) {
                goodsTypeList.remove(goodsTypeBrand);
                goodsBrand.setGoodsTypes(goodsTypeList);
            }
        }
        goodsBrandRepository.save(goodsBrand);
    }

    @Override
    public Page<GoodsBrand> findByCondition(final String brandName, String sortType, Integer curPage, int size) {
        final Pageable pageable = PageableUtils.getPageable(sortType, curPage, size);

        final Specification<GoodsBrand> spec = new Specification<GoodsBrand>() {
            public Predicate toPredicate(Root<GoodsBrand> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Store store = storeService.getCurrentStore();
                List<Predicate> pList = new ArrayList<Predicate>();
                pList.add(cb.equal(root.get("storeId"), store.getId()));
                if (StringUtils.hasText(brandName)) {
                    pList.add(cb.like(root.get("brandName").as(String.class), "%" + brandName + "%"));
                }
                Predicate[] pArray = pList.toArray(new Predicate[pList.size()]);
                query.where(cb.and(pArray));
                return query.getRestriction();
            }
        };
        return goodsBrandRepository.find(spec, pageable);
    }

    @Override
    public GoodsBrand findOne(String id) {
        return goodsBrandRepository.findOne(id);
    }

    @Override
    @Transactional
    public void delete(String id) {
        goodsBrandRepository.delete(id);
    }

    @Override
    @Transactional
    public GoodsBrand save(GoodsBrand goodsBrand) {
        return goodsBrandRepository.save(goodsBrand);
    }

    @Override
    @Transactional
    public GoodsBrand update(GoodsBrand goodsBrand) {
        GoodsBrand dataBase = goodsBrandRepository.findOne(goodsBrand.getId());
        try {
            BeanUtils.applyIf(dataBase, goodsBrand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodsBrandRepository.save(dataBase);
    }

    @Override
    @Transactional
    public GoodsBrand saveCurrent(GoodsBrand goodsBrand) {
        Store store = storeService.getCurrentStore();
        Assert.notNull(store, "异常店铺！");
        goodsBrand.setStoreId(store.getId());
        return this.save(goodsBrand);
    }

    @Override
    @Transactional
    public GoodsBrand updateCurrent(GoodsBrand goodsBrand) {
        Store store = storeService.getCurrentStore();
        Assert.notNull(store, "异常店铺！");
        goodsBrand.setStoreId(store.getId());
        return this.update(goodsBrand);
    }
}
