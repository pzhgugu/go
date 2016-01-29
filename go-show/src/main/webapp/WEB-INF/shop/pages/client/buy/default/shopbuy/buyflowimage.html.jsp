<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<header class="ncc-head-layout">
  <div class="site-logo"><a href="#"><img class="pngFix" src="${S_URL}/res/img/logo.png"></a></div>
  <ul class="ncc-flow">
    <li <c:if test="${P_STEP=='step1'}">class="current"</c:if> ><i class="step1"></i>
      <p>我的购物车</p>
      <sub></sub>
      <div class="hr"></div>
    </li>
    <li <c:if test="${P_STEP=='step2'}">class="current"</c:if>><i class="step2"></i>
      <p>填写核对购物信息</p>
      <sub></sub>
      <div class="hr"></div>
    </li>
    <li <c:if test="${P_STEP=='step3'}">class="current"</c:if>><i class="step3"></i>
      <p>支付提交</p>
      <sub></sub>
      <div class="hr"></div>
    </li>
    <li <c:if test="${P_STEP=='step4'}">class="current"</c:if>><i class="step4"></i>
      <p>订单完成</p>
      <sub></sub>
      <div class="hr"></div>
    </li>
  </ul>
</header>