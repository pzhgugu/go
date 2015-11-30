/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 * @require common:widget/jquery/jquery.form.js
 */
GPC.url.saveUrl = GLOBAL.S.URL + GLOBAL.P.MODULES + "/a/saveFile/" + GLOBAL.P.CLASSNAME + "?_key=" + GLOBAL.P.TREEONENAME + ".id";

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
		label : "选择图标",
		name : "file"
	};
	GPW.form.mainForm.addItem("id", itemData, 1);

	this.mainForm.attachEvent("onButtonClick", function(id) {
		if (id == "save") {
			$("#realForm").attr("action", GPC.url.saveUrl);
			$('#realForm').ajaxForm({
				dataType : "json",
				error : function(request) {
					alert(request.responseText);
				},
				success: function (response) {
					GPW.window.handle.unload();
					GLOBAL.errorMessage(response);
					GPW.grid.refreshMainGrid();
				}
			});

			$("#realForm").submit();
		}
	});
}


GPW.form.formButtonQueryLaterCallback = function () {
	GPW.groupBy();
}
GPW.grid.refreshMainGrid = function (url) {
	if (!url) {
		url = GPC.url.refreshGridUrl;
	}
	GPW.grid.mainGrid.clearAll();
	SpinnerCtl.show();
	GPW.grid.mainGrid.load(url, "js");

	GPW.groupBy();

}

GPW.groupBy = function () {
	GPW.grid.mainGrid.customGroupFormat = function (text, count) {
		var dValue;
		_.each(GLOBAL.P.P_GRID_FORMGRIDASSEMBLE.classId, function (val) {
			if (text == val.value) {
				dValue = val.text;
			}
		});
		return dValue + " (" + count + ")";
	};
	GPW.grid.mainGrid.groupBy(2);
}

