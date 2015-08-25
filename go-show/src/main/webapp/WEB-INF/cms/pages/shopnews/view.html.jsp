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
                <span>商城公告</span>
          </div>
  </div>



<div class="nch-container wrapper">
  <fis:block url="cms:pages/shopnews/left.html.jsp" />
  
  <div class="right">
    <div class="nch-article-con">
      <h1>${P_NEWSITEMS.subject}</h1>
      <h2><fmt:formatDate value="${P_NEWSITEMS.updated}" type="both" /></h2>
      <div class="default">
        <p>${P_NEWSITEMS.message}</p>
      </div>
      <div class="more_article"> <span class="fl">上一篇：
                <a href="#">我要买</a> <time>2014-01-16 17:31</time>
                </span> <span class="fr">下一篇：
                <a href="#">积分细则</a> <time>2014-01-16 17:31</time>
                </span> </div>
    </div>
  </div>
  
</div>



<fis:block url="cms:widget/tpl/button.html.jsp" />
<fis:block url="cms:widget/tpl/footer.html.jsp" />
	<fis:scripts/>
</body>
</fis:html>