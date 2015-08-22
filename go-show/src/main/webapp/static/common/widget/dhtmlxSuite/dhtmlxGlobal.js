/**
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */


GLOBAL.getTopWindow= function() {
	var obj = window.self;
	while (true) {
		if (obj.document.getElementById("topFlag")) {
			return obj;
		}
		obj = obj.window.parent;
	}
}

GLOBAL.isString = function(str){ 
	return (typeof str=='string')&&str.constructor==String; 
}

GLOBAL.Popup;
GLOBAL.PopupFunction=function(e,message){
	if (GLOBAL.Popup == null) 
		GLOBAL.Popup = new dhtmlXPopup();
	GLOBAL.Popup.attachHTML("<div style='width:200px;white-space: normal;'>"+message+"</div>");
	var t = e.target||e.srcElement;
	var x = window.dhx4.absLeft(t);
	var y = window.dhx4.absTop(t);
	var w = t.offsetWidth;
	var h = t.offsetHeight;	
	GLOBAL.Popup.show(x,y,w,h);
};

GLOBAL.getQueryObjct = function(name, operator, value) {
	var queryObject = {};
	queryObject.name = name;
	queryObject.operator = operator;
	queryObject.value = value;
	return queryObject;
}

GLOBAL.getBetweenQueryObjct = function(name, operator, begin, end) {
	var queryObject = {};
	queryObject.name = name;
	queryObject.operator = operator;
	queryObject.begin = begin;
	queryObject.end = end;
	return queryObject;
}

GLOBAL.initGridCombo = function(grid, object) {//{"colTypes":[{"text":"编辑列","value":"ed"},{"text":"文本编辑","value":"edtxt"}]}
	_.map(object, function(value, key) {
		var colIndex = grid.getColIndexById(key);
		_.each(value, function(v, k, value) {
			grid.getCombo(colIndex).put(v.value, v.text);
		});
	});
}

GLOBAL.paging = {
	results : "结果",
	records : "记录从 ",
	to : " 到 ",
	page : "页面 ",
	perpage : "条记录",
	first : "第一页",
	previous : "上一页",
	found : "发现记录",
	next : "下一页",
	last : "最后一页",
	of : " of ",
	notfound : "没有发现记录"
};

GLOBAL.validText = function(rule) {
	var value = "";
	switch (rule) {
	case "Empty":
		value = "必须为空"
		break;
	case "NotEmpty":
		value = "不能为空"
		break;
	case "ValidAplhaNumeric":
		value = "只能为文字与数字值"
		break;
	case "ValidBoolean ":
		value = "只能为布尔型或者（0,1）"
		break;
	case "ValidCurrency":
		value = "只能为货币类型"
		break;
	case "ValidDate":
		value = "只能为日期"
		break;
	case "ValidDatetime":
		value = "只能为日期时间"
		break;
	case "ValidEmail":
		value = "只能为Email"
		break;
	case "ValidInteger":
		value = "只能为整数"
		break;
	case "isValidIntegerEmpty":
		value = "只能为空或者整数"
		break;
	case "ValidIPv4":
		value = "只能为IP"
		break;
	case "ValidNumeric":
		value = "只能为数字"
		break;
	case "ValidNumericEmpty":
		value = "只能为空或者数字"
		break;
	case "ValidTime":
		value = "只能为IP"
		break;
	}
	return value;
}

GLOBAL.errorMessage = function(message) {
	try{
		if(message==null){
			return false;
		}
		if(GLOBAL.isString(message)){
			message=message.trim();
			if(message==""){
				return false;
			}
			message=$.parseJSON(message);
		}
	}catch (e) {
		return false;
	}
	if (!!message.code && message.code < 0) {
		var errorMsg = message.message;
		parent.dhtmlx.message({
			type : "error",
			expire : -1,
			text : errorMsg
		});
		if (message.code == -2) {
			location.reload();
		}else if(message.code == -3) {
			 window.location.href=GLOBAL.S.URL+"/accessDenied.jsp?error="+errorMsg; 
		}
		return true;
	} else {
		return false;
	}
}
