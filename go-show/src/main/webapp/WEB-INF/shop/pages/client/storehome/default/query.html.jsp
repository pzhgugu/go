<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

  <div class="nc-s-c-s3 ncg-list mt10">
    <div class="title pngFix">
      <h4>${P_STORE_TITLE}</h4>
    </div>
<c:if test="${!empty P_PAGE_SHOW.content}">
    <div class="content pt20">
      <ul>
        <c:forEach items="${P_PAGE_SHOW.content}" var="goods">
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
      <fis:block url="shop:widget/tpl/pagination.html.jsp" >
        <fis:param name="page" value="P_PAGE_SHOW"/>
        <fis:param name="paginationSize" value="9"/>
        <fis:param name="reqName" value="curpage"/>
        <fis:param name="url" value="${S_URL}/cl/store/query"/>
      </fis:block>
    </div>
</c:if>
  </div>

<c:if test="${empty P_PAGE_SHOW.content}">
  <div class="nothing">
    <p>很抱歉! 没有找到相关商品</p>
  </div>
</c:if>