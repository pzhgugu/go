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
<fis:require id="cms:scripts/news.js" />
<fis:styles/>
</head>
<body  >

<div id="layoutObj"></div>
<script type="text/javascript">

GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";
GLOBAL.P.CLASS="${P_CLASS}";
GLOBAL.P.TREECLASS="${P_TREE_CLASS}";
GLOBAL.P.TREEONENAME="${P_TREE_ONENAME}";
GLOBAL.P.MAXIMIZED=false;

</script>
	<fis:scripts/>
</body>
</fis:html>