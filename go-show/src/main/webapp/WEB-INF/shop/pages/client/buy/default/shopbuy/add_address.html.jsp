<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="ncc-form-default">
  <form method="POST" id="addr_form" action="#">
    <input type="hidden" value="buy" name="act">
    <input type="hidden" value="add_addr" name="op">
    <input type="hidden" name="isDefault" value="1"/>
    <dl>
      <dt><i class="required">*</i>收货人姓名：</dt>
      <dd>
        <input type="text" class="text w100" name="trueName" maxlength="20" id="trueName" value=""/>
      </dd>
    </dl>
    <dl>
      <dt><i class="required">*</i>所在地区：</dt>
      <dd>
        <div id="region">
          <select class="w110">
          </select>
          <input type="hidden" value="" name="cityId" id="cityId">
          <input type="hidden" name="areaId" id="areaId" class="area_ids"/>
          <input type="hidden" name="areaInfo" id="areaInfo" class="area_names"/>
        </div>
      </dd>
    </dl>
    <dl>
      <dt><i class="required">*</i>详细地址：</dt>
      <dd>
        <input type="text" class="text w500" name="address" id="address" maxlength="80" value=""/>
        <p class="hint">请填写真实地址，不需要重复填写所在地区</p>
      </dd>
    </dl>
    <dl>
      <dt> <i class="required">*</i>手机号码：</dt>
      <dd>
        <input type="text" class="text w200" name="mobPhone" id="mobPhone" maxlength="15" value=""/>
        &nbsp;&nbsp;(或) 固定电话：
        <input type="text" class="text w200" id="telPhone" name="telPhone" maxlength="20" value=""/>
      </dd>
    </dl>
  </form>
</div>

<script type="text/javascript">
  $(document).ready(function(){
    regionInit("region");
    $('#addr_form').validate({
      rules : {
        trueName : {
          required : true
        },
        areaId : {
          required : true,
          min   : 1,
          checkarea:true
        },
        address : {
          required : true
        },
        mobPhone : {
          required : checkPhone,
          minlength : 11,
          maxlength : 11,
          digits : true
        },
        telPhone : {
          required : checkPhone,
          minlength : 6,
          maxlength : 20
        }
      },
      messages : {
        trueName : {
          required : '<i class="icon-exclamation-sign"></i>请填写收货人姓名'
        },
        areaId : {
          required : '<i class="icon-exclamation-sign"></i>请选择所在地区',
          min  : '<i class="icon-exclamation-sign"></i>请选择所在地区',
          checkarea : '<i class="icon-exclamation-sign"></i>请选择所在地区'
        },
        address : {
          required : '<i class="icon-exclamation-sign"></i>请填写收货人详细地址'
        },
        mobPhone : {
          required : '<i class="icon-exclamation-sign"></i>手机号码或固定电话请至少填写一个',
          minlength: '<i class="icon-exclamation-sign"></i>手机号码只能是11位',
          maxlength: '<i class="icon-exclamation-sign"></i>手机号码只能是11位',
          digits : '<i class="icon-exclamation-sign"></i>手机号码只能是11位'
        },
        telPhone : {
          required : '<i class="icon-exclamation-sign"></i>手机号码或固定电话请至少填写一个',
          minlength: '<i class="icon-exclamation-sign"></i>',
          maxlength: '<i class="icon-exclamation-sign"></i>'
        }
      },
      groups : {
        phone:'mobPhone telPhone'
      }
    });
  });
  function checkPhone(){
    return ($('input[name="mobPhone"]').val() == '' && $('input[name="telPhone"]').val() == '');
  }
  function submitAddAddr(){
    if ($('#addr_form').valid()){
      $('#buy_city_id').val($('#region').find('select').eq(1).val());
      $('#cityId').val($('#region').find('select').eq(1).val());
      var datas=$('#addr_form').serialize();
      $.post('${S_URL}/address/save',datas,function(data){
        if (data.state){
          var trueName = $.trim($("#trueName").val());
          var telPhone = $.trim($("#telPhone").val());
          var mobPhone = $.trim($("#mobPhone").val());
          var areaInfo = $.trim($("#areaInfo").val());
          var address = $.trim($("#address").val());
          showShippingPrice($('#cityId').val(),$('#areaId').val());
          hideAddrList(data.addr_id,trueName,areaInfo+'&nbsp;&nbsp;'+address,(mobPhone != '' ? mobPhone : telPhone));
        }else{
          alert(data.msg);
        }
      },'json');
    }else{
      return false;
    }
  }
</script>