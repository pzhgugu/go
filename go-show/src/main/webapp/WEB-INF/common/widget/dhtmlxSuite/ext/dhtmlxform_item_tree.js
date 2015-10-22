/*
 Product Name: dhtmlxSuite
 Version: 4.0.1
 Edition: Standard
 License: content of this file is covered by GPL. Usage outside GPL terms is prohibited. To obtain Commercial or Enterprise license contact sales@dhtmlx.com
 Copyright UAB Dinamenta http://www.dhtmlx.com
 */

dhtmlXForm.prototype.items.inputTree = {
	data:{},
	tree: {},
	selectVar: {},
	selectUrl: {},
	selectIsRoot: {},
	selectLevel: -1,
	render : function(item, data) {
		this.data=data;
		item._type = "inputTree";
		item._enabled = true;
		this.doAddLabel(item, data);
		this._doAddInput(item, data, "input", null, true, true, "dhxform_inputTree");		
		return this;
	},
	showMenu : function(item,id,inputName,contentTree,menuBtn) {
		var cityObj = $("#"+id+"_Show");
		var cityOffset = $("#"+id+"_Show").offset();
		$("#"+contentTree).css({
			left : cityOffset.left + "px",
			top : cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
		$("body").bind("mousedown",  {than:this,id:id,item:item,inputName:inputName,contentTree:contentTree,menuBtn:menuBtn} ,this.onBodyDown);
	},
	hideMenu : function(item,id,inputName,contentTree,menuBtn) {
		$("#"+contentTree).fadeOut("fast");
		$("body").unbind("mousedown", {than:this,id:id,item:item,inputName:inputName,contentTree:contentTree,menuBtn:menuBtn} ,this.onBodyDown);
	},
	onBodyDown : function(event) {
		if (!(event.target.id == event.data.menuBtn || event.target.id == event.data.contentTree || $(event.target).parents("#"+event.data.contentTree ).length>0)) {
				event.data.than.hideMenu(event.data.inputName,event.data.id,event.data.inputName,event.data.contentTree);
		}
	},
	setValue: function(item, value) {
		if (!!this.selectVar) {
			var dValue;
			_.each(this.selectVar, function (val) {
				if (value == val.value) {
					dValue = val.text;
				}
			});
			this.setInputValue(item, dValue, value);
		}
		if (!!this.selectUrl) {
			var url = this.selectUrl.replace("${id}", value);
			var htmlobj = $.ajax({url: url, async: false});
			if (!!htmlobj && !!htmlobj.responseText) {
				this.setInputValue(item, htmlobj.responseText, value);
			}
		}
    	item._value = value;// it's basic code. You can add there any custom code as well.

	},
	getValue: function(item) {
	    return item._value;//it's basic code. You can add there any custom code as well.
	},
	setInputValue:function(item, showValue,value){
		var id = tree.getSelectedItemId();
		if (!!id) {
			if (this.selectIsRoot == true && tree.hasChildren(id) != 0) {
				alert("请选择树的根节点！");
				return;
			}
			if (this.selectLevel > -1 && tree.getLevel(id) != this.selectLevel) {
				alert("请选择树的第" + this.selectLevel + "层节点！");
				return;
			}
		}
		var vs = $("input:text[name='"+this.data.name+"_Show']");
		vs.attr("value", showValue);
		var v = $("input:hidden[name='"+this.data.name+"']");
		v.attr("value",value);
		item._value = value;
	},
	_doAddInput : function(item, data, el, type, pos, dim, css) {
		var userdata = data.userdata;
		if (!userdata.url) {
			alert("请在用户自定义中配置树选择的url链接。");
			return;
		}
		var p = document.createElement("DIV");
		p.className = "dhxform_control";

		if (item._ll) {
			item.appendChild(p);
		} else {
			item.insertBefore(p, item.firstChild);
		}

		var t = document.createElement(el);
		t.className = css;
		t.name = item._idd + "_Show";
		t._idd = item._idd + "_Show";
		t.id = data.uid + "_Show";


		if (typeof (type) == "string")
			t.type = type;

		if (el == "INPUT" || el == "TEXTAREA") {
			t.onkeyup = function (e) {
				e = e || event;
				item.callEvent("onKeyUp", [this, e, this._idd]);
			};
			t.onkeydown = function (e) {
				e = e || event;
				item.callEvent("onKeyDown", [this, e, this._idd]);
			};
		}

		p.appendChild(t);
		var hidden = document.createElement("input");
		hidden.setAttribute("type", "hidden");
		hidden.name = item._idd;
		hidden._idd = item._idd;
		hidden.id = data.uid;
		p.appendChild(hidden);

		//设置树选择为只读
		this.setReadonly(item, true);

		if (data.hidden == true)
			this.hide(item);
		if (data.disabled == true)
			this.userDisable(item);

		if (pos) {
			if (!isNaN(data.inputLeft))
				p.style.left = parseInt(data.inputLeft) + "px";
			if (!isNaN(data.inputTop))
				p.style.top = parseInt(data.inputTop) + "px";
		}

		var u = "";

		var dimFix = false;
		if (dim) {
			if (!isNaN(data.inputWidth)) {
				u += "width:" + (parseInt(data.inputWidth) - 25) + "px;";
				dimFix = true;
			}
			if (!isNaN(data.inputHeight))
				u += "height:" + parseInt(data.inputHeight) + "px;";

		}
		if (typeof (data.style) == "string")
			u += data.style;
		t.style.cssText = u;

		if (data.maxLength)
			t.setAttribute("maxLength", data.maxLength);
		if (data.connector)
			t.setAttribute("connector", data.connector);

		if (dimFix && {input: 1, password: 1, select: 1, calendar: 1, colorpicker: 1}[this.t]) {
			if (dhtmlXForm.prototype.items[this.t]._dim == null) {
				var w = parseInt(t.style.width);
				var w2 = (dhx4.isFF || dhx4.isIE ? t.offsetWidth : t.clientWidth);
				dhtmlXForm.prototype.items[this.t]._dim = w2 - w;
			}
			t.style.width = parseInt(t.style.width) - dhtmlXForm.prototype.items[this.t]._dim + "px";
		}

		if (typeof (data.note) == "object") {
			var note = document.createElement("DIV");
			note.className = "dhxform_note";
			note.style.width = (isNaN(data.note.width) ? t.offsetWidth : parseInt(data.note.width)) + "px";
			note._w = data.note.width;
			note.innerHTML = data.note.text;
			p.appendChild(note);
			note = null;
		}


		var treeId = data.name + "_UL_inputTree";
		var contentTree = data.name + "_ContentTree";

		var treeContent = document.createElement("DIV");
		treeContent.setAttribute("id", contentTree);
		treeContent.setAttribute("style", "display:none; position: absolute;z-index:999;");
		var treeDiv = document.createElement("DIV");
		treeDiv.setAttribute("style", "background: none repeat scroll 0 0 #fff;  border: 1px solid #a4bed4; height: 120px; overflow-x: auto; overflow-y: scroll;width: 230px;");
		treeDiv.setAttribute("id", treeId);
		treeContent.appendChild(treeDiv);
		var first = document.body.firstChild;
		//得到页面的第一个元素
		document.body.insertBefore(treeContent, first);

		tree = new dhtmlXTreeObject(treeId, "100%", "100%", 0);
		var contentDrop = __uri('contentDrop.gif');
		var img = contentDrop.substr(0, contentDrop.indexOf("/ext/"));
		tree.setImagePath(img + "/imgs/dhxtree_skyblue/");
		tree.enableTreeImages("false");
		tree.setXMLAutoLoading(userdata.url);
		tree.setDataMode("json");
		tree.loadJSON(userdata.url);
		tree.attachEvent("onClick", function (id) {
			dhtmlXForm.prototype.items.inputTree.setInputValue(item, tree.getItemText(id), id);
			dhtmlXForm.prototype.items.inputTree.hideMenu(data.name, data.uid, data.name, contentTree, menuBtn);
			//send方法需要的值
			item._value = id;
		});

		//if (typeof (data.form) == "string"){
		//注册一个全局的点击变量名
		var treeThat = "INPUTTREE_THAT_" + data.name;
		eval(treeThat + "=this;");

		var menuBtn = data.name + "_TreeBtn";
		var fName = treeThat + ".showMenu('" + data.name + "','" + data.uid + "','" + data.name + "','" + contentTree + "','" + menuBtn + "');return false;"

		var images = document.createElement("DIV");
		images.className = "dhxform_note";
		images.setAttribute("style", "width:20px; float:right;");
		images.innerHTML = "&nbsp;<a id='" + menuBtn + "' href='#' onclick=" + fName + "><img src='" + contentDrop + "'  style='margin-top: 3px;'/></a>";
		p.appendChild(images);
		//}

		if (!!userdata.selectVar) {
			eval("this.selectVar=" + userdata.selectVar);
		}
		if (!!userdata.selectUrl) {
			this.selectUrl = userdata.selectUrl;
		}
		if (!!userdata.selectIsRoot) {
			this.selectIsRoot = userdata.selectIsRoot;
		}
		if (!!userdata.selectLevel) {
			this.selectLevel = userdata.selectLevel;
		}
	},
	enable : function(item) {
		item._enabled = true;
		if (String(item.className).search("disabled") >= 0)
			item.className = String(item.className).replace(/disabled/gi, "");
		//
		item.callEvent("onEnable", [item._idd]);
	},

	disable : function(item) {
		item._enabled = false;
		if (String(item.className).search("disabled") < 0)
			item.className += " disabled";
		//
		item.callEvent("onDisable", [item._idd]);
	}
};


dhtmlXForm.prototype.getFormData_inputTree = function(name){
	return this.doWithItem(name, "getValue")
};
dhtmlXForm.prototype.setFormData_inputTree = function(name,value){
	return this.doWithItem(name, "setValue", value)
};
dhtmlXForm.prototype.setValue_inputTree = function(name,value,showValue){
	if(!!value)
		return this.doWithItem(name, "setInputValue", showValue,value)
};
(function() {
	for (var a in dhtmlXForm.prototype.items.input) {
		if (!dhtmlXForm.prototype.items.inputTree[a])
			dhtmlXForm.prototype.items.inputTree[a] = dhtmlXForm.prototype.items.input[a];
	}
})();
