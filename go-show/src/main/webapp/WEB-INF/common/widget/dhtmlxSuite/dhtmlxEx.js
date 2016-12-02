/**
 * @require common:widget/dhtmlxSuite/dhtmlxEx.css
 * @require common:widget/dhtmlxSuite/dhtmlx.js
 * @require common:widget/dhtmlxSuite/ext/dhtmlXToolbar_selectEx.js
 * @require common:widget/dhtmlxSuite/ext/dhtmlxgrid_errormessage.js
 * @require common:widget/dhtmlxSuite/ext/dhtmlxform_item_tree.js
 * @require common:widget/utils/spin.js
 * @require common:widget/dhtmlxSuite/dhtmlxGlobal.js
 */

GLOBAL.parentTreeParse = dhtmlXTreeObject.prototype._parse;
dhtmlXTreeObject.prototype._parse = function(g, m, a, c) {
	var that = this;
	var serviceData = g.d;
	if (!GLOBAL.errorMessage(serviceData)) {
		GLOBAL.parentTreeParse.call(that, g, m, a, c);
	}
}

GLOBAL.parentGridProcessJs = dhtmlXGridObject.prototype._process_js;
dhtmlXGridObject.prototype._process_js = function(a) {
	var that = this;
	var data;
	if (!!a.xmlDoc) {
		data = a.xmlDoc.responseText;
	} else if (!!a.data) {
		data = a.data;
	}else{
		data=a;
	}

	if (!GLOBAL.errorMessage(data)) {
		GLOBAL.parentGridProcessJs.call(that, a);
	}
}

dhtmlXCalendarObject.prototype.langData["ch"] = {
	dateformat: '%Y-%m-%d',
	monthesFNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	monthesSNames: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
	daysFNames: ["星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
	daysSNames: ["日", "一", "二", "三", "四", "五", "六"],
	weekstart: 1
}
dhtmlXCalendarObject.prototype.lang = "ch";
//E._validate = String(y.validate).split(this.separator)被修改，原因正则表达式逗号bug
// this.parentNode.parentNode.callEvent("onInfo", [this.parentNode.parentNode._idd,h]);被修改，加入事件h
//this.parentNode.callEvent("onInfo", [this.parentNode._idd,l]);被修改，加入事件l
