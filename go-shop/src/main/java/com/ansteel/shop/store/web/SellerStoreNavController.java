package com.ansteel.shop.store.web;


import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.store.domain.StoreNavigation;
import com.ansteel.shop.store.service.StoreNavigationService;
import com.ansteel.shop.utils.JavaScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Public.SELLER + "/storenav")
public class SellerStoreNavController {

    @Autowired
    StoreNavigationService storeNavigationService;


    @RequestMapping("/list")
    public String list(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {


        List<StoreNavigation> storeNavigationList = storeNavigationService.getCurrentStoreNavigation();


        model.addAttribute("P_CURRENT_LIST", storeNavigationList);

        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺导航");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeNav");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/storenav.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }


    @RequestMapping("/add")
    public String addPage(Model model,
                          @RequestParam(value = "id", required = false) String id,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        StoreNavigation storeNavigation = null;
        if (StringUtils.hasText(id)) {
            storeNavigation = storeNavigationService.findOne(id);
        }

        model.addAttribute("P_CURRENT_ENTITY", storeNavigation);
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺导航");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeNav");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/addnav.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/save")
    public String save(@Valid StoreNavigation storeNavigation,
                       BindingResult result,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {


        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }

        storeNavigationService.saveCurrent(storeNavigation);
        return "redirect:/se/storenav/list";
    }

    @RequestMapping("/del")
    public void del(Model model,
                    @RequestParam(value = "sn_id") String id,
                    HttpServletRequest request,
                    HttpServletResponse response) {

        String name = "删除成功";
        storeNavigationService.delete(id);
        String url = request.getContextPath() + "/se/storenav/list";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
