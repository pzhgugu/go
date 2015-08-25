<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>我的家 - 商城公告</title>
<meta content="GoShop,我的家" name="keywords">
<meta content="GoShop,我的家" name="description">
<meta content="GoShop" name="author">
<meta content="GoShop Inc. All Rights Reserved" name="copyright">
<style type="text/css">
body {
_behavior: url(/show/dev/common/styles/csshover.htc);
}
</style>
<fis:require id="cms:styles/base.css"/>
<fis:require id="cms:styles/home_header.css"/>
<fis:require id="cms:styles/layout.css"/>
<fis:styles/>
</head>
<body>

<fis:block url="cms:widget/tpl/top.html.jsp" />
<fis:block url="cms:widget/tpl/header.html.jsp" />
<fis:block url="cms:widget/tpl/nav.html.jsp" />

<div class="nch-breadcrumb-layout">
    <div class="nch-breadcrumb wrapper"><i class="icon-home"></i>
            <span><a href="index.php">首页</a></span><span class="arrow">&gt;</span>
                <span>${P_CURRENT_NEWSCATEGORY.alias}列表</span>
          </div>
  </div>



<div class="nch-container wrapper">
 <fis:block url="cms:pages/shopnews/left.html.jsp" />
  <div class="right">
    <div class="nch-article-con">
      <div class="title-bar">
        <h3>${P_CURRENT_NEWSCATEGORY.alias}</h3>
      </div>
            <ul class="nch-article-list">            
            <c:forEach items="${P_PAGE_NEWS.content}" var="news" >
            
             <li><i></i><a href="${S_URL}/cl/newshow/view/${news.id}" >${news.subject}</a><time><fmt:formatDate value="${news.updated}" type="both" /></time></li>
        	</c:forEach>
            </ul>
           
    </div> <div class="tc mb20">  <div class="pagination"> <ul><li><span>首页</span></li><li><span>上一页</span></li><li><span class="currentpage">1</span></li><li><span>下一页</span></li><li><span>末页</span></li></ul> </div></div>
  
  </div>
</div>



<fis:block url="cms:widget/tpl/button.html.jsp" />
<fis:block url="cms:widget/tpl/footer.html.jsp" />
	<fis:scripts/>
</body>
</fis:html>