package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.Cart;
import com.ansteel.shop.goods.web.CartSessionModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
public interface CartService {
    List<Cart> findByMemberId();

    Cart save(Cart cart);

    Cart saveOrUpdate(Cart cart);

    void delete(Cart cart);

    /**
     * 查询session中购物车与数据库购物车
     * @param request
     * @return
     */
    List<Cart> querySessionAndDataBase(HttpServletRequest request);

    List<Cart> addCartList(CartSessionModel csm, List<Cart> cartList, String userId, String url);

    Cart update(String goodsId, Integer quantity);

    void delete(String userId, String goodsId);
}
