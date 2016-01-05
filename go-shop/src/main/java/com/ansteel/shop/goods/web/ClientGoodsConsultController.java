package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.context.ValidationCodeServlet;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.GoodsConsult;
import com.ansteel.shop.goods.domain.JsonDone;
import com.ansteel.shop.goods.service.GoodsConsultService;
import com.ansteel.shop.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Created by Administrator on 2016/1/4.
 */
@Controller
@RequestMapping(value = Public.CLIENT + "/goodsconsult")
public class ClientGoodsConsultController {

    @Autowired
    GoodsConsultService goodsConsultService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/submit")
    @ResponseBody
    public Object submit(@Valid GoodsConsult goodsConsult,
                      BindingResult result,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        JsonDone json =new JsonDone();
        try {
            goodsConsultService.save(goodsConsult);
        }catch (Exception e){
            json.setDone(false);
            json.setMessage(e.getMessage());
            return json;
        }
        json.setDone(true);
        return json;

    }

    @RequestMapping("/validation")
    @ResponseBody
    public Boolean validation(Model model,
                         @RequestParam(value = "captcha") String captcha,
                         HttpServletRequest request,
                         HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        String validationCode= (String) session.getAttribute(ValidationCodeServlet.VALIDATION_CODE);
        if(captcha.equals(validationCode)){
            return true;
        }
        return false;

    }

    @RequestMapping("/cosulting")
    public String cosulting(Model model,
                            @RequestParam(value = "style") String style,
                            @RequestParam(value = "goodsId") String goodsId,
                            @RequestParam(value = "storeId") String storeId,
                            @RequestParam(value = "curpage", required = false) Integer curPage,
                            HttpServletRequest request,
                            HttpServletResponse response){

        if(curPage==null){
            curPage=0;
        }
        //咨询内容
        Page<GoodsConsult> consultPage=goodsConsultService.findByGoodsIdOrderByCreatedDesc(goodsId,curPage,10);
        model.addAttribute("P_CONSULT_PAGE",consultPage);

        Goods goods=goodsService.findOne(goodsId);
        model.addAttribute("P_GOODS", goods);
        model.addAttribute("P_STORE_id", storeId);
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/buy/" + style + "/goodsdetail/cosulting.html");
    }
}
