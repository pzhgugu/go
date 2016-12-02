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
		queryTotalUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryTotal/"+GLOBAL.P.CLASSNAME,
		toPdfUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/topdf/"+GLOBAL.P.CLASSNAME,
		toExcelUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/toexcel/"+GLOBAL.P.CLASSNAME
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

var GPW = {
	layout : {},
	grid : {},
	paging : {}
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

GPW.grid = {
	mainGrid : {},
	init : function() {
		this.mainGrid = GPW.layout.mainGridLayout.attachGrid();
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
		this.mainGrid.enableMultiselect(true);
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
			if(GLOBAL.P.P_GRID_FOOTER_IS_TOTAL){
				for(var j = 0;j < GLOBAL.P.P_GRID_FOOTER_TOTAL_ARRAY.length;j++) {
					var colIndex=GPW.grid.mainGrid.getColIndexById(GLOBAL.P.P_GRID_FOOTER_TOTAL_ARRAY[j]);
					var out = 0;
					for(var i=0;i<GPW.grid.mainGrid.getRowsNum();i++){
						try{
							var rowIndex=GPW.grid.mainGrid.getRowIndex(i+1);
							out+= parseFloat(GPW.grid.mainGrid.cells2(rowIndex,colIndex).getValue());
						}catch(e){
							out+=0;
						}
					}
					$("#"+GLOBAL.P.P_GRID_FOOTER_TOTAL_ARRAY[j]+"_total").html((Number(out)).toFixed(3));
				}
			}
		});
		/* this.mainGrid.attachEvent("onXLS",function(grid_obj,count){
			SpinnerCtl.show();
		}); */
		//选计
		if(GLOBAL.P.P_GRID_FOOTER_IS_SELECTSUM){
			this.mainGrid.attachFooter(GLOBAL.P.P_GRID_FOOTER_SELECTSUM);
		}
		//合计
		if(GLOBAL.P.P_GRID_FOOTER_IS_TOTAL){
			this.mainGrid.attachFooter(GLOBAL.P.P_GRID_FOOTER_TOTAL);
		}
		//总计
		if(GLOBAL.P.P_GRID_FOOTER_IS_GRANDTOTAL){
			this.mainGrid.attachFooter(GLOBAL.P.P_GRID_FOOTER_GRANDTOTAL);
		}
		this.mainGrid.attachEvent("onRowSelect", function(id,ind){
			if(GLOBAL.P.P_GRID_FOOTER_IS_SELECTSUM){
				var selectRowId=GPW.grid.mainGrid.getSelectedRowId();
				if(selectRowId.length>0) {
					for (var j = 0; j < GLOBAL.P.P_GRID_FOOTER_SELECTSUM_ARRAY.length; j++) {
						var colIndex = GPW.grid.mainGrid.getColIndexById(GLOBAL.P.P_GRID_FOOTER_SELECTSUM_ARRAY[j]);
						var out = 0;
						var rowIdArray = selectRowId.split(",");
						for (var i = 0; i < rowIdArray.length; i++) {
							try {
								var rowIndex=GPW.grid.mainGrid.getRowIndex(rowIdArray[i]);
								out += parseFloat(GPW.grid.mainGrid.cells2(rowIndex, colIndex).getValue());
							} catch (e) {
								out += 0;
							}
						}
						$("#" + GLOBAL.P.P_GRID_FOOTER_SELECTSUM_ARRAY[j] + "_selectsum").html((Number(out)).toFixed(3));
					}
				}
			}
			return true;
		});
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
	unionDataList:function(dataList){
		return dataList;
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
		dataList=this.unionDataList(dataList);
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
				//总计
				if(GLOBAL.P.P_GRID_FOOTER_IS_GRANDTOTAL){
					$.ajax({
						url:GPC.url.queryTotalUrl+"?_json="+encodeURI(strJson),
						type:'GET', //GET
						async:true,    //或false,是否异步
						data:{sumArray:GLOBAL.P.P_GRID_FOOTER_GRANDTOTAL_ARRAY},
						dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
						success:function(data,textStatus,jqXHR){
							if(!!data&&!!data.data){
								var totalData = data.data;
								for(var o in totalData){
									if(!!totalData[o]) {
										$("#" + o + "_grandtotal").html((Number(totalData[o])).toFixed(3));
									}
								}
							}
						},
						error:function(xhr,textStatus){
							console.log('错误')
							console.log(xhr)
							console.log(textStatus)
						}
					})
				}
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

	
$(function() {
	GPW.layout.init();
	GPW.grid.init();
	GPW.form.init();
});