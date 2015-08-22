package com.ansteel.shop.seller.web;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.shop.constant.ShopConstant;

@Controller
@RequestMapping(value = ShopConstant.SELLER+"/goodsonline")
public class SellerGoodsOnlineController {
	
	
	
	@RequestMapping("/list")
	public String list(Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		
		model.addAttribute("P_CURRENT_OP", "Online");
		Map<String ,String> nav = new HashMap<>();
		nav.put("n1", "商家管理中心");
		nav.put("n2", "商品");
		nav.put("n3", "出售中的商品");
		model.addAttribute("P_NAV", nav);
		model.addAttribute("P_VIEW", "shop:pages/seller/goodsOnline/goodsOnlineList.html.jsp");
		return FisUtils.page("shop:widget/tpl/seller/framework.html");
	}
	

}
