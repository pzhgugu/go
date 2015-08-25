<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
<div class="public-top-layout w">
  <div class="topbar wrapper">  
    <div class="user-entry">
          ${P_USER}您好，欢迎来到      <a alt="首页" title="首页" href="#">我的家</a>
       <span>[<a href="#">登录</a>]</span>
        <span>[<a href="#">注册</a>]</span>
    <span class="seller-login"><a title="登录商家管理中心" target="_blank" href="#">
    <i class="icon-signin"></i>商家管理中心</a></span></div>
    
    <div class="quick-menu">
      <dl>
        <dt><a href="#">我的订单</a><i></i></dt>
        <dd>
          <ul>
            <li><a href="#">待付款订单</a></li>
            <li><a href="#">待确认收货</a></li>
            <li><a href="#">待评价交易</a></li>
          </ul>
        </dd>
      </dl>
      <dl>
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
            <li><a href="#">帮助中心</a></li>
            <li><a href="#">售后服务</a></li>
            <li><a href="#">客服中心</a></li>
          </ul>
        </dd>
      </dl>
          </div>
  </div>
</div>