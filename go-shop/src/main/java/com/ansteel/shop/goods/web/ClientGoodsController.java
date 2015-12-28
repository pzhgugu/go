package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.domain.GoodsImages;
import com.ansteel.shop.goods.service.GoodsCommonService;
import com.ansteel.shop.goods.service.GoodsImagesService;
import com.ansteel.shop.goods.service.GoodsService;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreScoreModle;
import com.ansteel.shop.store.service.StoreScoreService;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            model.addAttribute("P_GOODS",goods);
            goodsCommon=goodsCommonService.findOne(goods.getGoodsCommonId());
        }else if(StringUtils.hasText(commonId)){
            goodsCommon=goodsCommonService.findOne(commonId);
        }else{
            throw new PageException("错误链接！");
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
        if(goods==null) {
            List<GoodsImages> goodsImagesAll = goodsImagesService.findByGoodsId(goodsCommon.getId());
            for(GoodsImages gi:goodsImagesAll){
                if(!defalutImageMap.containsKey(gi.getColorId())){
                    defalutImageMap.put(gi.getColorId(),gi);
                    goodsImagesList.add(gi);
                }
            }
        }else{
            goodsImagesList = goodsImagesService.findByGoodsIdAndColorId(goodsCommon.getId(), goods.getColorId());
        }
        model.addAttribute("P_GOODS_IMAGES",goodsImagesList);

        String style = this.getStyle();
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/goods/" + style + "/home.html");
    }

    public String getStyle() {
        return "default";
    }
}
