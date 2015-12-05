package com.ansteel.shop.store.web;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.constant.ShopConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/18.
 */

@Controller
@RequestMapping(ShopConstant.SELLER)
public class SellerHomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response) {


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺首页");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "home");
        model.addAttribute("P_VIEW", "shop:pages/seller/home.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }
}
