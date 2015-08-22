<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>goshow业务系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${S_URL_R}/js/global.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"	href="${S_URL_R}/js/dhtmlxSuite/dhtmlx.css" />
<script src="${S_URL_R}/js/dhtmlxSuite/dhtmlx.js"></script>

<style>
    html, body {
        width: 100%;
        height: 100%;
        margin: 0px;
        overflow: hidden;
    }
    
    .dhtmlxMenu_dhx_skyblue_Middle {
    	background-color: #FFFFFF;
    }
    
    div.dhtmlxMenu_dhx_skyblue_TopLevel_Item_Normal, div.dhtmlxMenu_dhx_skyblue_TopLevel_Item_Disabled {
    border: 1px solid #ffffff;
	}
</style>   
</head>
<script>
GLOBAL.S.URL="${S_URL}";
GLOBAL.S.URL_R="${S_URL_R}";


var frameLayout,frameMainCell,frameAccordCell,frameAccordCell,frameTabbar;
var initPageControl = {
	initFrameLayout:function() {
		frameLayout = new dhtmlXLayoutObject({
	        parent: document.body,  // parent container
	        pattern: "1C"           // layout's pattern
	    });
		frameMainCell=frameLayout.cells("a");
		frameMainCell.hideHeader();
		
		initPageControl.initMenu();
		initPageControl.initTabbar();
	},
	initFrameAccordLayout:function() {
		frameLayout = new dhtmlXLayoutObject({
	        parent: document.body,  // parent container
	        pattern: "2U"           // layout's pattern
	    });
		var frameAccordCell=frameLayout.cells("a");
		frameAccordCell.setWidth(200);
		frameAccordCell.hideHeader();
		
		var frameMainCell=frameLayout.cells("b");
		frameMainCell.hideHeader();
	},
	initMenu:function(){
		var menu=frameLayout.attachMenu();
		menu.setIconsPath(GLOBAL.S.URL_R+"/images/menu/");
		menu.addNewSibling(null, "Home", "首页", false);
		menu.addNewSibling("Home", "System", "开发者", false);
		menu.addNewChild("System", 0, "SystemSet", "系统设置", false, "new.gif");
		menu.addNewSeparator("SystemSet");
		menu.addNewChild("System", 2, "Cache", "缓存更新", false, "paste.gif");
		menu.addNewChild("System", 3, "Menu", "菜单管理", false, "bug_reporting.gif");
		menu.addNewChild("System", 4, "ModelManage", "模型管理", false, "bug_reporting.gif");
		
		menu.setTopText("<img  src="+GLOBAL.S.URL_R+"/images/logo.png><span style='margin-left: 5px;' >GoShow系统</span>");
		
		menu.attachEvent("onClick", menuOnClick);
		
		function menuOnClick(id) {
			var ids = frameTabbar.getAllTabs();
			for (var q=0; q<ids.length; q++) {
			    if(ids[q]==id){
			    	frameTabbar.tabs(ids[q]).setActive();
			    	return;
			    }
			}
			switch(id)
			{
				case "Home":
					frameTabbar.addTab("Home", "首页", null, null, true,false);
				  break;
				case "SystemSet":
				  break;
				case "Cache":
				  break;
				case "Menu":
					//frameTabbar.addTab("Menu", "菜单管理", null, null, true);
					//frameTabbar.tabs("Menu").attachURL("../../../view/df/Menu/menuView.do");
				  break;
				case "ModelManage":
					frameTabbar.addTab("ModelManage", "模型管理", null, null, true);
					frameTabbar.tabs("ModelManage").attachURL(GLOBAL.S.URL+"/model");
				  break;
				default:
					alert(id+":异常菜单选项");
			}
		    return true;
		}

	},
	initTabbar:function(){
		frameTabbar = frameMainCell.attachTabbar({
			close_button: true
		});
		frameTabbar.addTab("Home", "首页", null, null, true,false);
	}
}

$(function(){
	initPageControl.initFrameLayout();
});

</script>

<body >
	
</body>
</html>
