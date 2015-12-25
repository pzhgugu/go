<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:block url="shop:pages/client/goods/${P_STYLE}/breadcrumb.html.jsp" />

<div class="wrapper pr" id="content">
  <fis:block url="shop:pages/client/goods/${P_STYLE}/detail.html.jsp" />

</div>