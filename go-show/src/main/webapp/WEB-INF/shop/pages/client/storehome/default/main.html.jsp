<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<!--幻灯片轮播 -->
<fis:block url="shop:pages/client/storehome/flexslider.html.jsp"></fis:block>

<c:if test="${!empty P_GOODS_COMMEND}">
<div class="nc-s-c-s3 ncg-list mt10">
  <div class="title pngFix"> <span><a class="more" href="#">更多</a></span>
    <h4> 推荐商品</h4>
  </div>
  <div class="content pt20">
    <ul>
      <c:forEach items="${P_GOODS_COMMEND}" var="goods">
      <li>
        <dl>
          <dt><a target="_blank" href="${goods.id}">${goods.name}</a></dt>
          <dd class="ncg-pic pngFix">
            <a class="thumb" target="_blank" href="">
            <i></i>
              <img width="160" height="160" alt="${goods.name}" title="${goods.name}" onload="javascript:DrawImage(this,160,160);" src="${S_URL}/att/download/${goods.goodsImage}">
            </a></dd>
          <dd class="ncg-price"><em class="price"><i>¥</i>${goods.goodsStorePrice}</em></dd>
          <dd class="ncg-sold">已销售：<strong>${goods.goodsSalenum}</strong> 件</dd>
        </dl>
      </li>
      </c:forEach>
    </ul>
  </div>
</div>
</c:if>
<c:if test="${!empty P_GOODS_NEW}">
<div class="nc-s-c-s3 ncg-list mt10">
  <div class="title pngFix"> <span><a class="more" href="#">更多</a></span>
    <h4> 新品</h4>
  </div>
  <div class="content pt20">
    <ul>
      <c:forEach items="${P_GOODS_NEW}" var="goods">
        <li>
          <dl>
            <dt><a target="_blank" href="${goods.id}">${goods.name}</a></dt>
            <dd class="ncg-pic pngFix">
              <a class="thumb" target="_blank" href="">
                <i></i>
                <img width="160" height="160" alt="${goods.name}" title="${goods.name}" onload="javascript:DrawImage(this,160,160);" src="${S_URL}/att/download/${goods.goodsImage}">
              </a></dd>
            <dd class="ncg-price"><em class="price"><i>¥</i>${goods.goodsStorePrice}</em></dd>
            <dd class="ncg-sold">已销售：<strong>${goods.goodsSalenum}</strong> 件</dd>
          </dl>
        </li>
      </c:forEach>
    </ul>
  </div>
</div>
</c:if>
<c:if test="${empty P_GOODS_COMMEND&&empty P_GOODS_NEW}">
  <div class="nothing">
    <p>很抱歉! 没有找到相关商品</p>
  </div>
</c:if>