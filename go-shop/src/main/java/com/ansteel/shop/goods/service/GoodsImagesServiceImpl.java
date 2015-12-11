package com.ansteel.shop.goods.service;

import java.util.ArrayList;
import java.util.List;

import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.web.ColorImagesModel;
import com.ansteel.shop.goods.web.GoodsImagesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsImages;
import com.ansteel.shop.goods.repository.GoodsImagesRepository;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

@Service
@Transactional(readOnly = true)
public class GoodsImagesServiceImpl implements GoodsImagesService {

    @Autowired
    GoodsImagesRepository goodsImagesRepository;

    @Autowired
    StoreService storeService;

    @Autowired
    GoodsCommonService goodsCommonService;

    @Override
    @Transactional
    public GoodsImages saveDefault(String goodsId, String image, String storeId) {
        GoodsImages goodsImages = new GoodsImages();
        goodsImages.setGoodsId(goodsId);
        goodsImages.setGoodsImage(image);
        goodsImages.setStoreId(storeId);
        goodsImages.setIsDefault(1);
        goodsImages.setGoodsImageSort(0);
        return goodsImagesRepository.save(goodsImages);
    }

    @Override
    public List<GoodsImages> findByGoodsIdAndStoreId(String goodsId) {
        Store store = storeService.getCurrentStore();
        return goodsImagesRepository.findByStoreIdAndGoodsId(store.getId(), goodsId);
    }

    private List<GoodsImages> findByGoodsIdAndStoreId(String goodsId, String storeId) {
        return goodsImagesRepository.findByStoreIdAndGoodsId(storeId, goodsId);
    }


    @Override
    public GoodsImages getDefautlGoodsImages(List<GoodsImages> goodsImagesList) {
        for (GoodsImages image : goodsImagesList) {
            if (image.getIsDefault() == 1) {
                return image;
            }
        }
        throw new PageException("数据异常！没有找到主图！");
    }

    @Override
    @Transactional
    public GoodsCommon saevDefaultImage(GoodsImages[] goodsImagesArray, String goodsId) {
        GoodsImages goodsImagesDefault = null;
        for (GoodsImages gi : goodsImagesArray) {
            if (gi.getIsDefault() != null && gi.getIsDefault() == 1 && gi.getGoodsImage() != null) {
                goodsImagesDefault = gi;
            }
        }
        Assert.notNull(goodsImagesDefault, "主图没有设置图片，请检查！");
        GoodsCommon goods = goodsCommonService.findOneByStoreIdAndId(goodsId);
        if (!goods.getGoodsImage().equals(goodsImagesDefault.getGoodsImage())) {
            goods.setGoodsImage(goodsImagesDefault.getGoodsImage());
            return goodsCommonService.save(goods);
        }
        return goods;
    }

    @Override
    @Transactional
    public void save(String goodsId, GoodsImages[] goodsImagesArray) {
        Store store = storeService.getCurrentStore();
        List<GoodsImages> goodsImagesList = this.findByGoodsIdAndStoreId(goodsId, store.getId());
        goodsImagesRepository.delete(goodsImagesList);
        for (GoodsImages gi : goodsImagesArray) {
            if (StringUtils.hasText(gi.getGoodsImage())) {
                gi.setGoodsId(goodsId);
                gi.setStoreId(store.getId());
                goodsImagesRepository.save(gi);
            }
        }
    }

    @Override
    @Transactional
    public void save(ColorImagesModel colorImagesModel) {
        String goodsId=colorImagesModel.getGoodsId();
        Assert.hasText(goodsId,"商品ID不能为空！");
        goodsImagesRepository.delectGoodsId(goodsId);
        List<GoodsImagesModel> gimList = colorImagesModel.getGimList();
        if(gimList!=null&&gimList.size()>0){
            Store store = storeService.getCurrentStore();
            for(GoodsImagesModel gim:gimList){
                List<GoodsImages> goodsImagesModelList = gim.getImgList();
                for(GoodsImages gi:goodsImagesModelList){
                    if(StringUtils.hasText(gi.getGoodsImage())){
                        gi.setColorId(gim.getColorId());
                        gi.setStoreId(store.getId());
                        gi.setGoodsId(goodsId);
                        goodsImagesRepository.save(gi);
                    }
                }
            }
        }
    }


}
