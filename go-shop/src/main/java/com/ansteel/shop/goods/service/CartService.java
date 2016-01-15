package com.ansteel.shop.goods.service;

import com.ansteel.shop.goods.domain.Cart;

import java.util.List;

/**
 * Created by Administrator on 2016/1/15.
 */
public interface CartService {
    List<Cart> findByMemberId();

    Cart save(Cart cart);
}
