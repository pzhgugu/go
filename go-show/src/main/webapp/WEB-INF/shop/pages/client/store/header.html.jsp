<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<header class="wrapper">
    <h1 class="logo"><a href="#">
        <img class="pngFix" src="${S_URL}/res/img/store/logo.png"></a></h1>
    <nav>
        <ul>
            <li class='step1 <c:if test="${P_STEP==1}"> current</c:if>'>在线开店申请</li>
            <li class='step2 <c:if test="${P_STEP==2||P_STEP==3}"> current</c:if>'>商家信息提交</li>
            <li class='step3 <c:if test="${P_STEP==4}"> current</c:if>'>合同签订及缴费</li>
            <li class='step4 <c:if test="${P_STEP==5}"> current</c:if>'>店铺开通</li>
        </ul>
    </nav>
</header>
