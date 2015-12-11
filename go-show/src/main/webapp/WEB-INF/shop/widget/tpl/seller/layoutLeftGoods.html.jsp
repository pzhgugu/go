<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<div class="ncsc-layout-left" id="layoutLeft">
    <div class="sidebar" id="sidebar">
        <div id="main-nav" class="column-title">
            <span class="ico-goods">
            </span>
            <h2>
                商品
            </h2>
        </div>
        <div class="column-menu">
            <ul id="seller_center_left_menu">
                <li <c:if test='${P_CURRENT_OP=="GoodsAdd"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/goods/addstep/one">
                        商品发布
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="Online"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/goodsonline/list">
                        出售中的商品
                    </a>
                </li>
                <li class="">
                    <a href="#">
                        仓库中的商品
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="storeWarning"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/warning/edit">
                        库存警报
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="plate"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/plate/list">
                        关联板式
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="spec"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/spec/list">
                        商品规格
                    </a>
                </li>
                <li <c:if test='${P_CURRENT_OP=="Album"}'>class="current"</c:if>>
                    <a href="${S_URL}/se/album/cate">
                        图片空间
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>