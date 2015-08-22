<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>模型管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${S_URL_R}/js/global.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"	href="${S_URL_R}/js/dhtmlxSuite/dhtmlx.css" />
<script src="${S_URL_R}/js/dhtmlxSuite/dhtmlx.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/ext/dhtmlxform_item_tree.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/ext/dhtmlXToolbar_selectEx.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/underscore-min.js"></script>

<link rel="stylesheet" href="${S_URL_R}/js/jPages/css/animate.css">
<script src="${S_URL_R}/js/jPages/jPages.js"></script>
<style>
html,body {
	width: 100%;
	height: 100%;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
}
div#layoutObj {
	position: relative;
	width: 100%;
	height: 100%;
}

		
.dhxlayout_base_dhx_skyblue div.dhx_cell_layout div.dhx_cell_hdr {
    border: 0px ;
}
div.gridbox_dhx_skyblue.gridbox table.obj.row20px tr td {
    height: 20px;
}
.holder a {
  font-size: 12px;
  cursor: pointer;
  margin: 0 5px;
  color: #333;
}

.holder a:hover {
  background-color: #222;
  color: #fff;
}

.holder a.jp-previous { margin-right: 15px; }
.holder a.jp-next { margin-left: 15px; }

.holder a.jp-current, a.jp-current:hover {
  color: #FF4242;
  font-weight: bold;
}

.holder a.jp-disabled, a.jp-disabled:hover {
  color: #bbb;
}

.holder a.jp-current, a.jp-current:hover,
.holder a.jp-disabled, a.jp-disabled:hover {
  cursor: default;
  background: none;
}

.holder span { margin: 0 5px; }
  
form { float: right; margin-right: 10px; }

form label { margin-right: 5px; }
  
div.gridbox .xhdr {
    height: 26px;
}

div.gridbox_dhx_skyblue.gridbox table.hdr td {
    font-family: 宋体;
}

div.gridbox table.hdr td {
    padding: 5px 0;
}
.dhxform_obj_dhx_skyblue input.dhxform_textarea {
    padding: 3px 3px !important;
}
</style>

</head>
<body>
	<div id="layoutObj"></div>
</body>

<script type="text/javascript">


GLOBAL.S.URL = "${S_URL}";
GLOBAL.S.URL_R = "${S_URL_R}";
GLOBAL.namespace("P");
GLOBAL.P.MODULES="/model";
GLOBAL.P.ENTITY="Models";

var GPW = {
	layout : {},
	tree : {},
	toolbar : {},
	grid : {},
	window : {},
	paging : {},
	tabbar : {},
	cache : {}
};

$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
	GPW.layout.typeLayout.setHeight(180);
});

var GPC = {
	url:{
		treeQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryIdToTree/"+GLOBAL.P.ENTITY,
		dragOrderUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/dragTree/"+GLOBAL.P.ENTITY,
		treeSave:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeSave/"+GLOBAL.P.ENTITY,
		treeGetUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/get/"+GLOBAL.P.ENTITY,
		treeDelectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.ENTITY,
		refreshMainGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeToTable/Fields",
		refreshTypeGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeToTable/FieldsCategory",
		typeSaveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeToTableSave/FieldsCategory",
		fieldSaveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/treeToTableSave/Fields",
		typeDelectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/FieldsCategory",
		fieldDelectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/Fields",
		loadActiveTabUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/SingleGrid",
		scanUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/scan"
	},
	menuType:{add:"add",edit:"edit",delect:"delect"}
	//gridAry:${P_MAIN_GRID},
	//formAry:${P_MAIN_FORM}
}

GPW.cache = {
	grid:{}	
}

GPW.layout = {
	mainLayout : {},
	mainLayoutGrid : {},
	mainLayoutTabbar : {},
	leftLayout:{},
	leftMainLayout:{},
	treeLayout:{},
	typeLayout:{},
	init : function() {
		this.mainLayout = new dhtmlXLayoutObject("layoutObj", "3L");
		this.leftMainLayout=this.mainLayout.cells("a");
		this.leftMainLayout.setWidth(200);
		this.leftMainLayout.hideHeader();
		
		this.leftLayout = this.leftMainLayout.attachLayout({
			pattern: "2E"
		});
		
		this.treeLayout=this.leftLayout.cells("a");	
		//this.treeLayout.setText("树");
		this.treeLayout.hideHeader();
		
		this.typeLayout=this.leftLayout.cells("b");
		this.typeLayout.setText("模型类型");
		//this.typeLayout.hideHeader();
		this.typeLayout.setHeight(180);

		this.mainLayoutGrid=this.mainLayout.cells("b");
		//this.mainLayoutGrid.setText("表格");
		this.mainLayoutGrid.hideHeader();
		this.mainLayoutGrid.setHeight(200);
		
		this.mainLayoutTabbar=this.mainLayout.cells("c");
		//this.mainLayoutTabbar.setText("子表");
		this.mainLayoutTabbar.hideHeader();				
	}
}//end GPW.layout


