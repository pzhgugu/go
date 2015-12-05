<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<c:set var="S_URL" value="${pageContext.request.contextPath}"/>
<div class="ncsc-layout-left" id="layoutLeft">
    <div class="sidebar" id="sidebar">
        <div id="main-nav" class="column-title">
            <span class="ico-store">
            </span>

            <h2>
                首页
            </h2>
        </div>
        <div class="column-menu">
            <ul id="seller_center_left_menu">
                <li>
                    <a href="${S_URL}/se/goods/addstep/one">
                        商品发布
                    </a>
                </li>
                <li>
                    <a href="${S_URL}/se/store/set/list">
                        店铺设置
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>