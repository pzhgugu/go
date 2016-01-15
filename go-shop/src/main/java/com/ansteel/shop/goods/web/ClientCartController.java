package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
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


        CartModel cartModel = new CartModel();
        cartModel.setState(true);
        cartModel.setNum(quantity);
        cartModel.setAmount(BigDecimal.valueOf(88.00));


        //加入session
        HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);
        CartSessionModel cartSessionModel = new CartSessionModel();
        if(cartSessionModelList!=null&&cartSessionModelList.size()>0){
            for(CartSessionModel csm:cartSessionModelList){
                if(csm.getGoodsId().equals(goodsId)){
                    csm.setNumber(csm.getNumber()+quantity);
                }
            }
        }else{
            cartSessionModel.setGoodsId(goodsId);
            cartSessionModel.setNumber(quantity);
            cartSessionModelList=new ArrayList<>();
            cartSessionModelList.add(cartSessionModel);
        }
        session.setAttribute(CartSessionModel.SESSION_NAME,cartSessionModelList);
        return cartModel;
    }

    @RequestMapping("/load")
    public
    @ResponseBody
    Object load(@RequestParam(value = "callback") String callback,
               HttpServletRequest request,
               HttpServletResponse response) {

        HttpSession session = request.getSession(true);
        List<CartSessionModel> cartSessionModelList= (List<CartSessionModel>) session.getAttribute(CartSessionModel.SESSION_NAME);

        List<Cart> cartList=cartService.findByMemberId();

        String userId = UserUtils.getUserId();

        String url = request.getContextPath();

        if(cartList.size()>0){
            for(CartSessionModel csm:cartSessionModelList){
                boolean isExist=false;
                for(Cart cart:cartList){
                    if(csm.getGoodsId().equals(cart.getGoodsId())){
                        cart.setGoodsNum(cart.getGoodsNum()+csm.getNumber());
                        isExist=true;
                        break;
                    }
                }
                if(!isExist){
                    this.addCartList(csm, cartList,userId,url);
                }
            }
        }else{
            for(CartSessionModel csm:cartSessionModelList){
                this.addCartList(csm,cartList,userId,url);
            }
        }

        CartListModel cartListModel = new CartListModel();
        BigDecimal cart_all_price=new BigDecimal(0);
        Integer cart_goods_num=0;
        List<CartObjectModel> list = new ArrayList<>();
        for(Cart cart:cartList){
            CartObjectModel com =this.setCartObjectModel(cart,url);
            list.add(com);
            cart_all_price.add(com.getGoods_price());
            cart_goods_num+=com.getGoods_num();
        }
        cartListModel.setCart_all_price(cart_all_price);
        cartListModel.setCart_goods_num(cart_goods_num);
        cartListModel.setList(list);
        return JsonUtils.jsonCallback(callback, cartListModel);
    }

    private void addCartList(CartSessionModel csm, List<Cart> cartList,String userId,String url) {
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
            cartService.save(cart);
        }
        cartList.add(cart);
    }


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
