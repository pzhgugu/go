/**
 * @require common:scripts/global.js
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */


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
		menu.setIconsPath(GLOBAL.IconsPath);
		GLOBAL.P.addNewSibling(menu);
		var img = __inline('../images/logo.png');
		menu.setTopText("<img  src='"+img+"'><span style='margin-left: 5px;' >GoReport系统</span>");
		
		menu.attachEvent("onClick", menuOnClick);
		
		function menuOnClick(id) {
			var ids = frameTabbar.getAllTabs();
			for (var q=0; q<ids.length; q++) {
			    if(ids[q]==id){
			    	frameTabbar.tabs(ids[q]).setActive();
			    	return;
			    }
			}
			GLOBAL.P.tabbar(frameTabbar,id);
		    return true;
		}

	},
	initTabbar:function(){
		frameTabbar = frameMainCell.attachTabbar({
			close_button: true
		});
		frameTabbar.addTab("Home", "首页", null, null, true,false);
		frameTabbar.tabs("Home").attachURL(GLOBAL.S.URL+"/admin/main");
	}
}

$(function(){
	initPageControl.initFrameLayout();
});