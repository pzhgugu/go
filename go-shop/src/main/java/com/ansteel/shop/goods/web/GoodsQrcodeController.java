package com.ansteel.shop.goods.web;

import com.ansteel.common.qrcode.service.QrcodeService;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/1/3.
 */
@Controller
@RequestMapping(Public.CLIENT+"/goodsqrcode")
public class GoodsQrcodeController {

    @Autowired
    QrcodeService qrcodeService;

    @RequestMapping("/show")
    public void resource(
            @RequestParam(value = "goods_id") String goods_id,
            @RequestParam(value = "width",required = false) Integer width,
            @RequestParam(value = "height",required = false) Integer height,
            HttpServletRequest request,
            HttpServletResponse response, Model model) {
        String content= RequestUtils.getSiteURLEx(request)+"/cl/goods/show?goods_id="+goods_id;
        if(width==null){
            width=150;
        }
        if(height==null){
            height=150;
        }
        try {
            qrcodeService.encoderQRCode(content,response.getOutputStream(),width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
