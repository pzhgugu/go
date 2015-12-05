package com.ansteel.shop.store.web;


import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.service.StoreGoodsClassService;
import com.ansteel.shop.utils.JavaScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(ShopConstant.SELLER + "/storegoodsclass")
public class SellerStoreGoodsClassController {


    @Autowired
    StoreGoodsClassService storeGoodsClassService;

    @RequestMapping("/list")
    public String storeClass(Model model,
                             @RequestParam(value = "sort", required = false) String sortType,
                             @RequestParam(value = "curpage", required = false) Integer curPage,
                             HttpServletRequest request,
                             HttpServletResponse response) {


        Page<StoreGoodsClass> page = storeGoodsClassService.findCurrentByIsParentNull("sequence", curPage, 20);
        model.addAttribute("P_PAGE_SHOW", page);


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺分类");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeClass");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/storegoodsclass.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/addpage")
    public String addPage(Model model,
                          @RequestParam(value = "id", required = false) String id,
                          @RequestParam(value = "parent", required = false) String parent,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        StoreGoodsClass storeGoodsClass = null;
        StoreGoodsClass storeGoodsClassParent = null;

        if (StringUtils.hasText(id)) {
            storeGoodsClass = storeGoodsClassService.findCurrentOne(id);
        }
        if (StringUtils.hasText(parent)) {
            storeGoodsClassParent = storeGoodsClassService.findCurrentOne(parent);
        }
        List<StoreGoodsClass> storeGoodsClassList = storeGoodsClassService.findCurrentByIsParentNull();
        model.addAttribute("P_STORE_GOODSCLASS_LIST", storeGoodsClassList);
        model.addAttribute("P_CURRENT_ENTITY", storeGoodsClass);
        model.addAttribute("P_PARENT_ENTITY", storeGoodsClassParent);
        return FisUtils.page("shop:pages/seller/store/addGoodsClass.html");
    }

    @RequestMapping("/del")
    public void del(Model model,
                    @RequestParam(value = "id") String id,
                    HttpServletRequest request,
                    HttpServletResponse response) {
        String name = "分类删除成功";
        try {
            storeGoodsClassService.deleteCurrent(id);
        } catch (Exception e) {
            name = e.getMessage();
        }
        String url = request.getContextPath() + "/se/storegoodsclass/list";

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/dels")
    public void del(Model model,
                    @RequestParam(value = "class_id") String[] ids,
                    HttpServletRequest request,
                    HttpServletResponse response) {
        String name = "分类删除成功";
        try {
            storeGoodsClassService.deleteCurrents(ids);
        } catch (Exception e) {
            name = e.getMessage();
        }
        String url = request.getContextPath() + "/se/storegoodsclass/list";

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }


    @RequestMapping("/save")
    @ResponseBody
    public void save(StoreGoodsClass storeGoodsClass, Model model,
                     @RequestParam(value = "stc_parent_id", required = false) String parentId,
                     @RequestParam(value = "id", required = false) String id,
                     HttpServletRequest request,
                     HttpServletResponse response) {


        String url = request.getContextPath() + "/se/storegoodsclass/list";
        String name = "分类保存成功";
        try {
            if (StringUtils.hasText(id)) {
                StoreGoodsClass dataBase = storeGoodsClassService.findCurrentOne(id);
                try {
                    BeanUtils.applyIf(dataBase, storeGoodsClass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                storeGoodsClassService.save(dataBase);
            } else {
                storeGoodsClassService.saveCurrent(storeGoodsClass, parentId);
            }
        } catch (Exception e) {
            name = e.getMessage();
        }
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
