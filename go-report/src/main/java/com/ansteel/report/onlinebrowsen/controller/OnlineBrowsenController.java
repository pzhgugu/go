package com.ansteel.report.onlinebrowsen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ansteel.core.utils.FisUtils;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-12
 * 修 改 人：
 * 修改日 期：
 * 描   述：在线浏览控制器。
 */
@Controller
@RequestMapping(value = "/browsen")
public class OnlineBrowsenController {
	

	@RequestMapping(method = RequestMethod.GET)
	public String browsen(@RequestParam("url")String url,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response) {
		model.addAttribute("showUrl", url);
		return FisUtils.page("report:widget/OnlineBrowsen/OnlineBrowsen.html");
	}

}
