<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>

        <div id="mainContent" class="main-content">

            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="normal"><a href="${S_URL}/se/goodsonline/list?goodsState=0">仓库中的商品</a></li>
                    <li class="active"><a href="${S_URL}/se/goodsonline/outline">违规的商品</a></li>
                    <li class="normal"><a href="${S_URL}/se/goodsonline/verify">等待审核的商品</a></li>
                </ul>
            </div>

            <!--搜索开始 -->
            <form action="#" method="get">
                <table class="search-form">
                    <input type="hidden" value="${param.goodsState}" name="goodsState">
                    <tbody>
                    <tr>
                        <td>
                            &nbsp;
                        </td>
                        <th>
                            本店分类
                        </th>
                        <td class="w160">
                            <select class="w150" name="stc_id">
                                <option value="">请选择...</option>
                                <c:forEach items="${P_STOREGOODSCLASS_PARENT_LIST}" var="storeGoodsClass">
                                    <c:if test="${storeGoodsClass.stcState=='1'}">
                                        <option <c:if test="${param.stc_id==storeGoodsClass.id}"> selected="selected"</c:if> value="${storeGoodsClass.id}">${storeGoodsClass.name}</option>
                                        <c:forEach items="${storeGoodsClass.children}" var="children">
                                            <c:if test="${children.stcState=='1'}">
                                                <option <c:if test="${param.stc_id==children.id}"> selected="selected"</c:if>  value="${children.id}" >&nbsp;&nbsp;${children.name}</option>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                        <th>
                            <select name="search_type">
                                <option <c:if test="${empty param.search_type}"> selected="selected"</c:if> <c:if test="${param.search_type=='name'}"> selected="selected"</c:if> value="name">
                                    商品名称
                                </option>
                                <option <c:if test="${param.search_type=='goodsSerial'}"> selected="selected"</c:if> value="goodsSerial">
                                    商家货号
                                </option>
                                <option <c:if test="${param.search_type=='id'}"> selected="selected"</c:if>  value="id">
                                    平台货号
                                </option>
                            </select>
                        </th>
                        <td class="w160">
                            <input type="text" value="${param.keyword}" name="keyword" class="text w150">
                        </td>
                        <td class="tc w70">
                            <label class="submit-border">
                                <input type="submit" value="搜索" class="submit">
                            </label>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <!--搜索结束 -->


            <!--表格开始 -->
            <table class="ncsc-table-style">
                <thead>
                <tr nc_type="table_header">
                    <th class="w30">
                        &nbsp;
                    </th>
                    <th class="w50">
                        &nbsp;
                    </th>
                    <th inputwidth="230px" checker="check_required" column="goods_name" coltype="editable">
                        商品名称
                    </th>
                    <th class="w180">
                        违规禁售原因
                    </th>
                    <th class="w100">
                        价格
                    </th>
                    <th class="w100">
                        库存
                    </th>
                    <th class="w100">
                        操作
                    </th>
                </tr>
<c:if test="${fn:length(P_GOODS_LIST)>0}">
                <tr>
                    <td class="tc">
                        <input type="checkbox" class="checkall" id="all">
                    </td>
                    <td colspan="20">
                        <label for="all">
                            全选
                        </label>
                        <a confirm="您确定要删除吗?" name="commonid"
                           uri="se/goodsonline/del?goodsState=0"
                           nc_type="batchbutton" class="ncsc-btn-mini" href="javascript:void(0);">
                            <i class="icon-trash">
                            </i>
                            删除
                        </a>
                    </td>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${P_GOODS_LIST}" var="goods">
                    <tr>
                        <th class="tc">
                            <input type="checkbox" value="${goods.id}" class="checkitem tc">
                        </th>
                        <th colspan="20">
                            平台货号：${goods.id}
                        </th>
                    </tr>
                    <tr>
                        <td class="trigger">
                            <i title="点击展开查看此商品全部规格；规格值过多时请横向拖动区域内的滚动条进行浏览。" data-comminid="${goods.id}"
                               nctype="ajaxGoodsList" class="tip icon-plus-sign">
                            </i>
                        </td>
                        <td>
                            <div class="pic-thumb">
                                <a target="_blank" href="#">
                                    <img src="${S_URL}/att/download/${goods.goodsImage}">
                                </a>
                            </div>
                        </td>
                        <td class="tl">
                            <dl class="goods-name">
                                <dt>
                                    <c:if test="${goods.goodsCommend==1}">
                                        <span>荐</span>
                                    </c:if>
                                    <a target="_blank" href="#">
                                            ${goods.name}
                                    </a>
                                </dt>
                                <dd>
                                        ${goods.gcName}
                                </dd>
                                <dd>
                                    商家货号：
                                </dd>
                            </dl>
                        </td>

       <td>
               ${goods.goodsStateremark}
       </td>
       <td>
        <span>
          ¥ ${goods.goodsStorePrice}
        </span>
        </td>
                        <td>
        <span <c:if test="${goods.goodsStorage<P_STOREWARNING_VALUE}"> style="color:red;"</c:if>>
      ${goods.goodsStorage}件
        </span>
                        </td>
                        <td class="nscs-table-handle">
        <span>
          <a class="btn-blue" href="${S_URL}/se/goods/editgoods?commonid=${goods.id}">
              <i class="icon-edit">
              </i>

              <p>
                  编辑
              </p>
          </a>
        </span>
        <span>
          <a class="btn-red" onclick="ajax_get_confirm('您确定要删除吗?', 'se/goodsonline/del?goodsState=0&commonid=${goods.id}');"
             href="javascript:void(0);">
              <i class="icon-trash">
              </i>

              <p>
                  删除
              </p>
          </a>
        </span>
                        </td>
                    </tr>
                    <tr style="display:none;">
                        <td colspan="20">
                            <div class="ncsc-goods-sku ps-container">
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th class="tc">
                        <input type="checkbox" class="checkall" id="all2">
                    </th>
                    <th colspan="10">
                        <label for="all2">
                            全选
                        </label>
                        <a class="ncsc-btn-mini" confirm="您确定要删除吗?" name="commonid"
                           uri="se/goodsonline/del?goodsState=0"
                           nc_type="batchbutton" href="javascript:void(0);">
                            <i class="icon-trash">
                            </i>
                            删除
                        </a>
                    </th>
                </tr>

                <tr>
                    <td colspan="20">
                        <fis:block url="shop:widget/tpl/pagination.html.jsp" >
                            <fis:param name="page" value="P_PAGE_SHOW"/>
                            <fis:param name="paginationSize" value="9"/>
                            <fis:param name="reqName" value="curpage"/>
                            <fis:param name="url" value="${S_URL}/se/goodsonline/outline"/>
                        </fis:block>
                    </td>
                </tr>
                </tfoot>

                </c:if>

            </table>
            <!--表格结束 -->

            <c:if test="${fn:length(P_GOODS_LIST)==0}">
                <div class="warning-option">
                    <i class="icon-warning-sign">
                        &nbsp;
                    </i>
        <span>
          暂无符合条件的数据记录
        </span>
                </div>
            </c:if>
        </div>


    </div>
</div>

<fis:require id="shop:scripts/store_goods_list.js"/>
<fis:script>

    $(function(){

    $('a[nctype="batch"]').click(function(){
    if($('.checkitem:checked').length == 0){ //没有选择
    return false;
    }
    var _items = '';
    $('.checkitem:checked').each(function(){
    _items += $(this).val() + ',';
    });
    _items = _items.substr(0, (_items.length - 1));

    var data_str = '';
    eval('data_str = ' + $(this).attr('data-param'));

    });
    });
</fis:script>