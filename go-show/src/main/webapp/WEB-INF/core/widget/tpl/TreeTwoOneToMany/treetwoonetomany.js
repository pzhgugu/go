
/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */
$(window).resize(function() { 
	GPW.layout.tabbarLayout.setHeight(360);
	GPW.layout.leftMainLayout.setWidth(200);
	GPW.layout.mainGridLayout.setHeight(180);
	GPW.layout.leftLayout.setSizes();
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
	url:{
		treeQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryIdToTree/"+GLOBAL.P.TREECLASS,
		dragOrderUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/dragTree/"+GLOBAL.P.TREECLASS,
		treeSave:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeSave/"+GLOBAL.P.TREECLASS,
		treeGetUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/get/"+GLOBAL.P.TREECLASS,
		treeDelectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.TREECLASS,
		refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/"+GLOBAL.P.CLASSNAME+"?_key="+GLOBAL.P.TREEONENAME+".id",
		refreshTwoGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/"+GLOBAL.P.CLASSNAME_TWO+"?_key="+GLOBAL.P.TREEONENAME+".id",
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryPage/"+GLOBAL.P.CLASSNAME_TWO+"?_key="+GLOBAL.P.TREEONENAME+".id",
		saveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/"+GLOBAL.P.CLASSNAME+"?_key="+GLOBAL.P.TREEONENAME+".id",
		saveTwoUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/"+GLOBAL.P.CLASSNAME_TWO+"?_key="+GLOBAL.P.TREEONENAME+".id",
		detailQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/DetailQuery/"+GLOBAL.P.CLASSNAME_TWO+"/d?_key="+GLOBAL.P.TREEONENAME+".id",
		updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/"+GLOBAL.P.CLASSNAME+"?format=xml&_key="+GLOBAL.P.TREEONENAME+".id",		
		updateTwoUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/"+GLOBAL.P.CLASSNAME_TWO+"?format=xml&_key="+GLOBAL.P.TREEONENAME+".id",
		delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME,
		delectTwoUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME_TWO,
		loadActiveTabUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/SingleGrid"
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
	grid:{}	
}

GPW.layout = {
	mainLayout : {},
	mainGridLayout:{},
	tabbarLayout : {},
	treeLayout:{},
	twoGridLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "3L");
		this.leftMainLayout=this.mainLayout.cells("a");
		this.leftMainLayout.setWidth(200);
		this.leftMainLayout.setText("左侧");
		this.leftMainLayout.hideHeader();
		
		this.leftLayout = this.leftMainLayout.attachLayout({
			pattern: "2E"
		});
		
		this.treeLayout=this.leftLayout.cells("a");	
		this.treeLayout.hideHeader();
		
		this.mainGridLayout=this.leftLayout.cells("b");
		this.mainGridLayout.setHeight(180);
		this.mainGridLayout.hideHeader();
		
		this.twoGridLayout=this.mainLayout.cells("b");
		this.twoGridLayout.setText("主表");
		this.twoGridLayout.hideHeader();
		
		this.tabbarLayout=this.mainLayout.cells("c");
		this.tabbarLayout.setHeight(360);
		this.tabbarLayout.setText("从表");
		this.tabbarLayout.hideHeader();	
	},
	maximized:function(is){
		if(is){
			this.leftMainLayout.expand();
			this.twoGridLayout.expand();
		}else{
			this.leftMainLayout.collapse();
			this.twoGridLayout.collapse();
		}
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
		if(GLOBAL.S.SEC_U_V)
			this.dragOrder();	
		this.onClick(); 
		
	},
	onClick:function(){
		this.mainTree.attachEvent("onClick",function(id){
		    if(!this.hasChildren(id)){
		    	/*var url=GPC.url.refreshTwoGridUrl+"&_value="+id;;
		    	GPW.grid.twoGrid.clearAll();
		    	GPW.grid.twoGrid.load(url,function (){
			    	var url=GPC.url.refreshGridUrl+"&_value="+id;
			    	GPW.grid.refreshMainGrid(url);
		    	},"js");*/
				var url=GPC.url.refreshGridUrl+"&_value="+id;
				GPW.grid.mainGrid.clearAll();
				GPW.grid.mainGrid.load(url,function (){
					var urlTwo=GPC.url.refreshTwoGridUrl+"&_value="+id;
					GPW.grid.twoGrid.clearAll();
					GPW.grid.twoGrid.load(urlTwo,GPW.grid.doAfterRefresh,"js");
				},"js");
		    }
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

GPW.grid = {
	mainGrid : {},
	gridDataProcessor : {},
	twoGrid:{},
	initTowGrid : function() {
		this.twoGrid=GPW.layout.twoGridLayout.attachGrid();
		this.twoGrid.setImagePath(GLOBAL.IconsPath);
		
		this.twoGrid.setHeader(GLOBAL.P.TWO_P_GRID_HEADER);
		this.twoGrid.setColumnIds(GLOBAL.P.TWO_P_GRID_COLUMNIDS);
		this.twoGrid.setInitWidths(GLOBAL.P.TWO_P_GRID_WIDTH);
		this.twoGrid.setColAlign(GLOBAL.P.TWO_P_GRID_COLALIGN);
		this.twoGrid.setColTypes(GLOBAL.P.TWO_P_GRID_COLTYPES);
		this.twoGrid.setColSorting(GLOBAL.P.TWO_P_GRID_COLSORTING);
		this.twoGrid.setColValidators(GLOBAL.P.TWO_P_GRID_VALIDATORS);
		GLOBAL.initGridCombo(this.twoGrid,GLOBAL.P.TWO_P_GRID_FORMGRIDASSEMBLE);
		this.twoGrid.setDateFormat("%Y-%m-%d");
		if(!GLOBAL.S.SEC_U_V){
			this.mainGrid.setEditable(false); 
		}	
		//this.twoGrid.enableMultiselect(true);
		this.twoGrid.init();
		this.twoGrid.attachEvent("onValidationError", function(id,index,value,rule){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.grid.mainGrid.getColLabel(index)+","+validText(rule)+",字段值不能为["+value+"]！"});
		});
		
		this.gridDataProcessor = new dataProcessor(GPC.url.updateTwoUrl); //lock feed url
		//this.gridDataProcessor.setTransactionMode("POST",false); //set mode as send-all-by-post
		this.gridDataProcessor.setTransactionMode("REST");
		this.gridDataProcessor.setUpdateMode("off"); //disable auto-update
		this.gridDataProcessor.enableDataNames(true); 
		//只发送更改的项目
		this.gridDataProcessor.enablePartialDataSend(true);
		this.gridDataProcessor.init(this.twoGrid); //link dataprocessor to the grid
		
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
		this.twoGrid.attachEvent("onXLE",function(grid_obj,count){
			SpinnerCtl.close();
		});
		/* this.twoGrid.attachEvent("onXLS",function(grid_obj,count){
			SpinnerCtl.show();
		}); */
	},
	refreshTwoGrid : function(url) {
		if(!url){
			var tree=GPW.tree.mainTree;
			var selectId=tree.getSelectedItemId();
			url=GPC.url.refreshTwoGridUrl+"&_value="+selectId;
		}
   	 	this.twoGrid.clearAll();
   	 	SpinnerCtl.show();
   	 	this.twoGrid.load(url,"js");
	},
	sendGridData : function(){
		this.gridDataProcessor.sendData();
		
	},
	delectTwoGrid:function(){
		var selectId=this.twoGrid.getSelectedRowId();
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
						   url: GPC.url.delectTwoUrl,
						   data: "id="+selectId,
						   success: function(msg){						   
							   GPW.grid.refreshTwoGrid();
							   GPW.grid.refreshMainGrid();
						   }
					});
				}
			}
		});
	},
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
		
		this.mainGrid.init();
		
		this.mainGrid.attachEvent("onValidationError", function(id,index,value,rule){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.grid.mainGrid.getColLabel(index)+","+GLOBAL.validText(rule)+",字段值不能为["+value+"]！"});
		});
		
		this.refreshMainGrid();
		this.mainGrid.attachEvent("onRowSelect", function(id,ind){
		    GPW.tabbar.loadActiveTab(id);
		});
	},
	doAfterRefresh:function(){
		var grid=GPW.grid.mainGrid;
		grid.selectRow(0);
		var selectedId=grid.getSelectedRowId();
		if(selectedId!=null){
			 GPW.tabbar.loadActiveTab(selectedId);
		}
	},
	refreshMainGrid : function(url) {
		if(!url){
			var tree=GPW.tree.mainTree;
			var selectId=tree.getSelectedItemId();
			url=GPC.url.refreshGridUrl+"&_value="+selectId;
		}
   	 	this.mainGrid.clearAll();
   		this.mainGrid.load(url,this.doAfterRefresh,"js");
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
							   GPW.grid.refreshTwoGrid();
						   }
					});
				}
			}
		});
	}
}


