package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JavaScriptUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.goods.domain.*;
import com.ansteel.shop.goods.service.GoodsClassService;
import com.ansteel.shop.goods.service.GoodsSpecService;
import com.ansteel.shop.goods.service.GoodsSpecValueService;
import com.ansteel.shop.goods.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2015/11/24.
 */
@Controller
@RequestMapping(Public.SELLER + "/spec")
public class SellerGoodsSpecValueController {

    @Autowired
    GoodsClassService goodsClassService;

    @Autowired
    GoodsTypeService goodsTypeService;

    @Autowired
    GoodsSpecService goodsSpecService;

    @Autowired
    GoodsSpecValueService goodsSpecValueService;

    @RequestMapping("/list")
    public String list(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        List<GoodsClass> goodsClassList = goodsClassService.findByParentIsNull();
        model.addAttribute("P_GOODSCLASS_LIST", goodsClassList);


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "商品规格");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "goods");
        model.addAttribute("P_CURRENT_OP", "spec");
        model.addAttribute("P_VIEW", "shop:pages/seller/spec/storespeclist.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/class")
    public
    @ResponseBody
    JsonGoodsClassData getClass(
            @RequestParam("id") String id,
            @RequestParam("deep") Integer deep,
            HttpServletRequest request,
            HttpServletResponse response) {

        JsonGoodsClassData json = new JsonGoodsClassData();
        if (deep != null && deep == 3) {
            List<GoodsType> goodsTypeList = goodsTypeService.findByGoodsClass(id);
            Assert.isTrue(goodsTypeList.size() == 1, id + ",此ID在商品类型表中数据异常（重复或者没有）。");
            GoodsType goodsType = goodsTypeList.get(0);
            String typeId = goodsType.getId();
            Collection<GoodsSpec> goodsSpecsList = goodsType.getGoodsSpecs();
            List<JsonGoodsSpec> data = new ArrayList<JsonGoodsSpec>();
            for (GoodsSpec goodsSpec : goodsSpecsList) {
                JsonGoodsSpec jsonGoodsSpec = new JsonGoodsSpec();
                jsonGoodsSpec.setType_id(typeId);
                jsonGoodsSpec.setSp_id(goodsSpec.getId());
                jsonGoodsSpec.setSp_name(goodsSpec.getSpName());
                jsonGoodsSpec.setSp_sort(goodsSpec.getSpSort());
                jsonGoodsSpec.setClass_id(id);
                data.add(jsonGoodsSpec);
            }
            json.setType("spec");
            json.setData(data);
            json.setGcid(id);
            json.setDeep(deep + 1);
        } else {

            List<GoodsClass> goodsClassList = goodsClassService.findByParentId(id);

            List<JsonGoodsClass> data = new ArrayList<JsonGoodsClass>();
            for (GoodsClass gc : goodsClassList) {
                JsonGoodsClass jgc = new JsonGoodsClass();
                jgc.setGc_id(gc.getId());
                jgc.setGc_name(gc.getAlias());
                jgc.setType_id("0");
                data.add(jgc);
            }
            json.setType("class");
            json.setData(data);
            json.setDeep(deep + 1);
        }
        return json;
    }

    @RequestMapping("/addPage")
    public String addPage(Model model,
                          @RequestParam("spid") String spid,
                          @RequestParam("gcid") String gcid,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        GoodsClass goodsClass = goodsClassService.findOne(gcid);
        GoodsSpec goodsSpec = goodsSpecService.findOne(spid);

        List<GoodsSpecValue> goodsSpecValueList = goodsSpecValueService.findByStoreIdAndSpId(spid);

        model.addAttribute("P_GOODS_CLASS", goodsClass);
        model.addAttribute("P_GOODS_SPEC", goodsSpec);
        model.addAttribute("P_SPECVALUE_LIST", goodsSpecValueList);
        model.addAttribute("DEFAULT_SPEC_COLOR", "颜色");

        return FisUtils.page("shop:pages/seller/spec/storespecadd.html");
    }

    @RequestMapping("/save")
    @ResponseBody
    public void svae(Model model,
                     GoodsSpecValueModel goodsSpecValueModel,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        String url = request.getContextPath() + "/se/spec/addPage?spid=" + goodsSpecValueModel.getSpId() + "&gcid=" + goodsSpecValueModel.getGcId();
        String name = "保存成功";
        try {
            goodsSpecValueService.save(goodsSpecValueModel);
        } catch (Exception e) {
            name = "保存失败：" + e.getMessage();
        }

        ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
    }

    @RequestMapping("/del")
    @ResponseBody
    public boolean delete(Model model,
                          @RequestParam("id") String id,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            goodsSpecValueService.delete(id);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @RequestMapping("/addspec")
    @ResponseBody
    public JsonDone addspec(Model model,
                               @RequestParam("gc_id") String gcId,
                               @RequestParam("sp_id") String spId,
                               @RequestParam("name") String name,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        GoodsSpecValue goodsSpecValue = goodsSpecValueService.save(gcId, spId, name);
        JsonDone jsonAddspec = new JsonDone();
        jsonAddspec.setDone(true);
        jsonAddspec.setValue_id(goodsSpecValue.getId());
        return jsonAddspec;
    }
}
