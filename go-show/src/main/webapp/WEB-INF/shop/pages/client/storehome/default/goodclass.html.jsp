<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="nc-s-c-s1 ncs-class-bar">
  <div class="title">
    <h4>商品分类</h4>
  </div>
  <div class="content">
    <p><span><a href="http://localhost/shopnc/shop/index.php?act=show_store&amp;op=goods_all&amp;store_id=1&amp;key=1&amp;order=2">按新品</a></span><span><a href="http://localhost/shopnc/shop/index.php?act=show_store&amp;op=goods_all&amp;store_id=1&amp;key=2&amp;order=2">按价格</a></span><span><a href="http://localhost/shopnc/shop/index.php?act=show_store&amp;op=goods_all&amp;store_id=1&amp;key=3&amp;order=2">按销量</a></span><span><a href="http://localhost/shopnc/shop/index.php?act=show_store&amp;op=goods_all&amp;store_id=1&amp;key=5&amp;order=2">按人气</a></span></p>
    <div class="ncs-search">
      <form action="index.php" method="get" name="searchShop" id="">
        <input type="hidden" value="show_store" name="act">
        <input type="hidden" value="goods_all" name="op">
        <input type="hidden" value="1" name="store_id">
        <input type="text" placeholder="搜索店内商品" name="keyword" class="text w120">
        <a class="ncs-btn" href="javascript:document.searchShop.submit();">搜索</a>
      </form>
    </div>
    <ul class="ncs-submenu">
      <li><span class="ico-none"><em>-</em></span><a href="#">全部商品</a></li>
      <c:forEach items="${P_STOREGOODSCLASS_PARENT_LIST}" var="storeGoodsClass">
        <c:if test="${storeGoodsClass.stcState=='1'}">
          <li>
          <span style="cursor: pointer;" span_id="${storeGoodsClass.id}" onclick="class_list(this);" class="ico-none"><em>-</em></span>
          <a href="#">${storeGoodsClass.name}</a>
            <ul id="stc_${storeGoodsClass.id}">
          <c:forEach items="${storeGoodsClass.children}" var="children">
            <c:if test="${children.stcState=='1'}">
                <li><span class="ico-sub">&nbsp;</span>
                  <a href="${children.id}">${children.name}</a></li>
            </c:if>
          </c:forEach>
            </ul>
          </li>
        </c:if>
      </c:forEach>
    </ul>
    <div class="clear mb10"></div>
  </div>
</div>
<fis:out id="common:widget/jquery/jquery.js"/>
<script>
  // 商品分类
  function class_list(obj){
    var stc_id=$(obj).attr('span_id');
    var span_class=$(obj).attr('class');
    if(span_class=='ico-block') {
      $("#stc_"+stc_id).show();
      $(obj).html('<em>-</em>');
      $(obj).attr('class','ico-none');
    }else{
      $("#stc_"+stc_id).hide();
      $(obj).html('<em>+</em>');
      $(obj).attr('class','ico-block');
    }
  }
</script>
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