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
<fis:require id="cms:scripts/publishInfo.js" />
<fis:styles/>

<title>信息发布</title>
</head>
<body>

<div id="layoutObj"></div>
<script type="text/javascript">

GLOBAL.P.MODULES="${P_TPL_MODULES}";
GLOBAL.P.CLASSNAME="${P_CLASS_NAME}";
GLOBAL.P.CLASS="${P_CLASS}";
GLOBAL.P.MAXIMIZED=false;

GLOBAL.P.P_FORM=${P_FORM};
GLOBAL.P.id="${S_PARAM.id}";

</script>
	<fis:scripts/>
</body>
</fis:html>