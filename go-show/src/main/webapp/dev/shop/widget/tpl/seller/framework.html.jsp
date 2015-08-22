<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta charset="utf-8">
<title>商家中心</title>
<fis:require id="shop:styles/base.css"/>
<fis:require id="shop:styles/seller_center.css"/>
<fis:require id="shop:scripts/dialog/dialog.css"/>
<fis:styles/>
<fis:out id="common:widget/font-awesome/css/font-awesome.css"/>
<!--[if IE 7]>
  <fis:out id="common:widget/font-awesome/css/font-awesome-ie7.css"/>
<![endif]-->



<fis:require id="common:widget/jquery/jquery.js"/>
<fis:require id="common:widget/jquery/waypoints.js"/>
<fis:require id="common:widget/jquery/jquery.validation.js"/>
<fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
<fis:require id="shop:scripts/member.js"/>
<fis:require id="shop:scripts/common.js"/>
<fis:require id="shop:scripts/seller.js"/>

<script>
var COOKIE_PRE = '5BF5_';
var _CHARSET = 'utf-8';
var SITEURL = '${S_URL}';
var RESOURCE_SITE_URL = '${S_URL}/data/resource';
var SHOP_RESOURCE_SITE_URL = '${S_URL}/shop/resource';
var SHOP_TEMPLATES_URL = '${S_URL}/shop/templates/default';
</script>


<style type="text/css" id="poshytip-css-tip-yellowsimple">
div.tip-yellowsimple{
visibility:hidden;
position:absolute;
top:0;left:0;
}
div.tip-yellowsimple table, div.tip-yellowsimple td{
margin:0;
font-family:inherit;
font-size:inherit;
font-weight:inherit;
font-style:inherit;
font-variant:inherit;
}
div.tip-yellowsimple td.tip-bg-image span{
display:block;
font:1px/1px sans-serif;
height:10px;
width:10px;
overflow:hidden;
}
div.tip-yellowsimple td.tip-right{
background-position:100% 0;
}
div.tip-yellowsimple td.tip-bottom{
background-position:100% 100%;}
div.tip-yellowsimple td.tip-left{background-position:0 100%;}
div.tip-yellowsimple div.tip-inner{background-position:-10px -10px;}
div.tip-yellowsimple div.tip-arrow{
visibility:hidden;position:absolute;overflow:hidden;font:1px/1px sans-serif;}
</style>

</head>
<body>

<div id="append_parent"></div>
<div id="ajaxwaitid"></div>

<fis:block url="shop:widget/tpl/seller/header.html.jsp" />


<fis:block url="${P_VIEW}" />


<fis:block url="shop:widget/tpl/seller/footer.html.jsp" />




<fis:scripts/>
<fis:out id="shop:scripts/dialog/dialog.js" iid="dialog_js" />
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

<script type="text/javascript">
$(document).ready(function(){
    //添加删除快捷操作
    $('[nctype="btn_add_quicklink"]').on('click', function() {
        var $quicklink_item = $(this).parent();
        var item = $(this).attr('data-quicklink-act');
        if($quicklink_item.hasClass('selected')) {
            $.post("http://localhost/shopnc/shop/index.php?act=seller_center&op=quicklink_del", { item: item }, function(data) {
                $quicklink_item.removeClass('selected');
                $('#quicklink_' + item).hide('fadeOut');
            }, "json");
        } else {
            var count = $('#quicklink_list').find('dd.selected').length;
            if(count >= 8) {
                showError('快捷操作最多添加8个');
            } else {
                $.post("http://localhost/shopnc/shop/index.php?act=seller_center&op=quicklink_add", { item: item }, function(data) {
                    $quicklink_item.addClass('selected');
                                    }, "json");
            }
        }
    });
    //浮动导航  waypoints.js
    $("#sidebar,#mainContent").waypoint(function(event, direction) {
        $(this).parent().toggleClass('sticky', direction === "down");
        event.stopPropagation();
        });
    });
    // 搜索商品不能为空
    $('input[nctype="search_submit"]').click(function(){
        if ($('input[nctype="search_text"]').val() == '') {
            return false;
        }
    });
</script>
</body>
</fis:html>