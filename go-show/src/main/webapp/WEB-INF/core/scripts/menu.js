/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */

function hideItemCatalogue(){
	var form = GPW.form.mainForm;
	var cbv=GPW.form.mainForm.getItemValue("isCatalogue");
	if(cbv==1){
		form.hideItem("isPopup");
		form.hideItem("url");
	}else{
		form.showItem("isPopup");
		form.showItem("url");
	}
}

GPW.toolbar.addFunction=function(){
	GPW.tree.mainTree.clearSelection();
	GPW.form.mainForm.clear();
	hideItemCatalogue();
}

GPW.tree.onClick=function(){
	this.mainTree.attachEvent("onClick",function(id){
		    var url=GPC.url.treeGetUrl+"?id="+id;
		    GPW.form.mainForm.loadStruct(url,function(){
				hideItemCatalogue();
			});	
		    return true;
		});
}

$(function() {
	var form = GPW.form.mainForm;
	form.attachEvent("onChange", function (name, value){
     	if(name=="isCatalogue"){
			hideItemCatalogue();
     	}     	
	});

});
