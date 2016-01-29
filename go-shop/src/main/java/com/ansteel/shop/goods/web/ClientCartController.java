package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.JsonUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.core.utils.UserUtils;
import com.ansteel.shop.goods.domain.Cart;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.domain.GoodsClass;
import com.ansteel.shop.goods.domain.JsonGoodsClass;
import com.ansteel.shop.goods.service.CartService;
import com.ansteel.shop.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value = Public.CLIENT +"/cart")
public class ClientCartController {

    @Autowired
    CartService cartService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/add")
    public
    @ResponseBody
    Object add(@RequestParam(value = "goods_id") String goodsId,
                     @RequestParam(value = "quantity") Integer quantity,
                     HttpServletRequest request,
                     HttpServletResponse response) {

        Goods goods = goodsService.findOne(goodsId);
        //加入session
        HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);
        CartSessionModel cartSessionModel = new CartSessionModel();
        if(cartSessionModelList!=null&&cartSessionModelList.size()>0){
            Boolean isNew=true;
            for(CartSessionModel csm:cartSessionModelList){
                if(csm.getGoodsId().equals(goodsId)){
                    csm.setNumber(csm.getNumber()+quantity);
                    isNew=false;
                }
            }
            if(isNew){
                this.addSession(goodsId, quantity, goods.getGoodsStorePrice(), cartSessionModelList);
            }
        }else{
            cartSessionModelList = new ArrayList<>();
            this.addSession(goodsId, quantity, goods.getGoodsStorePrice(), cartSessionModelList);
        }
        session.setAttribute(CartSessionModel.SESSION_NAME, cartSessionModelList);

        //返回数据
        CartModel cartModel = new CartModel();
        Integer number = 0;
        BigDecimal amount = new BigDecimal(0);
        for (CartSessionModel csm:cartSessionModelList){
            number++;
            amount=amount.add(csm.getPrice().multiply(new BigDecimal(csm.getNumber())));
        }
        cartModel.setNum(number);
        cartModel.setState(true);
        cartModel.setAmount(amount);
        return cartModel;
    }

    @RequestMapping("/drop")
    public
    @ResponseBody
    Object drop(@RequestParam(value = "goods_id") String goodsId,
               @RequestParam(value = "cart_id") String cartId,
               @RequestParam(value = "callback") String callback,
               HttpServletRequest request,
               HttpServletResponse response) {
        HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);

        if(cartSessionModelList!=null) {
            for (CartSessionModel csm : cartSessionModelList) {
                if (csm.getGoodsId().equals(goodsId)) {
                    cartSessionModelList.remove(csm);
                    break;
                }
            }
            session.setAttribute(CartSessionModel.SESSION_NAME, cartSessionModelList);
        }
        List<Cart> cartList=cartService.findByMemberId();
        for(Cart cart:cartList){
            if(cart.getGoodsId().equals(goodsId)){
                cartService.delete(cart);
                cartList.remove(cart);
                break;
            }
        }
        //返回数据
        String url = request.getContextPath();
        if(cartSessionModelList!=null) {
            for (CartSessionModel csm : cartSessionModelList) {
                cartList=cartService.addCartList(csm, cartList, null, url);
            }
        }
        CartListModel cartListModel = new CartListModel();
        if(cartList!=null&&cartList.size()>0) {
            BigDecimal cart_all_price = new BigDecimal(0);
            Integer cart_goods_num = 0;
            List<CartObjectModel> list = new ArrayList<>();
            for (Cart cart : cartList) {
                CartObjectModel com = this.setCartObjectModel(cart, url);
                list.add(com);
                cart_all_price=cart_all_price.add(com.getGoods_price().multiply(new BigDecimal(cart.getGoodsNum())));
                cart_goods_num ++;
            }
            cartListModel.setCart_all_price(cart_all_price);
            cartListModel.setCart_goods_num(cart_goods_num);
            cartListModel.setList(list);
        }
        CartModel cartModel = new CartModel();
        cartModel.setState(true);
        cartModel.setAmount(new BigDecimal(0));
        List  list=cartListModel.getList();
        if(list!=null) {
            cartModel.setNum(cartListModel.getList().size());
        }else{
            cartModel.setNum(0);
        }
        return JsonUtils.jsonCallback(callback, cartModel);
    }

    private void addSession(String goodsId, Integer quantity, BigDecimal goodsStorePrice, List<CartSessionModel> cartSessionModelList) {
        CartSessionModel cartSessionModel = new CartSessionModel();
        cartSessionModel.setGoodsId(goodsId);
        cartSessionModel.setNumber(quantity);
        cartSessionModel.setPrice(goodsStorePrice);
        cartSessionModelList.add(cartSessionModel);
    }

    @RequestMapping("/load")
    public
    @ResponseBody
    Object load(@RequestParam(value = "callback") String callback,
               HttpServletRequest request,
               HttpServletResponse response) {

        List<Cart> cartList=cartService.querySessionAndDataBase(request);
        String url = request.getContextPath();

       /* HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);

        List<Cart> cartList=cartService.findByMemberId();

        String userId = UserUtils.getUserId();

        String url = request.getContextPath();

        if(cartList.size()>0){
            if(cartSessionModelList!=null) {
                for (CartSessionModel csm : cartSessionModelList) {
                    boolean isExist = false;
                    for (Cart cart : cartList) {
                        if (csm.getGoodsId().equals(cart.getGoodsId())) {
                            cart.setGoodsNum(cart.getGoodsNum() + csm.getNumber());
                            cartService.save(cart);
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
*/
        //返回数据
        CartListModel cartListModel = new CartListModel();
        if(cartList!=null&&cartList.size()>0) {
            BigDecimal cart_all_price = new BigDecimal(0);
            Integer cart_goods_num = 0;
            List<CartObjectModel> list = new ArrayList<>();
            for (Cart cart : cartList) {
                CartObjectModel com = this.setCartObjectModel(cart, url);
                list.add(com);
                cart_all_price=cart_all_price.add(com.getGoods_price().multiply(new BigDecimal(cart.getGoodsNum())));
                cart_goods_num ++;
            }
            cartListModel.setCart_all_price(cart_all_price);
            cartListModel.setCart_goods_num(cart_goods_num);
            cartListModel.setList(list);
        }
        return JsonUtils.jsonCallback(callback, cartListModel);
    }

   /* private void addCartList(CartSessionModel csm, List<Cart> cartList,String userId,String url) {
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
            cart=cartService.saveOrUpdate(cart);
        }
        cartList.add(cart);
    }*/


    public CartObjectModel setCartObjectModel(Cart cart,String url) {
        CartObjectModel com = new CartObjectModel();
        com.setCart_id(cart.getId());
        com.setGoods_id(cart.getGoodsId());
        com.setGoods_name(cart.getGoodsName());
        com.setGoods_image(url + "/att/download/" + cart.getGoodsImage());
        com.setGoods_num(cart.getGoodsNum());
        com.setGoods_price(cart.getGoodsPrice());
        com.setGoods_url(url+"/cl/goods/show?goods_id="+cart.getGoodsId());
        return  com;
    }
}
