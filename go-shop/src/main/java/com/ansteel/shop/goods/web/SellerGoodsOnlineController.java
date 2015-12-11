package com.ansteel.shop.goods.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.service.GoodsCommonService;
import com.ansteel.shop.goods.service.GoodsService;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.domain.StoreWarning;
import com.ansteel.shop.store.service.StorePlateService;
import com.ansteel.shop.store.service.StoreWarningService;
import com.ansteel.shop.utils.JavaScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.constant.ShopConstant;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = ShopConstant.SELLER + "/goodsonline")
public class SellerGoodsOnlineController {

    @Autowired
    GoodsCommonService goodsCommonService;

    @Autowired
    GoodsService goodsService;

    public static int PAGE_SIZE=20;

    @Autowired
    StorePlateService storePlateService;

    @Autowired
    StoreWarningService storeWarningService;


    @RequestMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "sort", required = false) String sortType,
                       @RequestParam(value = "curpage", required = false) Integer curPage,
                       @RequestParam(value = "class_id", required = false) String classId,
                       @RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "value", required = false) String value,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        //查询出售中商品列表
        Page<GoodsCommon> page=goodsCommonService.findCurrentSaleAll(classId, sortType, curPage, PAGE_SIZE, name, value);

        List<GoodsCommon> goodsCommonList = page.getContent();
        for(GoodsCommon gc:goodsCommonList){
            Integer grossInventory=goodsService.grossInventory(gc.getId());
            gc.setGoodsStorage(grossInventory);
        }

        StoreWarning storeWarning = storeWarningService.findCurrentStore();
        model.addAttribute("P_STOREWARNING_VALUE", storeWarning.getWarningValue());
        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_GOODS_LIST", page.getContent());
        model.addAttribute("P_CURRENT_OP", "Online");
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "出售中的商品");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsOnlineList.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }


    @RequestMapping("/unshow")
    public
    @ResponseBody
    void unShow(
            @RequestParam("commonid") String commonIds,
            HttpServletRequest request,
            HttpServletResponse response) {

        if(StringUtils.hasText(commonIds)){
            String[] goodsIdArray=commonIds.split(",");
            goodsCommonService.unShow(goodsIdArray);
        }

        String url=request.getContextPath()+"/se/goodsonline/list";
        String name="商品下架成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/ad")
    public String ad(Model model,
                     @RequestParam("commonid") String commonIds,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        model.addAttribute("P_COMMONID", commonIds);
        return FisUtils.page("shop:pages/seller/goodsOnline/ad.html");
    }

    @RequestMapping("/ad/edit")
    public
    @ResponseBody
    void adEdit(
            @RequestParam("commonid") String commonIds,
            @RequestParam("g_jingle") String adWord,
            HttpServletRequest request,
            HttpServletResponse response) {

        if(StringUtils.hasText(commonIds)){
            String[] goodsIdArray=commonIds.split(",");
            goodsCommonService.adEdit(goodsIdArray,adWord);
        }

        String url=request.getContextPath()+"/se/goodsonline/list";
        String name="操作成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }


    @RequestMapping("/edit/position")
    public String editPosition(Model model,
                               @RequestParam(value = "commonid") String commonid,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        List<StorePlate> storePlateList=storePlateService.findAllCurrentStore();
        model.addAttribute("P_COMMONID",commonid);
        model.addAttribute("P_STOREPLATE_LIST", storePlateList);
        return FisUtils.page("shop:pages/seller/goodsOnline/plateEditPosition.html");
    }

    @RequestMapping("/save/position")
    @ResponseBody
    public void savePosition(Model model,
                             @RequestParam(value = "commonid") String commonid,
                             @RequestParam(value = "plate_top") String plateTop,
                             @RequestParam(value = "plate_bottom") String plateBottom,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        if(StringUtils.hasText(commonid)) {
            String[] ids=commonid.split(",");
            goodsCommonService.savePosition(ids, plateTop, plateBottom);
        }

        String url=request.getContextPath()+"/se/goodsonline/list";
        String name="操作成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
