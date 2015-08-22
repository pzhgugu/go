<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/sources/dhtmlxCommon/codebase/dhtmlxdataprocessor.js"></script> -->
<div id="layoutObj"></div>
<script type="text/javascript">


function initGridCombo(grid,object){//{"colTypes":[{"text":"编辑列","value":"ed"},{"text":"文本编辑","value":"edtxt"}]}
	_.map(object, function(value, key){ 
		var colIndex=grid.getColIndexById(key);
		_.each(value,function (v, k, value) { 
			grid.getCombo(colIndex).put(v.value,v.text);
		}); 
	});
}

GLOBAL.S.URL = "${S_URL}";
GLOBAL.S.URL_R = "${S_URL_R}";
GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";

$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
	url:{
		refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/${P_CLASS_NAME}?_key=${S_PARAM._key}&_value=${S_PARAM._value}",
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryPage/${P_CLASS_NAME}?_key=${S_PARAM._key}&_value=${S_PARAM._value}",
		saveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/${P_CLASS_NAME}?_key=${S_PARAM._key}&_value=${S_PARAM._value}",
		detailQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/DetailQuery/"+GLOBAL.P.CLASSNAME+"/d?_key=${S_PARAM._key}&_value=${S_PARAM._value}",
		updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/${P_CLASS_NAME}?format=xml&_key=${S_PARAM._key}&_value=${S_PARAM._value}",		
		delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME
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
	mainLayoutGrid:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
		this.mainLayoutGrid=this.mainLayout.cells("a");
		this.mainLayoutGrid.hideHeader();
	}
}

GPW.grid = {
	mainGrid : {},
	gridDataProcessor : {},
	errorMessage:function(obj){
		GPW.grid.mainGrid.errorMessage(obj);
	},
	init : function() {
		this.mainGrid = GPW.layout.mainLayoutGrid.attachGrid();
		this.mainGrid.setImagePath(GLOBAL.S.URL_R+"/images/dhx/");
		this.mainGrid.setHeader("${P_GRID_HEADER}");
		this.mainGrid.setColumnIds("${P_GRID_COLUMNIDS}");
		this.mainGrid.setInitWidths("${P_GRID_WIDTH}");
		this.mainGrid.setColAlign("${P_GRID_COLALIGN}");
		this.mainGrid.setColTypes("${P_GRID_COLTYPES}");
		this.mainGrid.setColSorting("${P_GRID_COLSORTING}");
		initGridCombo(this.mainGrid,${P_GRID_FORMGRIDASSEMBLE});
		this.mainGrid.setDateFormat("%Y-%m-%d");
		//this.mainGrid.setEditable(false); 		
		
		var pagingContainer="<div id='recinfoArea' /><div id='pagingArea' style='border:none;'/>";
		
		var statusBar=GPW.layout.mainLayoutGrid.attachStatusBar({height: 28});
		statusBar.setText(pagingContainer);
		
		this.mainGrid.enablePaging(true,20,5,"pagingArea",true,"recInfoArea");
		this.mainGrid.setPagingSkin("toolbar", "dhx_skyblue");
		
		//this.mainGrid.enableAutoWidth(true)
		this.mainGrid.i18n.paging={
		      results:"结果",
		      records:"记录从 ",
		      to:" 到 ",
		      page:"页面 ",
		      perpage:"条记录",
		      first:"第一页",
		      previous:"上一页",
		      found:"发现记录",
		      next:"下一页",
		      last:"最后一页",
		      of:" of ",
		      notfound:"没有发现记录" 
		};
		
		this.mainGrid.init();
		
		
		
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
		this.mainGrid.attachEvent("onXLE",this.errorMessage);
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
	queryNames:${P_SIMPLE_QUERY_NAMES},
	queryValues:${P_SIMPLE_QUERY_VALUES},
	queryOperators:${P_SIMPLE_QUERY_OPERATOR},
	queryFormType:${P_SIMPLE_QUERY_FORM_TYPE},
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
		this.mainToolbar=GPW.layout.mainLayoutGrid.attachToolbar();	
		var toolbar = this.mainToolbar;
		var newOpts = Array(	
				Array(GPC.constant.add,'obj', '新增'   , 'stylesheet.gif'),
				Array(GPC.constant.edit,   'obj', '修改'     , 'database.gif'),
				Array(GPC.constant.delect,   'obj', '删除' , 'presentation.gif'));
		toolbar.setIconsPath(GLOBAL.S.URL_R+"/images/toolbar/");

		toolbar.addText("text_select", 0, "选");
		this.addQueryName();
		var selectValue = toolbar.getInput("queryName").value;
		this.addOperator(selectValue);		
		this.addQueryValue();
		toolbar.addButton("query", 9, "查询", "new.gif", "new_dis.gif");
		toolbar.addSeparator("sep1", 10);
		toolbar.addButton("detailQuery", 12, "详细查询", "new.gif", "new_dis.gif");
		toolbar.addButtonSelect("edit", 15, "编辑", newOpts, "new.gif", "new_dis.gif");	
		toolbar.addButton("update", 16, "更新", "new.gif", "new_dis.gif");
		toolbar.addButton("listView", 20, "全屏", "open.gif", "open_dis.gif");
		//toolbar.addButton("cardView", 25, "卡片", "save.gif", "save_dis.gif");
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
					var url=GPC.url.queryUrl+"&_json="+strJosn;
					GPW.grid.refreshMainGrid(url);
					
					break;	
				case GPC.constant.detailQuery:
					GPW.window.initDetailQueryWindow();
					break;	
				case GPC.constant.update:
					GPW.grid.sendGridData();
					break;	
				case "refresh":
					
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
		if(window.parent==window){
			this.initHandle();
			this.handle.attachViewportTo("layoutObj");
			this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 480);
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
		return ${P_FORM};
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
	init:function(window,type){
		var formData = this.getMainFormData();		
		this.mainForm = window.attachForm();
		this.mainForm.loadStruct(formData, "json");
		
		if(type==GPC.constant.edit)
			this.updateMainFromData();
		
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				//GPW.form.mainForm.validate();
				GPW.form.mainForm.send(GPC.url.saveUrl, function(loader, response){
					if(window.parent==window){
						GPW.window.handle.unload();
					}else{
						parent.GPW.window.handle.unload();
					}
					
					GPW.grid.refreshMainGrid();
				});
		    }
		});
		
		this.mainForm.attachEvent("onValidateError", function (name, value, result){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	text:"校验错误："+GPW.form.mainForm.getItemLabel(name)+"字段值不能为["+value+"]！"});
		});
	}
}//GPW.form 

	
$(function() {
	GPW.layout.init();
	GPW.grid.init();
	GPW.toolbar.init();
});
</script>