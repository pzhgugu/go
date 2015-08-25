/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */
GPC.url.scanUrl=GLOBAL.S.URL+GLOBAL.P.MODULES+"/scan";

var Model={
		scan:function(){
			var tree=GPW.tree.mainTree;
			var selectId=tree.getSelectedItemId();
			$.ajax({
				   type: "GET",
				   url: GPC.url.scanUrl,
				   data: "id="+selectId,
				   success: function(msg){						   
					   dhtmlx.alert("扫描成功！");
					   GPW.grid.refreshTypeGrid();
				       GPW.grid.refreshMainGrid();
				   }
			});
		}
	}

$(function() {
	GPW.toolbar.treeToolbar.addListOption("edit", 'new_s1',5, "separator");
	GPW.toolbar.treeToolbar.addListOption("edit", 'scan',6, "button", '重新扫描', 'paste.gif');
	GPW.toolbar.treeToolbar.attachEvent("onClick", function(id) {
		switch(id) {
			case "scan":
				Model.scan();
				break;
			default:
		}
	});
});
