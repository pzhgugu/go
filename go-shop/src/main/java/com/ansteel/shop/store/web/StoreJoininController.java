package com.ansteel.shop.store.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.JsonManagement;
import com.ansteel.shop.goods.service.GoodsClassService;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.domain.StoreClass;
import com.ansteel.shop.store.domain.StoreGrade;
import com.ansteel.shop.store.domain.StoreJoinin;
import com.ansteel.shop.store.service.StoreClassService;
import com.ansteel.shop.store.service.StoreGradeService;
import com.ansteel.shop.store.service.StoreJoininService;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 商家入驻
 * Created by Administrator on 2015/10/27.
 */
@Controller
@RequestMapping(value = Public.CLIENT+"/storejoinin")
public class StoreJoininController {

    @Autowired
    StoreJoininService storeJoininService;

    @Autowired
    StoreClassService storeClassService;

    @Autowired
    GoodsClassService goodsClassService;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreGradeService storeGradeService;

    @RequestMapping("/agreement")
    public String agreement(Model model,
                            @RequestParam(value = "state", required = false) String statePage,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        String jump = jump(statePage);
        if (jump != null) {
            return jump;
        }
        model.addAttribute("P_STEP", 1);
        return FisUtils.page("shop:pages/client/store/storeagreement.html");

    }

    public String jump(String statePage) {
        Store store = storeService.getCurrentStore();
        if (store != null) {
            return "redirect:/se/goods/addstep/one";
        }
        StoreJoinin storeJoinin = storeJoininService.getCurrentUserStoreJoinin();
        if (storeJoinin != null) {
            String state = storeJoinin.getJoininState();
            if (StringUtils.hasText(state) && state.equals("30") && !StringUtils.hasText(statePage)) {
                return "redirect:/storejoinin/step1";
            } else if (StringUtils.hasText(state)) {
                return "redirect:/storejoinin/step4";
            }
        }

        return null;
    }

    @RequestMapping("/step1")
    public String stepOne(Model model,
                          HttpServletRequest request,
                          HttpServletResponse response) {

        model.addAttribute("P_STEP", 1);
        model.addAttribute("P_SIDEBAR", 1);
        return FisUtils.page("shop:pages/client/store/settledstepone.html");

    }

    @RequestMapping(value = "/step2", method = RequestMethod.POST)
    public String stepTwo(@Valid StoreJoinin storeJoinint,
                          BindingResult result,
                          Model model,
                          @RequestParam("businessLicenceNumber_electronic") MultipartFile businessLicenceNumberFile,//营业执照号电子版
                          @RequestParam("organization_code_electronic") MultipartFile organizationCodeFile,//组织机构代码证电子版
                          @RequestParam("general_taxpayer") MultipartFile generalTaxpayerFile,//一般纳税人证明
                          HttpServletRequest request,
                          HttpServletResponse response) {

        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }

