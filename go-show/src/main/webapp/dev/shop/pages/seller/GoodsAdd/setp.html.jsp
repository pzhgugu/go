<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>
<!-- S setp --> 
<ul class="add-goods-step">
  <li <c:if test="${P_STEP==1}" >class="current"</c:if>>
    <i class="icon icon-list-alt">
    </i>
    <h6>
      STIP.1
    </h6>
    <h2>
      选择商品分类
    </h2>
    <i class="arrow icon-angle-right">
    </i>
  </li>
  <li <c:if test="${P_STEP==2}" >class="current"</c:if>>
    <i class="icon icon-edit">
    </i>
    <h6>
      STIP.2
    </h6>
    <h2>
      填写商品详情
    </h2>
    <i class="arrow icon-angle-right">
    </i>
  </li>
  <li <c:if test="${P_STEP==3}" >class="current"</c:if>>
    <i class="icon icon-camera-retro ">
    </i>
    <h6>
      STIP.3
    </h6>
    <h2>
      上传商品图片
    </h2>
    <i class="arrow icon-angle-right">
    </i>
  </li>
  <li <c:if test="${P_STEP==4}" >class="current"</c:if>>
    <i class="icon icon-ok-circle">
    </i>
    <h6>
      STIP.4
    </h6>
    <h2>
      商品发布成功
    </h2>
  </li>
</ul>