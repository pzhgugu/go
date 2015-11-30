/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */

GLOBAL.V.MainWindowWidth = "700";
GLOBAL.V.MainWindowHeight = "480";

GLOBAL.P.check=Array();


$(window).resize(function() { 
	GPW.layout.mainLayout.setSizes();
});

var GPC = {
	url:{
		queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/queryPage?"+GLOBAL.P.parameter,
		loadUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/load?"+GLOBAL.P.parameter,
		getMessageUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/message",
		detailQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/DetailQuery/News/d?"+GLOBAL.P.parameter,
		delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/delect",
		categoryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/category",
		operationUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/operation",
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
		
		this.mainGrid.attachEvent("onXLE",function(grid_obj,count){
			SpinnerCtl.close();
			grid_obj.forEachRow(function(id){
				var dImg=__uri("../images/delect.ico");
				var uImg = __uri("../images/modify1.png");
				this.cells(id,this.getColIndexById("delect")).setValue(dImg);
				this.cells(id,this.getColIndexById("update")).setValue(uImg);
				$("td img").hover(function () {
					$(this).css({cursor: "pointer"})
				}, function () {
					$(this).css({cursor: ""})
				})
				
			});
		});
		/* this.mainGrid.attachEvent("onXLS",function(grid_obj,count){
			SpinnerCtl.show();
		}); */
		//this.mainGrid.enableSmartRendering(true,5);
		this.refreshMainGrid();

		this.mainGrid.attachEvent("onRightClick", function(id,ind,obj){
			  if(this.getColIndexById("subject")==ind){
			    	$.ajax({
						   type: "GET",
						   url: GPC.url.getMessageUrl+"/"+id,
						   success: function(msg){					   
							   dhtmlx.message(msg);
						   }
					});
			    	
			    }
		});
		this.mainGrid.attachEvent("onRowSelect", function(id,ind){
		    if(this.getColIndexById("delect")==ind){
		    	dhtmlx.confirm({
					ok:"是", cancel:"否",
					text:"你确定删除此记录吗？ ",
					callback:function(state){
						if(state){
							$.ajax({
								   type: "GET",
								   url: GPC.url.delectUrl+"/"+id,
								   success: function(msg){						   
									   GPW.grid.refreshMainGrid();
								   }
							});
						}
					}
				});
		    }
		    if(this.getColIndexById("update")==ind){
		    	var tab=parent.GPW.tabbar.subListTabbar.tabs("publishInfo");
		    	parent.GPW.cache.recodeId=id;
		    	tab.setActive();
		    	
		    }
		  
		});
	},
	refreshMainGrid : function(url) {
		if(!url){
			url=GPC.url.loadUrl;
		}
   	 	this.mainGrid.clearAll();
   	 	SpinnerCtl.show();
   	 	this.mainGrid.load(url,"js");
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
	isCheck:function(){
		GLOBAL.P.check=[];
		GPW.grid.mainGrid.forEachRow(function(id){
			if(this.cells(id,0).getValue()==1){
				GLOBAL.P.check.push(id);
			}
			
		});
		if(GLOBAL.P.check.length<1){
			dhtmlx.alert("请选择操作记录！");
			return false;
		}
		return true;
	},
	sendCheck:function(message,oper){
		if(!this.isCheck()){
			return ;
		}
		dhtmlx.confirm({
			ok:"是", cancel:"否",
			text:message,
			callback:function(state){
				if(state){
					$.ajax({
						   type: "GET",
						   url: GPC.url.operationUrl+"/"+oper,
						   data:{
		                        sId:GLOBAL.P.check
		                    },
						   success: function(msg){						   
							   GPW.grid.refreshMainGrid();
						   }
					});
				}
			}
		});
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
		toolbar.addButton("query", 9, "查询", "search.gif", "search_dis.gif");
		toolbar.addSeparator("sep1", 10);
		toolbar.addButton("detailQuery", 12, "详细查询", "search.png", "search_dis.png");
		
		var approveOpts = [['publish','obj', '发布箱'   , GLOBAL.P.ico],
			               ['pending',   'obj', '待审箱'     , GLOBAL.P.ico]
			               ];
		toolbar.addButtonSelect("approve", 15, "审批",approveOpts);
		
		var delOpts = [['delect','obj', '直接删除'   , GLOBAL.P.ico],
			               ['junk',   'obj', '垃圾箱'     , GLOBAL.P.ico]
			               ];
		toolbar.addButtonSelect("del", 16, "删除",delOpts);
		

		toolbar.addButtonSelect("category", 17, "移动分类",GLOBAL.P.categorysOpts);
		
		var creamOpts = [['cream0','obj', '非精华'   , GLOBAL.P.ico],
				         ['cream1',   'obj', '精华I'     , GLOBAL.P.ico],
				         ['cream2',   'obj', '精华II'     , GLOBAL.P.ico],
				         ['cream3',   'obj', '精华III'     , GLOBAL.P.ico]
				               ];
		toolbar.addButtonSelect("cream", 18, "精华",creamOpts);
		
		var topOpts = [['top0','obj', '非置顶'   , GLOBAL.P.ico],
				         ['top1',   'obj', '置顶I'     , GLOBAL.P.ico],
				         ['top2',   'obj', '置顶II'     , GLOBAL.P.ico],
				         ['top3',   'obj', '置顶III'     , GLOBAL.P.ico]
				               ];
		toolbar.addButtonSelect("top", 19, "置顶",topOpts);
		
		var commentOpts = [['commentY','obj', '允许评论'   , GLOBAL.P.ico],
				         ['commentN',   'obj', '不允许评论'     , GLOBAL.P.ico]
				               ];
		toolbar.addButtonSelect("pcomment", 20, "评论",commentOpts);
		
		var gradeOpts = [['grade0','obj', '普通状态'   , GLOBAL.P.ico],
				         ['grade1',   'obj', '一等级'     , GLOBAL.P.ico],
				         ['grade2',   'obj', '二等级'     , GLOBAL.P.ico],
				         ['grade3',   'obj', '三等级'     , GLOBAL.P.ico],
				         ['grade4',   'obj', '四等级'     , GLOBAL.P.ico],
				         ['grade5',   'obj', '五等级'     , GLOBAL.P.ico]
				               ];
		toolbar.addButtonSelect("grade", 21, "等级审核",gradeOpts);
		
		this.mainToolbarClick();
	},
	mainToolbarClick:function(){
		this.mainToolbar.attachEvent("onClick", function(id) {
			switch(id)
			{
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
				case "approve"://审核
					GPW.toolbar.isCheck();
					break;
				case "pending"://待审箱
					GPW.toolbar.sendCheck("你确定将这些记录发送到待审箱吗？ ",id);
					break;
				case "publish"://发布箱
					GPW.toolbar.sendCheck("你确定将这些记录发送到发布箱吗？ ",id);
					break;
				case "del":
					GPW.toolbar.isCheck();
					break;
				case "delect"://直接删除
					GPW.toolbar.sendCheck("你确定将这些记录直接删除吗？ ",id);
					break;
				case "junk"://垃圾箱
					GPW.toolbar.sendCheck("你确定将这些记录发送到垃圾箱吗？ ",id);
					break;
				case "category"://移动分类
					GPW.toolbar.isCheck();
					break;
				case "cream"://精华
					GPW.toolbar.isCheck();
					break;
				case "cream0"://非精华
					GPW.toolbar.sendCheck("你确定将这些记录设置为非精华吗？ ",id);
					break;
				case "cream1"://精华I
					GPW.toolbar.sendCheck("你确定将这些记录设置为精华I吗？ ",id);
					break;
				case "cream2"://精华II
					GPW.toolbar.sendCheck("你确定将这些记录设置为精华II吗？ ",id);
					break;
				case "cream3"://精华III
					GPW.toolbar.sendCheck("你确定将这些记录设置为精华III吗？ ",id);
					break;
				case "top"://置顶
					GPW.toolbar.isCheck();
					break;
				case "top0"://非置顶
					GPW.toolbar.sendCheck("你确定将这些记录设置为非置顶吗？ ",id);
					break;
				case "top1"://置顶I
					GPW.toolbar.sendCheck("你确定将这些记录设置为置顶I吗？ ",id);
					break;
				case "top2"://置顶II
					GPW.toolbar.sendCheck("你确定将这些记录设置为置顶II吗？ ",id);
					break;
				case "top3"://置顶III	
					GLGPW.toolbar.sendCheck("你确定将这些记录设置为置顶III吗？ ",id);
					break;
				case "pcomment"://评论
					GPW.toolbar.isCheck();
					break;
				case "commentN"://不允许评论
					GPW.toolbar.sendCheck("你确定将这些记录设置为不允许评论吗？ ",id);
					break;
				case "commentY"://允许评论
					GPW.toolbar.sendCheck("你确定将这些记录设置为允许评论吗？ ",id);
					break;
				case "grade"://等级审核
					GPW.toolbar.isCheck();
					break;
				case "grade0"://普通状态
					GPW.toolbar.sendCheck("你确定将这些记录设置为普通状态吗？ ",id);
					break;
				case "grade1"://一等级
					GPW.toolbar.sendCheck("你确定将这些记录设置为一等级吗？ ",id);
					break;
				case "grade2"://二等级
					GPW.toolbar.sendCheck("你确定将这些记录设置为二等级吗？ ",id);
					break;
				case "grade3"://三等级
					GPW.toolbar.sendCheck("你确定将这些记录设置为三等级吗？ ",id);
					break;
				case "grade4"://四等级
					GPW.toolbar.sendCheck("你确定将这些记录设置为四等级吗？ ",id);
					break;
				case "grade5"://五等级
					GPW.toolbar.sendCheck("你确定将这些记录设置为五等级吗？ ",id);
					break;	
				default:	
					if(id.indexOf("_tree")>-1){
						if(!GPW.toolbar.isCheck()){
							return ;
						}
						dhtmlx.confirm({
							ok:"是", cancel:"否",
							text:"你确定将这些记录移动分类吗？ ",
							callback:function(state){
								if(state){
									$.ajax({
										   type: "GET",
										   url: GPC.url.categoryUrl+"/"+id.replace("_tree",""),
										   data:{
						                        sId:GLOBAL.P.check
						                    },
										   success: function(msg){						   
											   GPW.grid.refreshMainGrid();
										   }
									});
								}
							}
						});
					}
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
	initDetailQueryWindow:function(){
		this.createWindow(); 
		this.mainWindow.setText("详细查询");
		this.mainWindow.setModal(true);
		this.mainWindow.attachURL(GPC.url.detailQueryUrl);
	}
}//end GPW.window
	
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