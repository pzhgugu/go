package com.ansteel.shop.storehome.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.service.GoodsClassService;
import com.ansteel.shop.goods.service.GoodsCommonService;
import com.ansteel.shop.store.domain.SlideImage;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.domain.StoreNavigation;
import com.ansteel.shop.store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

        //热销商品
        List<GoodsCommon> hotList=goodsCommonService.findTop5ByHot(storeId);
        //热门收藏
        List<GoodsCommon> collectList=goodsCommonService.findTop5ByHotCollect(storeId);
        model.addAttribute("P_GOODS_HOT",hotList);
        model.addAttribute("P_GOODS_COLLECT",collectList);


        String style=this.getCurrentStyle(storeId);
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STORE",store);
        model.addAttribute("P_STORE_NAV",storeNavigation);
        model.addAttribute("P_SLIDEIMAGE_LIST", slideImageList);
        return FisUtils.page("shop:pages/client/storehome/"+style+"/home.html");
    }

    private String getCurrentStyle(String storeId) {
        return "default";
    }

    @RequestMapping(value = "/query")
    public String query(Model model,
                        @RequestParam(value = "store_id") String storeId,
                        @RequestParam(value = "key", required = false) String  key,
                        @RequestParam(value = "order", required = false) String  order,
                        @RequestParam(value = "keyword", required = false) String  keyword,
                        @RequestParam(value = "stc_id", required = false) String  stcId,
                        @RequestParam(value = "curpage", required = false) Integer curPage,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Store store = storeService.findOne(storeId);
        List<StoreNavigation> storeNavigation = storeNavigationService.findByStoreId(storeId);

        String style=this.getCurrentStyle(storeId);

        //店铺评分
        StoreScoreModle storeScoreModle=storeScoreService.getStoreScore(store);
        model.addAttribute("P_STORE_SCORE", storeScoreModle);

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findByIsParentNull(storeId);
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        //热销商品
        List<GoodsCommon> hotList=goodsCommonService.findTop5ByHot(storeId);
        //热门收藏
        List<GoodsCommon> collectList=goodsCommonService.findTop5ByHotCollect(storeId);
        model.addAttribute("P_GOODS_HOT",hotList);
        model.addAttribute("P_GOODS_COLLECT",collectList);

        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STORE",store);
        model.addAttribute("P_STORE_NAV",storeNavigation);
        if(StringUtils.hasText(keyword)){
            model.addAttribute("P_STORE_TITLE", "含有\"" + keyword + "\"的商品");
        }else if(StringUtils.hasText(stcId)){
            StoreGoodsClass goodsClass = storeGoodsClassService.findOne(stcId);
            if(goodsClass!=null){
                model.addAttribute("P_STORE_TITLE",goodsClass.getName());
            }else{
                model.addAttribute("P_STORE_TITLE","分类查询");
            }
        }else{
            model.addAttribute("P_STORE_TITLE", "全部商品");
        }

        Page<GoodsCommon> page=goodsCommonService.queryStoreGoods(storeId, key, keyword, stcId,order,curPage,20);
        model.addAttribute("P_PAGE_SHOW", page);
        return FisUtils.page("shop:pages/client/storehome/"+style+"/queryHome.html");
    }

}
