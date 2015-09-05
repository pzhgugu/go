/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */

//重新布局
$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
});

//url集合
var GPC = {
	url:{
		treeQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryIdToTree/"+GLOBAL.P.TREECLASS,
		dragOrderUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/dragTree/"+GLOBAL.P.TREECLASS,
		treeSave:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeSave/"+GLOBAL.P.TREECLASS,
		treeGetUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/get/"+GLOBAL.P.TREECLASS,
		treeDelectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.TREECLASS
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",select:"select",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

//组件声明
var GPW = {
	layout : {},
	toolbar : {},
	tree : {},
	grid : {},
	window : {},
	form:{},
	paging : {},
	cache : {}
};

//缓存
GPW.cache = {
	grid:{}	
}

//布局组件
GPW.layout = {
	mainLayout : {},
	mainGridLayout:{},
	treeLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "2U");
		this.treeLayout=this.mainLayout.cells("a");
		this.treeLayout.setWidth(300);
		this.treeLayout.hideHeader();
		
		this.mainGridLayout=this.mainLayout.cells("b");
		this.mainGridLayout.hideHeader();
		
	}
}

//树组件
GPW.tree = {
	mainTree:{},
	init:function(){
		this.mainTree=GPW.layout.treeLayout.attachTree();
		this.mainTree.setImagePath(GLOBAL.IconsPath+"dhxtree_skyblue/");
		this.mainTree.enableTreeImages("false");
		this.mainTree.setXMLAutoLoading(GPC.url.treeQueryUrl); 
		this.mainTree.setDataMode("json");
		this.mainTree.loadJSON(GPC.url.treeQueryUrl); 
		
		this.dragOrder();	
		this.onClick(); 
		
	},
	onClick:function(){
		this.mainTree.attachEvent("onClick",function(id){
		    var url=GPC.url.treeGetUrl+"?id="+id;
		    GPW.form.mainForm.loadStruct(url);	
		    return true;
		});
	},
	delectTree:function(){
		var tree=GPW.tree.mainTree;
		var id=tree.getSelectedItemId();
		if(!!id){
			dhtmlx.confirm({
				title:"删除树节点",
				ok:"是", cancel:"否",
				text:"您确认删除树节点: "+tree.getItemText(id),
				callback:function(state){
					if(state){
						$.ajax({
							   type: "GET",
							   url: GPC.url.treeDelectUrl,
							   data: "id="+id,
							   success: function(msg){
								   GPW.form.mainForm.clear();
								   tree.refreshItem();
							   }
						});
					}
				}
			});
		}
	},
	dragOrder:function(){
		this.mainTree.enableDragAndDrop(true);
		this.mainTree.setDragBehavior("complex");
		this.mainTree.attachEvent("onDrag", function(sId, tId, id, sObject, tObject){
			var data="sid="+sId+"&tid="+tId;
			if(!!id){
				data+="&id="+id;
			}
			$.ajax({
			   type: "GET",
			   url: GPC.url.dragOrderUrl,
			   data: data,
			   success: function(msg){

				   var tree=GPW.tree.mainTree;
				   if(tId==0){
					   tree.refreshItem();
				   }else{
					   var pSId=tree.getParentId(sId);
					   var pTId=tree.getParentId(tId);
					   if(!id){						   
						   if(pSId==pTId){
							   if(pSId==0){
								   tree.refreshItem(); 
							   }else{
						   			tree.refreshItem(pSId); 
							   }
					   	   }else{
					   		   if(pSId!=tId){
				   					tree.refreshItem();
				   				}else{
				   					tree.refreshItem(pSId);
				   				}
					   	   }
					   }else{
						   if(pSId==tId){
							   tree.refreshItem(tId); 
						   }else{
							   tree.refreshItem();
						   }
					   }
				   }
			   }
			});
		});
	}	
}//end GPW.tree


//工具条组件
GPW.toolbar = {
	treeToolbar : {},
	initTreeToolbar:function(){
		this.treeToolbar=GPW.layout.treeLayout.attachToolbar();
		this.treeToolbar.setIconsPath(GLOBAL.IconsPath);
		if(GLOBAL.S.SEC_C_V)
			this.treeToolbar.addButton(GPC.constant.add, 0, "新增", "new.gif", "new_dis.gif");
		if(GLOBAL.S.SEC_D_V)
			this.treeToolbar.addButton(GPC.constant.delect, 1, "删除", "delect.png", "delect.png");
		this.treeToolbar.addButton("refresh", 3, "刷新", "reload.gif", "reload.gif");
		
		this.treeToolbarClick();
	},
	addFunction:function(){
		GPW.tree.mainTree.clearSelection();
		GPW.form.mainForm.clear();
	},
	treeToolbarClick:function(){
		this.treeToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.add:
					GPW.toolbar.addFunction();
					 break;
				case GPC.constant.delect:
					GPW.tree.delectTree();
					 break;
				case "refresh":
					GPW.tree.mainTree.refreshItem();
					break;
				default:
			}
		});
	}
}//end GPW.toolbar

//表单组件
GPW.form = {
	mainForm:{},
	getTreeFormData:function(){
		return GLOBAL.P.P_FORM;
	},
	getForm:function(window,formData,type){
		return window.attachForm(formData);
	},
	init:function(){
		var formData = this.getTreeFormData();		
		this.mainForm=this.getForm(GPW.layout.mainGridLayout,formData);
		this.mainForm.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				if(GLOBAL.S.SEC_D_V||GLOBAL.S.SEC_C_V){
					SpinnerCtl.show();
					GPW.form.mainForm.send(GPC.url.treeSave, function(loader, response){
						SpinnerCtl.close();
						if(response.length>0){
							GLOBAL.errorMessage(response);
						}else{
							dhtmlx.alert("保存成功！");
						}					
						GPW.tree.mainTree.refreshItem();
					});
				}else{
					dhtmlx.alert("没有操作权限，请联系管理员！");
				}
		    }
		});
	}
}//GPW.form 

//初始化组件
$(function() {
	GPW.layout.init();
	GPW.tree.init();
	GPW.toolbar.initTreeToolbar();
	GPW.form.init();
});