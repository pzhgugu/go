package com.ansteel.shop.storehome.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/12/15.
 */
@Controller
@RequestMapping(Public.CLIENT+"/store")
public class StoreHomeContoller {

    @Autowired
    StoreService storeService ;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model,
                       @RequestParam(value = "store_id") String storeId,
                        HttpServletRequest request,
                        HttpServletResponse response) {
        Store store=storeService.findOne(storeId);
        String style="default";
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/storehome/"+style+"/home.html");
    }

}
