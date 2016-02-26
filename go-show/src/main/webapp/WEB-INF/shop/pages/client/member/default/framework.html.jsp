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
  <title>买家中心</title>
  <fis:require id="shop:styles/base.css"/>
  <fis:require id="shop:pages/client/member/${P_STYLE}/styles/member.css"/>
  <fis:require id="shop:styles/home_login.css"/>
  <fis:styles/>


  <fis:require id="common:widget/jquery/waypoints.js"/>
  <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
  <fis:require id="common:widget/jquery/jquery-browser.js"/>
  <fis:require id="common:widget/jquery/jquery.validation.js"/>
  <fis:require id="common:widget/jquery/jquery.masonry.js"/>
  <fis:require id="shop:scripts/member.js"/>
  <fis:require id="shop:scripts/common.js"/>
  <fis:require id="shop:scripts/common_select.js" />
  <fis:require id="shop:scripts/shop.js"/>
  <fis:require id="common:widget/qtip/jquery.qtip.min.js"/>
  <script type="text/javascript">
    var SITEURL = '${S_URL}';
  </script>

  <fis:scripts/>


  <fis:out id="shop:scripts/dialog/dialog.js" iid="dialog_js"/>
  </head>

  <body>
  <div id="append_parent"></div>
  <div id="ajaxwaitid"></div>
  <fis:block url="shop:pages/client/top.html.jsp"></fis:block>
  <fis:block url="shop:pages/client/member/${P_STYLE}/header.html.jsp" />
  <div id="container" class="wrapper">
    <div class="layout">
      <c:choose>
        <c:when test="${P_OP=='set'}"><fis:block url="shop:pages/client/member/${P_STYLE}/set_sidebar.html.jsp" /></c:when>
        <c:otherwise><fis:block url="shop:pages/client/member/${P_STYLE}/sidebar.html.jsp" /></c:otherwise>
      </c:choose>


      <fis:block url="${P_VIEW}" />
    </div>
  </div>
  <fis:block url="shop:pages/client/footer.html.jsp" />

  </body>

</fis:html>