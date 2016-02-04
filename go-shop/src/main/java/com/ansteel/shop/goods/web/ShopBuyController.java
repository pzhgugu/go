package com.ansteel.shop.goods.web;

import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.core.domain.Address;
import com.ansteel.shop.core.service.AddressService;
import com.ansteel.shop.goods.domain.Cart;
import com.ansteel.shop.goods.domain.Goods;
import com.ansteel.shop.goods.service.CartService;
import com.ansteel.shop.goods.service.GoodsOrderService;
import com.ansteel.shop.goods.service.GoodsService;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.*;

/**
 * Created by Administrator on 2016/1/8.
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopBuyController {

    @Autowired
    AddressService addressService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    StoreService storeService;

    @Autowired
    CartService cartService;

    @Autowired
    GoodsOrderService goodsOrderService;

    @RequestMapping("/cart")
    public String list(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response){
        List<Cart> cartList=cartService.querySessionAndDataBase(request);
        Map<String,List<Cart>> storeCartMap = new HashMap<>();
        for(Cart cart :cartList){
            if(storeCartMap.containsKey(cart.getStoreId())){
                List<Cart> cList=storeCartMap.get(cart.getStoreId());
                cList.add(cart);
                storeCartMap.put(cart.getStoreId(),cList);
            }else{
                List<Cart> cList = new ArrayList<>() ;
                cList.add(cart);
                storeCartMap.put(cart.getStoreId(),cList);
            }
        }
        model.addAttribute("P_CART",storeCartMap);
        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STEP","step1");
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/cart.html");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Model model,
                         @RequestParam(value = "goods_id") String goodsId,
                         HttpServletRequest request,
                         HttpServletResponse response){
        cartService.delete(goodsId);
        List<Cart> cartList=cartService.findByMemberId();
        CartModel cartModel = new CartModel();
        cartModel.setState(true);
        if(cartList.size()>0) {
            cartModel.setNum(cartList.size());
        }else{
            cartModel.setNum(0);
        }
        cartModel.setAmount(new BigDecimal(0));
        return cartModel;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Model model,
                         @RequestParam(value = "goods_id") String goodsId,
                         @RequestParam(value = "quantity") Integer quantity,
                       HttpServletRequest request,
                       HttpServletResponse response){
        Cart cart=cartService.update(goodsId, quantity);
        CartUpdateModel cartUpdateModel = new CartUpdateModel();
        if(cart==null){
            cartUpdateModel.setState("invalid");
        }else {
            Goods goods = goodsService.findOne(cart.getGoodsId());
            if(goods==null||(goods.getGoodsVerify()!=null&&goods.getGoodsVerify()!=1)||goods.getGoodsState()!=1){
                cartUpdateModel.setState("invalid");
            }else {
                cartUpdateModel.setGoods_num(cart.getGoodsNum());
                cartUpdateModel.setGoods_price(cart.getGoodsPrice());
                cartUpdateModel.setState("true");
                cartUpdateModel.setSubtotal(cart.getGoodsPrice().multiply(new BigDecimal(cart.getGoodsNum())));
            }
        }
        return cartUpdateModel;

    }

    @RequestMapping("/buy")
    public String index(Model model,
                        @RequestParam(value = "cart_id") String[] cartIds,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        List<Address> addressList=addressService.findByMemberIdOrderByIsDefaultDesc();
        model.addAttribute("P_ADDRESS_LIST", addressList);
        for(Address a:addressList){
            if(a.getIsDefault()!=null&&a.getIsDefault()==1){
                model.addAttribute("P_ADDRESS_ISDEFAULT", a);
                break;
            }
        }


        Map<String,List<BuyGoodsModel>> storeBuyGoodsMap = new HashMap<>();
        for(String cart:cartIds){
           String[] cartArr=cart.split("\\|");
           Goods goods = goodsService.findOne(cartArr[0]);
            BuyGoodsModel buyGoodsModel= new BuyGoodsModel();
            buyGoodsModel.setGoods(goods);
            buyGoodsModel.setCartId(cart);
            String storeId = goods.getStoreId();
            try{
                buyGoodsModel.setNumber(Integer.valueOf(cartArr[1]));
            }catch (Exception e){
                throw new PageException("转换商品数量出错！");
            }
            if(storeBuyGoodsMap.containsKey(storeId)){
                List<BuyGoodsModel> bgmList = storeBuyGoodsMap.get(goods.getStoreId());
                buyGoodsModel.setStore(bgmList.get(0).getStore());
                bgmList.add(buyGoodsModel);
                storeBuyGoodsMap.put(storeId,bgmList);
            }else{
                List<BuyGoodsModel> bgmList = new ArrayList<>();
                Store store=storeService.findOne(storeId);
                buyGoodsModel.setStore(store);
                bgmList.add(buyGoodsModel);
                storeBuyGoodsMap.put(storeId, bgmList);
            }
        }
        model.addAttribute("P_BUYGOODS_STORE", storeBuyGoodsMap);

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STEP","step2");
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/buy_step1.html");
    }

    @RequestMapping("/order")
    public String index(Model model,
                        ShopOrderModel shopOrderModel,
                        HttpServletRequest request,
                        HttpServletResponse response) {

        goodsOrderService.createOrder(shopOrderModel);
        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        model.addAttribute("P_STEP","step3");
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/buy_step2.html");
    }
}
