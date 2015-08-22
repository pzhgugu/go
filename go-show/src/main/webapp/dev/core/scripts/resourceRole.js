/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */


$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
	url:{
		refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/resLoadRole?_value="+GLOBAL.P.value,
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/resQueryRole?_value="+GLOBAL.P.value,
		updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateResourceRole/Roles?format=xml&_value="+GLOBAL.P.value
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

var GPW = {
	layout : {},
	toolbar : {},
	grid : {}
};

GPW.layout = {
	mainLayout : {},
	mainGridLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
		this.mainGridLayout=this.mainLayout.cells("a");
		this.mainGridLayout.hideHeader();
	}
}

GPW.grid = {
	mainGrid : {},
	gridDataProcessor : {},
	init : function() {
		this.mainGrid = GPW.layout.mainGridLayout.attachGrid();
		this.mainGrid.setImagePath(GLOBAL.IconsPath);
		this.mainGrid.setHeader("角色编码,角色名称,授权名称");
		this.mainGrid.setColumnIds("name,alias,permissionStr");
		this.mainGrid.setInitWidths("120,120,*");
		this.mainGrid.setColAlign("left,left,left");
		this.mainGrid.setColTypes("ro,ro,clist");
		this.mainGrid.setColSorting("str,str,str");
		this.mainGrid.registerCList(2,GLOBAL.P.P_PERMISSION);

		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";
		
		var statusBar=GPW.layout.mainGridLayout.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);
		
		this.mainGrid.enablePaging(true,20,5,"pagingArea",true,"recInfoArea");
		this.mainGrid.setPagingSkin("toolbar", "dhx_skyblue");
		
		//this.mainGrid.enableAutoWidth(true)
		this.mainGrid.i18n.paging=GLOBAL.paging;
		
		this.mainGrid.init();
		
		this.mainGrid.attachEvent("onValidationError", function(id,index,value,rule){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.grid.mainGrid.getColLabel(index)+","+GLOBAL.validText(rule)+",字段值不能为["+value+"]！"});
		});
		
		this.gridDataProcessor = new dataProcessor(GPC.url.updateUrl); //lock feed url
		//this.gridDataProcessor.setTransactionMode("POST",false); //set mode as send-all-by-post
		this.gridDataProcessor.setTransactionMode("REST");
		this.gridDataProcessor.setUpdateMode("off"); //disable auto-update
		this.gridDataProcessor.enableDataNames(true); 
		//只发送更改的项目
		this.gridDataProcessor.enablePartialDataSend(true);
		this.gridDataProcessor.init(this.mainGrid); //link dataprocessor to the grid
		
		this.gridDataProcessor.attachEvent("onBeforeDataSending", function(id, state, data){
			//去除Data数据的发送
			_.each(data,function (v, k, value) { 
				if(!!v.data){
					v.data=null;
					delete v.data;
				}
			});
		    return true;
		});
		this.gridDataProcessor.attachEvent("onAfterUpdate", function(id, action, tid, element){
			if(element instanceof Element){
				if(element.getElementsByTagName("code").item(0)!=null){
					var code=element.getElementsByTagName("code").item(0).childNodes[0].nodeValue;
					var message=element.getElementsByTagName("message").item(0).childNodes[0].nodeValue;
					if(code==-1){
						parent.dhtmlx.message({ 
					    	type:"error", 
					    	expire: -1,
					    	text:message});
					}
				}
			}
		});
		this.refreshMainGrid();
	},
	sendGridData : function(){
		this.gridDataProcessor.sendData();
		
	},
	refreshMainGrid : function(url) {
		if(!url){
			url=GPC.url.refreshGridUrl;
		}
   	 	this.mainGrid.clearAll();
   	 	this.mainGrid.load(url,"js");
	}
}


GPW.toolbar = {
	mainToolbar : {},
	init : function() {
		this.mainToolbar=GPW.layout.mainGridLayout.attachToolbar();	
		var toolbar = this.mainToolbar;
		toolbar.setIconsPath(GLOBAL.IconsPath);
		toolbar.addText("text_select", 0, "选");
		this.mainToolbar.addSelectEx("queryName", 1, [{"text":"编码","value":"name"},{"text":"名称","value":"alias"}], 100);
		toolbar.addInput("queryValue", 3, "", 200);
		toolbar.addButton("query", 9, "查询", "new.gif", "new_dis.gif");
		toolbar.addSeparator("sep1", 10);
		toolbar.addButton("update", 16, "更新", "new.gif", "new_dis.gif");
		this.mainToolbarClick();
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.query:
					var toolbar = GPW.toolbar.mainToolbar;
					var where = toolbar.getInput("queryName").value;
					var queryValue = toolbar.getInput("queryValue").value;
					var url=GPC.url.queryUrl+"&where="+encodeURI(where)+"&whereValue="+encodeURI(queryValue);
					GPW.grid.refreshMainGrid(url);					
					break;		
				case GPC.constant.update:
					GPW.grid.sendGridData();
					break;	
				default:					
			}
		});
	}
}//end GPW.toolbar

$(function() {
	GPW.layout.init();
	GPW.grid.init();
	GPW.toolbar.init();
});