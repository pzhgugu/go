<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<fis:html mapDir="/map"> 
  <head> 
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">   
    <title>首页</title>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <fis:require id="core:scripts/mainPage.js" />
    <fis:styles/>
</head>
<body>
<div class = "all"> 
<div class = "left">
<div class = "user"><div id="menuheader">用户信息</div>
<div class="usersn"><p>&nbsp;&nbsp;用 户 名：${ALIAS}</p><p>&nbsp;&nbsp;用 户ID：<sec:authentication property='name' /></p><table><tr><td><a href="javascript:opentab('changePassword','修改密码','${S_URL}/admin/changePassword');" style="color:#00F">修改密码</a></td><td><a href="javascript:logout();" href="" style="color:#00F">注销</a></td></tr></table></div></div>
<div class="lulu">
		<div id="menuheader">常用功能</div>
		<a href="javascript:opentab('ExcelManage','Excel报表',D${S_URL}ataCollection);">Excel报表</a>
		<a href="javascript:opentab('chartTest','图表测试','${S_URL}/admin/chart/show/test');">图表测试</a>
		<a href="javascript:opentab('ExcelIn','Excel导入','${S_URL}/admin/excelin/tpl/SingleGridQuery/ExcelIn/d');">Excel导入</a>
		<a href="javascript:opentab('SqlModelManage','SQL模型','${S_URL}/admin/sqlmodel/tpl/TreeTwoOneToMany/SqlFieldsCategory/d?two=SqlFields');">SQL模型</a>
		<a href="javascript:opentab('DynamicModelManage','动态模型','${S_URL}/admin/dymodel/tpl/TreeTwoOneToMany/DynamicFieldsCategory/d?two=DynamicFields');">动态模型</a>
		<a href="javascript:opentab('Chart','图表管理','${S_URL}/admin/chart/tpl/SingleGridQuery/Chart/d');">图表管理</a>
</div>	
<div>    
		<div id="menuheader">友情链接</div>
		<a href="#">攀钢集团网站</a>
		<a href="#">协同办公系统</a>
</div>
</div>


<div class="baobiao">        
        <div id="menuheaderbaobiao">关于网站</div>
        <div><p style="width:97%" style="height:100%" style="line-height:150%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;GoReport报表系统报是一款根据用户提供的报表模板和数据模型动态生成报表的数据分析系统。主要包含动态模型、SQL模型、图表生成、菜单管理、权限管理和附件管理等功能。系统根据用户提供的报表模板和数据源按用户的需求生成各种文件格式的数据报表，同时本系统支持多种类型的数据库，无论从报表格式还是数据源格式上来说
        本系统都具有很高的自由性。目前，本系统支持Excel、Pdf、Rtf和在线报表等报表格式。</p>
        </div>
</div>  
</div>   
</div>
	<fis:scripts/>
	<script type="text/javascript" language="javascript">
		
        GLOBAL.S.URL="${S_URL}";
        GLOBAL.S.URL_R="${S_URL_R}";
    </script>
</body>
</fis:html>
