<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<div class="ncs-goods-layout expanded">


    <div id="main-nav-holder" class="ncs-goods-main">
        <nav id="main-nav" class="tabbar pngFix">
            <div class="ncs-goods-title-nav">
                <ul id="categorymenu">
                    <li class="current"><a href="#content" id="tabGoodsIntro">商品详情</a></li>
                    <li><a href="#content" id="tabGoodsRate">商品评价</a></li>
                    <li><a href="#content" id="tabGoodsTraded">销售记录</a></li>
                    <li><a href="#content" id="tabGuestbook">购买咨询</a></li>
                </ul>
                <div class="switch-bar"><a id="fold" href="javascript:void(0)">&nbsp;</a></div>
            </div>
        </nav>


        <div class="ncs-intro">
            <div id="ncGoodsIntro" class="content bd">

                <!--S 满就送 -->
                <!--E 满就送 -->
                <ul class="nc-goods-sort">
                    <li>商家货号：${P_GOODS.goodsSerial}</li>
                    <li>品牌：${P_GOODS_BRAND.brandName}</li>
                    <c:forEach items="${P_GOODSSPEC_SELECT}" var="goodsSpec" varStatus="status">
                        <li>${goodsSpec.spName}：${P_GOODS_SPECVALUE.specName[status.index]}</li>
                    </c:forEach>
                </ul>
                <div class="ncs-goods-info-content">
                    <div class="default">
                        ${P_GOODSCOMMON.goodsBody}
                    </div>


                </div>
            </div>
        </div>
        <div class="ncs-comment">
            <div class="ncs-goods-title-bar hd">
                <h4><a href="javascript:void(0);">商品评价</a></h4>
            </div>

            <div id="ncGoodsRate" class="ncs-goods-info-content bd">
                <div class="top">
                    <div class="rate">
                        <p><strong>100</strong><sub>%</sub>好评</p>
                        <span>共有0人参与评分</span></div>
                    <div class="percent">
                        <dl>
                            <dt>好评<em>(100%)</em></dt>
                            <dd><i style="width: 100%"></i></dd>
                        </dl>
                        <dl>
                            <dt>中评<em>(0%)</em></dt>
                            <dd><i style="width: 0%"></i></dd>
                        </dl>
                        <dl>
                            <dt>差评<em>(0%)</em></dt>
                            <dd><i style="width: 0%"></i></dd>
                        </dl>
                    </div>
                    <div class="btns"><span>您可对已购商品进行评价</span>

                        <p><a target="_blank" class="ncs-btn ncs-btn-red"
                              href="#"><i
                                class="icon-comment-alt"></i>评价商品</a></p>
                    </div>
                </div>
                <div class="ncs-goods-title-nav">
                    <ul id="comment_tab">
                        <li class="current" data-type="all"><a href="javascript:void(0);">商品评价(0)</a></li>
                        <li data-type="1"><a href="javascript:void(0);">好评(0)</a></li>
                        <li data-type="2"><a href="javascript:void(0);">中评(0)</a></li>
                        <li data-type="3"><a href="javascript:void(0);">差评(0)</a></li>
                    </ul>
                </div>
                <!-- 商品评价内容部分 -->
                <div class="ncs-commend-main" id="goodseval">
                    <div class="ncs-norecord">暂无符合条件的数据记录</div>

                </div>
            </div>
        </div>
        <div class="ncg-salelog">
            <div class="ncs-goods-title-bar hd">
                <h4><a href="javascript:void(0);">销售记录</a></h4>
            </div>
            <div id="ncGoodsTraded" class="ncs-goods-info-content bd">
                <div class="top">
                    <div class="price">
                        商&nbsp;城&nbsp;价<strong>179.00</strong>元<span>购买的价格不同可能是由于店铺往期促销活动引起的，详情可以咨询卖家</span></div>
                </div>
                <!-- 成交记录内容部分 -->
                <div class="ncs-loading" id="salelog_demo">

                    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="mt10">
                        <thead>
                        <tr>
                            <th class="w200">买家</th>
                            <th class="w100">购买价</th>
                            <th class="">购买数量</th>
                            <th class="w200">购买时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td class="ncs-norecord" colspan="10">暂无符合条件的数据记录</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="ncs-consult">
            <div class="ncs-goods-title-bar hd">
                <h4><a href="javascript:void(0);">购买咨询</a></h4>
            </div>
            <div id="ncGuestbook" class="ncs-goods-info-content bd">
                <!-- 咨询留言内容部分 -->
                <div id="cosulting_demo" class="ncs-loading"> </div>
            </div>
        </div>
        <div class="ncs-recommend">
            <div class="title">
                <h4>推荐商品</h4>
            </div>
            <div class="content">
                <ul>
                    <c:forEach items="${P_GOODS_COMMON}" var="goodsCommon">
                        <li>
                            <dl>
                                <dt class="goods-name"><a title="全国唯一支持开瓶试饮7天无理由退换货" target="_blank"
                                                          href="${S_URL}/cl/goods/show?common_id=${goodsCommon.id}">${goodsCommon.name}</a>
                                </dt>
                                <dd class="goods-pic"><a title="全国唯一支持开瓶试饮7天无理由退换货" target="_blank"
                                                         href="${S_URL}/cl/goods/show?common_id=${goodsCommon.id}"><img
                                        alt="${goodsCommon.name}" src="${S_URL}/att/download/${goodsCommon.goodsImage}"></a>
                                </dd>
                                <dd class="goods-price">¥${goodsCommon.goodsStorePrice}</dd>
                            </dl>
                        </li>
                    </c:forEach>
                </ul>
                <div class="clear"></div>
            </div>
        </div>
    </div>


    <div class="ncs-sidebar">
        <fis:block url="shop:pages/client/buy/${P_STYLE}/goodsqrcode.html.jsp"></fis:block>
        <fis:block url="shop:pages/client/buy/${P_STYLE}/message.html.jsp"></fis:block>
        <fis:block url="shop:pages/client/buy/${P_STYLE}/goodclass.html.jsp"></fis:block>
        <fis:block url="shop:pages/client/buy/${P_STYLE}/among.html.jsp"></fis:block>
    </div>
</div>

<fis:script>
$(function () {
    $("#cosulting_demo").load('${S_URL}/cl/goodsconsult/cosulting?style=${P_STYLE}&goodsId=${P_GOODS.id}&storeId=${P_STORE.id}');
});
</fis:script>