GPW.tree = {
	mainTree:{},
	init:function(){
		this.mainTree=GPW.layout.treeLayout.attachTree();
		this.mainTree.setImagePath(GLOBAL.S.URL_R+"/js/dhtmlxSuite/imgs/dhxtree_skyblue/");
		this.mainTree.enableTreeImages("false");
		this.mainTree.setXMLAutoLoading(GPC.url.treeQueryUrl); 
		this.mainTree.setDataMode("json");
		this.mainTree.loadJSON(GPC.url.treeQueryUrl); 
		
		this.dragOrder();	
		this.onClick();
		
	},
	onClick:function(){
		this.mainTree.attachEvent("onClick",function(id){
		    if(!this.hasChildren(id)){
		    	GPW.grid.refreshTypeGrid(id);
		    	GPW.grid.refreshMainGrid(id);
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
								  /* if ( typeof (msg) == "string"){
									   msg=msg.trim();
									   msg= eval('(' + msg + ')'); ; 
									   if(!!msg.error){
										   dhtmlx.alert(msg.error);
									   }
								   }*/
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
					   		   if(pTId!=0){
					   				tree.refreshItem(pTId); 
					   		   }else{
					   				tree.refreshItem(pSId);
					   				if(pSId!=tId){
					   					tree.refreshItem(tId);
					   				}
					   		   }
					   	   }
					   }else{
						   if(pSId==tId){
							   tree.refreshItem(tId); 
						   }else{
							   tree.refreshItem(pSId);
							   tree.refreshItem(tId);
						   }
					   }
				   }
			   }
			});
		});
	}	
}//end GPW.tree

