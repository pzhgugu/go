<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>关闭审核</title>

        <fis:styles/>

        <fis:block url="core:widget/header.html.jsp"/>
        <fis:require id="common:widget/jquery/jqueryEx.js"/>
        <fis:require id="common:widget/utils/underscore.js"/>
        <fis:require id="common:widget/dhtmlxSuite/dhtmlxEx.js"/>
    </head>
    <body>

    <fis:scripts/>
    <script type="text/javascript">
        $(function () {
            var pTab = parent.GPW.tabbar.subListTabbar;
            pTab.tabs("apple").setActive();
            //pTab.tabs("verify").setActive();
        });
    </script>
    </body>

</fis:html>