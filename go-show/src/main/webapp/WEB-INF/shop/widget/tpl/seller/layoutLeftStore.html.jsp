<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="ncsc-layout-left" id="layoutLeft">
    <div class="sidebar" id="sidebar">
        <div id="main-nav" class="column-title">
            <span class="ico-store">
            </span>

            <h2>
                店铺
            </h2>
        </div>
        <div class="column-menu">
            <ul id="seller_center_left_menu">
                <li <c:if test='${P_CURRENT_OP=="storeSet"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/store/set/list">
                        店铺设置
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="storeNav"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/storenav/list">
                        店铺导航
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="storeInfo"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/store/info">
                        店铺信息
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="storeClass"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/storegoodsclass/list">
                        店铺分类
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="brandApply"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/brandApply/list">
                        品牌申请
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>