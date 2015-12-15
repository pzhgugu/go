<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>
<header class="ncsc-head-layout w">
    <div class="wrapper">
        <div class="ncsc-admin">
            <dl class="ncsc-admin-info">
                <dt class="admin-avatar">
                    <img width="32" alt="" class="pngFix" src="${S_URL}/res/img/default_user_portrait.gif"></dt>
                <dd class="admin-permission">当前用户</dd>
                <dd class="admin-name">shopnc_seller</dd>
            </dl>
            <div class="ncsc-admin-function">
                <a title="前往店铺" target="_blank" href="#"><i class="icon-home"></i></a>
                <a target="_blank" class="pr" title="站内消息" href="#">
                    <i class="icon-envelope-alt"></i><em>0</em></a>
                <a target="_blank" title="修改密码" href="#">
                    <i class="icon-wrench"></i></a>
                <a title="安全退出" href="#">
                    <i class="icon-signout"></i></a></div>
        </div>
        <div class="center-logo">
            <a target="_blank" href="http://localhost/shopnc/shop"><img alt="" class="pngFix"
                                                                        src="${S_URL}/res/img/seller_center_logo.png"></a>

            <h1>商家中心</h1>
        </div>
        <div class="index-search-container">
            <div class="index-sitemap"><a href="javascript:void(0);">导航管理 <i class="icon-angle-down"></i></a>

                <div class="sitemap-menu-arrow"></div>
                <div class="sitemap-menu">
                    <div class="title-bar">
                        <h2>
                            <i class="icon-sitemap"></i>管理导航<em>小提示：添加您经常使用的功能到首页侧边栏，方便操作。</em>
                        </h2>
                        <span class="close" id="closeSitemap">X</span></div>
                    <div class="content" id="quicklink_list">
                        <dl>
                            <dt>商品</dt>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_goods_add" nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_goods_add&amp;op=index"> 商品发布 </a></dd>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_goods_online"
                                                    nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_goods_online&amp;op=index"> 出售中的商品 </a></dd>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_goods_offline"
                                                    nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_goods_offline&amp;op=index"> 仓库中的商品 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_storage_alarm"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_storage_alarm&amp;op=index"> 库存警报 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_plate"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_plate&amp;op=index">
                                关联板式 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_spec"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_spec&amp;op=index">
                                商品规格 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_album"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_album&amp;op=album_cate">
                                图片空间 </a></dd>
                        </dl>
                        <dl>
                            <dt>订单</dt>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_order" nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_order&amp;op=index"> 订单管理 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_deliver"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_deliver&amp;op=index">
                                发货 </a></dd>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_deliver_set"
                                                    nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_deliver_set&amp;op=daddress_list"> 发货设置 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_evaluate"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_evaluate&amp;op=list">
                                评价管理 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_printsetup"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_printsetup&amp;op=index">
                                打印设置 </a></dd>
                        </dl>
                        <dl>
                            <dt>促销</dt>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_groupbuy"
                                                    nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_groupbuy&amp;op=index"> 团购管理 </a></dd>
                            <dd class="selected"><i title="添加为常用功能菜单" class="icon-check"
                                                    data-quicklink-act="store_promotion_xianshi"
                                                    nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_promotion_xianshi&amp;op=xianshi_list"> 限时折扣 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_promotion_mansong"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_promotion_mansong&amp;op=mansong_list"> 满即送 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_promotion_bundling"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_promotion_bundling&amp;op=bundling_list"> 优惠套装 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_promotion_booth"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_promotion_booth&amp;op=booth_goods_list"> 推荐展位 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_voucher"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_voucher&amp;op=templatelist"> 代金券管理 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_activity"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_activity&amp;op=store_activity"> 活动管理 </a></dd>
                        </dl>
                        <dl>
                            <dt>店铺</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_setting"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_setting&amp;op=store_setting"> 店铺设置 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_navigation"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_navigation&amp;op=navigation_list"> 店铺导航 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_sns"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_sns&amp;op=index">
                                店铺动态 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_info"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_info&amp;op=store_info">
                                店铺信息 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_goods_class"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_goods_class&amp;op=index"> 店铺分类 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_brand"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_brand&amp;op=brand_list">
                                品牌申请 </a></dd>
                        </dl>
                        <dl>
                            <dt>物流</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_transport"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_transport&amp;op=index">
                                物流工具 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_free_freight"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_free_freight&amp;op=index"> 免运费额度 </a></dd>
                        </dl>
                        <dl>
                            <dt>客服</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_callcenter"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_callcenter&amp;op=index">
                                客服设置 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_consult"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_consult&amp;op=consult_list"> 咨询管理 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_complain"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_complain&amp;op=list">
                                投诉管理 </a></dd>
                        </dl>
                        <dl>
                            <dt>售后</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_refund"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_refund&amp;op=index">
                                退款记录 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_return"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_return&amp;op=index">
                                退货记录 </a></dd>
                        </dl>
                        <dl>
                            <dt>结算</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_bill"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_bill&amp;op=index">
                                结算管理 </a></dd>
                        </dl>
                        <dl>
                            <dt>统计</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="statistics_flow"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=statistics_flow&amp;op=flow_statistics"> 流量统计 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="statistics_sale"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=statistics_sale&amp;op=sale_statistics"> 销量统计 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="statistics_probability"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=statistics_probability&amp;op=probability_statistics">
                                购买率统计 </a></dd>
                        </dl>
                        <dl>
                            <dt>帐号</dt>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_account"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_account&amp;op=account_list"> 帐号列表 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_account_group"
                                   nctype="btn_add_quicklink"></i><a
                                    href="index.php?act=store_account_group&amp;op=group_list"> 帐号组 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="seller_log"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=seller_log&amp;op=log_list">
                                帐号日志 </a></dd>
                            <dd><i title="添加为常用功能菜单" class="icon-check" data-quicklink-act="store_cost"
                                   nctype="btn_add_quicklink"></i><a href="index.php?act=store_cost&amp;op=cost_list">
                                店铺消费 </a></dd>
                        </dl>
                    </div>
                </div>
            </div>
            <div class="search-bar">
                <form target="_blank" method="get">
                    <input type="hidden" value="search" name="act">
                    <input type="text" class="search-input-text" placeholder="商城商品搜索" name="keyword"
                           nctype="search_text">
                    <input type="submit" value="" class="search-input-btn pngFix" nctype="search_submit">
                </form>
            </div>
        </div>
        <nav class="ncsc-nav">
            <dl <c:if test="${P_CURRENT_TOP=='home'}"> class="current" </c:if>>
                <dt><a href="${S_URL}/se/">首页</a></dt>
                <dd class="arrow"></dd>
            </dl>
            <dl  <c:if test="${P_CURRENT_TOP=='goods'}"> class="current" </c:if>>
                <dt><a href="${S_URL}/se/goods/addstep/one">商品</a></dt>
                <dd>
                    <ul>
                        <li><a href="${S_URL}/se/goods/addstep/one"> 商品发布 </a></li>
                        <li><a href="${S_URL}/se/goodsonline/list"> 出售中的商品 </a></li>
                        <li><a href="${S_URL}/se/goodsonline/list?goodsState=0"> 仓库中的商品 </a></li>
                        <li><a href="${S_URL}/se/warning/edit"> 库存警报 </a></li>
                        <li><a href="${S_URL}/se/plate/list"> 关联板式 </a></li>
                        <li><a href="${S_URL}/se/spec/list"> 商品规格 </a></li>
                        <li><a href="${S_URL}/se/album/cate"> 图片空间 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="${S_URL}/se/order/orderQuery"> 订单 </a></dt>
                <dd>
                    <ul>
                        <%--<li> <a href="index.php?act=store_order&amp;op=index"> 订单管理 </a> </li>--%>
                        <%--<li> <a href="index.php?act=store_deliver&amp;op=index"> 发货 </a> </li>--%>
                        <%--<li> <a href="index.php?act=store_deliver_set&amp;op=daddress_list"> 发货设置 </a> </li>--%>
                        <%--<li> <a href="index.php?act=store_evaluate&amp;op=list"> 评价管理 </a> </li>--%>
                        <%--<li> <a href="index.php?act=store_printsetup&amp;op=index"> 打印设置 </a> </li>--%>
                        <li><a href="${S_URL}/se/order/orderQuery"> 订单管理 </a></li>
                        <li><a href="${S_URL}/se/deliver/deliverQuery"> 发货 </a></li>
                        <li><a href="${S_URL}/se/deliver/deliverSetQuery"> 发货设置 </a></li>
                        <li><a href="${S_URL}/se/evaluate/evaluateQuery"> 评价管理 </a></li>
                        <li><a href="${S_URL}/se/printsetup/printsetupQuery"> 打印设置 </a></li>


                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=store_groupbuy&amp;op=index">促销</a></dt>
                <dd>
                    <ul>
                        <li><a href="index.php?act=store_groupbuy&amp;op=index"> 团购管理 </a></li>
                        <li><a href="index.php?act=store_promotion_xianshi&amp;op=xianshi_list"> 限时折扣 </a></li>
                        <li><a href="index.php?act=store_promotion_mansong&amp;op=mansong_list"> 满即送 </a></li>
                        <li><a href="index.php?act=store_promotion_bundling&amp;op=bundling_list"> 优惠套装 </a></li>
                        <li><a href="index.php?act=store_promotion_booth&amp;op=booth_goods_list"> 推荐展位 </a></li>
                        <li><a href="index.php?act=store_voucher&amp;op=templatelist"> 代金券管理 </a></li>
                        <li><a href="index.php?act=store_activity&amp;op=store_activity"> 活动管理 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl  <c:if test="${P_CURRENT_TOP=='store'}"> class="current" </c:if>>
                <dt><a href="${S_URL}/se/store/set/list">店铺</a></dt>
                <dd>
                    <ul>
                        <li><a href="${S_URL}/se/store/set/list"> 店铺设置 </a></li>
                        <li><a href="${S_URL}/se/storenav/list"> 店铺导航 </a></li>
                        <li><a href="${S_URL}/se/store/info"> 店铺信息 </a></li>
                        <li><a href="${S_URL}/se/storegoodsclass/list"> 店铺分类 </a></li>
                        <li><a href="${S_URL}/se/brandApply/list"> 品牌申请 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="${S_URL}/se/lgsTools/lgsToolsQuery">物流</a></dt>
                <dd>
                    <ul>
                        <li><a href="${S_URL}/se/lgsTools/lgsToolsQuery"> 物流工具 </a></li>
                        <li><a href="${S_URL}/se/lgsTools/feeSet"> 免运费额度 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=store_callcenter&amp;op=index">客服</a></dt>
                <dd>
                    <ul>
                        <li><a href="index.php?act=store_callcenter&amp;op=index"> 客服设置 </a></li>
                        <li><a href="index.php?act=store_consult&amp;op=consult_list"> 咨询管理 </a></li>
                        <li><a href="index.php?act=store_complain&amp;op=list"> 投诉管理 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=store_refund&amp;op=index">售后</a></dt>
                <dd>
                    <ul>
                        <li><a href="${S_URL}/se/refund/refundQuery"> 退款记录 </a></li>
                        <li><a href="${S_URL}/se/refund/returnQuery"> 退货记录 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=store_bill&amp;op=index">结算</a></dt>
                <dd>
                    <ul>
                        <li><a href="index.php?act=store_bill&amp;op=index"> 结算管理 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=statistics_flow&amp;op=flow_statistics">统计</a></dt>
                <dd>
                    <ul>
                        <li><a href="index.php?act=statistics_flow&amp;op=flow_statistics"> 流量统计 </a></li>
                        <li><a href="index.php?act=statistics_sale&amp;op=sale_statistics"> 销量统计 </a></li>
                        <li><a href="index.php?act=statistics_probability&amp;op=probability_statistics"> 购买率统计 </a>
                        </li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
            <dl class="">
                <dt><a href="index.php?act=store_account&amp;op=account_list">帐号</a></dt>
                <dd>
                    <ul>
                        <li><a href="index.php?act=store_account&amp;op=account_list"> 帐号列表 </a></li>
                        <li><a href="index.php?act=store_account_group&amp;op=group_list"> 帐号组 </a></li>
                        <li><a href="index.php?act=seller_log&amp;op=log_list"> 帐号日志 </a></li>
                        <li><a href="index.php?act=store_cost&amp;op=cost_list"> 店铺消费 </a></li>
                    </ul>
                </dd>
                <dd class="arrow"></dd>
            </dl>
        </nav>
    </div>
</header>

<div id="tbox"><i style="display: block;" title="" class="icon-chevron-up" id="gotop"></i></div>

