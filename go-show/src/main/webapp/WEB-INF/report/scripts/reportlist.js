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
		refreshGridUrl:GLOBAL.S.URL+"/report/a/loadPage/"+GLOBAL.P.REPORT_NAME+"?rKind="+GLOBAL.P.REPORT,
		queryUrl:GLOBAL.S.URL+"/report/a/queryPage/"+GLOBAL.P.REPORT_NAME+"?rKind="+GLOBAL.P.REPORT,
		makeReportUrl:GLOBAL.S.URL+"/report/make/"+GLOBAL.P.REPORT_NAME+"?rKind="+GLOBAL.P.REPORT,
		delectUrl:GLOBAL.S.URL+"/report/a/delect/"
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",
				update:"update",detailQuery:"detailQuery",between:"between"}
}

var GPW = {
	layout : {},
	toolbar : {},
	grid : {},
	window : {},
	form:{},
	paging : {}
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
	///errorMessage:function(obj){
	//	GPW.grid.mainGrid.errorMessage(obj);
	//},
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
		//this.mainGrid.setEditable(false); 
		//this.mainGrid.enableValidation(true); 

		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";
		
		var statusBar=GPW.layout.mainGridLayout.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);
		
		this.mainGrid.enablePaging(true,20,5,"pagingArea",true,"recInfoArea");
		this.mainGrid.setPagingSkin("toolbar", "dhx_skyblue");
		
		//this.mainGrid.enableAutoWidth(true)
		this.mainGrid.i18n.paging=GLOBAL.paging;
		
		this.mainGrid.init();
		
		var pdfId,excelId,swfId;
		var colNum=this.mainGrid.getColumnsNum();
		for(var i=0;i<colNum;i++){
			if(this.mainGrid.getColumnId(i)=="pdf"){
				pdfId=i;
			}
			if(this.mainGrid.getColumnId(i)=="excel"){
				excelId=i;
			}
			if(this.mainGrid.getColumnId(i)=="swf"){
				swfId=i;
			}
		}
		if(GLOBAL.P.REPORT==2){
			this.mainGrid.setColumnHidden(pdfId,true);
			this.mainGrid.setColumnHidden(swfId,true);
		}else{
			this.mainGrid.setColumnHidden(excelId,true);
		}
		this.refreshMainGrid();
	},
	refreshMainGrid : function(url) {
		if(!url){
			url=GPC.url.refreshGridUrl;
		}
   	 	this.mainGrid.clearAll();
   	 	this.mainGrid.load(url,"js");
	},
	delectMainGrid:function(){
		var selectId=this.mainGrid.getSelectedRowId();
		if(!selectId){
			dhtmlx.alert("请选中一条记录删除！");
			return ;
		}
		dhtmlx.confirm({
			ok:"是", cancel:"否",
			text:"你确定删除此记录吗？ ",
			callback:function(state){
				if(state){
					$.ajax({
						   type: "GET",
						   url: GPC.url.delectUrl+selectId+"?rKind="+GLOBAL.P.REPORT,
						   success: function(msg){						   
							   GPW.grid.refreshMainGrid();
						   }
					});
				}
			}
		});
	}
}


