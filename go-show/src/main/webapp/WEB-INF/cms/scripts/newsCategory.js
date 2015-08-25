/**
 * @require common:widget/jquery/jqueryEx.js
 * @require common:widget/jquery/jquery.Jcrop.js
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 * @require common:widget/jquery/jquery.form.js
 * @require cms:styles/newsCategory.css
 * 
 */


GLOBAL.P.size = 380;
GLOBAL.P.jcrop_api = null;
GLOBAL.P.uploadSuccess = false;
GPC.url.saveImage = GLOBAL.S.URL + GLOBAL.P.MODULES + "/image/save";

GLOBAL.P.xsize = 0;
GLOBAL.P.ysize = 0;
GLOBAL.P.boundx = 0;
GLOBAL.P.boundy = 0;

function imageMaxDiv(name, value) {
	return "<img id='imgPre' src='' class='jc-demo-box' />";
}

function imageMinDiv(name, value) {
	var f = this.getForm();
	return "<div id='preview-pane'><div class='preview-container'><img id='imgPreMin' alt='裁剪图' class='jcrop-preview' src=''/></div></div>";
}

function onloadDiv(name, value) {
	return " <div id='btnImage' onmouseover=\"this.style.backgroundPosition='left -32px'\" onmouseout=\"this.style.backgroundPosition='left top'\" ><div id='btnProgress' ></div><div id='btnContent'>上  传</div></div>";
}

function getFileUrl(object) {
	var url;
	if (navigator.userAgent.indexOf("MSIE") >= 1) {
		url = object.value;
	} else {
		var o=object.files.item(0);
		if(o!=null){
			url = window.URL.createObjectURL(object.files.item(0));
		}
	}
	return url;
}

function updatePreview(c) {
	if (GLOBAL.P.uploadSuccess) {
		$('#btnProgress').width(0);
		$('#btnContent').text("上传");
		GLOBAL.P.uploadSuccess = false;
	}
	$("#x").val(c.x);
	$("#y").val(c.y);
	$("#w").val(c.w);
	$("#h").val(c.h);
	if (parseInt(c.w) > 0) {
		var rx = GLOBAL.P.xsize / c.w;
		var ry = GLOBAL.P.ysize / c.h;

		$pimg.css({
			width : Math.round(rx * GLOBAL.P.boundx) + 'px',
			height : Math.round(ry * GLOBAL.P.boundy) + 'px',
			marginLeft : '-' + Math.round(rx * c.x) + 'px',
			marginTop : '-' + Math.round(ry * c.y) + 'px'
		});
	}
}

function preImg(sourceName, targetId) {
	var object = document.getElementsByName(sourceName)[0];
	var url = getFileUrl(object);

	var img = new Image();
	img.src = url;

	var w, h;
	img.onload = function() {
		if (this.width > this.height) {
			w = GLOBAL.P.size;
			h = GLOBAL.P.size * (this.height / this.width);
		} else {
			w = GLOBAL.P.size * (this.width / this.height);
			h = GLOBAL.P.size;
		}
		imgPre.width = w;
		imgPre.height = h;

		$('#imgPre').Jcrop({
			onChange : updatePreview,
			onSelect : updatePreview,
			setSelect : [150, 150, 0, 0],
			aspectRatio : GLOBAL.P.xsize / GLOBAL.P.ysize
		}, function() {
			GLOBAL.P.jcrop_api = this;
			var bounds = this.getBounds();
			GLOBAL.P.boundx = bounds[0];
			GLOBAL.P.boundy = bounds[1];
			//$preview.appendTo(GLOBAL.P.jcrop_api.ui.holder);
		});
	}
	var imgPre = document.getElementById(targetId);
	imgPre.src = url;

	var imgPreMin = document.getElementById("imgPreMin");
	imgPreMin.src = url;

	$preview = $('#preview-pane'), $pcnt = $('#preview-pane .preview-container'), $pimg = $('#preview-pane .preview-container img'), GLOBAL.P.xsize = $pcnt.width(), GLOBAL.P.ysize = $pcnt.height();
}

