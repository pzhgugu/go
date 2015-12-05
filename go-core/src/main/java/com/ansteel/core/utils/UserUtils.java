package com.ansteel.core.utils;

import com.ansteel.common.springsecurity.service.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * Created by Administrator on 2015/11/19.
 */
public class UserUtils {

    public static UserInfo getUserInfo() {
        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }

    public static String getUserName() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return getUserInfo().getUsername();
    }

    public static String getUserAlias() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return getUserInfo().getAlias();
    }

    public static String getUserId() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return getUserInfo().getId();
    }

    public static Date getUserLoginTime() {
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return null;
        }
        return getUserInfo().getLoginTime();
    }
}