GPW.toolbar = {
	mainToolbar : {},
	queryNames:GLOBAL.P.P_SIMPLE_QUERY_NAMES,
	queryValues:GLOBAL.P.P_SIMPLE_QUERY_VALUES,
	queryOperators:GLOBAL.P.P_SIMPLE_QUERY_OPERATOR,
	queryFormType:GLOBAL.P.P_SIMPLE_QUERY_FORM_TYPE,
	onChangeQuery:function(){
		this.addQueryValue();
	},
	onChangeQueryOperators:function(){
		var toolbar =  GPW.toolbar.mainToolbar;
		var operatorValue = toolbar.getInput("operator").value;
		var queryName = GPW.toolbar.mainToolbar.getInput("queryName");
		var selectValue=queryName.value;
		if(operatorValue==GPC.constant.between){		
			this.addBetweenValue(selectValue);
		}else{
			this.addOtherValue(selectValue);
		}
	},
	addBetweenValue : function(selectValue){
		
		this.mainToolbar.removeItem("queryValue");
		this.mainToolbar.removeItem("queryBegin");
		this.mainToolbar.removeItem("queryEnd");
		this.mainToolbar.removeItem("text_do");
		this.mainToolbar.addInput("queryBegin", 3, "", 100);
		this.mainToolbar.addText("text_do", 4, "到");
		this.mainToolbar.addInput("queryEnd", 5, "", 100);
		
		var type;
		_.map(this.queryFormType, function(value, key){
			if(key==selectValue){
				type=value;
				return;
			}
		})

		if(type=="calendar"){
			var beginCalendar = new dhtmlXCalendarObject(this.mainToolbar.getInput("queryBegin"));
			//beginCalendar.setDateFormat("%Y-%m-%d");
			//this.mainToolbar.setValue("queryBegin", beginCalendar.getFormatedDate());
			
			
			var endCalendar = new dhtmlXCalendarObject(this.mainToolbar.getInput("queryEnd"));
			//endCalendar.setDateFormat("%Y-%m-%d");
			//this.mainToolbar.setValue("queryEnd", endCalendar.getFormatedDate());
		}else{
			
		}
	},
	addOtherValue : function(selectValue){
		var toolbar =  GPW.toolbar.mainToolbar;
		var isEx = false;
		
		_.map(this.queryValues, function(value, key){
			//下拉选项
			if(key==selectValue){
				toolbar.removeItem("queryValue");
				toolbar.removeItem("queryBegin");
				toolbar.removeItem("text_do");
				toolbar.removeItem("queryEnd");
				toolbar.addSelectEx("queryValue", 3, value, 100);
				isEx=true;
			}
		});
		if(!isEx){			
			toolbar.removeItem("queryValue");
			toolbar.removeItem("queryBegin");
			toolbar.removeItem("text_do");
			toolbar.removeItem("queryEnd");
			
			var type;
			_.map(this.queryFormType, function(value, key){
				if(key==selectValue){
					type=value;
					return;
				}
			})
			toolbar.addInput("queryValue", 3, "", 200);
			if(type=="calendar"){
				new dhtmlXCalendarObject(toolbar.getInput("queryValue"));
			}
		}
	},
	addOperator : function(selectValue){	
		var toolbar =  GPW.toolbar.mainToolbar;
		_.find(this.queryOperators, function(oper,key){ 
			if(key==selectValue){	
				toolbar.removeItem("operator");
				toolbar.addSelectEx("operator", 2, oper, 70,"GPW.toolbar.onChangeQueryOperators()");
				return;
			}
		});
	},
	addQueryName : function(){
		this.mainToolbar.addSelectEx("queryName", 1, this.queryNames, 100,"GPW.toolbar.onChangeQuery()");
	},
	addQueryValue : function(){
		var queryName = GPW.toolbar.mainToolbar.getInput("queryName");
		var selectValue=queryName.value;	
		var that = this;
		var toolbar = that.mainToolbar;		
		that.addOperator(selectValue);	
		var operatorValue = toolbar.getInput("operator").value;
		if(operatorValue==GPC.constant.between){		
			this.addBetweenValue(selectValue);
		}else{		
			this.addOtherValue(selectValue);
		}
	},
	init : function() {
		this.mainToolbar=GPW.layout.mainGridLayout.attachToolbar();	
		var toolbar = this.mainToolbar;
		toolbar.setIconsPath(GLOBAL.IconsPath);

		toolbar.addText("text_select", 0, "选");
		this.addQueryName();
		var selectValue = toolbar.getInput("queryName").value;
		this.addOperator(selectValue);		
		this.addQueryValue();
		toolbar.addButton("query", 9, "查询", "new.gif", "new_dis.gif");
		toolbar.addSeparator("sep1", 10);
		if(GLOBAL.S.SEC_D_V)
			toolbar.addButton(GPC.constant.delect, 16, "删除", "new.gif", "new_dis.gif");
		if(GLOBAL.S.SEC_U_V){
			toolbar.addSeparator("sep2", 20);
			toolbar.addText("text_select1", 21, "生成报表日期：");
			if(GLOBAL.P.REPORT_TYPE=="region"){
				toolbar.addInput("makeReportDate", 22, "", 100);
				new dhtmlXCalendarObject(toolbar.getInput("makeReportDate"));
				toolbar.addInput("endDate", 23, "", 100);
				new dhtmlXCalendarObject(toolbar.getInput("endDate"));
			}else{
				toolbar.addInput("makeReportDate", 22, "", 200);
				var beginCalendar = new dhtmlXCalendarObject(toolbar.getInput("makeReportDate"));
				if(GLOBAL.P.REPORT_TYPE=="month"){
					beginCalendar.setDateFormat("%Y-%m");
				}else if(GLOBAL.P.REPORT_TYPE=="year"){
					beginCalendar.setDateFormat("%Y");
				}
			}		
			toolbar.addButton("make", 23, "生成报表", "new.gif", "new_dis.gif");
		}
		this.mainToolbarClick();
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.delect:
					GPW.grid.delectMainGrid();
					 break;
				case GPC.constant.query:
					var toolbar = GPW.toolbar.mainToolbar;
					var where = toolbar.getInput("queryName").value;
					var operator = toolbar.getInput("operator").value;

					var arrayObj = new Array();
					if(operator==GPC.constant.between){
						var begin = toolbar.getInput("queryBegin").value;
						var end = toolbar.getInput("queryEnd").value;
						arrayObj[0]=GLOBAL.getBetweenQueryObjct(where,operator,begin,end);
					}else{
						var whereValue = toolbar.getInput("queryValue").value;
						arrayObj[0]=GLOBAL.getQueryObjct(where,operator,whereValue);
					}
					var strJosn = JSON.stringify(arrayObj); 
					//var url=GPC.url.queryUrl+"&where="+where+"&operator="+operator+"&whereValue="+whereValue;
					var url=GPC.url.queryUrl+"&_json="+encodeURI(strJosn);
					GPW.grid.refreshMainGrid(url);
					
					break;		
				case "make":
					var toolbar = GPW.toolbar.mainToolbar;
					var makeReportDate=toolbar.getInput("makeReportDate").value;
					var url;
					if(GLOBAL.P.REPORT_TYPE=="region"){
						url='beginDate='+makeReportDate+'&endDate='+toolbar.getInput("endDate").value;
					}else{
						url='beginDate='+makeReportDate;
					}
					if(makeReportDate==""){
						dhtmlx.alert("请选择生成报表日期！");
						return;
					}
					SpinnerCtl.show();
					$.ajax({     
					    url:GPC.url.makeReportUrl,     
					    type:'post',     
					    data:url,     
					    error:function(){
					    	SpinnerCtl.close();
					       alert('error');     
					    },     
					    success:function(data){     
					    	dhtmlx.alert("报表生成成功！");
					    	SpinnerCtl.close();
					    	GPW.grid.refreshMainGrid();
					    }  
					});  
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