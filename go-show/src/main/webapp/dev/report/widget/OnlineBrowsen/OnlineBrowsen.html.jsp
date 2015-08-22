<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map"> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<fis:require id="report:widget/OnlineBrowsen/OnlineBrowsen.js"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">    
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>在线浏览</title>
</head>
<style type="text/css" media="screen"> 
html, body	{ height:100%; }
body { margin:0; padding:0; overflow:auto; }   
</style> 
<body>


      <div id="viewerPlaceHolder" style="width:100%;height:100%;display:block"></div>
       <script type="text/javascript"> 
       var showUrl="${showUrl}";
      </script>

<fis:scripts/>
</body>
</fis:html>