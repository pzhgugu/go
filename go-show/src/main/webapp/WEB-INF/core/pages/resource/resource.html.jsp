<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>资源管理</title>
<fis:block url="core:widget/header.html.jsp" />
<fis:require id="core:scripts/resource.js" />
<fis:styles/>


<script type="text/javascript" language="javascript">

GLOBAL.P.TOP_P_GRID_HEADER="${TOP_P_GRID_HEADER}";
GLOBAL.P.TOP_P_GRID_COLUMNIDS="${TOP_P_GRID_COLUMNIDS}";
GLOBAL.P.TOP_P_GRID_WIDTH="${TOP_P_GRID_WIDTH}";
GLOBAL.P.TOP_P_GRID_COLALIGN="${TOP_P_GRID_COLALIGN}";
GLOBAL.P.TOP_P_GRID_COLTYPES="${TOP_P_GRID_COLTYPES}";
GLOBAL.P.TOP_P_GRID_COLSORTING="${TOP_P_GRID_COLSORTING}";

GLOBAL.P.TOP_P_GRID_VALIDATORS=${TOP_P_GRID_VALIDATORS};
GLOBAL.P.TOP_P_GRID_FORMGRIDASSEMBLE=${TOP_P_GRID_FORMGRIDASSEMBLE};

GLOBAL.P.TOP_P_SIMPLE_QUERY_NAMES=${TOP_P_SIMPLE_QUERY_NAMES};
GLOBAL.P.TOP_P_SIMPLE_QUERY_VALUES=${TOP_P_SIMPLE_QUERY_VALUES};
GLOBAL.P.TOP_P_SIMPLE_QUERY_OPERATOR=${TOP_P_SIMPLE_QUERY_OPERATOR};
GLOBAL.P.TOP_P_SIMPLE_QUERY_FORM_TYPE=${TOP_P_SIMPLE_QUERY_FORM_TYPE};

GLOBAL.P.TOP_P_FORM=${TOP_P_FORM};
</script>
</head>
<body>
<div id="layoutObj"></div>
	<fis:scripts/>
</body>
</fis:html>