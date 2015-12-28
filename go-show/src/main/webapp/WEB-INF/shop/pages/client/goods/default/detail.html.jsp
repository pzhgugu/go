<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="ncs-detail" style="display: block;"><!-- S 商品举报 -->
    <div class="ncs-inform"><span>举报中心<i></i></span>
        <a title="举报该商品" href="#">举报该商品</a>
    </div>
    <!-- End -->

    <!-- S 商品图片 -->
    <div class="ncs-goods-picture image_zoom" id="ncs-goods-picture">
        <div class="gallery_wrap" style="height: 400px; width: 380px; position: relative; overflow: hidden;">
            <div class="gallery levelB"
                 style="position: absolute; overflow: hidden; opacity: 1; height: 360px; width: 360px; left: 10px; top: 20px;">
                <img alt="" src="${S_URL}/att/download/${P_GOODSCOMMON.goodsImage}"
                     style="height: 360px; width: 360px;"></div>
            <div class="gallery gallery_mask"
                 style="position: absolute; overflow: hidden; opacity: 1; display: none; height: 360px; width: 360px; left: 10px; top: 20px;">
                <img alt="" src="${S_URL}/att/download/${P_GOODSCOMMON.goodsImage}"
                     style="height: 360px; width: 360px;"></div>
        </div>
        <div class="controller_wrap">
            <div class="controller">
                <ul>
                    <c:forEach items="${P_GOODS_IMAGES}" var="images" varStatus="status" >
                    <li><a data-index="0" href="javascript:;" <c:if test="${status.index==0}"> class="current"</c:if> >
                        <img width="60" height="60" alt="" src="${S_URL}/att/download/${images.goodsImage}"></a>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <a class="prev" href="javascript:;"><span>«</span></a>
            <a class="next" href="javascript:;"><span>»</span></a>
        </div>
        <div class="close_wrap"><a class="close" href="javascript:;" style="display: none;">×</a></div>
    </div>
    <fis:require id="shop:pages/client/goods/${P_STYLE}/scripts/iZoomPath.js"/>
    <fis:script>

        /** 辅助浏览 **/
        jQuery(function($){
        //产品图片
        $.getScript(iZoomPath, function(){
        var
        zoomController,
        zoomControllerUl,
        zoomControllerUlLeft = 0,
        shell = $('#ncs-goods-picture'),
        shellPanel = shell.parent().hide(),
        heightOffset = 80,
        minGallerySize = [380, shellPanel.height() - heightOffset],
        imageZoom = new ImageZoom({
        shell: shell,
        basePath: '',
        levelASize: [60, 60],
        levelBSize: [360, 360],
        gallerySize: minGallerySize,
        onBeforeZoom: function(index, level){
        if(!zoomController){
        zoomController = shell.find('div.controller');
        }

        var
        self = this,
        duration = 320,
        width = minGallerySize[0],
        height = minGallerySize[1],
        zoomFx = function(){
        self.ops.gallerySize = [width, height];
        self.galleryPanel.stop().animate({width:width, height:height}, duration);
        shellPanel.stop().animate({height:height + heightOffset}, duration);
        zoomController.animate({width:width-22}, duration);
        shell.stop().animate({width:width}, duration);
        };
        if(level !== this.level && this.level !== 0){
        if(this.level === 1 && level > 1){
        height = Math.max(520, shellPanel.height());
        width = shellPanel.width();
        zoomFx();
        }
        else if(level === 1){
        zoomFx();
        }
        }
        },
        onZoom: function(index, level, prevIndex){
        if(index !== prevIndex){
        if(!zoomControllerUl){
        zoomControllerUl = zoomController.find('ul').eq(0);
        }
        var
        width = 76,
        ops = this.ops,
        count = ops.items.length,
        panelVol = ~~((zoomController.width() + 10)/width),
        minLeft = width * (panelVol - count),
        left = Math.min(0, Math.max(minLeft, -width * ~~(index-panelVol/2)));

        if(zoomControllerUlLeft !== left){
        zoomControllerUl.stop().animate({left: left}, 320);
        zoomControllerUlLeft = left;
        }
        }
        shell.find('a.prev,a.next')[level<3 ? 'removeClass' : 'addClass']('hide');
        shell.find('a.close').css('display', [level>1 ? 'block' : 'none']);
        },
        items: [
        <c:forEach items="${P_GOODS_IMAGES}" var="images" varStatus="status" >
        { title : '',
        levelA : '${S_URL}/att/download/${images.goodsImage}',
        levelB : '${S_URL}/att/download/${images.goodsImage}',
        levelC : '${S_URL}/att/download/${images.goodsImage}',
        levelD : '${S_URL}/att/download/${images.goodsImage}'}<c:if test="${!status.last}">,</c:if>
        </c:forEach>
        ]
        });
        shell.data('imageZoom', imageZoom);

        shellPanel.show();
        });

        });

    </fis:script>

    <!-- S 商品基本信息 -->
    <div class="ncs-goods-summary">
        <div class="name">
            <h1>${P_GOODSCOMMON.name}</h1>
            <strong></strong></div>
        <div class="ncs-meta">
            <!-- S 商品参考价格 -->
            <dl>
                <dt>市&nbsp;场&nbsp;价：</dt>
                <dd class="cost-price"><strong>¥${P_GOODSCOMMON.goodsMarketprice}</strong></dd>
            </dl>
            <!-- S 商品发布价格 -->
            <dl>
                <dt>商&nbsp;城&nbsp;价：</dt>
                <dd class="price">
                    <strong>¥${P_GOODSCOMMON.goodsStorePrice}</strong>
                </dd>
            </dl>
            <!-- E 商品发布价格 -->
            <!-- S 限时优惠 -->
            <!-- E 限时优惠  -->
            <!-- S 团购-->
            <!-- E 团购 -->
            <!-- S 描述相符评分及评价数量 -->
            <dl>
                <dt>商品评价：</dt>
                <dd>
                    <div data-score="5" class="raty" title="很满意" style="width: 100px;"><img
                            src="${S_URL}/res/img/star-on.png" alt="1"
                            title="很满意">&nbsp;<img
                            src="${S_URL}/res/img/star-on.png" alt="2"
                            title="很满意">&nbsp;<img
                            src="${S_URL}/res/img/star-on.png" alt="3"
                            title="很满意">&nbsp;<img
                            src="${S_URL}/res/img/star-on.png" alt="4"
                            title="很满意">&nbsp;<img
                            src="${S_URL}/res/img/star-on.png" alt="5"
                            title="很满意"><input type="hidden" name="score" value="5" readonly="readonly"></div>
                    <a href="#ncGoodsRate">(0条评论)</a></dd>
            </dl>
            <!-- E 描述相符评分及评价数量 -->
            <!-- S 物流运费 -->
            <dl class="ncs-freight">
                <dt>
                    <!-- 如果买家承担运费 -->
                    <!-- 如果使用了运费模板 -->
                    至&#12288;全国：
                </dt>
                <dd id="transport_price">
                    卖家承担运费
                </dd>
                <dd id="loading_price" style="color:red;display:none">loading.....</dd>
            </dl>
            <!-- E 物流运费 --->
            <!-- S 累计售出数量 -->
            <dl>
                <dt>累计售出：</dt>
                <dd><strong><a href="#ncGoodsTraded">${P_GOODSCOMMON.goodsSalenum}</a></strong>&nbsp;件</dd>
            </dl>
            <!-- E 累计售出数量 -->

        </div>
        <div class="ncs-key">
            <!-- S 商品规格值-->
            <dl nctype="nc-spec">
                <dt>颜色：</dt>
                <dd>
                    <ul nctyle="ul_sign">
                        <!-- 图片类型规格-->
                        <li class="sp-img"><a title="蓝色" data-param="{valid:222}" class=""
                                              href="javascript:void(0);"><img
                                src="http://localhost/shopnc/data/upload/shop/store/goods/1/1_04418207207476705_60.jpg"><i></i></a>
                        </li>
                        <!-- 图片类型规格-->
                        <li class="sp-img"><a title="绿色" data-param="{valid:224}" class="hovered"
                                              href="javascript:void(0);"><img
                                src="http://localhost/shopnc/data/upload/shop/store/goods/1/1_04418206625430066_60.jpg"><i></i></a>
                        </li>
                        <!-- 图片类型规格-->
                        <li class="sp-img"><a title="梅红" data-param="{valid:225}" class=""
                                              href="javascript:void(0);"><img
                                src="http://localhost/shopnc/data/upload/shop/store/goods/1/1_04418207471410641_60.jpg"><i></i></a>
                        </li>
                        <!-- 图片类型规格-->
                        <li class="sp-img"><a title="黑色" data-param="{valid:226}" class=""
                                              href="javascript:void(0);"><img
                                src="http://localhost/shopnc/data/upload/shop/store/goods/1/1_04418207748921454_60.jpg"><i></i></a>
                        </li>
                    </ul>
                </dd>
            </dl>
            <!-- E 商品规格值-->
            <!-- S 购买数量及库存 -->
            <dl>
                <dt>购买数量：</dt>
                <dd class="ncs-figure-input">
                    <input type="text" class="text w30" maxlength="6" size="3" value="1" id="quantity" name="">
                    <a class="increase" href="javascript:void(0)">+</a><a class="decrease"
                                                                          href="javascript:void(0)">-</a>
                    <em>(当前库存<strong nctype="goods_stock">${P_GOODSCOMMON.goodsStorage}</strong>件)</em></dd>
            </dl>
            <!-- E 购买数量及库存 -->

            <!-- S 购买按钮 -->
            <div class="ncs-btn"><!-- S 提示已选规格及库存不足无法购买 -->
                <div class="ncs-point" nctype="goods_prompt">
                    <span class="yes">已选择 <strong>绿色</strong></span>
                </div>
                <!-- E 提示已选规格及库存不足无法购买 -->
                <!-- 立即购买-->
                <a title="立即购买" class="buynow " nctype="buynow_submit" href="javascript:void(0);">立即购买</a>
                <!-- 加入购物车-->
                <a title="添加购物车" class="addcart " nctype="addcart_submit" href="javascript:void(0);"><i
                        class="icon-shopping-cart"></i>添加购物车</a>

                <!-- S 加入购物车弹出提示框 -->
                <div class="ncs-cart-popup">
                    <dl>
                        <dt>成功添加到购物车<a onclick="$('.ncs-cart-popup').css({'display':'none'});" title="关闭">X</a></dt>
                        <dd>购物车共有 <strong id="bold_num"></strong> 种商品 总金额为：<em class="saleP" id="bold_mly"></em></dd>
                        <dd class="btns"><a onclick="location.href='http://localhost/shopnc/shop/index.php?act=cart'"
                                            class="ncs-btn-mini ncs-btn-green" href="javascript:void(0);">查看购物车</a> <a
                                onclick="$('.ncs-cart-popup').css({'display':'none'});" value="" class="ncs-btn-mini"
                                href="javascript:void(0);">继续购物</a></dd>
                    </dl>
                </div>
                <!-- E 加入购物车弹出提示框 -->

            </div>
            <!-- E 购买按钮 -->
            <div class="ncs_share"><a data-param="{&quot;gid&quot;:&quot;39&quot;}" nc_type="sharegoods"
                                      href="javascript:void(0);"><i class="icon-share"></i>分享<em
                    nc_type="sharecount_39">0</em></a><a href="javascript:collect_goods('39','count','goods_collect');"><i
                    class="icon-star-empty"></i>收藏商品<em nctype="goods_collect">0</em></a></div>
        </div>
        <!--E 商品信息 -->

    </div>
    <!-- E 商品图片及收藏分享 -->
    <!--S 店铺信息-->
    <div style=" position: absolute; z-index: 1; top: 60px; right: 0;" class="ncg-info">
        <!--店铺基本信息-->
        <fis:block url="shop:pages/client/storehome/storeinfo.html.jsp"></fis:block>
    </div>
    <!--E 店铺信息 -->
</div>