        storeJoininService.save(storeJoinint, businessLicenceNumberFile, organizationCodeFile, generalTaxpayerFile);
        model.addAttribute("P_STEP", 2);
        model.addAttribute("P_SIDEBAR", 2);
        return FisUtils.page("shop:pages/client/store/settledsteptwo.html");

    }

    @RequestMapping(value = "/step3", method = RequestMethod.POST)
    public String stepThree(@Valid StoreJoinin storeJoinint,
                            BindingResult result,
                            Model model,
                            @RequestParam("bank_licence_electronic") MultipartFile bankLicenceElectronicFile,//开户银行许可证电子版
                            @RequestParam("taxRegistrationCertificate_electronic") MultipartFile taxRegistrationCertificateElectronicFile,//税务登记证号电子版
                            HttpServletRequest request,
                            HttpServletResponse response) {

        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }

        storeJoininService.save(storeJoinint, bankLicenceElectronicFile, taxRegistrationCertificateElectronicFile);
        //店铺分类
        List<StoreClass> storeClassParentList = storeClassService.findByParentIsNull();
        //店铺等级
        List<StoreGrade> storeGradeList = storeGradeService.findAll();
        //经营类目
        List<GoodsClass> goodsClassParentList = goodsClassService.findByParentIsNull();

        model.addAttribute("P_STEP", 3);
        model.addAttribute("P_SIDEBAR", 3);
        model.addAttribute("P_PARENT_STORECLASS", storeClassParentList);
        model.addAttribute("P_PARENT_GOODSCLASS", goodsClassParentList);
        model.addAttribute("P_STOREGRADE", storeGradeList);
        return FisUtils.page("shop:pages/client/store/settledstepthree.html");

    }

    @RequestMapping(value = "/step4", method = RequestMethod.POST)
    public String stepFour(@Valid StoreJoinin storeJoinint,
                           BindingResult result,
                           @RequestParam(value = "store_class_ids[]") String[] ids,
                           @RequestParam(value = "store_class_names[]") String[] names,
                           Model model, HttpServletRequest request,
                           HttpServletResponse response) {

        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }

        StoreJoinin dataBase = storeJoininService.save(storeJoinint, ids, names);

        model.addAttribute("P_STEP", 4);
        model.addAttribute("P_SIDEBAR", 4);
        model.addAttribute("P_STOREJOININ", dataBase);
        return FisUtils.page("shop:pages/client/store/settledstepfour.html");

    }

    @RequestMapping(value = "/step4", method = RequestMethod.GET)
    public String stepFour(Model model, HttpServletRequest request,
                           HttpServletResponse response) {

        StoreJoinin storeJoinin = storeJoininService.getCurrentUserStoreJoinin();

        Store store = storeService.getCurrentStore();
        if (store != null) {
            return "redirect:/se/goods/addstep/one";
        }

        model.addAttribute("P_STEP", 4);
        model.addAttribute("P_SIDEBAR", 4);
        model.addAttribute("P_STOREJOININ", storeJoinin);
        return FisUtils.page("shop:pages/client/store/settledstepfour.html");

    }


    @RequestMapping("/verification/sellerName")
    public
    @ResponseBody
    boolean verificationSellerName(@RequestParam(value = "sellerName") String sellerName,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        return storeJoininService.verificationSellerName(sellerName);
    }

    @RequestMapping("/verification/storeName")
    public
    @ResponseBody
    boolean verificationStoreName(@RequestParam(value = "storeName") String storeName,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        return storeJoininService.verificationStoreName(storeName);
    }


    @RequestMapping(value = "/pay")
    public String pay(Model model, HttpServletRequest request,
                      HttpServletResponse response) {
        StoreJoinin storeJoinin = storeJoininService.getCurrentUserStoreJoinin();
        if (!storeJoinin.getJoininState().equals("20") && !storeJoinin.getJoininState().equals("31")) {
            return "redirect:/storejoinin/agreement";
        }
        String scIds = storeJoinin.getStoreClassIds();
        List<JsonManagement> jsonManagementList = JsonUtils.readValue(scIds, JsonManagement.class);

        model.addAttribute("P_CLASS_LIST", jsonManagementList);
        model.addAttribute("P_STEP", 5);
        model.addAttribute("P_SIDEBAR", 5);
        model.addAttribute("P_STOREJOININ", storeJoinin);
        return FisUtils.page("shop:pages/client/store/settledpay.html");

    }


    @RequestMapping(value = "/paysave")
    public String paysave(
            @RequestParam("paying_money_certificate") MultipartFile certificate,//上传付款凭证
            @RequestParam("paying_money_certificate_explain") String explain,//备注
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        StoreJoinin storeJoinin = storeJoininService.getCurrentUserStoreJoinin();
        if (!storeJoinin.getJoininState().equals("20") && !storeJoinin.getJoininState().equals("31")) {
            return "redirect:/storejoinin/agreement";
        }
        storeJoinin.setPayingMoneyCertificateExplain(explain);
        storeJoininService.savePay(certificate, storeJoinin);

        return "redirect:/storejoinin/step4";

    }
}
