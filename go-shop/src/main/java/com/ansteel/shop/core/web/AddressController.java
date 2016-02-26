package com.ansteel.shop.core.web;

import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.core.domain.Address;
import com.ansteel.shop.core.service.AddressService;
import com.ansteel.shop.utils.PageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@Valid Address address,
                       BindingResult result,
                       Model model,
                            HttpServletRequest request,
                            HttpServletResponse response){
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        Address addr=addressService.save(address);
        AddressModel addressModes=new AddressModel();
        addressModes.setState(true);
        addressModes.setAddr_id(addr.getId());
        return addressModes;
    }

    @RequestMapping("/load")
    public String load(Model model,
                       @RequestParam(value="id",required = false) String id,
                       HttpServletRequest request,
                       HttpServletResponse response){
        if(StringUtils.hasText(id)){
            addressService.delect(id);
        }
        List<Address> addressList=addressService.findByMemberIdOrderByIsDefaultDesc();
        model.addAttribute("P_ADDRESS_LIST", addressList);
        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE", style);
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/load_address.html");
    }

    @RequestMapping("/add")
    public String add(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response){

        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/add_address.html");
    }

    @RequestMapping("/freight")
    @ResponseBody
    public Object freight(Model model,
                       @RequestParam(value="store_ids[]") String[] storeIds,
                       @RequestParam(value="city_id") String city_id,
                       @RequestParam(value="area_id") String area_id,
                       HttpServletRequest request,
                       HttpServletResponse response){

        AddressFreightModel addressFreightModel = new AddressFreightModel();
        addressFreightModel.setState("success");
        addressFreightModel.setAllow_offpay("0");

        Map<String,Object> content = new HashMap<>();
        for(String id:storeIds){
            content.put(id,9.00);
        }
        addressFreightModel.setContent(content);
        return addressFreightModel;
    }
}
