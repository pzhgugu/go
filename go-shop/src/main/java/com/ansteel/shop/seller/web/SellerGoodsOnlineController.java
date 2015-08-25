package com.ansteel.shop.seller.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.JsonGoodsClass;
import com.ansteel.shop.goods.service.GoodsService;
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
    GoodsService goodsService;

    public static int PAGE_SIZE=20;


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
        Page<Goods> page=goodsService.findCurrentSaleAll(classId, sortType, curPage, PAGE_SIZE, name, value);

        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_GOODS_LIST", page.getContent());
        model.addAttribute("P_CURRENT_OP", "Online");
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
            goodsService.unShow(goodsIdArray);
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
            goodsService.adEdit(goodsIdArray,adWord);
        }

        String url=request.getContextPath()+"/se/goodsonline/list";
        String name="操作成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

}
