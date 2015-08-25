<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<fis:block url="core:widget/header.html.jsp" />
<fis:require id="cms:scripts/newscontainer.js" />
<fis:styles/>
</head>
<body  >
<div id="layoutObj"></div>
<script type="text/javascript">

GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";
GLOBAL.P.CLASS="${P_CLASS}";
GLOBAL.P.MAXIMIZED=false;


GLOBAL.P.P_GRID_HEADER="${P_GRID_HEADER}";
GLOBAL.P.P_GRID_COLUMNIDS="${P_GRID_COLUMNIDS}";
GLOBAL.P.P_GRID_WIDTH="${P_GRID_WIDTH}";
GLOBAL.P.P_GRID_COLALIGN="${P_GRID_COLALIGN}";
GLOBAL.P.P_GRID_COLTYPES="${P_GRID_COLTYPES}";
GLOBAL.P.P_GRID_COLSORTING="${P_GRID_COLSORTING}";
GLOBAL.P.P_GRID_VALIDATORS=${P_GRID_VALIDATORS};
GLOBAL.P.P_GRID_FORMGRIDASSEMBLE=${P_GRID_FORMGRIDASSEMBLE};

GLOBAL.P.P_SIMPLE_QUERY_NAMES=${P_SIMPLE_QUERY_NAMES};
GLOBAL.P.P_SIMPLE_QUERY_VALUES=${P_SIMPLE_QUERY_VALUES};
GLOBAL.P.P_SIMPLE_QUERY_OPERATOR=${P_SIMPLE_QUERY_OPERATOR};
GLOBAL.P.P_SIMPLE_QUERY_FORM_TYPE=${P_SIMPLE_QUERY_FORM_TYPE};

GLOBAL.P.categorysOpts=Array();
GLOBAL.P.ico="dhxtree_skyblue/radio_off.gif";
<c:forEach items="${P_NEWCATEGORYS}" var="categorys" >
GLOBAL.P.categorysOpts.push(Array('${categorys.name}_tree','obj', '${categorys.alias}'   , GLOBAL.P.ico));
</c:forEach>
</script>
	<fis:scripts/>
</body>
</fis:html>