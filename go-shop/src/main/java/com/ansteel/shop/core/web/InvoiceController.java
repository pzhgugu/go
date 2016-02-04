package com.ansteel.shop.core.web;

import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.core.domain.Invoice;
import com.ansteel.shop.core.service.InvoiceService;
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
import java.util.List;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @RequestMapping("/load")
    public String load(Model model,
                       @RequestParam(value="id",required = false) String id,
                       HttpServletRequest request,
                       HttpServletResponse response){

        if(StringUtils.hasText(id)){
            invoiceService.delete(id);
        }

        List<Invoice> invoiceList=invoiceService.findByMemberId();
        model.addAttribute("P_INVOICE_LIST",invoiceList);
        String style = PageStyle.getStyle();
        model.addAttribute("P_STYLE",style);
        return FisUtils.page("shop:pages/client/buy/" + style + "/shopbuy/loadInv.html");
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(@Valid Invoice invoice, BindingResult result,
                       Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        Invoice inv = invoiceService.save(invoice);

        InvoiceModel invoiceModes = new InvoiceModel();
        invoiceModes.setState("success");
        invoiceModes.setId(inv.getId());
        return  invoiceModes;
    }
}
