package com.ansteel.shop.goods.web;

import com.ansteel.core.utils.*;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.service.StorePlateService;
import com.ansteel.shop.utils.JavaScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

    public static int PAGE_SIZE=20;

    @RequestMapping("/list")
    public String query(Model model,
                        @RequestParam(value = "p_position", required = false) String position,
                        @RequestParam(value = "p_name", required = false) String name,
                       @RequestParam(value = "sort", required = false) String sortType,
                       @RequestParam(value = "curpage", required = false) Integer curPage,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        Page<StorePlate> page=storePlateService.findByStoreId(position, name, sortType, curPage, PAGE_SIZE);

        model.addAttribute("P_PAGE_SHOW", page);
        model.addAttribute("P_PLATE_LIST", page.getContent());

        model.addAttribute("P_CURRENT_OP", "plate");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "关联板式");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/plate/plateList.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/edit/page")
    public String addPage(Model model,
                          @RequestParam(value = "id", required = false) String id,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        if(StringUtils.hasText(id)){
            StorePlate storePlate=storePlateService.findOne(id);
            model.addAttribute("P_STORE_PLATE", storePlate);
        }

        model.addAttribute("P_CURRENT_OP", "plate");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "关联板式");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/plate/plateAdd.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }


    @RequestMapping("/save")
    public
    @ResponseBody
    void save(@Valid StorePlate storePlate,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        if(StringUtils.hasText(storePlate.getId())){
            StorePlate dataBaseEntiy = storePlateService.findOne(storePlate.getId());
            Assert.notNull(dataBaseEntiy, storePlate.getId() + ",没有找到！");
            try {
                BeanUtils.applyIf(dataBaseEntiy, storePlate);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            storePlateService.save(dataBaseEntiy);
        }else {
            storePlateService.save(storePlate);
        }
        String url=request.getContextPath()+"/se/plate/list";
        String name="操作成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/del")
    public
    @ResponseBody
    void delete(@RequestParam(value = "p_id") String ids,
            HttpServletRequest request,
              HttpServletResponse response) {
        if(StringUtils.hasText(ids)){
            String[] idArray = ids.split(",");
            storePlateService.deleteCurrent(idArray);
        }
        String url=request.getContextPath()+"/se/plate/list";
        String name="删除成功";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }


}
