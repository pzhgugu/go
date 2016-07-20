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
		refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/"+GLOBAL.P.CLASSNAME+"?"+GLOBAL.P.parameter,
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryPage/"+GLOBAL.P.CLASSNAME+"?"+GLOBAL.P.parameter,
		saveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/"+GLOBAL.P.CLASSNAME+"?"+GLOBAL.P.parameter,
		detailQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/DetailQuery/"+GLOBAL.P.CLASSNAME+"/d?"+GLOBAL.P.parameter,
		updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/"+GLOBAL.P.CLASSNAME+"?format=xml&"+GLOBAL.P.parameter,		
		delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME
	},
	constant:{add:"add",edit:"edit",
		delect:"delect",query:"query",
		update:"update",detailQuery:"detailQuery",
		between:"between",defaultPagingNumber:20}
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
		if(!GLOBAL.S.SEC_U_V){
			this.mainGrid.setEditable(false); 
		}
		//this.mainGrid.enableValidation(true); 

		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";
		
		var statusBar=GPW.layout.mainGridLayout.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);
		
		this.mainGrid.enablePaging(true,GPC.constant.defaultPagingNumber,5,"pagingArea",true,"recInfoArea");
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
		this.mainGrid.attachEvent("onXLE",function(grid_obj,count){
			SpinnerCtl.close();
		});
		/* this.mainGrid.attachEvent("onXLS",function(grid_obj,count){
			SpinnerCtl.show();
		}); */
		//this.mainGrid.enableSmartRendering(true,5);
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
   	 	SpinnerCtl.show();
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
						   url: GPC.url.delectUrl,
						   data: "id="+selectId,
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
		var newOpts = Array();
		if(GLOBAL.S.SEC_C_V)
			newOpts.push(Array(GPC.constant.add,'obj', '新增'   , 'new.gif'));
		if(GLOBAL.S.SEC_U_V)
			newOpts.push(Array(GPC.constant.edit,   'obj', '修改'     , 'edit.gif'));
		if(GLOBAL.S.SEC_D_V)
			newOpts.push(Array(GPC.constant.delect,   'obj', '删除' , 'delect.png'));
		toolbar.setIconsPath(GLOBAL.IconsPath);

		toolbar.addText("text_select", 0, "选");
		this.addQueryName();
		var selectValue = toolbar.getInput("queryName").value;
		this.addOperator(selectValue);		
		this.addQueryValue();
		toolbar.addButton("query", 9, "查询", "search.gif", "search_dis.gif");
		toolbar.addSeparator("sep1", 10);
		toolbar.addButton("detailQuery", 12, "详细查询", "search.png", "search_dis.png");
		if(GLOBAL.S.SEC_U_V||GLOBAL.S.SEC_C_V||GLOBAL.S.SEC_D_V)
			toolbar.addButtonSelect("edit", 15, "编辑", newOpts, "edit.gif", "edit_dis.gif");	
		if(GLOBAL.S.SEC_U_V)
			toolbar.addButton("update", 16, "更新", "cut.gif", "cut_dis.gif");
		toolbar.addButton("maximized", 20, "全屏", "fullscreen_mode.gif", "fullscreen_mode.gif");
		//toolbar.addButton("cardView", 25, "卡片", "selection.gif", "save_dis.gif");
		this.mainToolbarClick();
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.add:
					GPW.window.initMainWindow(GPC.constant.add);
					 break;
				case GPC.constant.edit:
					GPW.window.initMainWindow(GPC.constant.edit);
					 break;
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
				case GPC.constant.detailQuery:
					GPW.window.initDetailQueryWindow();
					break;	
				case GPC.constant.update:
					GPW.grid.sendGridData();
					break;	
				case "maximized":
					var toolbar = GPW.toolbar.mainToolbar;
					if(!!parent.GPW.layout.maximized){
						parent.GPW.layout.maximized(GLOBAL.P.MAXIMIZED);
						if(GLOBAL.P.MAXIMIZED){
							toolbar.setItemText("maximized", "全屏");	
							toolbar.setItemImage("maximized", "fullscreen_mode.gif");
							GLOBAL.P.MAXIMIZED=false;
						}else{
							toolbar.setItemText("maximized", "恢复");	
							toolbar.setItemImage("maximized", "selection.gif");
							GLOBAL.P.MAXIMIZED=true;
						}
					}
					break;
				default:					
			}
		});
	}
}//end GPW.toolbar


