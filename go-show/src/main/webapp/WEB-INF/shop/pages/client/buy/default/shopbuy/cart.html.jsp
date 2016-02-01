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
    <title>购物车</title>
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
    <fis:require id="shop:scripts/goods_cart.js"/>

    <fis:scripts/>

    <script type="text/javascript">
      SITEURL='${S_URL}';

    </script>
    <fis:out id="shop:scripts/dialog/dialog.js" iid="dialog_js"/>
  </head>

  <body>
  <div id="append_parent"></div>
  <div id="ajaxwaitid"></div>
  <fis:block url="shop:pages/client/top.html.jsp"></fis:block>

  <fis:block url="shop:pages/client/buy/${P_STYLE}/shopbuy/buyflowimage.html.jsp"></fis:block>

  <c:if test="${empty P_CART}">
    <div class="ncc-wrapper">


      <div class="ncc-null-shopping"><i class="ico"></i>
        <h4>您的购物车还没有商品</h4>
        <p><a class="ncc-btn-mini mr10" href="#"><i class="icon-reply-all"></i>马上去购物</a> <a class="ncc-btn-mini" href="#"><i class="icon-file-text"></i>查看自己的订单</a></p>
      </div>

    </div>
  </c:if>
  <c:if test="${!empty P_CART}">
  <div class="ncc-wrapper">

    <style>
      .ncc-table-style tbody tr.item_disabled td {
        background: none repeat scroll 0 0 #F9F9F9;
        height: 30px;
        padding: 10px 0;
        text-align: center;
      }
    </style>
    <div class="ncc-main">
      <div class="ncc-title">
        <h3>我的购物车</h3>
        <h5>查看购物车商品清单，增加减少商品数量，并勾选想要的商品进入下一步操作。</h5>
      </div>
      <form name="form_buy" id="form_buy" method="POST" action="${S_URL}/shop/buy">
        <input type="hidden" name="ifcart" value="1">
        <table nc_type="table_cart" class="ncc-table-style">
          <thead>
          <tr>
            <th class="w50"><label>
              <input type="checkbox" id="selectAll" value="1" checked="">
              全选</label></th>
            <th></th>
            <th>商品</th>
            <th class="w120">单价(元)</th>
            <th class="w120">数量</th>
            <th class="w120">小计</th>
            <th class="w80">操作</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${P_CART}" var="cartMap">
          <tr>
            <th colspan="20"><i class="icon-home"></i><a href="${S_URL}/cl/store/home?store_id=${cartMap.key}" target="_blank">${cartMap.value[0].storeName}</a> <span member_id="1"></span>
            </th>
          </tr>

            <c:forEach items="${cartMap.value}" var="cart">
          <!-- S one store list -->
          <tr class="shop-list " nc_group="${cart.goodsId}" id="cart_item_${cart.goodsId}">
            <td><input type="checkbox" name="cart_id" id="cart_id${cart.goodsId}" value="${cart.goodsId}|${cart.goodsNum}" nc_type="eachGoodsCheckBox" checked=""></td>
            <td class="w60"><a class="ncc-goods-thumb" target="_blank" href="${S_URL}/cl/goods/show?goods_id=${cart.goodsId}"><img alt="${cart.goodsName}" src="${S_URL}/att/download/${cart.goodsImage}"></a></td>
            <td class="tl"><dl class="ncc-goods-info">
              <dt><a target="_blank" href="${S_URL}/cl/goods/show?goods_id=${cart.goodsId}">${cart.goodsName}</a></dt>
              <dd>
              </dd>
            </dl></td>
            <td class="w120">￥<em id="item${cart.goodsId}_price">${cart.goodsPrice}</em></td>
            <td class="w120 ws0"><a class="add-substract-key tip" title="减少商品件数" onclick="decrease_quantity('${cart.goodsId}');" href="JavaScript:void(0);">-</a>
              <input type="text" class="text w20" onkeyup="change_quantity('${cart.goodsId}', this);" changed="1" orig="1" value="${cart.goodsNum}" id="input_item_${cart.goodsId}">
              <a class="add-substract-key tip" title="增加商品件数" onclick="add_quantity('${cart.goodsId}');" href="JavaScript:void(0);">+</a></td>
            <td class="w120">           ￥<em nc_type="eachGoodsTotal" id="item${cart.goodsId}_subtotal">${cart.goodsPrice*cart.goodsNum}</em>
            </td>
            <td class="w80">            <a onclick="collect_goods('232');" href="javascript:void(0)">收藏</a><br>
              <a onclick="drop_cart_item('${cart.goodsId}');" href="javascript:void(0)">删除</a></td>
          </tr>
            </c:forEach>

          <!-- S bundling goods list -->
          </c:forEach>

          <tr>
            <td colspan="20" class="tr"><div class="ncc-store-account">
              <dl>
                <dt>店铺合计：</dt>
                <dd>￥<em nc_type="eachStoreTotal"></em></dd>
              </dl>
            </div></td>
          </tr>
          </tbody>
          <tfoot>
          <tr>
            <td colspan="20"><div class="ncc-all-account">商品总价（不含运费）￥<em id="cartTotal"></em>元</div></td>
          </tr>
          </tfoot>
        </table>
      </form>
      <div class="ncc-bottom"><a class="ncc-btn ncc-btn-acidblue fr" href="javascript:void(0)" id="next_submit"><i class="icon-pencil"></i>下一步，填写核对购物信息</a></div>
    </div>

  </div>
  </c:if>

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
