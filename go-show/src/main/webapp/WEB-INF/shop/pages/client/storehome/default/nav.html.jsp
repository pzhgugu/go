<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fis" uri="/fis" %>

<div class="ncsl-nav">
  <div class="banner">
    <a class="img" href="${S_URL}/cl/store/home?store_id=${P_STORE.id}">
      <c:if test="${empty P_STORE.storeBanner}">
    <div class="ncs-default-banner pngFix"></div>
      </c:if>
      <c:if test="${!empty P_STORE.storeBanner}">
        <img src="${S_URL}/att/download/${P_STORE.storeBanner}" />
      </c:if>
    </a></div>
  <nav class="pngFix" id="nav">
    <ul class="pngFix">
      <li class="active"><a href="${S_URL}/cl/store/home?store_id=${P_STORE.id}"><span>店铺首页<i></i></span></a></li>
      <c:forEach items="${P_STORE_NAV}" var="nav">
        <c:if test="${nav.snIfShow==1}">
      <li class="normal"><a <c:if test="${nav.snNewOpen==1}">target="_blank" </c:if> <c:if test="${!empty nav.snUrl}" > href="${nav.snUrl}"</c:if><c:if test="${empty nav.snUrl}" > href="${S_URL}/cl/store/article?store_id=${P_STORE.id}&sn_id=${nav.id}"</c:if>><span>${nav.snTitle}<i></i></span></a></li>
        </c:if>
      </c:forEach>
    </ul>
  </nav>
</div>