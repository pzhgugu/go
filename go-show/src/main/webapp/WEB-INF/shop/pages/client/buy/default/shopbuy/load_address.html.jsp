<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<ul>
  <c:forEach items="${P_ADDRESS_LIST}" var="address" varStatus="status">
    <li class="receive_add address_item <c:if test="${status.index==0}">ncc-selected-item</c:if>">
      <input address="${address.areaInfo} ${address.address}" true_name="${address.trueName}" id="addr_${address.id}" nc_type="addr" type="radio" class="radio" city_id="${address.cityId}" area_id="${address.areaId}" name="addr" value="${address.id}" phone="${address.mobPhone}" <c:if test="${status.index==0}">checked</c:if>/>
      <label for="addr_${address.id}">
        <span class="true-name">${address.trueName}</span>
        <span class="address">${address.areaInfo}&nbsp;${address.address}</span>
        <span class="phone"><i class="icon-mobile-phone"></i>${address.mobPhone}</span></label>
      <a href="javascript:void(0);" onclick="delAddr('${address.id}');" class="del">[ 删除 ]</a> </li>
  </c:forEach>
  <li class="receive_add addr_item">
    <input value="0" nc_type="addr" id="add_addr" type="radio" name="addr">
    <label for="add_addr">使用新地址</label>
  </li>
  <div id="add_addr_box"><!-- 存放新增地址表单 --></div>
</ul>
<div class="hr16">
  <a id="hide_addr_list" class="ncc-btn ncc-btn-red" href="javascript:void(0);">
    保存收货人信息</a></div>
<script type="text/javascript">
  function delAddr(id){
    $('#addr_list').load("${S_URL}/address/load?id="+id);
  }
  $(function(){
    function addAddr() {
      $('#add_addr_box').load('${S_URL}/address/add');
    }
    $('input[nc_type="addr"]').on('click',function(){
      if ($(this).val() == '0') {
        $('.address_item').removeClass('ncc-selected-item');
        $('#add_addr_box').load('${S_URL}/address/add');
      } else {
        $('.address_item').removeClass('ncc-selected-item');
        $(this).parent().addClass('ncc-selected-item');
        $('#add_addr_box').html('');
      }
    });
    $('#hide_addr_list').on('click',function(){
      if ($('input[nc_type="addr"]:checked').val() == '0'){
        submitAddAddr();
      } else {
        var city_id = $('input[name="addr"]:checked').attr('city_id');
        var area_id = $('input[name="addr"]:checked').attr('area_id');
        var addr_id = $('input[name="addr"]:checked').val();
        var true_name = $('input[name="addr"]:checked').attr('true_name');
        var address = $('input[name="addr"]:checked').attr('address');
        var phone = $('input[name="addr"]:checked').attr('phone');
        showShippingPrice(city_id,area_id);
        hideAddrList(addr_id,true_name,address,phone);
      }
    });
    if ($('input[nc_type="addr"]').size() == 1){
      $('#add_addr').attr('checked',true);
      addAddr();
    }
  });
</script>
