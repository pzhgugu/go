package com.ansteel.shop.login.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = Public.CLIENT + "/sl")
public class LoginShopController {

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public String login(Model model,HttpServletRequest request,
                        HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        Object errorObject = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if(errorObject instanceof AuthenticationException){
            AuthenticationException e =(AuthenticationException) errorObject;
            String message = e.getMessage();
            if(message.equals("Bad credentials")){
                model.addAttribute("errorMessage", "密码错误！");
            }else{
                model.addAttribute("errorMessage", e.getMessage());
            }
        }
        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/buy/"+style+"/login/login.html");
    }
}
