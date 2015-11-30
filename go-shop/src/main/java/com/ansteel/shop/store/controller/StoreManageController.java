package com.ansteel.shop.store.controller;

import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.goods.domain.JsonManagement;
import com.ansteel.shop.store.domain.StoreJoinin;
import com.ansteel.shop.store.service.StoreJoininService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2015/10/31.
 */
@Controller
@RequestMapping(value = Public.ADMIN + "/storemanage")
public class StoreManageController {

    @Autowired
    StoreJoininService storeJoininService;

    @RequestMapping("/home")
    public String home(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        return FisUtils.page("shop:pages/admin/store/storeManage.html");
    }

    @RequestMapping("/verify")
    public String verifyPage(Model model,
                             @RequestParam(value = "id") String id,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        StoreJoinin storeJoinin = storeJoininService.findOne(id);
        String scIds = storeJoinin.getStoreClassIds();
        List<JsonManagement> jsonManagementList = JsonUtils.readValue(scIds, JsonManagement.class);
        model.addAttribute("P_CLASS_LIST", jsonManagementList);
        model.addAttribute("P_STOREJOININ", storeJoinin);
        return FisUtils.page("shop:pages/admin/store/storeVerify.html");
    }

    @RequestMapping(value = "/verifyinfo", method = RequestMethod.POST)
    public String verifyInfo(Model model,
                             @RequestParam(value = "verify_type") String type,
                             @RequestParam(value = "joininMessage") String joininMessage,
                             @RequestParam(value = "id") String id,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        storeJoininService.verify(id, type, joininMessage);
        return FisUtils.page("shop:pages/admin/store/closeVerify.html");
    }

    @RequestMapping(value = "/verifypayment", method = RequestMethod.POST)
    public String verifyPayment(Model model,
                                @RequestParam(value = "verify_type") String type,
                                @RequestParam(value = "joininMessage") String joininMessage,
                                @RequestParam(value = "id") String id,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        storeJoininService.verify(id, type + "_payment", joininMessage);
        return FisUtils.page("shop:pages/admin/store/closeVerify.html");
    }
}
