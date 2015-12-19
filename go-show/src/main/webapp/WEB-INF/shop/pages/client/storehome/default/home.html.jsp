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
    <title>店铺首页</title>
    <fis:require id="shop:styles/base.css"/>
    <fis:require id="shop:styles/shop.css"/>
    <fis:require id="shop:pages/client/storehome/${P_STYLE}/style.css"/>
    <fis:styles/>
    <fis:out id="common:widget/font-awesome/css/font-awesome.css"/>
    <!--[if IE 7]>
    <fis:out id="common:widget/font-awesome/css/font-awesome-ie7.css"/>
    <![endif]-->

    <fis:require id="common:widget/jquery/jquery.js"/>
    <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
    <fis:require id="common:widget/jquery/jquery.validation.js"/>
    <fis:require id="shop:scripts/member.js"/>
    <fis:require id="shop:scripts/common.js"/>
    <fis:require id="shop:scripts/shop.js"/>
  <fis:require id="common:widget/qtip/jquery.qtip.min.js"/>
  </head>
<script>
  SITEURL='${S_URL}';
</script>

  <body>
  <fis:block url="shop:pages/client/storehome/${P_STYLE}/top.html.jsp"></fis:block>
  <fis:block url="shop:pages/client/storehome/${P_STYLE}/header.html.jsp"></fis:block>

  <div class="background clearfix">
    <fis:block url="shop:pages/client/storehome/${P_STYLE}/nav.html.jsp"></fis:block>


    <article id="content">
      <section class="layout expanded mt10">
        <article class="nc-goods-main">
          <!--右边-->
          <fis:block url="shop:pages/client/storehome/${P_STYLE}/main.html.jsp"></fis:block>
          </article>
          <aside class="nc-sidebar">
           <!--左边-->
          <fis:block url="shop:pages/client/storehome/${P_STYLE}/sidebar.html.jsp"></fis:block>
        </aside>
      </section>
    </article>



  </div>

  <div id="faq">
    <div class="wrapper">
    </div>
  </div>

  <fis:block url="shop:pages/client/storehome/footer.html.jsp"></fis:block>
  <fis:scripts/>
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
