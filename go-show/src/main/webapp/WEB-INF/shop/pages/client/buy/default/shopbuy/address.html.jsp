<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="ncc-receipt-info">
  <div class="ncc-receipt-info-title">
    <h3>收货人信息</h3>
    <a href="javascript:void(0)" nc_type="buy_edit" id="edit_reciver">[修改]</a></div>
  <div id="addr_list" class="ncc-candidate-items">
    <ul>
      <li><span class="true-name">${P_ADDRESS_ISDEFAULT.trueName}</span>
        <span class="address">${P_ADDRESS_ISDEFAULT.areaInfo} ${P_ADDRESS_ISDEFAULT.address}</span>
        <span class="phone"><i class="icon-mobile-phone"></i>${P_ADDRESS_ISDEFAULT.mobPhone} ${P_ADDRESS_ISDEFAULT.telPhone}</span>
      </li>
    </ul>
  </div>
</div>

<script>
  $(function() {
    //加载收货地址列表
    $('#edit_reciver').on('click',function(){
      $(this).hide();
      disableOtherEdit('如需修改，请先保存收货人信息 ');
      $(this).parent().parent().addClass('current_box');
      $('#addr_list').load('${S_URL}/address/load');
    });
    <c:if test="${empty P_ADDRESS_LIST}">
    $('#edit_reciver').click();
    </c:if>
    <c:if test="${!empty P_ADDRESS_LIST}">
    showShippingPrice('${P_ADDRESS_ISDEFAULT.cityId}','${P_ADDRESS_ISDEFAULT.areaId}');
    </c:if>
  });
</script>