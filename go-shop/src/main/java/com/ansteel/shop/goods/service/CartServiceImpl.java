package com.ansteel.shop.goods.service;

import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.Cart;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.repository.CartRepository;
import com.ansteel.shop.goods.web.CartSessionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    GoodsService goodsService;

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
    @Transactional
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart saveOrUpdate(Cart cart) {
        String memberId = cart.getMemberId();
        if(StringUtils.hasText(memberId)) {
            Cart cartDatabase = cartRepository.findOneByMemberIdAndGoodsId(memberId, cart.getGoodsId());
            if(cartDatabase==null){
                return this.save(cart);
            }
            cartDatabase.setGoodsNum(cart.getGoodsNum()+cartDatabase.getGoodsNum());
            return this.save(cartDatabase);
        }
        return this.save(cart);
    }

    @Override
    @Transactional
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    @Transactional
    public List<Cart> querySessionAndDataBase(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);

        List<Cart> cartList=this.findByMemberId();

        String userId = UserUtils.getUserId();

        String url = request.getContextPath();

        if(cartList.size()>0){
            if(cartSessionModelList!=null) {
                for (CartSessionModel csm : cartSessionModelList) {
                    boolean isExist = false;
                    for (Cart cart : cartList) {
                        if (csm.getGoodsId().equals(cart.getGoodsId())) {
                            cart.setGoodsNum(cart.getGoodsNum() + csm.getNumber());
                            this.save(cart);
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        this.addCartList(csm, cartList, userId, url);
                    }
                }
            }
        }else{
            if(cartSessionModelList!=null) {
                for (CartSessionModel csm : cartSessionModelList) {
                    this.addCartList(csm, cartList, userId, url);
                }
            }
        }
        if(StringUtils.hasText(userId)){
            session.setAttribute(CartSessionModel.SESSION_NAME,null);
        }
        return cartList;
    }

    @Override
    @Transactional
    public List<Cart> addCartList(CartSessionModel csm, List<Cart> cartList, String userId, String url) {
        Goods goods=goodsService.findOne(csm.getGoodsId());
        Cart cart = new Cart();
        cart.setGoodsId(goods.getId());
        cart.setGoodsNum(csm.getNumber());
        cart.setMemberId(userId);
        cart.setStoreId(goods.getStoreId());
        cart.setGoodsName(goods.getName());
        cart.setGoodsImage(goods.getGoodsImage());
        cart.setGoodsPrice(goods.getGoodsStorePrice());
        cart.setStoreName(goods.getStoreName());
        if(StringUtils.hasText(userId)){
            cart=this.saveOrUpdate(cart);
        }
        cartList.add(cart);
        return cartList;
    }

    @Override
    @Transactional
    public Cart update(String goodsId, Integer quantity) {
        Cart cart=cartRepository.findOneByMemberIdAndGoodsId(UserUtils.getUserId(), goodsId);
        if(cart==null){
            return null;
        }
        cart.setGoodsNum(quantity);
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void delete(String userId,String goodsId) {
        Cart cart=cartRepository.findOneByMemberIdAndGoodsId(userId,goodsId);
        if(cart!=null) {
            this.delete(cart);
        }
    }

}
