<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="sidebar">
    <h3>商家信息提交</h3>
    <ul>
        <li class='<c:if test="${P_SIDEBAR==1}"> current</c:if>'><i></i>公司信息</li>
        <li class='<c:if test="${P_SIDEBAR==2}"> current</c:if>'><i></i>财务信息</li>
        <li class='<c:if test="${P_SIDEBAR==3}"> current</c:if>'><i></i>店铺信息</li>
        <li class='<c:if test="${P_SIDEBAR==4}"> current</c:if>'><i></i>申请状态</li>
    </ul>
</div>
