package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.*;
import com.ansteel.shop.goods.service.*;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.service.StoreGoodsClassService;
import com.ansteel.shop.store.service.StoreScoreModle;
import com.ansteel.shop.store.service.StoreScoreService;
import com.ansteel.shop.store.service.StoreService;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2015/12/23.
 */
@Controller
@RequestMapping(value = Public.CLIENT + "/goods")
public class ClientGoodsController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    GoodsCommonService goodsCommonService;

    @Autowired
    StoreService storeService ;

    @Autowired
    StoreScoreService storeScoreService;

    @Autowired
    GoodsImagesService goodsImagesService;

    @Autowired
    GoodsClassService goodsClassService;

    @Autowired
    GoodsSpecValueService goodsSpecValueService;

    @Autowired
    StoreGoodsClassService storeGoodsClassService;

    @Autowired
    GoodsBrandService goodsBrandService;

    @RequestMapping("/show")
    public String one(Model model,
                      @RequestParam(value = "goods_id",required = false) String goodsId,
                      @RequestParam(value = "common_id",required = false) String commonId,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        GoodsCommon goodsCommon=null;
        Goods goods=null;
        if(StringUtils.hasText(goodsId)){
            goods=goodsService.findOne(goodsId);
            goodsCommon=goodsCommonService.findOne(goods.getGoodsCommonId());
        }else if(StringUtils.hasText(commonId)){
            goodsCommon=goodsCommonService.findOne(commonId);
            //得到默认商品商品
            List<Goods> goodsList = goodsService.findByGoodsCommonIdOrderByGoodsStorePriceAsc(goodsCommon.getId());
            if(goodsList.size()>0){
                goods=goodsList.get(0);
            }
        }else{
            throw new PageException("错误链接！");
        }
        Assert.notNull(goods,"商品为找到！");
        model.addAttribute("P_GOODS", goods);
        if(StringUtils.hasText(goods.getGoodsSpec())) {
            Object goodsSpecValueStockModel = JsonUtils.objectFromJson(goods.getGoodsSpec(), GoodsSpecValueStockModel.class);
            model.addAttribute("P_VALUESTOCK_MODEL",goodsSpecValueStockModel );
        }
        model.addAttribute("P_GOODSCOMMON",goodsCommon);

        Store store=storeService.findOne(goodsCommon.getStoreId());
        //店铺评分
        StoreScoreModle storeScoreModle=storeScoreService.getStoreScore(store);
        model.addAttribute("P_STORE_SCORE", storeScoreModle);
        model.addAttribute("P_STORE",store);

        //获取商品图片
        List<GoodsImages> goodsImagesList=new ArrayList<>();
        Map<String ,GoodsImages> defalutImageMap = new HashMap<>();
        List<GoodsImages> defalutImageList =new ArrayList<>();
        List<GoodsImages> goodsImagesAll = goodsImagesService.findByGoodsId(goodsCommon.getId());
        for(GoodsImages gi:goodsImagesAll){
            if(!defalutImageMap.containsKey(gi.getColorId())){
                defalutImageMap.put(gi.getColorId(),gi);
                defalutImageList.add(gi);
            }
        }
        model.addAttribute("P_GOODS_DEFAULTIMAGES",defalutImageList);

        goodsImagesList = goodsImagesService.findByGoodsIdAndColorId(goodsCommon.getId(), goods.getColorId());
        model.addAttribute("P_GOODS_IMAGES",goodsImagesList);

        //选中规格
        String specName=goodsCommon.getSpecName();
        List<GoodsSpecValueSelectListModel> gsvslList = JsonUtils.readValue(specName, GoodsSpecValueSelectListModel.class);
        model.addAttribute("P_GOODSSPEC_SELECT",gsvslList);
        //选中规格值，表格选项
        String specValue=goodsCommon.getSpecValue();
        if(StringUtils.hasText(specValue)) {
            List<GoodsSpecValueStockModel> stockList = JsonUtils.readValue(specValue, GoodsSpecValueStockModel.class);
            model.addAttribute("P_GOODSSPECVALUE_SELECT", stockList);
        }

        //得到分类
        GoodsClass goodsClass = goodsClassService.findOne(goodsCommon.getGcId());
        GoodsType goodsType = goodsClass.getGoodsType();
        if (goodsType != null) {
            //关联规格
            Collection<GoodsSpec> goodsSpecs = goodsType.getGoodsSpecs();
            model.addAttribute("P_GOODSSPECS", goodsSpecs);
            if (goodsSpecs.size() > 0) {
                List<GoodsSpecValue> goodsSpecValueList = goodsSpecValueService.findByStoreIdOrderByStoreIdAsc(store.getId());
                model.addAttribute("P_GOODSSPECVALUE_LIST", goodsSpecValueList);
                Map<String ,GoodsSpecValue> specValueMap = new HashMap<>();
                for(GoodsSpecValue spec:goodsSpecValueList){
                    specValueMap.put(spec.getId(),spec);
                }
                model.addAttribute("P_GOODSSPECVALUE_MAP",specValueMap);
            }

        }


        //----------------------------------------------------------------------------------

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findByIsParentNull(store.getId());
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        //热销商品
        List<GoodsCommon> hotList=goodsCommonService.findTop5ByHot(store.getId());
        //热门收藏
        List<GoodsCommon> collectList=goodsCommonService.findTop5ByHotCollect(store.getId());
        model.addAttribute("P_GOODS_HOT",hotList);
        model.addAttribute("P_GOODS_COLLECT",collectList);

        //---------------------------------------------------------------------
        //商品品牌
        GoodsBrand goodsBrand = goodsBrandService.findOne(goods.getBrandId());
        model.addAttribute("P_GOODS_BRAND",goodsBrand);

        //商品规格
        String goodsSpec=goods.getGoodsSpec();
        if(StringUtils.hasText(goodsSpec)){
            GoodsSpecValueStockModel goodsSpecValueStockModel = (GoodsSpecValueStockModel) JsonUtils.objectFromJson(goodsSpec, GoodsSpecValueStockModel.class);
            model.addAttribute("P_GOODS_SPECVALUE",goodsSpecValueStockModel);
        }

        //推荐商品
        List<GoodsCommon> commendList=goodsCommonService.findTopByGoodsCommend(store.getId(), 5);
        model.addAttribute("P_GOODS_COMMON",commendList);

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/buy/" + style + "/goodsdetail/home.html");
    }
}
