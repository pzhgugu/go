<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>


<a id="edit_invoice" nc_type="buy_edit" href="javascript:void(0)" style="display: none;">[修改]</a></div>
<div class="ncc-candidate-items" id="invoice_list">
  <ul>
  <c:forEach items="${P_INVOICE_LIST}" var="invoice">
    <li class="inv_item ncc-selected-item">
      <input type="radio" checked="" value="${invoice.id}" name="inv" nc_type="inv" id="inv_${invoice.id}" content="<c:if test="${invoice.invState==1}" >普通发票</c:if><c:if test="${invoice.invState==2}" >增值税发票</c:if> ${invoice.invTitle} ${invoice.invContent}">
      <label for="inv_${invoice.id}">&nbsp;&nbsp;<c:if test="${invoice.invState==1}" >普通发票</c:if><c:if test="${invoice.invState==2}" >增值税发票</c:if> ${invoice.invTitle} ${invoice.invContent}</label>
      &emsp;&emsp;&emsp;<a class="del" onclick="delInv('${invoice.id}');" href="javascript:void(0);">[ 删除 ]</a> </li>
  </c:forEach>
  <li class="inv_item">
    <input type="radio" name="inv" id="add_inv" nc_type="inv" value="0">
    <label for="add_inv">&nbsp;&nbsp;使用新的发票信息</label>
  </li>
  <div style="display:none" id="add_inv_box">
    <form action="${S_URL}/invoice/save" id="inv_form" method="POST">
      <div class="ncc-form-default">
        <dl>
          <dt>发票类型：</dt>
          <dd>
            <label>
              <input type="radio" value="1" name="invState" checked="">
              普通发票</label>
            &emsp;&emsp;
          </dd>
        </dl>
      </div>
      <div class="ncc-form-default" id="invoice_panel">
        <dl>
          <dt>发票抬头：</dt>
          <dd>
            <select name="inv_title_select">
              <option value="person">个人</option>
              <option value="company">单位</option>
            </select>
            <input value="" placeholder="单位名称" id="invTitle" name="invTitle" style="display:none" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt>发票内容：</dt>
          <dd>
            <select name="invContent" id="invContent">
              <option value="明细" selected="">明细</option>
              <option value="酒">酒</option>
              <option value="食品">食品</option>
              <option value="饮料">饮料</option>
              <option value="玩具">玩具</option>
              <option value="日用品">日用品</option>
              <option value="装修材料">装修材料</option>
              <option value="化妆品">化妆品</option>
              <option value="办公用品">办公用品</option>
              <option value="学生用品">学生用品</option>
              <option value="家居用品">家居用品</option>
              <option value="饰品">饰品</option>
              <option value="服装">服装</option>
              <option value="箱包">箱包</option>
              <option value="精品">精品</option>
              <option value="家电">家电</option>
              <option value="劳防用品">劳防用品</option>
              <option value="耗材">耗材</option>
              <option value="电脑配件">电脑配件</option>
            </select>
          </dd>
        </dl>
      </div>
      <div style="display:none" class="ncc-form-default" id="vat_invoice_panel">
        <dl>
          <dt><i class="required">*</i>单位名称：</dt>
          <dd>
            <input type="text" value="" name="invCompany" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>纳税人识别号：</dt>
          <dd>
            <input type="text" value="" name="invCode" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>注册地址：</dt>
          <dd>
            <input type="text" value="" name="invRegAddr" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>注册电话：</dt>
          <dd>
            <input type="text" value="" name="invRegPhone" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>开户银行：</dt>
          <dd>
            <input type="text" value="" name="invRegBankName" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>银行帐户：</dt>
          <dd>
            <input type="text" value="" name="invRegBaccount" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt></dt>
          <dd>如您是首次开具增值税专用发票，请您填写纳税人识别号等开票信息，并上传 加盖公章的营业执照副本、税务登记证副本、一般纳税人资格证书及银行开户 许可证扫描件邮寄给我们，收到您的开票资料后，我们会尽快审核。 </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>发票内容：</dt>
          <dd>明细</dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>收票人姓名：</dt>
          <dd>
            <input type="text" value="" name="invRecName" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>收票人手机号：</dt>
          <dd>
            <input type="text" value="" name="invRecMobphone" maxlength="50" class="text w200">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>收票人省份：</dt>
          <dd id="region">
            <select>
            </select>
            <input type="hidden" id="city_id" name="city_id" value="">
            <input type="hidden" class="area_ids" id="invRecProvince" name="invRecProvince">
            <input type="hidden" class="area_names" id="area_info" name="area_info">
          </dd>
        </dl>
        <dl>
          <dt><i class="required">*</i>送票地址：</dt>
          <dd>
            <input type="text" value="" name="invGotoAddr" maxlength="50" class="text w200">
          </dd>
        </dl>
      </div>
    </form>
  </div>
</ul>
  <div class="hr16">
    <a href="javascript:void(0);" class="ncc-btn ncc-btn-red" id="hide_invoice_list">保存发票信息</a>
    <a href="javascript:void(0);" class="ncc-btn ml10" id="cancel_invoice">不需要发票</a></div>