GPW.window = {
	handle:{},
	mainWindow:{},
	createWindow:function(){
		if(!parent.GPW){
			this.initHandle();
			this.handle.attachViewportTo("layoutObj");
			this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-(GLOBAL.V.MainWindowWidth/2), 30, GLOBAL.V.MainWindowWidth, GLOBAL.V.MainWindowHeight);
		}else{
			this.mainWindow=parent.GPW.window.initSubWindow(GLOBAL.V.MainWindowWidth,GLOBAL.V.MainWindowHeight);	
			parent.GPW.cache.grid=GPW.grid.mainGrid;
		}
	},
	initHandle:function(){
		this.handle = new dhtmlXWindows();
		return this.handle;
	},
	initMainWindow:function(type){
		var name;
		if(type==GPC.constant.edit){
			var gridId=GPW.grid.mainGrid.getSelectedRowId();
			if(!gridId){
				dhtmlx.alert("请选中一条记录！");
				return ;
			}
		}
		
		this.createWindow();
			
		if(type==GPC.constant.add)
			this.mainWindow.setText("新增");
		else
			this.mainWindow.setText("修改");
		this.mainWindow.setModal(true);
		GPW.form.init(this.mainWindow,type);
	},
	initDetailQueryWindow:function(){
		this.createWindow(); 
		this.mainWindow.setText("详细查询");
		this.mainWindow.setModal(true);
		this.mainWindow.attachURL(GPC.url.detailQueryUrl);
	}
}//end GPW.window


GPW.form = {
	mainForm:{},
	getMainFormData:function(){
		return GLOBAL.P.P_FORM;
	},
	updateMainFromData:function(){
		var grid=GPW.grid.mainGrid;
		var gridId=grid.getSelectedRowId();
		grid.forEachCell(gridId,function(cellObj,ind){
			var columnid=grid.getColumnId(ind);
			GPW.form.mainForm.setItemValue(columnid, _.unescape(cellObj.getValue()));
		});
		GPW.form.mainForm.setItemValue("id", gridId);
	},
	getForm:function(window,formData,type){
		return window.attachForm(formData);
	},
	init:function(window,type){
		var formData = this.getMainFormData();
		this.mainForm = this.getForm(window, formData, type);
		
		if(type==GPC.constant.edit)
			this.updateMainFromData();
		
		this.mainForm.attachEvent("onInfo", function(name,e){
			parent.GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				//GPW.form.mainForm.validate();
				SpinnerCtl.show();
				GPW.form.mainForm.send(GPC.url.saveUrl, function(loader, response){
					SpinnerCtl.close();
					var win;
					if(!parent.GPW){
						win=GPW.window;
					}else{
						win=parent.GPW.window;
					}
					win.handle.unload();
					GLOBAL.errorMessage(response);
					GPW.grid.refreshMainGrid();
				});
		    }
		});
		
		this.mainForm.attachEvent("onValidateError", function (name, value, result){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.form.mainForm.getItemLabel(name)+"字段值不能为["+value+"]！"});
		});
	}
}//GPW.form 

	
$(function() {
	GPW.layout.init();
	GPW.grid.init();
	GPW.toolbar.init();
});

dhtmlxValidation.isValidIntegerEmpty=function(data){
    if (data=="") return true;// returns 'true' if a cell is empty
    return dhtmlxValidation.isValidInteger(data);
};
dhtmlxValidation.isValidNumericEmpty=function(data){
    if (data=="") return true;// returns 'true' if a cell is empty
    return dhtmlxValidation.isValidNumeric(data);
};