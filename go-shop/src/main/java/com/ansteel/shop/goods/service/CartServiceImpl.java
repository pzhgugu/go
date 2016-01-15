package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.Cart;
import com.ansteel.shop.goods.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Override
    public List<Cart> findByMemberId() {
        String userId=UserUtils.getUserId();
        if(StringUtils.hasText(userId)) {
            return cartRepository.findByMemberId(userId);
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