GPW.form = {
	mainForm:{},
	queryForm:{},
	treeForm:{},
	typeForm:{},
	getTreeFormData:function(){
		return [
			{type: "settings", position: "label-left",labelWidth: 80, inputWidth: 200},
			{type: "block",width: 680,  list:[
			    {type: "inputTree", label: "父节点",readonly:true,form:"GPW.form.treeForm", name:"parent",url:GPC.url.treeQueryUrl},
      				{type: "input", label: "编码", name:"name",required:true },
      				{type: "input", label: "名称", name:"alias",required:true},
      				{type: "newcolumn",offset:10}, 
      				{type: "select", label: "类型", name:"modelType",required:true, options:[
     					{text: "实体模型", value: "0", selected: true},
     					{text: "动态模型", value: "1"},
     					{text: "目录", value: "-1"}
     				]},
      				{type: "input", label: "排序", name:"displayOrder"}
			]},
			{type: "input", offsetLeft:20,  label: "类全称", inputWidth: 500,name:"clazz"},
  				{type: "input", label: "备注", name:"scription",offsetLeft:20,  rows:8,inputWidth: 500},
  				{type: "input", name:"id",hidden:true},
			{type: "button",offsetLeft:20,  value: "保存",name:"save"}
		];
	},
	updateTreeFromData:function(){
		var id=GPW.tree.mainTree.getSelectedItemId();
		var that=this;
		$.ajax({
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
		this.treeForm = window.attachForm();
		this.treeForm.loadStruct(formData, "json");
		
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		var selectText=tree.getItemText(selectId);
		
		if(type==GPC.menuType.edit)
			this.updateTreeFromData();
		
		this.treeForm.setValue_inputTree("parent", selectId,selectText);
			
		
		this.treeForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				GPW.form.treeForm.send(GPC.url.treeSave, function(loader, response){
					GPW.window.handle.unload();
					GPW.tree.mainTree.refreshItem();
				});
		    }
		});
	},
	getTypeFormData:function(){
		return [
				{type: "settings", position: "label-left",offsetLeft:20,labelWidth: 80, inputWidth: 200},
				{type: "input", label: "编码", name:"name",required:true},
     				{type: "input", label: "名称", name:"alias",required:true},
	  			{type: "input", name:"id",hidden:true},
	  			{type: "input", name:"treeId",hidden:true},
				{type: "button",offsetLeft:20,  value: "保存",name:"save"}
			];
	},
	initTypeForm:function(window,type){
		var formData = this.getTypeFormData();			
		this.typeForm = window.attachForm();
		this.typeForm.loadStruct(formData, "json");
		
		if(type==GPC.menuType.edit)
			this.updateTypeFromData();
		
		this.typeForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				var tree=GPW.tree.mainTree;
				var selectId=tree.getSelectedItemId();
				GPW.form.typeForm.setItemValue("treeId", selectId);
				GPW.form.typeForm.send(GPC.url.typeSaveUrl, function(loader, response){
					GPW.window.handle.unload();
					GPW.grid.refreshTypeGrid();
				});
		    }
		});
		
	},
	updateTypeFromData:function(){
		var grid=GPW.grid.typeGrid;
		var gridId=grid.getSelectedRowId();
		grid.forEachCell(gridId,function(cellObj,ind){
			var columnid=grid.getColumnId(ind);
			GPW.form.typeForm.setItemValue(columnid, cellObj.getValue());
		});
		GPW.form.typeForm.setItemValue("id", gridId);
	},
	getMainFormData:function(){
		return [
			{type: "settings", position: "label-left",labelWidth: 80, inputWidth: 200},
			{type: "block",width: 680,  list:[
			    	{type: "input", label: "编码", name:"name",required:true },
      				{type: "input", label: "类路径", name:"typeClass"},
      				{type: "input", label: "类型", name:"fieldType",required:true},
      				{type: "checkbox", label: "是否唯一",name:"fieldUnique", checked: true},
      				{type: "input", label: "长度", name:"fieldLength"},
      				{type: "input", label: "精度", name:"fieldPrecision"},
      				{type: "newcolumn",offset:10}, 
      				{type: "input", label: "名称", name:"alias",required:true},
			    	{type: "input", label: "虚拟字段", name:"isInvented"},
      				{type: "input", label: "默认值", name:"fieldDefault"},
      				{type: "checkbox", label: "是否为空", name:"fieldNullable", checked: true},
      				{type: "input", label: "占位", name:"fieldScale"},
      				{type: "input", label: "关联关系", name:"relation"}	      				
			]},
 				{type: "input", label: "备注", name:"scription",offsetLeft:20,  rows:8,inputWidth: 500},
 				{type: "input", name:"id",hidden:true},
 				{type: "input", name:"treeId",hidden:true},
			{type: "button",offsetLeft:20,  value: "保存",name:"save"}
		];
	},
	updateMainFromData:function(){
		var grid=GPW.grid.mainGrid;
		var gridId=grid.getSelectedRowId();
		grid.forEachCell(gridId,function(cellObj,ind){
			var columnid=grid.getColumnId(ind);
			GPW.form.mainForm.setItemValue(columnid, cellObj.getValue());
		});
		GPW.form.mainForm.setItemValue("id", gridId);
	},
	init:function(window,type){
		var formData = this.getMainFormData();			
		this.mainForm = window.attachForm();
		this.mainForm.loadStruct(formData, "json");
		
		if(type==GPC.menuType.edit)
			this.updateMainFromData();
		
		this.mainForm.attachEvent("onButtonClick", function(id){
			if (id == "save") {
				var tree=GPW.tree.mainTree;
				var selectId=tree.getSelectedItemId();
				GPW.form.mainForm.setItemValue("treeId", selectId);
				GPW.form.mainForm.send(GPC.url.fieldSaveUrl, function(loader, response){
					GPW.window.handle.unload();
					GPW.grid.refreshMainGrid();
				});
		    }
		});
	}
}//GPW.form 

