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
    <fis:require id="shop:pages/client/buy/${P_STYLE}/scripts/iZoomPath.js"/>
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
                <dd class="cost-price"><strong>¥${P_GOODS.goodsMarketprice}</strong></dd>
            </dl>
            <!-- S 商品发布价格 -->
            <dl>
                <dt>商&nbsp;城&nbsp;价：</dt>
                <dd class="price">
                    <strong>¥${P_GOODS.goodsStorePrice}</strong>
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
            <c:forEach items="${P_GOODSSPEC_SELECT}"  var="goodsSpecValue" varStatus="status">
                <dl nctype="nc-spec">
                    <dt>${goodsSpecValue.spName}：</dt>
                    <dd>
                        <ul nctyle="ul_sign">
                            <c:if test="${goodsSpecValue.spName=='颜色'}" >
                                <c:forEach items="${goodsSpecValue.spvId}" var="spec">
                                    <c:forEach items="${P_GOODS_DEFAULTIMAGES}" var="im">
                                        <c:if test="${spec==im.colorId}">
                                            <li class="sp-img"><a <c:if test="${spec==P_GOODS.colorId}" > class="hovered" </c:if>title="${P_GOODSSPECVALUE_MAP[spec].name}" data-param="{valid:'${spec}'}"                                                                 href="javascript:void(0);"><img
                                                    src="${S_URL}/att/download/${im.goodsImage}"><i></i></a>
                                            </li>
                                        </c:if>

                                    </c:forEach>
                                </c:forEach>
                            </c:if>

                            <c:if test="${goodsSpecValue.spName!='颜色'}" >
                                <c:forEach items="${goodsSpecValue.spvId}" var="spec">
                                   <li class="sp-txt"><a data-param="{valid:'${spec}'}" <c:if test="${spec==P_VALUESTOCK_MODEL.specId[status.index]}" > class="hovered" </c:if> href="javascript:void(0)">${P_GOODSSPECVALUE_MAP[spec].name}<i></i></a></li>
                                </c:forEach>
                            </c:if>
                         </ul>

                    </dd>
                </dl>
            </c:forEach>
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
                <c:if test="${!empty P_GOODS.colorId}">
                <div class="ncs-point" nctype="goods_prompt">
                    <span class="yes">已选择 <strong><c:forEach items="${P_VALUESTOCK_MODEL.specName}" var="sname"> ${sname} </c:forEach></strong></span>
                </div>
                </c:if>
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
                        <dd class="btns"><a onclick="location.href='#'"
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

