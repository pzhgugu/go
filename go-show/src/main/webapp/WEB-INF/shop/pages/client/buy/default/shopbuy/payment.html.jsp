<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>


<div id="paymentCon" class="ncc-receipt-info">
  <div class="ncc-receipt-info-title">
    <h3>支付方式</h3>
    <a id="edit_payment" nc_type="buy_edit" href="javascript:void(0)" style="display: none;">[修改]</a><font color="#B0B0B0">如需修改，请先保存发票信息</font>
  </div>
  <div class="ncc-candidate-items">
    <ul>
      <li>在线支付</li>
    </ul>
  </div>
  <div style="display:none" class="ncc-candidate-items" id="payment_list">
    <ul>
      <li>
        <input type="radio" id="payment_type_online" name="payment_type" value="online">
        <label for="payment_type_online">在线支付</label>
      </li>
      <li>
        <input type="radio" id="payment_type_offline" name="payment_type" value="offline">
        <label for="payment_type_offline">货到付款</label>
      </li>
    </ul>
    <div class="hr16"> <a id="hide_payment_list" class="ncc-btn ncc-btn-red" href="javascript:void(0);">保存支付方式</a></div>
  </div>
  <div class="ncc-payment-showgoods-list" id="ncc-payment-showgoods-list">
    <dl>
      <!-- 如果没有在线支付的商品，都是货到付款的，则就不再显示两种支付方式的商品数量了 -->
    </dl>
  </div>
</div>