package com.ansteel.shop.member.web;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/shop/member_order")
public class MemberShopOrderController {

    @RequestMapping("/new")
    public String newOrder(Model model,
                        HttpServletRequest request,
                        HttpServletResponse response){


        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_OP","set");
        model.addAttribute("P_VIEW","new_order_list.html.jsp");
        return FisUtils.page("shop:pages/client/member/" + style + "/framework.html");
    }
}
