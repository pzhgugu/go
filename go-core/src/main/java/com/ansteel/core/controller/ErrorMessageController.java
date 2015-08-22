package com.ansteel.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.RedirectAttributesEx;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：异常控制器，用于异常跳转页面。 
 */
@Controller
@RequestMapping(value = "/error")
public class ErrorMessageController {

	@RequestMapping(method = RequestMethod.GET)
	public String error(Model model,HttpServletRequest request) {
		RedirectAttributesEx rae=new RedirectAttributesEx(request);
		model.addAttribute("ERROR_MESSAGE", rae.getAttribute("ERROR_MESSAGE"));
		return FisUtils.page("common:widget/exception/error.html");
	}
}
