<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>goreport业务系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<fis:require id="core:scripts/framework.js" />
<fis:styles/>


<style>
    html, body {
        width: 100%;
        height: 100%;
        margin: 0px;
        overflow: hidden;
    }
    
    .dhtmlxMenu_dhx_skyblue_Middle {
    	background-color: #FFFFFF;
    }
    
    div.dhtmlxMenu_dhx_skyblue_TopLevel_Item_Normal, div.dhtmlxMenu_dhx_skyblue_TopLevel_Item_Disabled {
    border: 1px solid #ffffff;
	}
</style>   
</head>

<body >
<div id='topFlag'></div>
<fis:scripts/>	
<fis:script>		
		
GLOBAL.S.URL="${S_URL}";
GLOBAL.S.URL_R="${S_URL_R}";

GLOBAL.P.addNewSibling=function (menu){
	<c:set value="null" var="mp" /> 
	<c:set value="null" var="last" />
	<c:forEach items="${P_MENUS}" var="menu" >
		<c:choose>
			<c:when test="${menu.parent==null}">
				menu.addNewSibling(${mp}, "${menu.name}", "${menu.alias}", false);
				<c:set value="\"${menu.name}\"" var="mp" />
			</c:when> 
			<c:otherwise>
				<c:choose>
				<c:when test="${menu.isCatalogue==2}">
					menu.addNewSeparator("${last}","${menu.name}");
				</c:when>
				<c:otherwise>
					<c:set value="${menu.name}" var="last" />
					menu.addNewChild("${menu.parent.name}", <c:choose><c:when test="${menu.displayOrder==null}">0</c:when><c:otherwise>${menu.displayOrder}</c:otherwise></c:choose> , "${menu.name}", "${menu.alias}", false, "${menu.image}");
				</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose> 
    </c:forEach>
}

GLOBAL.P.tabbar=function (frameTabbar,id){
	switch(id)
	{
	<c:forEach items="${P_MENUS}" var="menu" >
		<c:if test="${menu.isCatalogue!=1&&!empty menu.url}">
		case "${menu.name}":
			frameTabbar.addTab("${menu.name}", "${menu.alias}", null, null, true);
			frameTabbar.tabs("${menu.name}").attachURL("${menu.url}");
		  break;
		</c:if>
	</c:forEach>
		default:
			alert(id+":异常菜单选项");
	}
}
		
	
</fis:script>
</body>
</fis:html>

