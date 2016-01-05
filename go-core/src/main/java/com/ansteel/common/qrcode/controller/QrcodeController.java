package com.ansteel.common.qrcode.controller;

import com.ansteel.common.qrcode.service.QrcodeService;
import com.ansteel.core.constant.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2015/12/31.
 */
@Controller
@RequestMapping(value = Public.CLIENT + "/qrcode")
public class QrcodeController {

    @Autowired
    QrcodeService qrcodeService;

    @RequestMapping("/show")
    public void resource(
            @RequestParam(value = "content") String content,
            @RequestParam(value = "width") Integer width,
            @RequestParam(value = "height") Integer height,
            HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        try {
            qrcodeService.encoderQRCode(content,response.getOutputStream(),width,height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
