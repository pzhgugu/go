package com.ansteel.shop.store.web;


import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JavaScriptUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.goods.domain.GoodsBrand;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.service.GoodsBrandService;
import com.ansteel.shop.goods.service.GoodsClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Public.SELLER + "/brandApply")
public class SellerBrandApplyController {
    @Autowired
    GoodsBrandService goodsBrandService;
    @Autowired
    GoodsClassService goodsClassService;


    @Autowired
    AttachmentService attachmentService;

    @RequestMapping("/list")
    public String listInfo(Model model, @RequestParam(value = "brandName", required = false) String brandName,
                           @RequestParam(value = "sort", required = false) String sortType,
                           @RequestParam(value = "curpage", required = false) Integer curPage,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        Page<GoodsBrand> goodsBrandPage = goodsBrandService.findByCondition(brandName, sortType, curPage, 15);
        model.addAttribute("P_BRAND_GOODS", goodsBrandPage);

        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "品牌申请");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "brandApply");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/brandApply.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    /**
     * 开店申请跳转界面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/addBrandApply")
    public String addBrandApply(Model model, HttpServletRequest request,
                                HttpServletResponse response) {
        List<GoodsClass> goodsClassList = goodsClassService.findAll();
        model.addAttribute("GOODS_CLASS_LIST", goodsClassList);
        return FisUtils.page("shop:pages/seller/store/addBrandApply.html");
    }

    @RequestMapping("/brandSave")
    public String brandSave(@Valid GoodsBrand goodsBrand, BindingResult result, Model model,
                            @RequestParam(value = "idAtt1", required = false) MultipartFile file,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        if (file != null && file.getSize() > 0) {
            try {
                Attachment attachment = attachmentService.saveAttachment(file);
                goodsBrand.setLogoImage(attachment.getId());
            } catch (Exception e) {
                throw new PageException(e.getMessage());
            }
        }
        if (StringUtils.hasText(goodsBrand.getId())) {
            goodsBrandService.updateCurrent(goodsBrand);
        } else {
            goodsBrand.setBrandApply(0);
            goodsBrandService.saveCurrent(goodsBrand);
        }
        return "redirect:/se/brandApply/list";
    }

    /**
     * 开店申请编辑跳转界面
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/editorBrandApply")
    public String editorBrandApply(Model model, HttpServletRequest request, @RequestParam(value = "brandId") String brandId,
                                   HttpServletResponse response) {
        GoodsBrand goodsBrand = goodsBrandService.findOne(brandId);
        model.addAttribute("BRAND_GOODS", goodsBrand);
        List<GoodsClass> goodsClassList = goodsClassService.findAll();
        model.addAttribute("GOODS_CLASS_LIST", goodsClassList);
        return "shop/pages/seller/store/editorBrandApply.html";
    }

    @RequestMapping("/brandDelete")
    @ResponseBody
    public void brandDelete(@Valid GoodsBrand goodsBrand, BindingResult result, Model model, @RequestParam(value = "brandId", required = false) String brandId,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String url = request.getContextPath() + "/se/brandApply/list";
        if (result.hasErrors()) {
            JavaScriptUtils.BindingResultError(result, url, response);
            return;
        }
        String name = "";
        try {
            goodsBrandService.delete(brandId);
        } catch (Exception ex) {
            ex.printStackTrace();
            name = "删除失败！";
        }
        name = "删除成功！";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }
}
