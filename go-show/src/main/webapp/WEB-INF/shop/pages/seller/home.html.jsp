<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<c:set var="S_URL" value="${pageContext.request.contextPath}"/>

<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutHome.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>

        店铺首页

    </div>
</div>

