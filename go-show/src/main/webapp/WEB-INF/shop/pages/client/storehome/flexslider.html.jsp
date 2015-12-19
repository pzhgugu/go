<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="flexslider">
  <ul class="slides">
    <c:if test="${!empty P_SLIDEIMAGE_LIST}">
      <c:forEach items="${P_SLIDEIMAGE_LIST}" var="slideImage">
        <c:if test="${!empty slideImage.attId}">
          <li>
            <c:if test="${!empty slideImage.url}"><a href="${slideImage.url}"></c:if>
            <img src="${S_URL}/att/download/${slideImage.attId}">
            <c:if test="${!empty slideImage.url}"></a></c:if>
          </li>
        </c:if>
      </c:forEach>
    </c:if>
    <c:if test="${empty P_SLIDEIMAGE_LIST}">
      <li><img src="${S_URL}/res/img/store/slides/f01.jpg"></li>
      <li><img src="${S_URL}/res/img/store/slides/f02.jpg"></li>
      <li><img src="${S_URL}/res/img/store/slides/f03.jpg"></li>
      <li><img src="${S_URL}/res/img/store/slides/f04.jpg"></li>
    </c:if>
  </ul>
</div>


<fis:require id="common:widget/ajaxfileupload/ajaxfileupload.js"/>
<fis:require id="common:widget/jquery/jquery.flexslider-min.js"/>
<fis:require id="shop:scripts/store_slide.js"/>