GPW.toolbar = {
	mainToolbar : {},
	queryNames:GLOBAL.P.TWO_P_SIMPLE_QUERY_NAMES,
	queryValues:GLOBAL.P.TWO_P_SIMPLE_QUERY_VALUES,
	queryOperators:GLOBAL.P.TWO_P_SIMPLE_QUERY_OPERATOR,
	queryFormType:GLOBAL.P.TWO_P_SIMPLE_QUERY_FORM_TYPE,
	treeToolbar : {},
	twoToolbar : {},
	initTwoToolbar:function(){
		this.twoToolbar=GPW.layout.twoGridLayout.attachToolbar();
		this.twoToolbar.setIconsPath(GLOBAL.IconsPath);
		var toolbar = this.twoToolbar;
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
		toolbar.addButton("detailQuery", 12, "详细查询", "search.png", "search.png");
		if(GLOBAL.S.SEC_U_V||GLOBAL.S.SEC_C_V||GLOBAL.S.SEC_D_V)
			toolbar.addButtonSelect("edit", 15, "编辑", newOpts, "edit.gif", "edit_dis.gif");	
		if(GLOBAL.S.SEC_U_V)
			toolbar.addButton("update", 16, "更新", "cut.gif", "cut_dis.gif");
		toolbar.addButton("maximized", 20, "全屏", "fullscreen_mode.gif", "fullscreen_mode.gif");
		this.twoToolbarClick();
	},
	twoToolbarClick:function(){
		this.twoToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.add:
					GPW.window.initTwoWindow(GPC.constant.add);
					 break;
				case GPC.constant.edit:
					GPW.window.initTwoWindow(GPC.constant.edit);
					 break;
				case GPC.constant.delect:
					GPW.grid.delectTwoGrid();
					 break;
				case GPC.constant.query:
					var toolbar = GPW.toolbar.twoToolbar;
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
					var tree=GPW.tree.mainTree;
					var selectId=tree.getSelectedItemId();
					var url=GPC.url.queryUrl+"&_value="+selectId+"&_json="+encodeURI(strJosn);
					GPW.grid.refreshTwoGrid(url);
					
					break;	
				case GPC.constant.detailQuery:
					GPW.window.initDetailQueryWindow();
					break;	
				case GPC.constant.update:
					GPW.grid.sendGridData();
					break;	
				case "maximized":
					var layout=GPW.layout;
					var toolbar = GPW.toolbar.twoToolbar;
					if(GLOBAL.P.MAXIMIZED){
						layout.tabbarLayout.expand();
						layout.leftMainLayout.expand();
						toolbar.setItemText("maximized", "全屏");	
						toolbar.setItemImage("maximized", "fullscreen_mode.gif");
						GLOBAL.P.MAXIMIZED=false;
					}else{
						layout.tabbarLayout.collapse();
						layout.leftMainLayout.collapse();
						toolbar.setItemText("maximized", "恢复");	
						toolbar.setItemImage("maximized", "selection.gif");
						GLOBAL.P.MAXIMIZED=true;
					}
					break;
				default:					
			}
		});
	},
	initTreeToolbar:function(){
		this.treeToolbar=GPW.layout.treeLayout.attachToolbar();
		this.treeToolbar.setIconsPath(GLOBAL.IconsPath);
		var newOpts = Array();
		if(GLOBAL.S.SEC_C_V)
			newOpts.push(Array(GPC.constant.add,'obj', '新增节点'   , 'new.gif'));
		if(GLOBAL.S.SEC_U_V)
			newOpts.push(Array(GPC.constant.edit,   'obj', '修改节点'     , 'edit.gif'));
		if(GLOBAL.S.SEC_D_V)
			newOpts.push(Array(GPC.constant.delect,   'obj', '删除节点' , 'delect.png'));
		newOpts.push(Array(GPC.constant.select,   'obj', '取消选择' , 'cancel.png'));
		this.treeToolbar.addButtonSelect("edit", 0, "编辑", newOpts, "edit.gif", "edit_dis.gif");
		this.treeToolbar.addSeparator("sep1", 1);			
		this.treeToolbar.addButton("refresh", 2, "刷新", "reload.gif", "reload.gif");
		
		this.treeToolbarClick();
	},
	treeToolbarClick:function(){
		this.treeToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.constant.add:
					GPW.window.initTreeWindow(GPC.constant.add);
					 break;
				case GPC.constant.edit:
					GPW.window.initTreeWindow(GPC.constant.edit);
					 break;
				case GPC.constant.delect:
					GPW.tree.delectTree();
					 break;
				case GPC.constant.select:
					GPW.tree.mainTree.clearSelection();
					break;
				case "refresh":
					GPW.tree.mainTree.refreshItem();
					break;
				default:
			}
		});
	},
	onChangeQuery:function(){
		this.addQueryValue();
	},
	onChangeQueryOperators:function(){
		var toolbar =  GPW.toolbar.twoToolbar;
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
		
		this.twoToolbar.removeItem("queryValue");
		this.twoToolbar.removeItem("queryBegin");
		this.twoToolbar.removeItem("queryEnd");
		this.twoToolbar.removeItem("text_do");
		this.twoToolbar.addInput("queryBegin", 3, "", 100);
		this.twoToolbar.addText("text_do", 4, "到");
		this.twoToolbar.addInput("queryEnd", 5, "", 100);
		
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
		var toolbar =  GPW.toolbar.twoToolbar;
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
		var toolbar =  GPW.toolbar.twoToolbar;
		_.find(this.queryOperators, function(oper,key){ 
			if(key==selectValue){	
				toolbar.removeItem("operator");
				toolbar.addSelectEx("operator", 2, oper, 70,"GPW.toolbar.onChangeQueryOperators()");
				return;
			}
		});
	},
	addQueryName : function(){
		this.twoToolbar.addSelectEx("queryName", 1, this.queryNames, 100,"GPW.toolbar.onChangeQuery()");
	},
	addQueryValue : function(){
		var queryName = GPW.toolbar.twoToolbar.getInput("queryName");
		var selectValue=queryName.value;	
		var that = this;
		var toolbar = that.twoToolbar;		
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
		if(GLOBAL.S.SEC_U_V||GLOBAL.S.SEC_C_V||GLOBAL.S.SEC_D_V)
			toolbar.addButtonSelect("edit", 15, "编辑", newOpts, "edit.gif", "edit_dis.gif");	
		toolbar.addButton("refresh", 16, "刷新", "reload.gif", "reload.gif");
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
				case "refresh":
					
					break;
				default:					
			}
		});
	}
}//end GPW.toolbar


