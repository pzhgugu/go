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
    <title>订单创建</title>
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
        <h3>支付提交</h3>
        <h5>订单详情内容可通过查看<a target="_blank" href="index.php?act=member_order">我的订单</a>进行核对处理。</h5>
      </div>
      <form id="buy_form" method="POST" action="index.php?act=payment">
        <input type="hidden" value="420507391341721002" name="pay_sn">
        <input type="hidden" value="" name="payment_code" id="payment_code">
        <input type="hidden" value="product_buy" name="order_type">
        <div class="ncc-receipt-info">
          <div class="ncc-receipt-info-title">
            <h3>请您及时付款，以便订单尽快处理！                    在线支付金额：<strong>￥899.00</strong>
            </h3>
          </div>
          <table class="ncc-table-style">
            <thead>
            <tr>
              <th class="w50"></th>
              <th class="w250 tl">订单号</th>
              <th class="tl">支付方式</th>
              <th class="w150">金额</th>
              <th class="w150">物流</th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td></td>
              <td class="tl">8000000000000201</td>
              <td class="tl">在线支付</td>
              <td>￥899.00</td>
              <td>快递</td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="ncc-receipt-info">
          <div class="ncc-receipt-info-title">
            <h3>支付选择</h3>
          </div>
          <ul class="ncc-payment-list">
            <li payment_code="alipay">
              <label for="pay_alipay">
                <i></i>
                <div for="pay_2" class="logo"> <img src="http://localhost/shopnc/shop/templates/default/images/payment/alipay_logo.gif"> </div>
                <div style="display:none" nc_type="predeposit" class="predeposit">
                </div>
              </label>
            </li>
            <li payment_code="tenpay">
              <label for="pay_tenpay">
                <i></i>
                <div for="pay_3" class="logo"> <img src="http://localhost/shopnc/shop/templates/default/images/payment/tenpay_logo.gif"> </div>
                <div style="display:none" nc_type="predeposit" class="predeposit">
                </div>
              </label>
            </li>
            <li payment_code="chinabank">
              <label for="pay_chinabank">
                <i></i>
                <div for="pay_4" class="logo"> <img src="http://localhost/shopnc/shop/templates/default/images/payment/chinabank_logo.gif"> </div>
                <div style="display:none" nc_type="predeposit" class="predeposit">
                </div>
              </label>
            </li>
          </ul>
        </div>
        <div class="ncc-bottom tc mb50"><a class="ncc-btn ncc-btn-green" id="next_button" href="javascript:void(0);"><i class="icon-shield"></i>确认提交支付</a></div>
      </form>
    </div>
    <script type="text/javascript">
      $(function(){
        $('.ncc-payment-list &gt; li').on('click',function(){
          $('.ncc-payment-list &gt; li').removeClass('using');
          $(this).addClass('using');
          $('#payment_code').val($(this).attr('payment_code'));
        });
        $('#next_button').on('click',function(){
          if ($('#payment_code').val() == '') {
            showDialog('请选择支付方式', 'error','','','','','','','','',2);return false;
          }
          $('#buy_form').submit();
        });
      });
    </script>
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
