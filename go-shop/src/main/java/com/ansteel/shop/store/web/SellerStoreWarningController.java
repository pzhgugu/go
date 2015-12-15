package com.ansteel.shop.store.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreWarning;
import com.ansteel.shop.store.service.StoreService;
import com.ansteel.shop.store.service.StoreWarningService;
import com.ansteel.shop.utils.JavaScriptUtils;
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
 * Created by Administrator on 2015/12/11.
 */
@Controller
@RequestMapping(Public.SELLER + "/warning")
public class SellerStoreWarningController {


    @Autowired
    StoreWarningService storeWarningService;

    @RequestMapping("/edit")
    public String list(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {


        StoreWarning storeWarning=storeWarningService.findCurrentStore();

        model.addAttribute("P_STOREWARNING", storeWarning);
        model.addAttribute("P_CURRENT_OP", "storeWarning");
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "库存警报");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/GoodsAdd/storeWarning.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/save")
    public void del(Model model,
                    @RequestParam(value = "store_storage_alarm") Integer value,
                    HttpServletRequest request,
                    HttpServletResponse response) {

        String name = "保存成功";
        storeWarningService.save(value);
        String url = request.getContextPath() + "/se/warning/edit";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
