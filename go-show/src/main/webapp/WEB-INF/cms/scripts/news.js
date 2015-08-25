/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 * @require common:widget/jquery/jquery.form.js
 */
$(window).resize(function() {
	GPW.layout.treeLayout.setWidth(200);
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
	url:{
		treeQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/loadTree",
		publishInfoUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/publishInfo",
		loadActiveTabUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/container"
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",select:"select",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

var GPW = {
	layout : {},
	toolbar : {},
	tree : {},
	grid : {},
	window : {},
	form:{},
	paging : {},
	cache : {},
	tabbar : {}
};

GPW.cache = {
	grid:{}	,
	recodeId:null
}

GPW.layout = {
	mainLayout : {},
	mainGridLayout:{},
	treeLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "2U");
		this.treeLayout=this.mainLayout.cells("a");
		this.treeLayout.setWidth(200);
		this.treeLayout.hideHeader();	

		this.tabbarLayout=this.mainLayout.cells("b");
		this.tabbarLayout.setHeight(300);
		this.tabbarLayout.hideHeader();	
		
	}
}


GPW.tree = {
	mainTree:{},
	init:function(){
		this.mainTree=GPW.layout.treeLayout.attachTree();
		this.mainTree.setImagePath(GLOBAL.IconsPath+"dhxtree_skyblue/");
		this.mainTree.enableTreeImages("false");
		this.mainTree.setXMLAutoLoading(GPC.url.treeQueryUrl); 
		this.mainTree.setDataMode("json");
		this.mainTree.loadJSON(GPC.url.treeQueryUrl); 
		this.onClick(); 
		
	},
	onClick:function(){
		this.mainTree.attachEvent("onClick",function(id){
		    if(!this.hasChildren(id)){
		    	GPW.tabbar.loadActiveTab();
		    }
		    return true;
		});
	}
}//end GPW.tree


GPW.tabbar={
	subListTabbar:{},
	subInfo:[{"name":"publish","alias":"发布箱","oneName":"tpl"},{"name":"pendingtrial","alias":"待审箱","oneName":"tpl"},{"name":"rubbish","alias":"垃圾箱","oneName":"tpl"},{"name":"publishInfo","alias":"发布信息","oneName":"tpl"}],
	getTabs:function(){
		var sInfo=GPW.tabbar.subInfo;
		var arr=new Array(sInfo.length);
		for(var i=0;i<sInfo.length;i++){
			var o={};
			o.id=sInfo[i].name;
			o.text=sInfo[i].alias;
			if(i==0){
				o.active=true;
			}
			arr[i]=o;
		}
		return arr;
	},
	attachURL:function(actvId){
		var sInfo=GPW.tabbar.subInfo;
		var o=_.find(sInfo, function(v) {
			if(v.name==actvId){
				return v;
			}
		});
		var url ;
		if(actvId=='publishInfo'){
			url = GPC.url.publishInfoUrl;
			if(GPW.cache.recodeId!=null){
				url+="?id="+GPW.cache.recodeId;
				GPW.cache.recodeId=null;
			}
		}else{
			var pid=GPW.tree.mainTree.getSelectedItemId();
			if(pid==null||pid==""){
				return ;
			}
			url = GPC.url.loadActiveTabUrl+"?_pid="+pid+"&_type="+actvId;
		}
		this.subListTabbar.tabs(actvId).attachURL(url);
	},
	initSubList:function(){
		this.subListTabbar=GPW.layout.tabbarLayout.attachTabbar({			
			tabs: GPW.tabbar.getTabs()
			});
		this.subListTabbar.attachEvent("onSelect", function(actvId, lastId){
			var id=GPW.tree.mainTree.getSelectedItemId();
			if(id==null||id==""){
				dhtmlx.alert("请选择分类节点！");
				return false;
			}
			GPW.tabbar.attachURL(actvId);
		    return true;
		}); 
	},
	loadActiveTab:function(){
		var actvId = this.subListTabbar.getActiveTab();
		this.attachURL(actvId);
	}
}


GPW.window = {
	handle:{},
	subWindow:{},
	initHandle:function(){
		this.handle = new dhtmlXWindows();
		return this.handle;
	},
	initSubWindow:function(width,height){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.subWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-(width/2), 30, width, height);
		return this.subWindow;
	}
}//end GPW.window
		
$(function() {
	GPW.layout.init();
	GPW.tree.init();
	GPW.tabbar.initSubList();
});
