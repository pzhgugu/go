package com.ansteel.shop.store.web;


import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.attachment.service.FileAttachmentService;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.*;
import com.ansteel.shop.goods.domain.JsonManagement;
import com.ansteel.shop.store.domain.*;
import com.ansteel.shop.store.service.StoreGradeService;
import com.ansteel.shop.store.service.StoreJoininService;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(Public.SELLER + "/store")
public class SellerStoreController {

    @Autowired
    StoreJoininService storeJoininService;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreGradeService storeGradeService;

    @Autowired
    FileAttachmentService fileAttachmentService;

    @RequestMapping("/info")
    public String info(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {


        StoreJoinin storeJoinin = storeJoininService.getCurrentUserStoreJoinin();
        String scIds = storeJoinin.getStoreClassIds();
        List<JsonManagement> jsonManagementList = JsonUtils.readValue(scIds, JsonManagement.class);
        model.addAttribute("P_CLASS_LIST", jsonManagementList);
        model.addAttribute("P_STOREJOININ", storeJoinin);


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺信息");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeInfo");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/storeinfo.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/set/list")
    public String setList(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        Store store = storeService.getCurrentStore();
        StoreGrade storeGrade = storeGradeService.findOne(store.getGradeId());
        store.setGradeName(storeGrade.getName());


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺设置");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_STORE", store);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeSet");
        model.addAttribute("P_SET", "set");
        model.addAttribute("P_VIEW", "shop:pages/seller/store/setlist.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void save(@Valid Store store, BindingResult result,
                     @RequestParam("store_label") MultipartFile storeLabel,
                     @RequestParam("store_banner") MultipartFile storeBanner,
                     Model model,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        String name = "保存成功";
        storeService.update(store, storeLabel, storeBanner);
        String url = request.getContextPath() + "/se/store/set/list";
        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/slide")
    public String storeSlide(Model model,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        Store store = storeService.getCurrentStore();

        List<SlideImage> slideImageList = null;
        String storeSlideJson = store.getStoreSlide();
        if (StringUtils.hasText(storeSlideJson)) {
            slideImageList = JsonUtils.readValue(storeSlideJson, SlideImage.class);
        }


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "店铺");
        nav.put("n3", "店铺设置");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_STORE", store);
        model.addAttribute("P_CURRENT_TOP", "store");
        model.addAttribute("P_CURRENT_OP", "storeSet");
        model.addAttribute("P_SET", "slide");
        model.addAttribute("P_SLIDEIMAGE_LIST", slideImageList);
        model.addAttribute("P_VIEW", "shop:pages/seller/store/setslide.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/slide/imagesave")
    public
    @ResponseBody
    SlideImageJson imageSave(@RequestParam(value = "name") String name,
                             @RequestParam(value = "id") String id,
                             @RequestParam(value = "file_id") String file_id,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = mhs.getFile(id);


        SlideImageJson slideImageJson = new SlideImageJson();

        try {
            Attachment att = fileAttachmentService.save(multipartFile);
            slideImageJson.setFile_name(att.getId());
            slideImageJson.setFile_id(att.getId());
        } catch (Exception e) {
            e.printStackTrace();
            slideImageJson.setError(e.getMessage());
        }


        slideImageJson.setId(id);
        return slideImageJson;
    }

    @RequestMapping("/slide/save")
    public void del(Model model,
                    @RequestParam(value = "image_url[]") String[] imageUrl,
                    @RequestParam(value = "image_path[]") String[] imagePath,
                    HttpServletRequest request,
                    HttpServletResponse response) {

        Store store = storeService.getCurrentStore();
        String name = "保存成功";
        List<SlideImage> slideImageList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            SlideImage slideImage = new SlideImage();
            slideImage.setAttId(imagePath[i]);
            slideImage.setUrl(imageUrl[i]);
            slideImageList.add(slideImage);
        }
        store.setStoreSlide(JsonUtils.jsonFromObject(slideImageList));
        storeService.save(store);
        String url = request.getContextPath() + "/se/store/slide";

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

}
