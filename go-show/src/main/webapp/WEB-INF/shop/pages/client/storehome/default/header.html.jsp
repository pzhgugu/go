<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fis" uri="/fis" %>

<header id="header">
  <div class="layout">
    <h1 id="shop-logo">
      <a title="${P_STORE.sellerName}" href="${S_URL}/cl/store/home?store_id=${P_STORE.id}" class="mall-home"><img title="${P_STORE.name}" alt="${P_STORE.name}" <c:if test="${empty P_STORE.storeLabel}">src="${S_URL}/res/img/store/default_store_logo.gif"></c:if><c:if test="${!empty P_STORE.storeLabel}">src="${S_URL}/att/download/${P_STORE.storeLabel}"></c:if> </a>
    </h1>
    <div id="jsddm" class="shop-head-info">

      <div id="shop-info" class="search">
        <form id="formSearch" name="formSearch" action="" method="get">
          <input type="hidden" value="search" id="search_act" name="act">
          <input type="text" lang="zh-CN" placeholder="想找什么商品？" x-webkit-grammar="builtin:search" onwebkitspeechchange="foo()" x-webkit-speech="" value="${param.keyword}" class="ncs-search-input-text" id="keyword" name="keyword" style="color: rgb(153, 153, 153);">
          <a nctype="search_in_shop" class="ncs-search-btn-mall" href="javascript:void(0)"><span>全站搜</span></a>
          <a nctype="search_in_store" class="ncs-search-btn-shop" href="javascript:void(0)"><span>店内搜</span></a>
        </form>
      </div>
      <div class="service"><i></i>客服<em></em>
        <div class="arrow"></div>
        <div class="sub" style="visibility: hidden;">
          <div class="ncs-message-bar">
            <div class="default">
              <h5>官方店铺</h5>
              <span member_id="1"></span>
            </div>
          </div>
        </div>
      </div>
      <div class="favorites"><i></i>收藏<em></em>
        <div class="arrow"></div>
        <div class="sub" style="visibility: hidden;">
          <div class="title-bar">
            <h3>店铺空间</h3>
          </div>
          <ul>
            <li><a class="btn" href="javascript:collect_store('1','count','store_collect')"><i></i>收藏</a></li>
            <li><a class="no-url" nctype="store_collect" href="javascript:void(0);">0</a><span>收藏人气</span></li>
            <li><a target="_blank" href="#">2</a><span>店铺动态</span></li>
            <li><a nctype="share_store" class="share" href="javascript:void(0);"></a><span>分享</span></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</header>

<fis:script>
  $('a[nctype="search_in_store"]').click(function(){
  if ($('#keyword').val() == '') {
  return false;
  }
  $('#search_act').val('show_store');
  $('<input type="hidden" value="${P_STORE.id}" name="store_id" />').appendTo("#formSearch");
  var path="${S_URL}/cl/store/query";
  $('#formSearch').attr("action", path).submit();
  });
  $('a[nctype="search_in_shop"]').click(function(){
  if ($('#keyword').val() == '') {
  return false;
  }
  $('#formSearch').submit();
  });
  $('#keyword').css("color","#999999");

  var storeTrends	= true;
  $('.favorites').mouseover(function(){
  /*var $this = $(this);
  if(storeTrends){
  $.getJSON('index.php?act=show_store&op=ajax_store_trend_count&store_id=1', function(data){
  $this.find('li:eq(2)').find('a').html(data.count);
  storeTrends = false;
  });
  }*/
  });

  $('a[nctype="share_store"]').click(function(){
  //ajax_form('sharestore', '分享店铺', 'XX');
  });
</fis:script>