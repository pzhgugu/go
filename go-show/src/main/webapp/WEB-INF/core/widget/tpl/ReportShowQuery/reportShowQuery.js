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
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryPage/"+GLOBAL.P.CLASSNAME,
		toPdfUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/topdf/"+GLOBAL.P.CLASSNAME,
		toExcelUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/toexcel/"+GLOBAL.P.CLASSNAME,
		toSwfUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/toswf/"+GLOBAL.P.CLASSNAME
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

var GPW = {
	layout : {},
	grid : {},
	paging : {},
	tabbar : {}
};

GPW.layout = {
	mainLayout : {},
	mainGridLayout:{},
	queryLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "2E");
		this.queryLayout=this.mainLayout.cells("a");
		if(!GLOBAL.V.QueryHeight){
			GLOBAL.V.QueryHeight=150;
		}
		this.queryLayout.setHeight(GLOBAL.V.QueryHeight);
		this.queryLayout.hideHeader();
		
		this.mainGridLayout=this.mainLayout.cells("b");
		this.mainGridLayout.hideHeader();
	}
}

GPW.form = {
	queryForm:{},
	allQueryForm:GLOBAL.P.P_TOP_FORM,
	queryJson:function(){
		var form = this.queryForm ;
		var i=0;
		var arrayObj = new Array();
		form.forEachItem(function(name){
			var type = form.getItemType(name);
			if("input"==type||"combo"==type||"calendar"==type){
				var iEnd = name.lastIndexOf("_End");
				if(iEnd<0){
					var index = name.lastIndexOf("_Begin");
					var itemName=name.substring(0,index);
					var formEnd=form.getInput(itemName+"_End");
					if(index>-1&&formEnd!=null){
						 var valueEnd = formEnd.value;
						 var valueBegin = form.getInput(name).value;
						 if(!!valueBegin&&!!valueEnd){
								arrayObj[i]=GLOBAL.getBetweenQueryObjct(itemName,"between",valueBegin,valueEnd);
								i++;
						 }
					}else{
						var value = form.getItemValue(name);
						if(!_.isEmpty(value)){
							arrayObj[i]=GLOBAL.getQueryObjct(name,"=",value);
							i++;
						}
					}
				}
				
			}
		});
		return JSON.stringify(arrayObj); 
	},
	getForm:function(window,formData,type){
		return window.attachForm(formData);
	},
	init:function(){
		this.queryForm=this.getForm(GPW.layout.queryLayout,this.allQueryForm);
		var form = this.queryForm ;

		//form.removeItem("blockButton");
		var dataList=[{"type":"button","name":"query","value":"查询"},
	        {"type":"newcolumn"},
	        {"type":"button","name":"reset","value":"重置"}];
		if(GLOBAL.V.ExportExcel=="1"){
			dataList=_.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"excel","value":"导出Excel"}]);
		}
		if(GLOBAL.V.ExportPdf=="1"){
			dataList=_.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"pdf","value":"导出PDF"}]);
		}
		var itemData = {"type":"block","width":800,"name":"blockButton",
				"list":dataList}
		
		form.addItem(null, itemData);
		form.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		form.attachEvent("onButtonClick", function(id){
			if (id == "excel") {
				var strJson=GPW.form.queryJson();
				window.location.href=GPC.url.toExcelUrl+"?_json="+encodeURI(strJson)+"&_report="+GLOBAL.V.ExportReportName;
			}
			if (id == "pdf") {
				var strJson=GPW.form.queryJson();
				window.location.href=GPC.url.toPdfUrl+"?_json="+encodeURI(strJson)+"&_report="+GLOBAL.V.ExportReportName;
			}
			if (id == "query") {
				var strJson=GPW.form.queryJson();
				var grid=GPW.grid.mainGrid;
				grid.clearAll();
				SpinnerCtl.show();
				grid.load(GPC.url.queryUrl+"?_json="+encodeURI(strJson),"js");
		    }
			if(id=="reset"){
				var form=GPW.form.queryForm;
				form.forEachItem(function(name){
					var type=form.getItemType(name);
					if(type!='hidden'&&type!='block'&&type!='button'){
						form.setItemValue(name, '');
					}
				});
			}
		});
	}
}


GPW.grid = {
	mainGrid : {},
	init : function() {
		this.mainGrid = GPW.tabbar.subListTabbar.tabs("query").attachGrid();
		this.mainGrid.setImagePath(GLOBAL.IconsPath);
		this.mainGrid.setHeader(GLOBAL.P.P_GRID_HEADER);
		this.mainGrid.setColumnIds(GLOBAL.P.P_GRID_COLUMNIDS);
		this.mainGrid.setInitWidths(GLOBAL.P.P_GRID_WIDTH);
		this.mainGrid.setColAlign(GLOBAL.P.P_GRID_COLALIGN);
		this.mainGrid.setColTypes(GLOBAL.P.P_GRID_COLTYPES);
		this.mainGrid.setColSorting(GLOBAL.P.P_GRID_COLSORTING);
		this.mainGrid.setColValidators(GLOBAL.P.P_GRID_VALIDATORS);
		GLOBAL.initGridCombo(this.mainGrid,GLOBAL.P.P_GRID_FORMGRIDASSEMBLE);
		this.mainGrid.setDateFormat("%Y-%m-%d");
		this.mainGrid.setEditable(false);
		//this.mainGrid.enableValidation(true);

		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";

		var statusBar=GPW.layout.mainGridLayout.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);

		this.mainGrid.enablePaging(true,200,5,"pagingArea",true,"recInfoArea");
		this.mainGrid.setPagingSkin("toolbar", "dhx_skyblue");

		//this.mainGrid.enableAutoWidth(true)
		this.mainGrid.i18n.paging=GLOBAL.paging;

		this.mainGrid.init();
		this.mainGrid.attachEvent("onXLE",function(grid_obj,count){
			SpinnerCtl.close();
		});
		/* this.mainGrid.attachEvent("onXLS",function(grid_obj,count){
		 SpinnerCtl.show();
		 }); */

		this.refreshMainGrid();
	},
	refreshMainGrid : function(url) {
		if(!url){
			url=GPC.url.refreshGridUrl;
		}
		this.mainGrid.clearAll();
		//this.mainGrid.load(url,"js");
	}
}

GPW.tabbar={
	subListTabbar:{},
	initSubList:function() {
		this.subListTabbar = GPW.layout.mainGridLayout.attachTabbar({
			tabs: [{id: "query", text: "查询", active: 1},
				{id: "show", text: "查看报表"}]
		});
		this.subListTabbar.attachEvent("onSelect", function(actvId, lastId){
			if(actvId=="show"){
				var strJson=GPW.form.queryJson();
				var url=GPC.url.toSwfUrl+"?_json="+encodeURI(strJson)+"&_report="+GLOBAL.V.ExportReportName;
				GPW.tabbar.subListTabbar.tabs(actvId).attachURL(url);
			}
			return true;
		});
	}
}
	
$(function() {
	GPW.layout.init();
	GPW.form.init();
	GPW.tabbar.initSubList();
	GPW.grid.init();
});