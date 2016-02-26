<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="tabmenu"><ul class="tab pngFix">
  <li <c:choose><c:when test="${P_OP=='base'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose>><a href="${S_URL}/shop/member/set">基本信息</a></li>
  <li <c:choose><c:when test="${P_OP=='passwd'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose>><a href="${S_URL}/shop/member/passwd">修改密码</a></li>
  <li <c:choose><c:when test="${P_OP=='email'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose>><a href="${S_URL}/shop/member/email">修改电子邮件</a></li>
  <li <c:choose><c:when test="${P_OP=='avatar'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose>><a href="${S_URL}/shop/member/avatar">更换头像</a></li></ul>
</div>