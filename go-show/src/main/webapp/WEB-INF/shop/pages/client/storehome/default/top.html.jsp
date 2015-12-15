<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="public-top-layout w">
  <div class="topbar wrapper">
    <div class="user-entry">
      您好<span><a href="http://localhost/shopnc/shop/index.php?act=member_snsindex&amp;op=index">shopnc</a></span>，欢迎来到      <a alt="首页" title="首页" href="http://localhost/shopnc/shop"><span>电商测试</span></a>
      <span>[<a href="http://localhost/shopnc/shop/index.php?act=login&amp;op=logout">退出</a>]</span>
      <span class="seller-login"><a title="登录商家管理中心" target="_blank" href="http://localhost/shopnc/shop/index.php?act=seller_login&amp;op=show_login"><i class="icon-signin"></i>商家管理中心</a></span></div>

    <div class="quick-menu">
      <dl class="">
        <dt><a href="http://localhost/shopnc/shop/index.php?act=member_order">我的订单</a><i></i></dt>
        <dd>
          <ul>
            <li><a href="http://localhost/shopnc/shop/index.php?act=member_order&amp;state_type=state_new">待付款订单</a></li>
            <li><a href="http://localhost/shopnc/shop/index.php?act=member_order&amp;state_type=state_send">待确认收货</a></li>
            <li><a href="http://localhost/shopnc/shop/index.php?act=member_order&amp;state_type=state_noeval">待评价交易</a></li>
          </ul>
        </dd>
      </dl>
      <dl class="">
        <dt><a href="http://localhost/shopnc/shop/index.php?act=member_favorites&amp;op=fglist">我的收藏</a><i></i></dt>
        <dd>
          <ul>
            <li><a href="http://localhost/shopnc/shop/index.php?act=member_favorites&amp;op=fglist">商品收藏</a></li>
            <li><a href="http://localhost/shopnc/shop/index.php?act=member_favorites&amp;op=fslist">店铺收藏</a></li>
          </ul>
        </dd>
      </dl>
      <dl class="">
        <dt>客户服务<i></i></dt>
        <dd>
          <ul>
            <li><a href="http://localhost/shopnc/shop/index.php?act=article&amp;op=article&amp;ac_id=2">帮助中心</a></li>
            <li><a href="http://localhost/shopnc/shop/index.php?act=article&amp;op=article&amp;ac_id=5">售后服务</a></li>
            <li><a href="http://localhost/shopnc/shop/index.php?act=article&amp;op=article&amp;ac_id=6">客服中心</a></li>
          </ul>
        </dd>
      </dl>
    </div>
  </div>
</div>