GPW.window = {
	handle:{},
	treeWindow:{},
	mainWindow:{},
	subWindow:{},
	createWindow:function(){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 480);
	},
	initSubWindow:function(width,height){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.subWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-(width/2), 30, width, height);
		return this.subWindow;
	},
	initHandle:function(){
		this.handle = new dhtmlXWindows();
		return this.handle;
	},
	initTwoWindow:function(type){
		var name;
		if(type==GPC.constant.edit){
			var gridId=GPW.grid.twoGrid.getSelectedRowId();
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
		GPW.form.initTwoForm(this.mainWindow,type);
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
		GPW.cache.grid=GPW.grid.twoGrid;
		this.createWindow(); 
		this.mainWindow.setText("详细查询");
		this.mainWindow.setModal(true);
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		this.mainWindow.attachURL(GPC.url.detailQueryUrl+"&_value="+selectId);
	},
	initTreeWindow:function(type){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.treeWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 380);
		if(type==GPC.constant.add)
			this.treeWindow.setText("新增树节点");
		else
			this.treeWindow.setText("修改树节点");
		this.treeWindow.setModal(true);
		GPW.form.initTreeForm(this.treeWindow,type);
	}
}//end GPW.window


GPW.form = {
	mainForm:{},
	treeForm:{},
	twoForm:{},
	getTreeFormData:function(){
		return GLOBAL.P.TREE_P_FORM;
	},
	updateTreeFromData:function(){
		var id=GPW.tree.mainTree.getSelectedItemId();
		var that=this;
		$.ajax({
			cache : false,
			   type: "GET",
			   url: GPC.url.treeGetUrl,
			   data: "id="+id,
			   success: function(msg){
				   that.treeForm.load(msg);
			   }
		});
	},
	initTreeForm:function(window,type){
		var formData = this.getTreeFormData();			
		this.treeForm = this.getForm(window,formData,"tree");
		
		var tree=GPW.tree.mainTree;
		//var selectId=tree.getSelectedItemId();
		//var selectText=tree.getItemText(selectId);		
		//this.treeForm.addItem("name", {type: "input", name:"treeId"});
		
		if(type==GPC.constant.edit)
			this.updateTreeFromData();
			
		this.treeForm.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
		this.treeForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				GPW.form.treeForm.send(GPC.url.treeSave, function(loader, response){
					GPW.window.handle.unload();
					GLOBAL.errorMessage(response);
					GPW.tree.mainTree.refreshItem();
				});
		    }
		});
	},
	getMainFormData:function(){
		return GLOBAL.P.P_FORM;
	},
	getTwoFormData:function(){
		return GLOBAL.P.TWO_P_FORM;
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
	updateTwoFromData:function(){
		var grid=GPW.grid.twoGrid;
		var gridId=grid.getSelectedRowId();
		grid.forEachCell(gridId,function(cellObj,ind){
			var columnid=grid.getColumnId(ind);
			GPW.form.twoForm.setItemValue(columnid, _.unescape(cellObj.getValue()));
		});
		GPW.form.twoForm.setItemValue("id", gridId);
	},
	getForm:function(window,formData,type){
		return window.attachForm(formData);
	},
	init:function(window,type){
		var formData = this.getMainFormData();
		this.mainForm = this.getForm(window, formData, type);
		
		if(type==GPC.constant.edit)
			this.updateMainFromData();
		
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				var tree=GPW.tree.mainTree;
				var selectId=tree.getSelectedItemId();
				if(!selectId){
					alert("请选择树节点！");
					return;
				}
				SpinnerCtl.show();
				GPW.form.mainForm.send(GPC.url.saveUrl+"&_value="+selectId, function(loader, response){
					SpinnerCtl.close();
					GPW.window.handle.unload();
					GLOBAL.errorMessage(response);
					GPW.grid.refreshMainGrid();
					GPW.grid.refreshTwoGrid();
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
	initTwoForm:function(window,type){
		var formData = this.getTwoFormData();		
		this.twoForm = this.getForm(window,formData,"two");
		
		if(type==GPC.constant.edit)
			this.updateTwoFromData();
		
		this.twoForm.attachEvent("onInfo", function(name,e){
			GLOBAL.PopupFunction(e,this.getUserData(name,"info"));			
		});
	
		this.twoForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				var tree=GPW.tree.mainTree;
				var selectId=tree.getSelectedItemId();
				if(!selectId){
					alert("请选择树节点！");
					return;
				}
				SpinnerCtl.show();
				GPW.form.twoForm.send(GPC.url.saveTwoUrl+"&_value="+selectId, function(loader, response){
					SpinnerCtl.close();
					GPW.window.handle.unload();
					GLOBAL.errorMessage(response);
					GPW.grid.refreshMainGrid();
					GPW.grid.refreshTwoGrid();
				});
		    }
		});
		
		this.twoForm.attachEvent("onValidateError", function (name, value, result){
			parent.dhtmlx.message({ 
		    	type:"error", 
		    	expire: -1,
		    	text:"校验错误："+GPW.form.twoForm.getItemLabel(name)+"字段值不能为["+value+"]！"});
		});
	}
}//GPW.form 

