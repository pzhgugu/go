<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
   <style>
		div#winVP {
			position: relative;
			height: 350px;
			border: #a4bed4 1px solid;
			border-radius: 2px;
			margin: 10px;
		}
	</style> 
<link rel="stylesheet" type="text/css"	href="/show/static/js/dhtmlxSuite/dhtmlx.css" />
<link rel="stylesheet" type="text/css"	href="/show/static/css/adminBase.css" />
    
<script type="text/javascript" src="/show/static/js/underscore-min.js"> </script>
<script type="text/javascript" src="/show/static/js/global.js"> </script>
<script type="text/javascript" src="/show/static/js/jquery-1.9.1.js"> </script>
<script type="text/javascript" src="/show/static/js/dhtmlxSuite/dhtmlx.js"> </script>
<script type="text/javascript" src="/show/static/js/dhtmlxSuite/ext/dhtmlXToolbar_selectEx.js"> </script>
<script type="text/javascript" src="/show/static/js/dhtmlxSuite/ext/dhtmlxgrid_errormessage.js"> </script>
<script type="text/javascript" src="/show/static/js/spin.min.js"> </script>
<script type="text/javascript" src="/show/static/js/jquery.form.js"> </script>
<script type="text/javascript" src="/show/static/js/dhtmlxSuite/dhtmlxEx.js"> </script>
    
<script type="text/javascript" src="/show/static/js/edit_area/edit_area_full.js"> </script>
<script type="text/javascript" src="/show/static/js/dhtmlxSuite/ext/dhtmlxform_item_editArea.js"> </script>
<script language="Javascript" type="text/javascript">
	// initialisation

	var myForm, formData;
	var dhxWins;
	function doOnLoad() {
		formData = [{"type":"settings","inputWidth":200,"labelWidth":80,"position":"label-left"},{"type":"block","list":[{"type":"input","label":"编码","name":"name","required":true},{"type":"newcolumn","offset":10},{"type":"input","label":"名称","name":"alias","required":true}],"width":680},{"type":"block","name":"block_0","list":[],"width":680},{"type":"block","list":[{"type":"input","label":"排序","name":"displayOrder"},{"type":"newcolumn","offset":10},{"type":"input","label":"标签页","name":"sheetName"}],"width":680},{"type":"block","name":"block_2","list":[],"width":680},{"type":"block","list":[{"type":"combo","label":"是否循环","name":"isLoop","options":[{"text":"是","value":"1"},{"text":"否","value":"0"}]},{"type":"newcolumn","offset":10},{"type":"input","label":"循环最大值","name":"loopMaxNumber"}],"width":680},{"type":"block","name":"block_4","list":[],"width":680},{"type":"block","list":[{"type":"input","label":"单元格开始","name":"cellStart"},{"type":"newcolumn","offset":10},{"type":"input","label":"单元格结束","name":"cellEnd"}],"width":680},{"type":"block","name":"block_6","list":[],"width":680},{"type":"block","list":[{"type":"combo","label":"连续打印","name":"isSeries","options":[{"text":"否","value":"0"},{"text":"是","value":"1"}]},{"type":"newcolumn","offset":10},{"type":"input","label":"连续打印编码","name":"seriesSheetRecode"}],"width":680},{"type":"block","name":"block_8","list":[],"width":680},{"type":"block","list":[{"type":"editArea","inputWidth":495,"label":"sql内容","name":"sqlContent","required":false},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_10","list":[],"width":680},{"type":"block","list":[{"type":"input","inputWidth":495,"label":"单元格","name":"recode","rows":5},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_11","list":[],"width":680},{"type":"block","list":[{"type":"input","inputWidth":495,"label":"固定单元格","name":"fixedRecode","rows":5},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_12","list":[],"width":680},{"type":"block","list":[{"type":"input","inputWidth":495,"label":"页眉编码","name":"headerRecode"},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_13","list":[],"width":680},{"type":"block","list":[{"type":"input","inputWidth":495,"label":"页脚编码","name":"footerRecode"},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_14","list":[],"width":680},{"type":"block","list":[{"type":"input","inputWidth":495,"label":"合并编码","name":"mergerRegionRecode"},{"type":"newcolumn","offset":10}],"width":680},{"type":"block","name":"block_15","list":[],"width":680},{"type":"hidden","name":"id"},{"type":"button","className":"button_save","name":"save","offsetLeft":20,"value":"保存"}];
		dhxWins = new dhtmlXWindows();
		dhxWins.attachViewportTo("winVP");
		var win=dhxWins.createWindow("winVP", 20, 30, 1320, 1200);
		var f=win.attachForm();
		f.loadStruct(formData, "json");
	}
</script>
</head>
<body onload="doOnLoad();">
	<div id="winVP"></div>

</body>
</html>