GPW.toolbar = {
	mainToolbar : {},
	treeToolbar : {},
	typeToolbar : {},
	initTreeToolbar:function(){
		this.treeToolbar=GPW.layout.treeLayout.attachToolbar();
		this.treeToolbar.setIconsPath(GLOBAL.S.URL_R+"/images/toolbar/");
		var newOpts = Array(	
				Array(GPC.menuType.add,'obj', '新增节点'   , 'stylesheet.gif'),
				Array(GPC.menuType.edit,   'obj', '修改节点'     , 'database.gif'),
				Array(GPC.menuType.delect,   'obj', '删除节点' , 'presentation.gif'),
				Array("select",   'obj', '取消选择' , 'presentation.gif'),
				Array('new_s1',   'sep'),
				Array("scan",   'obj', '重新扫描' , 'presentation.gif'));
		this.treeToolbar.addButtonSelect("edit", 0, "编辑", newOpts, "edit.gif", "edit_dis.gif");
		this.treeToolbar.addSeparator("sep1", 1);			
		this.treeToolbar.addButton("refresh", 2, "刷新", "open.gif", "open_dis.gif");
		
		this.treeToolbarClick();
	},
	treeToolbarClick:function(){
		this.treeToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.menuType.add:
					GPW.window.initTreeWindow(GPC.menuType.add);
					 break;
				case GPC.menuType.edit:
					GPW.window.initTreeWindow(GPC.menuType.edit);
					 break;
				case GPC.menuType.delect:
					GPW.tree.delectTree();
					 break;
				case "select":
					GPW.tree.mainTree.clearSelection();
					break;
				case "scan" :
					Model.scan();
					break;
				case "refresh":
					GPW.tree.mainTree.refreshItem();
					break;
				default:
			}
		});
	},
	initTypeToolbar:function(){
		this.typeToolbar=GPW.layout.typeLayout.attachToolbar();
		this.typeToolbar.setIconsPath(GLOBAL.S.URL_R+"/images/toolbar/");
		var newOpts = Array(	
				Array(GPC.menuType.add,'obj', '新增'   , 'stylesheet.gif'),
				Array(GPC.menuType.edit,   'obj', '修改'     , 'database.gif'),
				Array(GPC.menuType.delect,   'obj', '删除' , 'presentation.gif'));
		this.typeToolbar.addButtonSelect("edit", 0, "编辑", newOpts, "edit.gif", "edit_dis.gif");
		this.typeToolbar.addSeparator("sep1", 1);			
		this.typeToolbar.addButton("refresh", 2, "刷新", "open.gif", "open_dis.gif");
		
		this.typeToolbarClick();
	},
	typeToolbarClick:function(){
		this.typeToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.menuType.add:
					GPW.window.initTypeWindow(GPC.menuType.add);
					 break;
				case GPC.menuType.edit:
					GPW.window.initTypeWindow(GPC.menuType.edit);
					 break;
				case GPC.menuType.delect:
					GPW.grid.delectTypeGrid();
					 break;
				case "refresh":
					GPW.grid.refreshTypeGrid();
					break;
				default:
			}
		});
	},
	init : function() {
		this.mainToolbar=GPW.layout.mainLayoutGrid.attachToolbar();	
		var newOpts = Array(	
				Array(GPC.menuType.add,'obj', '新增'   , 'stylesheet.gif'),
				Array(GPC.menuType.edit,   'obj', '修改'     , 'database.gif'),
				Array(GPC.menuType.delect,   'obj', '删除' , 'presentation.gif'));
		this.mainToolbar.setIconsPath(GLOBAL.S.URL_R+"/images/toolbar/");

		this.mainToolbar.addText("text_select", 0, "选");
		this.mainToolbar.addSelectEx("selectQuery", 1, "请选中...", 100);
		this.mainToolbar.addInput("queryMainGrid", 2, "查询", 200);
		this.mainToolbar.addButton("query", 5, "查询", "new.gif", "new_dis.gif");
		this.mainToolbar.addSeparator("sep1", 10);
		this.mainToolbar.addButtonSelect("edit", 15, "编辑", newOpts, "new.gif", "new_dis.gif");			
		this.mainToolbar.addButton("listView", 20, "列表", "open.gif", "open_dis.gif");
		this.mainToolbar.addButton("cardView", 25, "卡片", "save.gif", "save_dis.gif");
		this.mainToolbarClick();
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
				case GPC.menuType.add:
					GPW.window.initMainWindow(GPC.menuType.add);
					 break;
				case GPC.menuType.edit:
					GPW.window.initMainWindow(GPC.menuType.edit);
					 break;
				case GPC.menuType.delect:
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
	typeWindow:{},
	mainWindow:{},
	subWindow:{},
	initHandle:function(){
		this.handle = new dhtmlXWindows();
		return this.handle;
	},
	initSubWindow:function(){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.subWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 480);
		return this.subWindow;
	},
	initTreeWindow:function(type){
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.treeWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 380);
		if(type==GPC.menuType.add)
			this.treeWindow.setText("新增模型树节点");
		else
			this.treeWindow.setText("修改模型树节点");
		this.treeWindow.setModal(true);
		GPW.form.initTreeForm(this.treeWindow,type);
	},
	initTypeWindow:function(type){
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		if(!selectId){
			dhtmlx.alert("请选中树节点！");
			return ;
		}
		if(type==GPC.menuType.edit){
			var gridId=GPW.grid.typeGrid.getSelectedRowId();
			if(!gridId){
				dhtmlx.alert("请选中模板类型！");
				return ;
			}
		}
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.typeWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-190, 30, 380, 220);
		if(type==GPC.menuType.add)
			this.typeWindow.setText("新增模型类型");
		else
			this.typeWindow.setText("修改模型类型");
		this.typeWindow.setModal(true);
		GPW.form.initTypeForm(this.typeWindow,type);
	},
	initMainWindow:function(type){
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		if(!selectId){
			dhtmlx.alert("请选中树节点！");
			return ;
		}
		if(type==GPC.menuType.edit){
			var gridId=GPW.grid.mainGrid.getSelectedRowId();
			if(!gridId){
				dhtmlx.alert("请选中一条字段记录！");
				return ;
			}
		}
		this.initHandle();
		this.handle.attachViewportTo("layoutObj");
		this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 480);
		if(type==GPC.menuType.add)
			this.mainWindow.setText("新增模型类型");
		else
			this.mainWindow.setText("修改模型类型");
		this.mainWindow.setModal(true);
		GPW.form.init(this.mainWindow,type);
	}				
}//end GPW.window

