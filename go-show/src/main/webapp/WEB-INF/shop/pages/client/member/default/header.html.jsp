<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>

<header class="pngFix" id="header">
  <div class="wrapper">
    <h1 title="电商测试" id="logo"><a href="#"><img class="pngFix" alt="电商测试" src="${S_URL}/res/img/logo.png"></a></h1>
    <nav>
      <ul>
        <li class="frist"><a title="买家首页" href="${S_URL}/shop/member/index" <c:choose><c:when test="${P_ACT=='index'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose> >买家首页</a></li>
        <li><a title="个人主页" href="#" class="normal">个人主页</a></li>
        <li><a title="好友" href="#" class="normal">好友</a></li>
        <li><a title="站内信" href="#" class="normal">站内信                    </a></li>
        <li><a title="设置" href="${S_URL}/shop/member/set" <c:choose><c:when test="${P_ACT=='set'}"> class="active"</c:when><c:otherwise>class="normal"</c:otherwise></c:choose>>设置</a></li>
      </ul>
      <div class="search-box">
        <form id="formSearch" action="index.php" method="get">
          <input type="hidden" value="search" id="search_act" name="act">
          <input type="text" maxlength="200" placeholder="" class="text" id="keyword" name="keyword">
          <input type="submit" class="submit pngFix" value="" name="">
        </form>
      </div>
    </nav>
  </div>
</header>
