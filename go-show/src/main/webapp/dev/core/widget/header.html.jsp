<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:out id="common:scripts/global.js" />
<script>
	GLOBAL.S.URL = "${S_URL}";
	GLOBAL.S.URL_R = "${S_URL_R}";
	<c:forEach items="${P_VARIABLE}" var="variable">
	GLOBAL.V.${variable.key} = "${variable.value}";
	</c:forEach> 
	
	if(!!GLOBAL.V.DefaultPermission){
		GLOBAL.V.DefaultPermissionArray=GLOBAL.V.DefaultPermission.split(";");
		GLOBAL.S.SEC_C_V=GLOBAL.isPermissionArray(GLOBAL.V.DefaultPermissionArray,GLOBAL.S.SEC_C);
		GLOBAL.S.SEC_R_V=GLOBAL.isPermissionArray(GLOBAL.V.DefaultPermissionArray,GLOBAL.S.SEC_R);
		GLOBAL.S.SEC_U_V=GLOBAL.isPermissionArray(GLOBAL.V.DefaultPermissionArray,GLOBAL.S.SEC_U);
		GLOBAL.S.SEC_D_V=GLOBAL.isPermissionArray(GLOBAL.V.DefaultPermissionArray,GLOBAL.S.SEC_D);
	}
	

	<c:set value="" var="parameter" /> 
	<c:forEach var="item" items="${S_PARAM}"  varStatus="status"> 
		<c:set value="&" var="parameter_q" />  
		<c:if test="${status.index==0}">	
		<c:set value="" var="parameter_q" /> 
		</c:if>
		<c:if test="${!empty item.value}">	
			<c:set value="${parameter}${parameter_q}${item.key}=${item.value}" var="parameter" /> 
		</c:if>	 
	</c:forEach> 
	GLOBAL.P.parameter="${parameter}";
</script>