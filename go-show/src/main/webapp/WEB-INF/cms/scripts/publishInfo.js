/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 * @require common:widget/jquery/jquery.form.js
 * @require common:widget/dhtmlxSuite/ext/dhtmlxform_item_kindeditor.js
 */

$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
		url:{
			findOneUrl:GLOBAL.S.URL +"/admin/news/find/",
			saveUrl:GLOBAL.S.URL +"/admin/news"
		}
	}

var GPW = {
	layout : {},
	form:{}
};

GPW.layout = {
	mainLayout : {},
	formLayout : {},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
		this.formLayout=this.mainLayout.cells("a");
		this.formLayout.hideHeader();				
	}
}

GPW.form = {
	mainForm:{},
	getFormData:function(){
		return GLOBAL.P.P_FORM;
	},
	getForm:function(window,formData,type){
		return window.attachForm(formData);
	},
	init:function(){
		var formData = this.getFormData();		
		this.mainForm=this.getForm(GPW.layout.formLayout,formData);
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				if(GLOBAL.S.SEC_D_V||GLOBAL.S.SEC_C_V){
					SpinnerCtl.show();
					
					GPW.form.mainForm.send(GPC.url.saveUrl, function(loader, response){
						SpinnerCtl.close();
						if(response.length>0){
							GLOBAL.errorMessage(response);
						}else{
							dhtmlx.alert("保存成功！");
						}					
						//GPW.tree.mainTree.refreshItem();
					});
				}else{
					dhtmlx.alert("没有操作权限，请联系管理员！");
				}
		    }
		});
		this.mainForm.attachEvent("onValidateError", function (name, value, result){
			SpinnerCtl.close();
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.form.mainForm.getItemLabel(name)+"字段值不能为["+value+"]！"});
		});
		//this.mainForm.attachEvent("onValidateSuccess", function (name, value, result){alert(name);});
		this.mainForm.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		//this.mainForm.disableItem("categoryName");
		this.mainForm.setItemValue("publishDate",new Date());
		this.mainForm.setItemValue("categoryId",parent.GPW.tree.mainTree.getSelectedItemId());
		if(!!GLOBAL.P.id)	
			this.mainForm.load(GPC.url.findOneUrl+GLOBAL.P.id);
	}
}//GPW.form 
	
$(function() {
	GPW.layout.init();
	GPW.form.init();
});