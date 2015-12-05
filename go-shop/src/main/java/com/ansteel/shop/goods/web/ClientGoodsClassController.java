package com.ansteel.shop.goods.web;

import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.JsonGoodsClass;
import com.ansteel.shop.goods.service.GoodsClassService;
import com.ansteel.shop.utils.JavaScriptUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2015/10/29.
 */
@Controller
@RequestMapping(value = ShopConstant.CLIENT + "/goodsclass")
public class ClientGoodsClassController {

    @Autowired
    GoodsClassService goodsClassService;

    @RequestMapping("/jsonclass")
    public
    @ResponseBody
    String JsonClass(@RequestParam(value = "callback") String callback,
                     @RequestParam(value = "gc_id") String id,
                     HttpServletRequest request,
                     HttpServletResponse response) {
        Collection<GoodsClass> goodsClassList = goodsClassService.findChildren(id);
        List<JsonGoodsClass> JsonGoodsClassList = new ArrayList<>();
        for (GoodsClass goodsClass : goodsClassList) {
            JsonGoodsClass jsonGoodsClass = new JsonGoodsClass();
            jsonGoodsClass.setGc_id(goodsClass.getId());
            jsonGoodsClass.setGc_name(goodsClass.getAlias());
            jsonGoodsClass.setGc_parent_id(id);
            jsonGoodsClass.setGc_sor(goodsClass.getDisplayOrder());
            JsonGoodsClassList.add(jsonGoodsClass);
        }
        return JsonUtils.jsonCallback(callback, JsonGoodsClassList);
    }
}
