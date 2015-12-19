<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fis" uri="/fis" %>

<div class="public-top-layout w">
  <div class="topbar wrapper">
    <div class="user-entry">
      您好<span><sec:authorize ifNotGranted="ROLE_ANONYMOUS"><a href="#"><sec:authentication property='name' /></a></sec:authorize>
      <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">游客</sec:authorize></span>
      ，欢迎来到      <a alt="首页" title="首页" href="${S_URL}/cl/store/home?store_id=${P_STORE.id}"><span>${P_STORE.name}</span></a>
      <sec:authorize ifAnyGranted="ROLE_ANONYMOUS"><span>[<a href="#">登录</a>]</span><span>[<a href="#">注册</a>]</span></sec:authorize>
      <span>[<a href="${S_URL}/j_spring_security_logout">退出</a>]</span>
      <span class="seller-login"><a title="登录商家管理中心" target="_blank" href="${S_URL}/se/"><i class="icon-signin"></i>商家管理中心</a></span></div>

    <div class="quick-menu">
      <dl class="">
        <dt><a href="#">我的订单</a><i></i></dt>
        <dd>
          <ul>
            <li><a href="#">待付款订单</a></li>
            <li><a href="#">待确认收货</a></li>
            <li><a href="#">待评价交易</a></li>
          </ul>
        </dd>
      </dl>
      <dl class="">
        <dt><a href="#">我的收藏</a><i></i></dt>
        <dd>
          <ul>
            <li><a href="#">商品收藏</a></li>
            <li><a href="#">店铺收藏</a></li>
          </ul>
        </dd>
      </dl>
      <dl class="">
        <dt>客户服务<i></i></dt>
        <dd>
          <ul>
            <li><a href="${S_URL}/cl/newshow/list?type=help">帮助中心</a></li>
            <li><a href="#">售后服务</a></li>
            <li><a href="#">客服中心</a></li>
          </ul>
        </dd>
      </dl>
    </div>
  </div>
</div>
<fis:script>
  $(function(){
    $(".quick-menu dl").hover(function() {
              $(this).addClass("hover");
            },
            function() {
              $(this).removeClass("hover");
            });

  });
</fis:script>