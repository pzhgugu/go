<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="nc-s-c-s1">
  <div class="title">
    <h4>商品二维码</h4>
  </div>
  <div class="content">
    <div class="ncs-goods-code"><p><img title="商品原始地址：${S_URL}/cl/goods/show?goods_id=${P_GOODS.id}" onerror="#" src="${S_URL}/cl/goodsqrcode/show?goods_id=${P_GOODS.id}"></p> <span class="ncs-goods-code-note"><i></i>扫描二维码，手机查看分享</span> </div>
  </div>
</div>