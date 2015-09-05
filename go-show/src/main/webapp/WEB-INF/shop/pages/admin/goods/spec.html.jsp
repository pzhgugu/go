<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<!DOCTYPE html>
<fis:html mapDir="/map">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>规格管理</title>
    <fis:styles/>
    <fis:block url="core:widget/header.html.jsp" />
    <fis:require id="common:widget/jquery/jqueryEx.js" />
    <fis:require id="common:widget/utils/underscore.js" />
    <fis:require id="common:widget/dhtmlxSuite/dhtmlxEx.js" />
  </head>
  <body>
  <div id="layoutObj"></div>
  <fis:scripts/>

  <script type="text/javascript" language="javascript">

    $(window).resize(function() {
      GPW.layout.mainLayout.setSizes();
    });

    var GPC = {
      url:{
        refreshGridUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/loadPage/"+GLOBAL.P.CLASSNAME,
        queryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/queryPage/"+GLOBAL.P.CLASSNAME,
        saveUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/save/"+GLOBAL.P.CLASSNAME,
        detailQueryUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/tpl/DetailQuery/"+GLOBAL.P.CLASSNAME+"/d",
        updateUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/updateAll/"+GLOBAL.P.CLASSNAME+"?format=xml",
        delectUrl:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/delect/"+GLOBAL.P.CLASSNAME,
        loadActiveTabUrl:GLOBAL.S.URL+GLOBAL.P.MODULES
      },
      constant:{add:"add",edit:"edit",delect:"delect",query:"query",
        update:"update",detailQuery:"detailQuery",between:"between"}
    }

    var GPW = {
      layout : {},
      toolbar : {},
      grid : {},
      window : {},
      form:{},
      paging : {},
      cache : {}
    };

    GPW.layout = {
      mainLayout : {},
      mainGridLayout:{},
      tabbarLayout:{},
      init : function() {
        this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
        this.mainGridLayout=this.mainLayout.cells("a");
        this.mainGridLayout.setWidth(720);
        this.mainGridLayout.hideHeader();
      }
    }


    GPW.grid = {
      mainGrid: {},
      init : function() {
        this.mainGrid = GPW.layout.mainGridLayout.attachGrid();
        this.mainGrid.setImagePath(GLOBAL.IconsPath);
        this.mainGrid.setHeader("名称,关联");
        this.mainGrid.setInitWidths("*,100");
        this.mainGrid.setColAlign("left,center");
        this.mainGrid.setColTypes("ed,dhxCalendar");
        this.mainGrid.setColSorting("str,date");
        this.mainGrid.init();
      }
    }

    $(function() {
      GPW.layout.init();
      GPW.grid.init();
    });

  </script>
  </body>
</fis:html>