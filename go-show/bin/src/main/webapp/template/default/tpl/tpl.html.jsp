<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<script type="text/javascript" src="${S_URL_R}/js/underscore-min.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/global.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/jquery-1.9.1.js"></script>
<link rel="stylesheet" type="text/css"	href="${S_URL_R}/js/dhtmlxSuite/dhtmlx.css" />
<script src="${S_URL_R}/js/dhtmlxSuite/dhtmlx.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/ext/dhtmlxform_item_tree.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/ext/dhtmlXToolbar_selectEx.js"></script>
<script type="text/javascript" src="${S_URL_R}/js/dhtmlxSuite/ext/dhtmlxgrid_errormessage.js"></script>
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
<script>
	GLOBAL.S.URL = "${S_URL}";
	GLOBAL.S.URL_R = "${S_URL_R}";
	GLOBAL.namespace("P");
</script>
</head>
<body  >
<c:import url="page_top.html.jsp" var="BODY_PAGE_TOP" />
${BODY_PAGE_TOP}  
<c:import url="${P_TPL_PATH_JSP}.html.jsp" var="BODY_PAGE" />
${BODY_PAGE} 
<c:import url="page_bottom.html.jsp" var="BODY_PAGE_BOTTOM" />
${BODY_PAGE_BOTTOM}
</body>
</html>