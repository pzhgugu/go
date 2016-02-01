package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.shop.goods.service.GoodsOrderService;
import com.ansteel.shop.goods.service.OrderSerialNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/1/29.
 */
@Controller
@RequestMapping("/goodsorder")
public class GoodsOrderController {

    @Autowired
    GoodsOrderService goodsOrderService;

    @Autowired
    OrderSerialNumberService orderSerialNumberService;

    @RequestMapping("/show")
    public void resource(
            HttpServletRequest request,
            HttpServletResponse response, Model model) {
       String n= orderSerialNumberService.getOrderSerialNumber();
        int m=3;
    }
}
