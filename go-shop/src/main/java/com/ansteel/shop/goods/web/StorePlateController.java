package com.ansteel.shop.goods.web;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.store.service.StorePlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/25.
 */
@Controller
@RequestMapping(value = ShopConstant.SELLER + "/plate")
public class StorePlateController {

    @Autowired
    StorePlateService storePlateService;

    @RequestMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "sort", required = false) String sortType,
                       @RequestParam(value = "curpage", required = false) Integer curPage,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        model.addAttribute("P_CURRENT_OP", "plate");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "关联板式");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/plate/plateList.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/add/page")
    public String addPage(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        model.addAttribute("P_CURRENT_OP", "plate");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "关联板式");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/plate/plateAdd.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }
}
