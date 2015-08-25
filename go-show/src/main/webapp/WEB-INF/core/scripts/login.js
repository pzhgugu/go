/**
 * @require core:styles/login.css
 * @require common:widget/jquery/jquery.js
 */

$(function() {
	
	$("#j_username").focus();

	var browser = navigator.appName

	var b_version = navigator.appVersion

	var version = b_version.split(";");

	var trim_Version = version[1].replace(/[ ]/g, "");

	if (browser == "Microsoft Internet Explorer" && trim_Version == "MSIE7.0") {
		document.getElementById("loginbtn").disabled = true;
		alert("当前IE版本为IE7，请升级到IE8以上版本！");
	}

	else if (browser == "Microsoft Internet Explorer"
			&& trim_Version == "MSIE6.0") {
		document.getElementById("loginbtn").disabled = true;
		alert("当前IE版本为IE6，请升级到IE8以上版本！");
	}
})