GPW.form.getForm = function(window, formData, type) {
	var form = $("<div id='fileDiv'  style='height:100%;overflow-y:scroll;'><form id='realForm' method='POST' enctype='multipart/form-data'><div id='dhxForm'><input type='hidden' id='size' name='size' /><input type='hidden' id='x' name='x' /><input type='hidden' id='y' name='y' /><input type='hidden' id='w' name='w' /><input type='hidden' id='h' name='h' /></div></form></div>");
	$(document.body.firstChild).before(form);
	$("#size").val(GLOBAL.P.size);
	window.attachObject("fileDiv");
	return new dhtmlXForm("dhxForm", formData);
}
function progress() {
	var idiv = $('#btnProgress');
	var ibox = $('#btnImage');
	var iboxwidth = ibox[0].offsetWidth - 32;
	var timer = setInterval(function() {
		var iWidth = idiv[0].offsetWidth / iboxwidth * 100;
		idiv.width(idiv[0].offsetWidth + 1);
		//idiv.innerHTML=Math.round(iWidth)+"%";
		if (iWidth > 90) {
			clearInterval(timer);
		}
	}, 1);
}

function processJson(response) {
	$('#btnProgress').width($('#btnImage')[0].offsetWidth - 32);
	$('#btnContent').text("上传成功");
	GLOBAL.P.uploadSuccess = true;
	if (!!response.data) {
		GPW.form.mainForm.setItemValue("image",response.data);
	} else {
		$('#btnContent').text("上传异常");
		GLOBAL.P.uploadSuccess = false;
	}
}

function submitForm() {
	$("#realForm").attr("action", GPC.url.saveImage);
	$('#realForm').ajaxForm({
		dataType : "json",
		error : function(request) {
			dhtmlx.alert("上传失败" + request.responseText);
		},
		success : processJson
	});

	$("#realForm").submit();
}

function clearFile(){
	var ie = (navigator.appVersion.indexOf("MSIE") != -1);
		//IE
	if (ie) {
		$("input[name='doImage']").select();
		document.execCommand("delete");
	} else {
		$("input[name='doImage']").val("");
	}
}

function clearImage(){
	$("#imgPre").attr("src", "");
	var imageId=GPW.form.mainForm.getItemValue("image");
	if(!!imageId){
		$("#imgPreMin").attr("src", GLOBAL.S.URL+'/att/download/'+imageId);
	}else{
		var noimg=__inline("../images/no.png");
		$("#imgPreMin").attr("src",noimg);
	}
	$("#imgPreMin").width(185);
	$("#imgPreMin").height(180);
	$("#imgPreMin").css("margin",0);
	destroy();
}

GPW.tree.onClick=function(){
		this.mainTree.attachEvent("onClick",function(id){
		    var url=GPC.url.treeGetUrl+"?id="+id;
		    GPW.form.mainForm.loadStruct(url,function(){
			    clearFile();
			    clearImage();
			});	
		    return true;
		});
	}
	
function destroy(){
	if (GLOBAL.P.jcrop_api != null) {
		GLOBAL.P.jcrop_api.destroy();
		GLOBAL.P.jcrop_api = null;
		GPW.form.mainForm.removeItem("imageMax");
		GPW.form.mainForm.addItem("doImage", {
			type : "template",
			name : "imageMax",
			position : "label-left",
			format : "imageMaxDiv",
			"offsetLeft" : 0
				}, 0);
	}
}

$(function() {
	var itemData = {
		type : "fieldset",
		name : "doImage",
		label : "上传图片",
		"offsetLeft" : -50,
		list : [{
			type : "template",
			name : "imageMax",
			position : "label-left",
			format : "imageMaxDiv",
			"offsetLeft" : 0
		}, {
			"type" : "file",
			"label" : "上传图片",
			"name" : "doImage",
			width : 100,
			"offsetLeft" : 0
		}, {
			"type" : "newcolumn",
			"offset" : 10
		}, {
			type : "template",
			name : "imageMin",
			format : "imageMinDiv",
			"offsetLeft" : 128,
			"offsetTop" : 10
		}, {
			type : "template",
			"name" : "onload",
			"offsetLeft" : 128,
			"offsetTop" : 10,
			format : "onloadDiv"
		}],
		width : 760
	};
	GPW.form.mainForm.addItem("block_2", itemData, 0);

	GPW.form.mainForm.attachEvent("onChange", function(name, value) {
		if (name == "doImage") {
			destroy();
			preImg("doImage", 'imgPre');
			$('#btnProgress').width(0);
			$('#btnContent').text("上传");
			GLOBAL.P.uploadSuccess = false;
		}
	});

	$('#btnImage').click(function() {
		if (GLOBAL.P.uploadSuccess) {
			dhtmlx.alert("图片已经上传！");
		} else {
			progress();
			submitForm();
		}
	});
});
