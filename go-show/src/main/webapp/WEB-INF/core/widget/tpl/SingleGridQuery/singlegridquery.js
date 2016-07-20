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
		updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/"+GLOBAL.P.CLASSNAME+"?format=xml",	
		saveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/"+GLOBAL.P.CLASSNAME,
		delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME,
		toPdfUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/topdf/"+GLOBAL.P.CLASSNAME,
		toExcelUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/toexcel/"+GLOBAL.P.CLASSNAME,
		importExcelDataUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/inexcel/"+GLOBAL.P.CLASSNAME+"/"+GLOBAL.V.InExcelName,
		refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/"+GLOBAL.P.CLASSNAME
	},
	constant:{add:"add",edit:"edit",delect:"delect",query:"query",
				update:"update",detailQuery:"detailQuery",between:"between",defaultPagingNumber:20}
}

var GPW = {
	layout : {},
	toolbar : {},
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
		this.queryLayout.setHeight(GLOBAL.V.QueryHeight);
		this.queryLayout.hideHeader();
		
		this.mainGridLayout=this.mainLayout.cells("b");
		this.mainGridLayout.hideHeader();
	}
}

GPW.grid = {
	mainGrid : {},
	gridDataProcessor : {},
	inDataProcessor : {},
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
		this.mainGrid.setEditable(GLOBAL.P.tableEdit); 
		//this.mainGrid.enableValidation(true); 

		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";
		
		var statusBar=GPW.layout.mainGridLayout.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);
		
		this.mainGrid.enablePaging(true,GPC.constant.defaultPagingNumber,5,"pagingArea",true,"recInfoArea");
		this.mainGrid.setPagingSkin("toolbar", "dhx_skyblue");
		
		//this.mainGrid.enableAutoWidth(true)
		this.mainGrid.i18n.paging=GLOBAL.paging;
		
		
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
		
		this.mainGrid.init();
		this.mainGrid.attachEvent("onXLE",function(grid_obj,count){
			SpinnerCtl.close();
		});
		/* this.mainGrid.attachEvent("onXLS",function(grid_obj,count){
			SpinnerCtl.show();
		}); */

		this.refreshMainGrid();
	},
	sendGridData : function(){
		this.gridDataProcessor.sendData();	
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
	},
	refreshMainGrid : function(url) {
		if(!url){
			url=GPC.url.refreshGridUrl;
		}
   	 	this.mainGrid.clearAll();
      	SpinnerCtl.show();
   	 	this.mainGrid.load(url,"js");
	},
	callbackParse:function(){		
		GPW.grid.mainGrid.forEachRow(function(id){
			GPW.grid.gridDataProcessor.setUpdated(id,"inserted");
			GPW.grid.gridDataProcessor.obj.setUserData(id, GPW.grid.gridDataProcessor.action_param,"inserted");
		});
		GPW.grid.gridDataProcessor.enablePartialDataSend(false)
	},
	inMainGrid : function(result) {
   	 	this.mainGrid.clearAll();
      	SpinnerCtl.show();
      	GLOBAL.P.tableEdit=true;
      	GPW.toolbar.tableEditToolbar(GLOBAL.P.tableEdit); 
   	 	this.mainGrid.parse(result,this.callbackParse,"js");
	}
}

