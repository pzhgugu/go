/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */
GPC.url.createTableUrl=GLOBAL.S.URL+GLOBAL.P.MODULES+"/createTable";

var Create={
	select:function(){
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		if(!!selectId){
			$.ajax({
				   type: "GET",
				   url: GPC.url.createTableUrl,
				   data: "id="+selectId,
				   success: function(msg){						   
					   dhtmlx.alert("生成表成功！");
				   }
			});
		}else{
			alert("请选中模型！");
		}
	},
	all:function(){
		$.ajax({
			   type: "GET",
			   url: GPC.url.createTableUrl+"/all",
			   success: function(msg){						   
				   dhtmlx.alert("生成表成功！");
			   }
		});
	}
}

$(function() {
	GPW.toolbar.treeToolbar.addListOption("edit", 'new_s1',5, "separator");
	GPW.toolbar.treeToolbar.addListOption("edit", 'selectCreate',6, "button", '生成选择表', 'icon_treegrid.gif');
	GPW.toolbar.treeToolbar.addListOption("edit", 'allCreate',7, "button", '生成全部表', 'icon_grid.gif');
	GPW.toolbar.treeToolbar.attachEvent("onClick", function(id) {
		switch(id) {
			case "selectCreate":
				Create.select();
				break;
			case "allCreate":
				Create.all();
				break;
			default:
		}
	});
});
