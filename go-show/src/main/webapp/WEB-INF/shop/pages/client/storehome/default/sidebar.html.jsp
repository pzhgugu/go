<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<!--店铺基本信息-->
<fis:block url="shop:pages/client/storehome/storeinfo.html.jsp"></fis:block>
<!--店铺商品分类-->
<fis:block url="shop:pages/client/storehome/${P_STYLE}/goodclass.html.jsp"></fis:block>
<!--店铺排行-->
<fis:block url="shop:pages/client/storehome/${P_STYLE}/among.html.jsp"></fis:block>
