<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="sidebar">
  <div class="member-handle"><h3>账户设置</h3>
    <ul>
      <li class="active"><a href="index.php?act=home&amp;op=member">个人资料</a></li>
      <li class="normal"><a href="index.php?act=member&amp;op=address">收货地址</a></li>
      <li class="normal"><a href="index.php?act=predeposit">预存款充值</a></li>
      <li class="normal"><a href="index.php?act=predeposit&amp;op=pd_cash_list">预存款提现</a></li>
      <li class="normal"><a href="index.php?act=predeposit&amp;op=pd_log_list">预存款明细</a></li>
      <li class="normal"><a href="index.php?act=member_sharemanage">分享绑定</a></li>
    </ul></div>
</div>