</div>


<script>
  var postResult = false;
  function delInv(id){
    $('#invoice_list').load('${S_URL}/invoice/load?id='+id);
  }
  $(function(){
    $.ajaxSetup({async : false});
    //不需要发票
    $('#cancel_invoice').on('click',function(){
      $('#invoice_id').val('');
      hideInvList('不需要发票');
    });
    //使用新的发票信息
    $('input[nc_type="inv"]').on('click',function(){
      regionInit("region");
      if ($(this).val() == '0') {
        $('.inv_item').removeClass('ncc-selected-item');
        $('#add_inv_box').show();
      } else {
        $('.inv_item').removeClass('ncc-selected-item');
        $(this).parent().addClass('ncc-selected-item');
        $('#add_inv_box').hide();
      }
    });
    //<?php if (empty($output['inv_list'])) {?>
      //$('input[nc_type="inv"]').click();
   // <?php } ?>
    //保存发票信息
    $('#hide_invoice_list').on('click',function(){
      var content = '';
      if ($('input[name="inv"]:checked').size() == 0){
        $('#cancel_invoice').click();
        return false;
      }
      if ($('input[name="inv"]:checked').val() != '0'){
        //如果选择已保存过的发票信息
        content = $('input[name="inv"]:checked').attr('content');
        $('#invoice_id').val($('input[name="inv"]:checked').val());
        hideInvList(content);
        return false;
      }
      //如果是新增发票信息
      if ($('input[name="invState"]:checked').val() == 1){
        //如果选择普通发票
        if ($('select[name="inv_title_select"]').val() == 'person'){
          content = '普通发票 个人 ' + $('select[name="invContent"]').val();
        }else if($.trim($('#invTitle').val()) == '' || $.trim($('#invTitle').val()) == '单位名称'){
          showDialog('请填写单位名称', 'error','','','','','','','','',2);return false;
        }else{
          content = '普通发票 ' + $.trim($('#invTitle').val())+ ' ' + $('#invContent').val();
        }
      }else{
        content = '增值税发票 ' + $.trim($('input[name="invCompany"]').val()) + ' ' + $.trim($('input[name="invCode"]').val()) + ' ' + $.trim($('input[name="invRegAddr"]').val());
        //验证增值税发票表单
        if (!$('#inv_form').valid()){
          return false;
        }
      }
      var datas=$('#inv_form').serialize();


      $.post('${S_URL}/invoice/save',datas,function(data){
        if (data.state=='success'){
          $('#invoice_id').val(data.id);
          postResult = true;
        }else{
          showDialog(data.msg, 'error','','','','','','','','',2);
          postResult = false;
        }
      },'json');
      if (postResult){
        hideInvList(content);
      }
    });
    $('input[name="invState"]').on('click',function(){
      if ($(this).val() == 1) {
        $('#invoice_panel').show();
        $('#vat_invoice_panel').hide();
      } else {
        $('#invoice_panel').hide();
        $('#vat_invoice_panel').show();
      }
    });
    $('select[name="inv_title_select"]').on('change',function(){
      if ($(this).val()=='company') {
        $('#invTitle').show();
      } else {
        $('#invTitle').hide();
      }
    });

    $('#inv_form').validate({
      rules : {
        invCompany : {
          required : true
        },
        invCode : {
          required : true
        },
        invRegAddr : {
          required : true
        },
        invRegPhone : {
          required : true
        },
        invRegBankName : {
          required : true
        },
        invRegBaccount : {
          required : true
        },
        invRecName : {
          required : true
        },
        invRecMobphone : {
          required : true
        },
        invRecProvince : {
          required : true,
          min   : 1,
          checkarea:true
        },
        invGotoAddr : {
          required : true
        }
      },
      messages : {
        invCompany : {
          required : '<i class="icon-exclamation-sign"></i>单位名称不能为空'
        },
        invCode : {
          required : '<i class="icon-exclamation-sign"></i>纳税人识别号不能为空'
        },
        invRegAddr : {
          required : '<i class="icon-exclamation-sign"></i>注册地址不能为空'
        },
        invRegPhone : {
          required : '<i class="icon-exclamation-sign"></i>注册电话不能为空'
        },
        invRegBankName : {
          required : '<i class="icon-exclamation-sign"></i>开户银行不能为空'
        },
        invRegBaccount : {
          required : '<i class="icon-exclamation-sign"></i>银行帐户不能为空'
        },
        invRecName : {
          required : '<i class="icon-exclamation-sign"></i>收票人姓名不能为空'
        },
        invRecMobphone : {
          required : '<i class="icon-exclamation-sign"></i>收票人手机号不能为空'
        },
        invRecProvince : {
          required : '<i class="icon-exclamation-sign"></i>请选择地区',
          min : '<i class="icon-exclamation-sign"></i>请选择地区',
          checkarea:'<i class="icon-exclamation-sign"></i>请选择地区'
        },
        invGotoAddr : {
          required : '<i class="icon-exclamation-sign"></i>送票地址不能为空'
        }
      }
    });
  });
</script>