<fis:script>
    //收藏分享处下拉操作
    jQuery.divselect = function(divselectid,inputselectid) {
        var inputselect = $(inputselectid);
        $(divselectid).mouseover(function(){
            var ul = $(divselectid+" ul");
            ul.slideDown("fast");
            if(ul.css("display")=="none"){
                ul.slideDown("fast");
            }
        });
       <%-- $(divselectid).live('mouseleave',function(){
            $(divselectid+" ul").hide();
        });--%>
    };
    $(function(){
        // 加入购物车
        $('a[nctype="addcart_submit"]').click(function(){
            addcart(256, checkQuantity());
        });
        // 立即购买
        $('a[nctype="buynow_submit"]').click(function(){
            buynow(256,checkQuantity());
        });

        //浮动导航  waypoints.js
       <%-- $('#main-nav').waypoint(function(event, direction) {
            $(this).parent().parent().parent().toggleClass('sticky', direction === "down");
            event.stopPropagation();
        });--%>

        // 分享收藏下拉操作
        $.divselect("#handle-l");
        $.divselect("#handle-r");

        // 规格选择
        $('dl[nctype="nc-spec"]').find('a').each(function(){
            $(this).click(function(){
                if ($(this).hasClass('hovered')) {
                    return false;
                }
                $(this).parents('ul:first').find('a').removeClass('hovered');
                $(this).addClass('hovered');
                checkSpec();
            });
        });

    });

    function checkSpec() {
        var spec_param = [
    <c:forEach items="${P_GOODSSPECVALUE_SELECT}" var="goodsSpec" >
        <c:forEach items="${goodsSpec.specId}" var="specId" varStatus="status">
            <c:if test="${status.first}">
                <c:set var="sign" value="${specId}"></c:set>
            </c:if>
            <c:if test="${!status.first}">
                <c:set var="sign" value="${sign}|${specId}"></c:set>
            </c:if>
        </c:forEach>
    {"sign":"${sign}","url":"${S_URL}/cl/goods/show?common_id=${P_GOODSCOMMON.id}&goods_id=${goodsSpec.goodsId}"},
    </c:forEach>
    ];
        var spec = new Array();
        $('ul[nctyle="ul_sign"]').find('.hovered').each(function(){
            var data_str = ''; eval('data_str =' + $(this).attr('data-param'));
            spec.push(data_str.valid);
        });
        <%--spec1 = spec.sort(function(a,b){
            return a-b;
        });--%>

        var spec_sign = spec.join('|');
    var urlPath;
        $.each(spec_param, function(i, n){
            if (n.sign == spec_sign) {
                window.location.href = n.url;
            }
        });
    }

    // 验证购买数量
    function checkQuantity(){
        var quantity = parseInt($("#quantity").val());
        if (quantity < 1) {
            alert("请填写购买数量");
            $("#quantity").val('1');
            return false;
        }
        max = parseInt($('[nctype="goods_stock"]').text());
        if(quantity > max){
            alert("库存不足");
            return false;
        }
        return quantity;
    }

    // 规格页面跳转
    // function

    // 立即购买js
    function buynow(goods_id,quantity){
        login_dialog();
    }
    $(function(){
        //选择地区查看运费
        $('#transport_pannel>a').click(function(){
            var id = $(this).attr('nctype');
            if (id=='undefined') return false;
            var _self = this,tpl_id = '0';
            var url = '#'+Math.random();
            $('#transport_price').css('display','none');
            $('#loading_price').css('display','');
            $.getJSON(url, {'id':id,'tid':tpl_id}, function(data){
                if (data == null) return false;
                if(data != 'undefined') {$('#nc_kd').html(data);}else{$('#nc_kd').html('');}
                $('#transport_price').css('display','');
                $('#loading_price').css('display','none');
                $('#ncrecive').html($(_self).html());
            });
        });
        $("#nc-bundling").load('www', function(){
            if($(this).html() != '') {
                $(this).parents('.ncs-promotion:first').show();
            }
        });
        $("#salelog_demo").load('www', function(){
            // Membership card
            $(this).find('[nctype="mcard"]').membershipCard({type:'shop'});
        });
    });

    /** */
    $(function(){
        // 商品内容部分折叠收起侧边栏控制
        $('#fold').click(function(){
            $('.ncs-goods-layout').toggleClass('expanded');
        });
        // 商品内容介绍Tab样式切换控制
        $('#categorymenu').find("li").click(function(){
            $('#categorymenu').find("li").removeClass('current');
            $(this).addClass('current');
        });
        // 商品详情默认情况下显示全部
        $('#tabGoodsIntro').click(function(){
            $('.bd').css('display','');
            $('.hd').css('display','');
        });
        // 点击评价隐藏其他以及其标题栏
        $('#tabGoodsRate').click(function(){
            $('.bd').css('display','none');
            $('#ncGoodsRate').css('display','');
            $('.hd').css('display','none');
        });
        // 点击成交隐藏其他以及其标题
        $('#tabGoodsTraded').click(function(){
            $('.bd').css('display','none');
            $('#ncGoodsTraded').css('display','');
            $('.hd').css('display','none');
        });
        // 点击咨询隐藏其他以及其标题
        $('#tabGuestbook').click(function(){
            $('.bd').css('display','none');
            $('#ncGuestbook').css('display','');
            $('.hd').css('display','none');
        });
        //商品排行Tab切换
        $(".ncs-top-tab > li > a").mouseover(function(e) {
            if (e.target == this) {
                var tabs = $(this).parent().parent().children("li");
                var panels = $(this).parent().parent().parent().children(".ncs-top-panel");
                var index = $.inArray(this, $(this).parent().parent().find("a"));
                if (panels.eq(index)[0]) {
                    tabs.removeClass("current ").eq(index).addClass("current ");
                    panels.addClass("hide").eq(index).removeClass("hide");
                }
            }
        });
        //信用评价动态评分打分人次Tab切换
        $(".ncs-rate-tab > li > a").mouseover(function(e) {
            if (e.target == this) {
                var tabs = $(this).parent().parent().children("li");
                var panels = $(this).parent().parent().parent().children(".ncs-rate-panel");
                var index = $.inArray(this, $(this).parent().parent().find("a"));
                if (panels.eq(index)[0]) {
                    tabs.removeClass("current ").eq(index).addClass("current ");
                    panels.addClass("hide").eq(index).removeClass("hide");
                }
            }
        });

//触及显示缩略图
        $('.goods-pic > .thumb').hover(
                function(){
                    $(this).next().css('display','block');
                },
                function(){
                    $(this).next().css('display','none');
                }
        );

        /* 商品购买数量增减js */
        // 增加
        $('.increase').click(function(){
            num = parseInt($('#quantity').val());
            max = parseInt($('[nctype="goods_stock"]').text());
            if(num < max){
                $('#quantity').val(num+1);
            }
        });
        //减少
        $('.decrease').click(function(){
            num = parseInt($('#quantity').val());
            if(num > 1){
                $('#quantity').val(num-1);
            }
        });

        // 搜索价格不能填写非数字。
        var re = /^[1-9]+[0-9]*(\.\d*)?$|^0(\.\d*)?$/;
        $('input[name="start_price"]').change(function(){
            if(!re.test($(this).val())){
                $(this).val('');
            }
        });
        $('input[name="end_price"]').change(function(){
            if(!re.test($(this).val())){
                $(this).val('');
            }
        });
    });

    /* add cart */
    function addcart(goods_id, quantity)
    {
        if (!quantity) return false;
        var url = 'wwww';
        $.getJSON(url, {'goods_id':goods_id, 'quantity':quantity}, function(data){
            if(data != null){
                if (data.state)
                {
                    $('#bold_num').html(data.num);
                    $('#bold_mly').html(price_format(data.amount));
                    $('.ncs-cart-popup').fadeIn('fast');
//                 setTimeout(slideUp_fn, 5000);
                    // 头部加载购物车信息
                    load_cart_information();
                }
                else
                {
                    alert(data.msg);
                }
            }
        });
    }
    // 显示举报下拉链接
    $(document).ready(function() {
        $(".ncs-inform").hover(function() {
                    $(this).addClass("hover");
                },
                function() {
                    $(this).removeClass("hover");
                });
    })

    //评价列表
    $(document).ready(function(){
        $('#comment_tab').on('click', 'li', function() {
            $('#comment_tab li').removeClass('current');
            $(this).addClass('current');
            load_goodseval($(this).attr('data-type'));
        });

        load_goodseval('all');

        function load_goodseval(type) {
            var url = 'wwww';
            url += '&type=' + type;
            $("#goodseval").load(url, function(){
                $(this).find('[nctype="mcard"]').membershipCard({type:'shop'});
            });
        }
    });
</fis:script>