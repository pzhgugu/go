<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<style>
        .ncc-table-style tbody tr.item_disabled td {
                background: none repeat scroll 0 0 #F9F9F9;
                height: 30px;
                padding: 10px 0;
                text-align: center;
        }
</style>
<div class="ncc-receipt-info">
        <div class="ncc-receipt-info-title">
                <h3>商品清单</h3>
        </div>

        <table class="ncc-table-style">
                <thead>
                <tr>
                        <th class="w20"></th>
                        <th></th>
                        <th>商品</th>
                        <th class="w120">单价(元)</th>
                        <th class="w120">数量</th>
                        <th class="w120">小计</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${P_BUYGOODS_STORE}" var="storeBuyGoods" varStatus="statusStore">
                        <c:set var="amount" value="0" />
                        <c:set var="store" value="${storeBuyGoods.value[0].store}"></c:set>
                <tr>
                   <th colspan="20">
                        <i class="icon-home"></i>
                        <a href="${S_URL}/cl/store/home?store_id=${store.id}" target="_blank">${store.name}</a>
                        <div class="store-sale">&emsp;</div>
                       <input type="hidden" name="storeList[${statusStore.index}].storeId" value="${store.id}">
                       <input type="hidden" name="storeList[${statusStore.index}].storeName" value="${store.name}">
                   </th>
                </tr>
                        <c:set var="price" value="0" />
               <c:forEach items="${storeBuyGoods.value}" var="buyGoods" varStatus="statusGoods">
                <tr class="shop-list " id="cart_item_${buyGoods.goods.id}">
                        <td>
                            <input type="hidden" name="storeList[${statusStore.index}].cartList[${statusGoods.index}].number" value="${buyGoods.number}">
                            <input type="hidden" name="storeList[${statusStore.index}].cartList[${statusGoods.index}].goodsId" value="${buyGoods.goods.id}">
                        </td>
                        <td class="w60">
                                <a class="ncc-goods-thumb" target="_blank" href="${S_URL}/cl/goods/show?goods_id=${buyGoods.goods.id}"><img alt="${buyGoods.goods.name}" src="${S_URL}/att/download/${buyGoods.goods.goodsImage}"></a></td>
                        <td class="tl"><dl class="ncc-goods-info">
                                <dt><a target="_blank" href="${S_URL}/cl/goods/show?goods_id=${buyGoods.goods.id}">${buyGoods.goods.name}</a></dt>
                                <dd>
                                </dd>
                        </dl></td>
                        <td class="w120">￥<em>${buyGoods.goods.goodsStorePrice}</em></td>
                        <td class="w60">${buyGoods.number}</td><c:set var="price" value="${price+buyGoods.goods.goodsStorePrice*buyGoods.number}" />
                        <td class="w120">          ￥<em nc_type="eachGoodsTotal" id="item231_subtotal">${buyGoods.goods.goodsStorePrice*buyGoods.number}</em>
                        </td>
                </tr>
               </c:forEach>

                <tr>
                        <td class="w10"></td>
                        <td colspan="2" class="tl">买家留言：
                                <input type="text" maxlength="150" name="storeList[${statusStore.index}].message" class="text w340" value="">
                                &nbsp;</td>
                        <td colspan="10" class="tl"><div class="ncc-form-default"> </div></td>
                </tr>
                <tr>
                        <td colspan="20" class="tr"><div class="ncc-store-account">
                                <dl class="freight">
                                        <dt>运费：</dt>
                                        <c:set var="freight" value="8.00" />
                                        <dd>￥<em id="eachStoreFreight_1">${freight}</em></dd>
                                </dl>
                                <dl>
                                        <dt>商品金额：</dt>
                                        <dd>￥<em id="eachStoreGoodsTotal_1">${price}</em></dd>
                                </dl>
                            <input type="hidden" name="storeList[${statusStore.index}].freight" value="${freight}">
                            <input type="hidden" name="storeList[${statusStore.index}].storeGoodsPrice" value="${price}">
                            <input type="hidden" name="storeList[${statusStore.index}].storePrice" value="${freight+price}">
                                <!-- S voucher list -->


                                <!-- E voucher list -->

                                <dl class="total">
                                        <dt>本店合计：</dt>
                                        <dd>￥<em nc_type="eachStoreTotal" store_id="1">${freight+price}</em></dd>
                                </dl>
                        </div></td>
                </tr>
                        <c:set var="amount" value="${amount+(freight+price)}" />
                </c:forEach>
                <!-- S 预存款 -->
                <!-- E 预存款 -->

                </tbody>
                <tfoot>
                <tr>
                    <input type="hidden" name="amount" value="${amount}">
                    <td colspan="20"><div class="ncc-all-account">订单总金额：￥<em id="orderTotal">${amount}</em>元</div></td>
                </tr>
                </tfoot>
        </table>
</div>