GPW.tabbar={
	subListTabbar:{},
	subInfo:GLOBAL.P.P_SUB_INFO,
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
	attachURL:function(actvId,id){
		var sInfo=GPW.tabbar.subInfo;
		var o=_.find(sInfo, function(v) {
			if(v.name==actvId){
				return v;
			}
		});
		var url = GPC.url.loadActiveTabUrl+"/"+actvId+"/d?_key="+o.oneName+".id&_value="+id;
		this.subListTabbar.tabs(actvId).attachURL(url);
	},
	initSubList:function(){
		this.subListTabbar=GPW.layout.tabbarLayout.attachTabbar({			
			tabs: GPW.tabbar.getTabs()
			});
		this.subListTabbar.attachEvent("onSelect", function(actvId, lastId){
			var grid=GPW.grid.mainGrid;
			var id = grid.getSelectedRowId();
			GPW.tabbar.attachURL(actvId,id);
		    return true;
		}); 
	},
	loadActiveTab:function(id){
		var actvId = this.subListTabbar.getActiveTab();
		this.attachURL(actvId,id);
	}
}
		
$(function() {
	GPW.layout.init();
	GPW.tree.init();
	GPW.toolbar.initTreeToolbar();
	GPW.toolbar.initTwoToolbar();
	GPW.grid.init();
	GPW.grid.initTowGrid();
	GPW.toolbar.init();
	GPW.tabbar.initSubList();
});

dhtmlxValidation.isValidIntegerEmpty=function(data){
    if (data=="") return true;// returns 'true' if a cell is empty
    return dhtmlxValidation.isValidInteger(data);
};
dhtmlxValidation.isValidNumericEmpty=function(data){
    if (data=="") return true;// returns 'true' if a cell is empty
    return dhtmlxValidation.isValidNumeric(data);
};