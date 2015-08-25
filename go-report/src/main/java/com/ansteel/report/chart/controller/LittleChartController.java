package com.ansteel.report.chart.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.report.chart.domain.Chart;
import com.ansteel.report.chart.service.ChartService;

@Controller
@RequestMapping(value="/littleChart")
public class LittleChartController {

	@Autowired
	ChartService chartService;

	@RequestMapping(method = RequestMethod.GET, value = "/show/{name}")
	public String home(@PathVariable("name")String name,
			Model model,HttpServletRequest request) {
		Chart chart=chartService.getChartByName(name);
		model.addAttribute("ReportName", name);
		model.addAttribute("NameData", chartService.getNameData(chart,request));
		model.addAttribute("Chart", chart);
		return FisUtils.page("report:widget/chart/littleChart.html");
	}
}