GPW.grid = {
	mainGrid: {},
	typeGrid:{},
	init : function() {			
		this.mainGrid=GPW.layout.mainLayoutGrid.attachGrid();
		this.mainGrid.setImagePath(GLOBAL.S.URL_R+"/images/dhx/");
		this.mainGrid.setHeader("编码,名称,类型,长度,精度,占位,唯一,默认值,空值,类全称,关联关系,虚拟字段");
		this.mainGrid.setColumnIds("name,alias,fieldType,fieldLength,fieldPrecision,fieldScale,fieldUnique,fieldDefault,fieldNullable,typeClass,relation,isInvented");
		this.mainGrid.setInitWidths("100,100,100,100,100,100,100,100,100,100,100,100");
		this.mainGrid.setColAlign("left,left,left,left,left,left,left,left,left,left,left,left");
		this.mainGrid.setColTypes("ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ch");
		//var colIndex=this.mainGrid.getColIndexById("isInvented");
		//alert(colIndex);
		//this.mainGrid.getCombo(colIndex).put(0,"否");
		//this.mainGrid.getCombo(colIndex).put(1,"是");
		this.mainGrid.setColSorting("str,str,str,str,str,str,str,str,str,str,str,str");
		this.mainGrid.setEditable(false); 
		
		this.mainGrid.enableMultiselect(true);
		this.mainGrid.init();
		
	},
	refreshMainGrid:function(id){
		if(!id){id = GPW.tree.mainTree.getSelectedItemId();}			   
		var url=GPC.url.refreshMainGridUrl+"?id="+id;
		this.mainGrid.clearAll();
		this.mainGrid.load(url,"js");
	},
	initTypeGrid:function(){
		this.typeGrid=GPW.layout.typeLayout.attachGrid();
		this.typeGrid.setImagePath(GLOBAL.S.URL+"/images/dhx/");
		this.typeGrid.setHeader("编码,名称");
		this.typeGrid.setColumnIds("name,alias");
		this.typeGrid.setInitWidths("100,99");
		this.typeGrid.setColAlign("left,left");
		this.typeGrid.setColTypes("ro,ro");
		this.typeGrid.setColSorting("str,str");
		this.typeGrid.setEditable(false); 			
		this.typeGrid.init();
		this.onRowSelectType();
	},
	refreshTypeGrid:function(id){
		if(!id){id = GPW.tree.mainTree.getSelectedItemId();}			   
		var url=GPC.url.refreshTypeGridUrl+"?id="+id;
		this.typeGrid.clearAll();
		this.typeGrid.load(url,function(){
			GPW.grid.typeGrid.selectRow(0);
			GPW.tabbar.loadActiveTab(GPW.grid.typeGrid.getSelectedRowId());
		},"js");
	},
	onRowSelectType:function(){
		this.typeGrid.attachEvent("onRowSelect", function(id,ind){
		    GPW.tabbar.loadActiveTab(id);
		});
	},
	delectTypeGrid:function(){
		var selectId=this.typeGrid.getSelectedRowId();
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
						   url: GPC.url.typeDelectUrl,
						   data: "id="+selectId,
						   success: function(msg){						   
							   GPW.grid.refreshTypeGrid();
						   }
					});
				}
			}
		});
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
						   url: GPC.url.fieldDelectUrl,
						   data: "id="+selectId,
						   success: function(msg){						   
							   GPW.grid.refreshMainGrid();
						   }
					});
				}
			}
		});
	}
}	//GPW.grid 

