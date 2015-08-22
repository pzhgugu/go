<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<div class="ncsc-layout wrapper">
  <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp" />
  <div class="ncsc-layout-right" id="layoutRight">
    <fis:block url="shop:widget/tpl/seller/nav.html.jsp" />
    <div id="mainContent" class="main-content">
      <fis:block url="shop:pages/seller/GoodsAdd/setp.html.jsp" />


<div class="alert alert-block hr32">
  <h2><i class="icon-ok-circle mr10"></i>恭喜您，商品发布成功！&nbsp;&nbsp;</h2>
  <div class="hr16"></div>
  <strong>
    <a href="#" class="ml30 mr30">去店铺查看商品详情&gt;&gt;</a>
    <a href="#">重新编辑刚发布的商品&gt;&gt;</a>
  </strong>
  <div class="hr16"></div>
  <h4 class="ml10">您还可以:</h4>
  <ul class="ml30">
    <li>1. 继续 " <a href="#">发布新商品</a>"</li>
    <li>2. 进入 " 我的商城" 管理 "<a href="#">商品列表</a>"</li>
    <!-- li>4. 选择商品参加 &quot; <a href="#">团购活动</a> &quot;</li -->
    <li>3. 参与商城的 " <a href="#">专题活动</a> "</li>
  </ul>
</div>


	</div>
  </div>
</div>