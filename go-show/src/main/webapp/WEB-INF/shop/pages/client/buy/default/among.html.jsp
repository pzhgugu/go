<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>


<div class="nc-s-c-s1 ncs-top-bar">
  <div class="title">
    <h4>商品排行</h4>
  </div>
  <div class="content">
    <ul class="ncs-top-tab pngFix">
      <li class="current" id="hot_sales_tab"><a href="#">热销商品排行</a></li>
      <li id="hot_collect_tab"><a href="#">热门收藏排行</a></li>
    </ul>
    <div class="ncs-top-panel" id="hot_sales_list">
      <ol>
        <c:forEach items="${P_GOODS_HOT}" var="hot">
        <li>
          <dl>
            <dt><a href="${S_URL}/cl/goods/show?common_id=${hot.id}">${hot.name}</a></dt>
            <dd class="goods-pic"><a href="${S_URL}/cl/goods/show?common_id=${hot.id}">
              <span class="thumb size40"><i></i>
                <img width="40" height="40" onload="javascript:DrawImage(this,40,40);" src="${S_URL}/att/download/${hot.goodsImage}"></span></a>
              <p><span class="thumb size100"><i></i>
                <img width="100" height="100" title="${hot.name}" onload="javascript:DrawImage(this,100,100);" src="${S_URL}/att/download/${hot.goodsImage}">
                <big></big><small></small></span>
              </p>
            </dd>
            <dd class="price pngFix">${hot.goodsStorePrice}</dd>
            <dd class="selled pngFix">售出：<strong>${hot.goodsSalenum}</strong>笔</dd>
          </dl>
        </li>
        </c:forEach>
      </ol>
    </div>
    <div class="ncs-top-panel hide" id="hot_collect_list">
      <ol>
<c:forEach items="${P_GOODS_COLLECT}" var="collect">
        <li>
          <dl>
            <dt><a href="${S_URL}/cl/goods/show?common_id=${collect.id}">${collect.name}</a></dt>
            <dd class="goods-pic">
              <a title="" href="${S_URL}/cl/goods/show?common_id=${collect.id}">
                <span class="thumb size40">
                  <i></i>
                  <img width="40" height="40" onload="javascript:DrawImage(this,40,40);" src="${S_URL}/att/download/${collect.goodsImage}"></span></a>
              <p><span class="thumb size100"><i></i>
                <img width="100" height="100" title="${collect.name}" onload="javascript:DrawImage(this,100,100);" src="${S_URL}/att/download/${collect.goodsImage}"><big></big><small></small></span></p>
            </dd>
            <dd class="price pngFix">${collect.goodsStorePrice}</dd>
            <dd class="collection pngFix">收藏人气：<strong>${collect.goodsCollect}</strong></dd>
          </dl>
        </li>
</c:forEach>
      </ol>
    </div>
    <p><a href="${S_URL}/cl/store/query?store_id=${P_STORE.id}">查看本店其他商品</a></p>
  </div>
</div>
<fis:script>
  $(document).ready(function(){
  //热销排行切换
  $('#hot_sales_tab').on('mouseenter', function() {
  $(this).addClass('current');
  $('#hot_collect_tab').removeClass('current');
  $('#hot_sales_list').removeClass('hide');
  $('#hot_collect_list').addClass('hide');
  });
  $('#hot_collect_tab').on('mouseenter', function() {
  $(this).addClass('current');
  $('#hot_sales_tab').removeClass('current');
  $('#hot_sales_list').addClass('hide');
  $('#hot_collect_list').removeClass('hide');
  });
  });


</fis:script>