GPW.paging = {
		mainPaging:{},
		init:function(){
			
			var form="<form><label>本页20条/100条记录 </label><select><option>10</option><option>20</option><option>50</option></select></form>";
			var holder="<div class='holder' id='jpage'></div>";
			var ul="<ul id='itemContainer'><li>1</li><li>2</li></ul>";
			
			var statusBar=GPW.layout.mainLayoutGrid.attachStatusBar({height: 22});
			statusBar.setText(form+holder+ul);
			
			$("#jpage").jPages({
				  containerID  : "itemContainer", //显示数据所在的块的ID      
				  first: '首页',      
				  last: '尾页',      
				  previous: '上页', 		   
				  next: '下页', 
				  perPage: 12,   //每页显示数据为多少行             
				  startPage: 1, //起始页 
				  startRange: 2, //开始页码为2个 
				  midRange: 3, //最多显示几个页码页码,其余用..代替             
				  endRange: 2, //结束页码为2个 
				  animation: 'wobble',//数据显示的动画效果	   
				  keyBrowse: true
			 });
		}
	}
	
GPW.tabbar={
	subListTabbar:{},
	initSubList:function(){
		this.subListTabbar=GPW.layout.mainLayoutTabbar.attachTabbar({
			tabs: [
					{ id: "FieldsGrid", text: "表格", active: true },
					{ id: "FieldsForm", text: "表单" },
					{ id: "FieldsQuery", text: "查询" }//,
					//{ id: "FieldsPrint", text: "打印" },
					//{ id: "FieldsReport", text: "报表" }
				]
			});
		//this.subListTabbar.tabs("grid").attachURL(GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/SingleGrid/FieldsGrid/d");
		 /*this.mainTabbar.tabs("where").attachURL(GLOBAL.S.URL+"/view/df/SqlReport/whereTpl.do");
		this.mainTabbar.attachEvent("onSelect", function(actvId, lastId){
			var grid=GLOBAL.P.WIDGET.grid;
			var id = grid.mainGrid.getSelectedRowId();
			grid.flushSubGridTable(id,actvId);
		    return true;
		}); */
		this.subListTabbar.attachEvent("onSelect", function(actvId, lastId){
			var grid=GPW.grid.typeGrid;
			var id = grid.getSelectedRowId();
			var url = GPC.url.loadActiveTabUrl+"/"+actvId+"/d?_key=fieldsCategory.id&_value="+id;
			GPW.tabbar.subListTabbar.tabs(actvId).attachURL(url);
		    return true;
		}); 
	},
	loadActiveTab:function(id){
		var actvId = this.subListTabbar.getActiveTab();
		var url = GPC.url.loadActiveTabUrl+"/"+actvId+"/d?_key=fieldsCategory.id&_value="+id;
		this.subListTabbar.tabs(actvId).attachURL(url);
	}
}

var Model={
	scan:function(){
		var tree=GPW.tree.mainTree;
		var selectId=tree.getSelectedItemId();
		$.ajax({
			   type: "GET",
			   url: GPC.url.scanUrl,
			   data: "id="+selectId,
			   success: function(msg){						   
				   dhtmlx.alert("扫描成功！");
				   GPW.grid.refreshTypeGrid(selectId);
			       GPW.grid.refreshMainGrid(selectId);
			   }
		});
	}
}

$(function() {
	GPW.layout.init();
	GPW.tree.init();
	GPW.toolbar.initTreeToolbar();
	GPW.grid.initTypeGrid();
	GPW.toolbar.initTypeToolbar();
	GPW.grid.init();
	GPW.toolbar.init();
	GPW.paging.init();
	GPW.tabbar.initSubList();
});

</script>

</html>