GPW.toolbar = {
	mainToolbar : {},
	init : function() {
		if(GLOBAL.S.SEC_U_V||GLOBAL.S.SEC_D_V){
			this.mainToolbar=GPW.layout.queryLayout.attachToolbar();	
			var toolbar = this.mainToolbar;
			toolbar.setIconsPath(GLOBAL.IconsPath);
	
			if(GLOBAL.S.SEC_U_V)
				toolbar.addButton(GPC.constant.edit, 1, "修改", "edit.gif", "edit_dis.gif");
			if(GLOBAL.S.SEC_D_V)
				toolbar.addButton(GPC.constant.delect, 7, "删除", "delect.png", "delect.png");
			if(GLOBAL.S.SEC_U_V){
				if(GLOBAL.P.tableEdit){	
					toolbar.addButton(GPC.constant.add, 0, "新增一行", "new.gif", "new_dis.gif");
					toolbar.addButton(GPC.constant.update, 2, "更新", "cut.gif", "cut_dis.gif");
					toolbar.addButton("tableEdit", 15, "关闭表格编辑", "icon_grid.gif", "icon_grid.gif");
					toolbar.hideItem(GPC.constant.edit);
				}else{
					toolbar.addButton(GPC.constant.add, 0, "新增", "new.gif", "new_dis.gif");
					toolbar.addButton(GPC.constant.update, 2, "更新", "cut.gif", "cut_dis.gif");
					toolbar.addButton("tableEdit", 15, "启用表格编辑", "cancel.gif", "cancel.gif");
					toolbar.hideItem("update");
				}
			}
			this.mainToolbarClick();
		}
	},
	tableEditToolbar:function(tableEdit){
		var toolbar = GPW.toolbar.mainToolbar;
		if(tableEdit){
			toolbar.showItem(GPC.constant.update);
			toolbar.hideItem(GPC.constant.edit);
			toolbar.setItemText("tableEdit", "关闭表格编辑");
			toolbar.setItemImage("tableEdit", "icon_grid.gif");
			toolbar.setItemText(GPC.constant.add, "新增一行");
		}else{
			toolbar.hideItem(GPC.constant.update);
			toolbar.showItem(GPC.constant.edit);
			toolbar.setItemText("tableEdit", "启用表格编辑");
			toolbar.setItemImage("tableEdit", "cancel.gif");
			toolbar.setItemText(GPC.constant.add, "新增");
		}
		GPW.grid.mainGrid.setEditable(tableEdit); 
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.add:
					if(GLOBAL.P.tableEdit){	
						GPW.grid.mainGrid.addRow(GPW.grid.mainGrid.uid(),"",0);
					}else{
						GPW.window.initMainWindow(GPC.constant.add);
					}
					 break;
				case GPC.constant.edit:
					GPW.window.initMainWindow(GPC.constant.edit);
					 break;
				case GPC.constant.delect:
					GPW.grid.delectMainGrid();
					 break;
				case GPC.constant.update:
					GPW.grid.sendGridData();
					break;	
				case "tableEdit":
					GLOBAL.P.tableEdit=!GLOBAL.P.tableEdit;
					GPW.toolbar.tableEditToolbar(GLOBAL.P.tableEdit); 
					break;
				default:					
			}
		});
	}	
}

