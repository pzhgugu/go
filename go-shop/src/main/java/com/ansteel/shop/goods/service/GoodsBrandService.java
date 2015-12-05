package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.QueryMapping;
import com.ansteel.shop.goods.domain.GoodsBrand;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface GoodsBrandService {
    String saveAttachment(MultipartFile file,GoodsBrand goodsBrand);

    /**
     * 检测名称是否重复
     * @param goodsBrand
     * @return
     */
    boolean isNameRepeat(GoodsBrand goodsBrand);

    /**
     * 查询品牌，并且显示关联
     *
     * @return
     */
    List<GoodsBrand> loadRelationBrand(String url, String typeId);

    List<GoodsBrand> queryRelationBrand(String url, String typeId, List<QueryMapping> queryList);

    /**
     * 更新品牌关联
     *
     * @param id
     * @param parameter
     */
    void updateBrandRelation(String typeId, String id, String parameter);

    Page<GoodsBrand> findByCondition(String brandName, String sortType, Integer curPage, int size);

    GoodsBrand findOne(String id);

    void delete(String id);

    GoodsBrand save(GoodsBrand goodsBrand);

    GoodsBrand update(GoodsBrand goodsBrand);

    GoodsBrand saveCurrent(GoodsBrand goodsBrand);

    GoodsBrand updateCurrent(GoodsBrand goodsBrand);
}
