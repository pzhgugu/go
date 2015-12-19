package com.ansteel.shop.storehome.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.service.GoodsCommonService;
import com.ansteel.shop.store.domain.SlideImage;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.domain.StoreNavigation;
import com.ansteel.shop.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2015/12/15.
 */
@Controller
@RequestMapping(Public.CLIENT+"/store")
public class StoreHomeContoller {

    @Autowired
    StoreService storeService ;

    @Autowired
    StoreNavigationService storeNavigationService;

    @Autowired
    StoreScoreService storeScoreService;

    @Autowired
    StoreGoodsClassService storeGoodsClassService;

    @Autowired
    GoodsCommonService goodsCommonService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model,
                       @RequestParam(value = "store_id") String storeId,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Store store=storeService.findOne(storeId);
        List<StoreNavigation> storeNavigation = storeNavigationService.findByStoreId(storeId);

        List<SlideImage> slideImageList = null;
        String storeSlideJson = store.getStoreSlide();
        if (StringUtils.hasText(storeSlideJson)) {
            slideImageList = JsonUtils.readValue(storeSlideJson, SlideImage.class);
        }

        //店铺评分
        StoreScoreModle storeScoreModle=storeScoreService.getStoreScore(store);
        model.addAttribute("P_STORE_SCORE", storeScoreModle);

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findByIsParentNull(storeId);
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        //推荐商品
        List<GoodsCommon> commendList=goodsCommonService.findTop20ByGoodsCommend(storeId);
        //最新商品
        List<GoodsCommon> newList=goodsCommonService.findTop20ByNew(storeId);
        model.addAttribute("P_GOODS_COMMEND",commendList);
        model.addAttribute("P_GOODS_NEW",newList);


        String style="default";
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STORE",store);
        model.addAttribute("P_STORE_NAV",storeNavigation);
        model.addAttribute("P_SLIDEIMAGE_LIST", slideImageList);
        return FisUtils.page("shop:pages/client/storehome/"+style+"/home.html");
    }

}
