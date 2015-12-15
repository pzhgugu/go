package com.ansteel.shop.core.context;

import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/12/15.
 */
public class ShopSellerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    StoreService storeService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        Store store=storeService.getCurrentStore();
        Assert.notNull(store,"请注册卖家后登陆！");
        request.setAttribute("G_STORE",store);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }
}
