<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="sidebar">
  <dl class="ncu-user">
    <dt class="username"><a title="编辑用户信息" href="#">${P_MEMBER.alias}</a></dt>
    <dd class="userface">
      <div class="pic"><span class="thumb size100"><i></i><img width="100" height="100" alt="${P_MEMBER.alias}" onload="javascript:DrawImage(this,100,100);" <c:choose><c:when test="${empty P_MEMBER.memberAvatar}">src="${S_URL}/res/img/default_user_portrait.gif"</c:when><c:otherwise>src="${S_URL}/att/download/${P_MEMBER.memberAvatar}"</c:otherwise></c:choose>></span>
        <p><a title="上传新头像" href="index.php?act=home&amp;op=avatar">上传新头像</a><i></i></p>
      </div>
    </dd>
    <dd class="info">
      <ul>
        <li>积分数：590</li>
        <li>预存款：<span class="price">0.00</span>元</li>
      </ul>
    </dd>
  </dl>
  <div class="business-intro">
    <h3>交易信息</h3>
    <ul>
      <li><span><a href="${S_URL}/shop/member_order/new">待付款订单</a></span> <i class="yes">3</i></li>
      <li><span><a href="index.php?act=member_order&amp;state_type=state_send">待确认收货</a></span> <i class="no">0</i></li>
      <li><span><a href="index.php?act=member_order&amp;state_type=state_noeval">待评价交易</a></span> <i class="no">0</i></li>
    </ul>
  </div>

  <div id="my_menu" class="business-handle">
    <h3>交易操作</h3>
    <div class="normal"><em class="i1"></em><a href="http://localhost/shopnc/shop/index.php?act=cart&amp;op=index">我的购物车</a></div>
    <div class="normal"><em class="i2"></em><a href="index.php?act=member_order">已买到的商品</a></div>
    <dl class="collapsed">
      <dt><em class="i4"></em><a href="javascript:void(0)">我的收藏</a><i></i></dt>
      <dd class="normal"><a href="index.php?act=member_favorites&amp;op=fglist">收藏商品</a></dd>
      <dd class="normal"><a href="index.php?act=member_favorites&amp;op=fslist">收藏店铺</a></dd>
    </dl>
    <dl class="collapsed">
      <dt><em class="i5"></em><a href="javascript:void(0)">我的积分</a><i></i></dt>
      <dd class="normal"><a href="index.php?act=member_points">积分明细</a></dd>
      <dd class="normal"><a href="index.php?act=member_pointorder">已兑换的商品</a></dd>

    </dl>
    <div class="normal"><em class="i6"></em><a href="index.php?act=member_voucher">我的代金券</a>
    </div>
    <div class="normal"><em class="i7"></em><a href="index.php?act=member_evaluate&amp;op=list">评价晒单</a></div>
    <dl class="">
      <dt><em class="i8"></em><a href="javascript:void(0)">咨询与维权</a><i></i></dt>
      <dd class="normal"><a href="index.php?act=member_inform">我的举报</a></dd>
      <dd class="normal"><a href="index.php?act=member_consult&amp;op=my_consult">我的咨询</a></dd>
      <dd class="normal"><a href="index.php?act=member_complain">投诉管理</a></dd>
    </dl>
  </div>
</div>