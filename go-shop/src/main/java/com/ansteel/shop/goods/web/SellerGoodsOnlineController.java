package com.ansteel.shop.goods.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.JavaScriptUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.GoodsCommon;
import com.ansteel.shop.goods.service.GoodsCommonService;
import com.ansteel.shop.goods.service.GoodsService;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.domain.StoreWarning;
import com.ansteel.shop.store.service.StoreGoodsClassService;
import com.ansteel.shop.store.service.StorePlateService;
import com.ansteel.shop.store.service.StoreWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ansteel.core.utils.FisUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = Public.SELLER + "/goodsonline")
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

    @Autowired
    StoreGoodsClassService storeGoodsClassService;


    @RequestMapping("/outline")
    public String outLine(Model model,
                       @RequestParam(value = "sort", required = false) String sortType,
                       @RequestParam(value = "curpage", required = false) Integer curPage,
                       @RequestParam(value = "stc_id", required = false) String classId,
                       @RequestParam(value = "search_type", required = false) String name,
                       @RequestParam(value = "keyword", required = false) String value,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findCurrentByIsParentNull();
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        //查询违规商品列表
        Page<GoodsCommon> page=goodsCommonService.query(classId, sortType, curPage, PAGE_SIZE, name, value,10,1);

       /* List<GoodsCommon> goodsCommonList = page.getContent();
        for(GoodsCommon gc:goodsCommonList){
            Integer grossInventory=goodsService.grossInventory(gc.getId());
            gc.setGoodsStorage(grossInventory);
        }*/

        StoreWarning storeWarning = storeWarningService.findCurrentStore();
        model.addAttribute("P_STOREWARNING_VALUE", storeWarning.getWarningValue());
        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_GOODS_LIST", page.getContent());
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "仓库中的商品");
        model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsOutlineList.html.jsp");
        model.addAttribute("P_CURRENT_OP", "Offline");
        model.addAttribute("P_NAV", nav);
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/verify")
    public String verify(Model model,
                          @RequestParam(value = "sort", required = false) String sortType,
                          @RequestParam(value = "curpage", required = false) Integer curPage,
                          @RequestParam(value = "stc_id", required = false) String classId,
                          @RequestParam(value = "search_type", required = false) String name,
                          @RequestParam(value = "keyword", required = false) String value,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findCurrentByIsParentNull();
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        //查询审核商品列表
        Page<GoodsCommon> page=goodsCommonService.query(classId, sortType, curPage, PAGE_SIZE, name, value,null,11);

        List<GoodsCommon> goodsCommonList = page.getContent();
        for(GoodsCommon gc:goodsCommonList){
            Integer grossInventory=goodsService.grossInventory(gc.getId());
            gc.setGoodsStorage(grossInventory);
        }

        StoreWarning storeWarning = storeWarningService.findCurrentStore();
        model.addAttribute("P_STOREWARNING_VALUE", storeWarning.getWarningValue());
        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_GOODS_LIST", page.getContent());
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "仓库中的商品");
        model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsVerifyList.html.jsp");
        model.addAttribute("P_CURRENT_OP", "Offline");
        model.addAttribute("P_NAV", nav);
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }


    @RequestMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "sort", required = false) String sortType,
                       @RequestParam(value = "curpage", required = false) Integer curPage,
                       @RequestParam(value = "stc_id", required = false) String classId,
                       @RequestParam(value = "search_type", required = false) String name,
                       @RequestParam(value = "keyword", required = false) String value,
                       @RequestParam(value="goodsState",required = false) Integer goodsState,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findCurrentByIsParentNull();
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);

        if(goodsState==null){
            goodsState=1;
        }

        //查询出售中商品列表
        Page<GoodsCommon> page=goodsCommonService.query(classId, sortType, curPage, PAGE_SIZE, name, value, goodsState,1);

        List<GoodsCommon> goodsCommonList = page.getContent();
        for(GoodsCommon gc:goodsCommonList){
            Integer grossInventory=goodsService.grossInventory(gc.getId());
            gc.setGoodsStorage(grossInventory);
        }

        StoreWarning storeWarning = storeWarningService.findCurrentStore();
        model.addAttribute("P_STOREWARNING_VALUE", storeWarning.getWarningValue());
        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_GOODS_LIST", page.getContent());
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        if(goodsState==0){
            nav.put("n3", "仓库中的商品");
            model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsOfflineList.html.jsp");
            model.addAttribute("P_CURRENT_OP", "Offline");
        }else {
            nav.put("n3", "出售中的商品");
            model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsOnlineList.html.jsp");
            model.addAttribute("P_CURRENT_OP", "Online");
        }

        model.addAttribute("P_NAV", nav);
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
            goodsCommonService.isShow(goodsIdArray,0);
        }

        String url=request.getContextPath()+"/se/goodsonline/list";
        String name="商品下架成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/show")
    public
    @ResponseBody
    void show(
            @RequestParam("commonid") String commonIds,
            HttpServletRequest request,
            HttpServletResponse response) {

        if(StringUtils.hasText(commonIds)){
            String[] goodsIdArray=commonIds.split(",");
            goodsCommonService.isShow(goodsIdArray, 1);
        }

        String url=request.getContextPath()+"/se/goodsonline/list?goodsState=0";
        String name="商品上架成功";
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


    @RequestMapping("/del")
    public
    @ResponseBody
    void delect(
            @RequestParam("commonid") String commonIds,
            @RequestParam("goodsState") Integer goodsState,
            HttpServletRequest request,
            HttpServletResponse response) {
        String[] goodsCommonIds=commonIds.split(",");

        String name="删除成功";
        try {
            goodsCommonService.delect(goodsCommonIds);
        }catch (Exception e){
            name="删除失败："+e.getMessage();
        }
        String url=request.getContextPath()+"/se/goodsonline/list?goodsState="+goodsState;
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
