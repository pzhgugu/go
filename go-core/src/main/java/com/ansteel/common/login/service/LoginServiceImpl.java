package com.ansteel.common.login.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/1/6.
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Value("${go_pro.is_login_captcha}")
    private String is_login_captcha;

    @Override
    public String getIsLoginCaptcha() {
        return is_login_captcha;
    }
}
