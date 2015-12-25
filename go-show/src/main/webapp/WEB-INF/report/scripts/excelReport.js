/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 * @require common:widget/jquery/jquery.form.js
 */

GPC.url.saveUrl = GLOBAL.S.URL + GLOBAL.P.MODULES + "/a/saveFile/" + GLOBAL.P.CLASSNAME + "?_key=" + GLOBAL.P.TREEONENAME + ".id";

function processJson(response) {
	GPW.window.handle.unload();
	GLOBAL.errorMessage(response);
	GPW.grid.refreshMainGrid();
}

GPW.form.init = function(window, type) {
	var form=$("<div id='fileDiv'><form id='realForm' method='POST' enctype='multipart/form-data'><div id='dhxForm'></div></form></div>");
    $(document.body.firstChild).before(form);
	
	this.mainForm = new dhtmlXForm("dhxForm", this.getMainFormData());		
	window.attachObject("fileDiv");
	if (type == GPC.constant.edit)
		this.updateMainFromData();

	this.mainForm.attachEvent("onValidateError", function(name, value, result) {
		parent.dhtmlx.message({
			type : "error",
			expire : -1,
			text : "校验错误：" + GPW.form.mainForm.getItemLabel(name) + "字段值不能为[" + value + "]！"
		});
	});
	var itemData = {
		type : "file",
		label : "选择文件",
		name : "file"
	};
	GPW.form.mainForm.addItem("id", itemData, 1);

	this.mainForm.attachEvent("onButtonClick", function(id) {
		if (id == "save") {
			var tree = GPW.tree.mainTree;
			var selectId = tree.getSelectedItemId();
			if (!selectId) {
				alert("请选择树节点！");
				return;
			}

			$("#realForm").attr("action", GPC.url.saveUrl + "&_value=" + selectId);
			$('#realForm').ajaxForm({
				dataType : "json",
				error : function(request) {
					alert(request.responseText);
				},
				success : processJson
			});
			$("#realForm").submit();
		}
	});
}