GPW.form = {
	mainForm:{},
	formButtonQueryLaterCallback: {},
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
		this.mainForm=this.getForm(window,formData);
		
		if(type==GPC.constant.edit)
			this.updateMainFromData();		
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				//GPW.form.mainForm.validate();
				GPW.form.mainForm.send(GPC.url.saveUrl, function(loader, response){
					var win;
					if(!parent.GPW){
						win=GPW.window;
					}else{
						win=parent.GPW.window;
					}
					win.handle.unload();
					if(response.length>0){
						var msg = JSON.parse(response);
						GLOBAL.errorMessage(msg);
					}
					GPW.grid.refreshMainGrid();
				});
		    }
		});
		this.mainForm.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		this.mainForm.attachEvent("onValidateError", function (name, value, result){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.form.mainForm.getItemLabel(name)+"字段值不能为["+value+"]！"});
		});
	},
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
					if(index>-1){
						 var itemName=name.substring(0,index);
						 var valueEnd = form.getInput(itemName+"_End").value;
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
	initTop:function(){
		if(GLOBAL.V.ImportExcelData=="1"){
			var form=$("<div id='fileDiv'><form id='realForm' method='POST' enctype='multipart/form-data' style='margin-right: 60px;'><div id='dhxForm'></div></form></div>");
		    $(document.body.firstChild).before(form);
			
		    this.queryForm  = new dhtmlXForm("dhxForm", this.allQueryForm);		
		    GPW.layout.queryLayout.attachObject("fileDiv");
		}else{
			this.queryForm = GPW.layout.queryLayout.attachForm(this.allQueryForm);
		}
		var form = this.queryForm ;
		var dataList=[{"type":"button","name":"query","value":"查询"},
	        {"type":"newcolumn"},
	        {"type":"button","name":"reset","value":"重置"}];
		if(GLOBAL.V.ExportExcel=="1"){
			dataList=_.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"excel","value":"导出Excel"}]);
		}
		if(GLOBAL.V.ExportPdf=="1"){
			dataList=_.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"pdf","value":"导出PDF"}]);
		}
		if(GLOBAL.V.ImportExcelData=="1"){
			dataList=_.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"ImportExcelData","value":"导入Excel"},{"type":"newcolumn"},{type : "file",offsetTop:10,name : "file"}]);
		}
		var itemData = {"type":"block","width":800,"name":"blockButton",
				"list":dataList}
		
		form.addItem(null, itemData);
		form.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		form.attachEvent("onButtonClick", function(id){
			switch(id)
			{
				case "excel":
					var strJson=GPW.form.queryJson();
					window.location.href=GPC.url.toExcelUrl+"?_json="+encodeURI(strJson)+"&_report="+GLOBAL.V.ExportReportName;
					break;
				case "pdf":
					var strJson=GPW.form.queryJson();
					window.location.href=GPC.url.toPdfUrl+"?_json="+encodeURI(strJson)+"&_report="+GLOBAL.V.ExportReportName;
					break;
				case "ImportExcelData":
					$("#realForm").attr("action", GPC.url.importExcelDataUrl);
					$('#realForm').ajaxForm({
						dataType : "json",
						error : function(request) {
							alert(request.responseText)
						},
						success : processJson
					});
					function processJson(response) {
						GPW.grid.inMainGrid(response);
					}
					$("#realForm").submit();
					break;
				case "reset":
					var form=GPW.form.queryForm;
					form.forEachItem(function(name){
						var type=form.getItemType(name);
						if(type!='hidden'&&type!='block'&&type!='button'){
							form.setItemValue(name, '');
						}
					});
				case "query":
					var i=0;
					var arrayObj = new Array();
					var form=GPW.form.queryForm;
					form.forEachItem(function(name){
						var type = form.getItemType(name);
						if("input"==type||"combo"==type||"calendar"==type){
							var iEnd = name.lastIndexOf("_End");
							if(iEnd<0){
								var index = name.lastIndexOf("_Begin");
								var itemName=name.substring(0,index);
								var formEnd=form.getInput(itemName+"_End");
								if(index>-1&&formEnd!=null){
									 var valueEnd = form.getInput(itemName+"_End").value;
									 var valueBegin = formEnd.value;
									 if(!!valueBegin&&!!valueEnd){
											arrayObj[i]=GLOBAL.getBetweenQueryObjct(itemName,"between",valueBegin,valueEnd);
											i++;
									 }
								}else{
									var value = form.getItemValue(name);
									var options = form.getItemValue(name+"_options");
									if(!options){
										options="=";
									}
									if(!_.isEmpty(value)){
										arrayObj[i]=GLOBAL.getQueryObjct(name,options,value);
										i++;
									}
								}
							}
							
						}
					});
					var strJson = JSON.stringify(arrayObj); 
					var grid=GPW.grid.mainGrid;
					grid.clearAll();
					SpinnerCtl.show();
					grid.load(GPC.url.queryUrl+"?_json="+encodeURI(strJson),"js");
					GPW.form.formButtonQueryLaterCallback();
					break;
				default:					
			}			
		});
	}
}

GPW.window = {
	handle:{},
	mainWindow:{},
	createWindow:function(){
		if(!parent.GPW){
			this.initHandle();
			this.handle.attachViewportTo("layoutObj");
			this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-(GLOBAL.V.MainWindowWidth/2), 30, GLOBAL.V.MainWindowWidth, GLOBAL.V.MainWindowHeight);
		}else{
			this.mainWindow=parent.GPW.window.initSubWindow();	
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
	}
}//end GPW.window
	
$(function() {
	GPW.layout.init();
	GPW.grid.init();
	GPW.form.initTop();
	GPW.toolbar.init();
});