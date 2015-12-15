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
    <fis:styles/>
  </head>


  <body>
  <fis:block url="shop:pages/client/storehome/${P_STYLE}/top.html.jsp"></fis:block>


  <fis:scripts/>
  </body>

</fis:html>
