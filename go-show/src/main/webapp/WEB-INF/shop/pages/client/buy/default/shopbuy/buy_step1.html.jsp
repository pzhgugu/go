<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<!DOCTYPE html>
<fis:html mapDir="/map">

  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta charset="utf-8">
    <title>购买商品</title>
    <fis:require id="shop:styles/base.css"/>
    <fis:require id="shop:styles/home_login.css"/>
    <fis:require id="shop:styles/home_cart.css"/>
    <fis:require id="shop:pages/client/buy/${P_STYLE}/styles/home_header.css"/>
    <fis:styles/>
    <fis:out id="common:widget/font-awesome/css/font-awesome.css"/>
    <!--[if IE 7]>
    <fis:out id="common:widget/font-awesome/css/font-awesome-ie7.css"/>
    <![endif]-->

    <fis:out id="common:widget/jquery/jquery.js"/>
    <fis:require id="common:widget/jquery/waypoints.js"/>
    <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
    <fis:require id="common:widget/jquery/jquery.validation.js"/>
    <fis:require id="common:widget/jquery/jquery.masonry.js"/>
    <fis:require id="shop:scripts/member.js"/>
    <fis:require id="shop:scripts/common.js"/>
    <fis:require id="shop:scripts/common_select.js" />
    <fis:require id="shop:scripts/shop.js"/>
  <fis:require id="common:widget/qtip/jquery.qtip.min.js"/>

    <fis:scripts/>

    <script type="text/javascript">
      SITEURL='${S_URL}';
      //计算部运费和每个店铺小计
      function calcOrder() {
        var allTotal = 0;
        $('em[nc_type="eachStoreTotal"]').each(function(){
          store_id = $(this).attr('store_id');
          var eachTotal = 0;
          if ($('#eachStoreFreight_'+store_id).length > 0) {
            eachTotal += parseFloat($('#eachStoreFreight_'+store_id).html());
          }
          if ($('#eachStoreGoodsTotal_'+store_id).length > 0) {
            eachTotal += parseFloat($('#eachStoreGoodsTotal_'+store_id).html());
          }
          if ($('#eachStoreManSong_'+store_id).length > 0) {
            eachTotal += parseFloat($('#eachStoreManSong_'+store_id).html());
          }
          if ($('#eachStoreVoucher_'+store_id).length > 0) {
            eachTotal += parseFloat($('#eachStoreVoucher_'+store_id).html());
          }
          $(this).html(number_format(eachTotal,2));
          allTotal += eachTotal;
        });
        $('#orderTotal').html(number_format(allTotal,2));
      }
      $(function(){
        $.ajaxSetup({
          async : false
        });
        $('select[nctype="voucher"]').on('change',function(){
          if ($(this).val() == '') {
            $('#eachStoreVoucher_'+items[1]).html('-0.00');
          } else {
            var items = $(this).val().split('|');
            $('#eachStoreVoucher_'+items[1]).html('-'+number_format(items[2],2));
          }
          calcOrder();
        });
      });
      function disableOtherEdit(showText){
        $('a[nc_type="buy_edit"]').each(function(){
          if ($(this).css('display') != 'none'){
            $(this).after('<font color="#B0B0B0">' + showText + '</font>');
            $(this).hide();
          }
        });
        disableSubmitOrder();
      }
      function ableOtherEdit(){
        $('a[nc_type="buy_edit"]').show().next('font').remove();
        ableSubmitOrder();

      }
      function ableSubmitOrder(){
        $('#submitOrder').on('click',function(){submitNext()}).css('cursor','').addClass('ncc-btn-acidblue');
      }
      function disableSubmitOrder(){
        $('#submitOrder').unbind('click').css('cursor','not-allowed').removeClass('ncc-btn-acidblue');
      }

      //隐藏收货地址列表
      function hideAddrList(addr_id,true_name,address,phone) {
        $('#edit_reciver').show();
        $("#address_id").val(addr_id);
        $("#addr_list").html('<ul><li><span class="true-name">'+true_name+'</span><span class="address">'+address+'</span><span class="phone"><i class="icon-mobile-phone"></i>'+phone+'</span></li></ul>');
        $('.current_box').removeClass('current_box');
        ableOtherEdit();
        $('#edit_payment').click();
      }

      //异步显示每个店铺运费 city_id计算运费area_id计算是否支持货到付款
      function showShippingPrice(city_id,area_id) {
        /*$('#buy_city_id').val('');
         $.post(SITEURL + '/index.php?act=buy&op=change_addr', {'freight_hash':'php',city_id:city_id,'area_id':area_id}, function(data){
         if(data.state == 'success') {
         $('#buy_city_id').val(city_id);
         $('#allow_offpay').val(data.allow_offpay);
         $('#offpay_hash').val(data.offpay_hash);
         var content = data.content;
         var amount = 0;
         for(var i in content){
         $('#eachStoreFreight_'+i).html(number_format(content[i],2));
         amount = amount + parseFloat(content[i]);
         }
         calcOrder();
         }

         },'json');*/
      }
    </script>
    <fis:out id="shop:scripts/dialog/dialog.js" iid="dialog_js"/>
  </head>

  <body>
  <div id="append_parent"></div>
  <div id="ajaxwaitid"></div>
  <fis:block url="shop:pages/client/top.html.jsp"></fis:block>

  <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/buyflowimage.html.jsp"></fis:block>

  <div class="ncc-wrapper">
    <div class="ncc-main">

      <div class="ncc-title">
        <h3>填写核对购物信息</h3>
        <h5>请仔细核对填写收货、发票等信息，以确保物流快递及时准确投递。</h5>
      </div>
      <!--地址信息-->
      <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/address.html.jsp"></fis:block>
      <!--支付方式 -->
      <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/payment.html.jsp"></fis:block>
      <!--发票 -->
      <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/invoice.html.jsp"></fis:block>
      <!--购买商品列表 -->
      <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/goods_list.html.jsp"></fis:block>

      <div class="ncc-bottom"> <a class="ncc-btn ncc-btn-acidblue fr" id="submitOrder" href="javascript:void(0)">提交订单</a> </div>

    </div>
  </div>

  <fis:block url="shop:pages/client/buy/${P_STYLE}/button.html.jsp" />
  <fis:block url="shop:pages/client/buy/${P_STYLE}/footer.html.jsp" />

  </body>

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <fis:out id="common:widget/utils/html5shiv.js"/>
  <fis:out id="common:widget/utils/respond.js"/>
  <![endif]-->
  <!--[if IE 6]>
  <fis:out id="common:widget/utils/IE6_MAXMIX.js"/>
  <fis:out id="common:widget/utils/IE6_PNG.js"/>
  <script>
  DD_belatedPNG.fix('.pngFix');
  </script>
  <script>
  // <![CDATA[
  if((window.navigator.appName.toUpperCase().indexOf("MICROSOFT")>=0)&&(document.execCommand))
  try{
  document.execCommand("BackgroundImageCache", false, true);
  }
  catch(e){}
  // ]]>
  </script>
  <![endif]-->
</fis:html>
