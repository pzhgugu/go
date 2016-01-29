package com.ansteel.shop.goods.repository;

import com.ansteel.core.repository.ProjectRepository;
import com.ansteel.shop.goods.domain.Cart;

import java.util.List;

/**
 * 购物车
 */
public interface CartRepository extends ProjectRepository<Cart,String> {
    List<Cart> findByMemberId(String userId);

    Cart findOneByMemberIdAndGoodsId(String memberId, String goodsId);
}
