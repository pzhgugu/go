<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<!DOCTYPE html>
<fis:html mapDir="/map">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>品牌管理</title>
    <fis:styles/>
    <fis:block url="core:widget/header.html.jsp"/>
    <fis:require id="common:widget/jquery/jqueryEx.js"/>
    <fis:require id="common:widget/utils/underscore.js"/>
    <fis:require id="common:widget/dhtmlxSuite/dhtmlxEx.js"/>
  </head>
  <body>
  <div id="layoutObj"></div>
  <fis:scripts/>

  <script type="text/javascript" language="javascript">

    GLOBAL.P.MODULES = "/admin/goodsbrand";
    GLOBAL.P.CLASSNAME = "GoodsBrand";

    var GoodsClassByLayeList = new Array();
    <c:forEach items="${GoodsClassByLayeList}" var="GoodsClass">
    GoodsClassByLayeList.push({"text": "${GoodsClass.name}", "value": "${GoodsClass.id}"});
    </c:forEach>
    GLOBAL.P.P_GRID_FORMGRIDASSEMBLE = {"isSelect": [{"text": "否", "value": "0"}, {"text": "是", "value": "1"}]};
    GLOBAL.P.P_GRID_FORMGRIDASSEMBLE.classId = GoodsClassByLayeList;

    $(window).resize(function () {
      GPW.layout.mainLayout.setSizes();
    });

    var GPC = {
      url: {
        refreshGridUrl: GLOBAL.S.URL + GLOBAL.P.MODULES + "/load/relation?" + GLOBAL.P.parameter,
        queryUrl: GLOBAL.S.URL + GLOBAL.P.MODULES + "/query/relation?" + GLOBAL.P.parameter,
        updateUrl: GLOBAL.S.URL + GLOBAL.P.MODULES + "/relation/update?format=xml&" + GLOBAL.P.parameter
      },
      constant: {
        add: "add", edit: "edit", delect: "delect", query: "query",
        update: "update", detailQuery: "detailQuery", between: "between"
      }
    }

    var GPW = {
      layout: {},
      toolbar: {},
      grid: {},
      window: {},
      form: {},
      paging: {},
      cache: {}
    };

    GPW.layout = {
      mainLayout: {},
      mainGridLayout: {},
      tabbarLayout: {},
      init: function () {
        this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
        this.mainGridLayout = this.mainLayout.cells("a");
        this.mainGridLayout.setWidth(720);
        this.mainGridLayout.hideHeader();
      }
    }


    GPW.grid = {
      mainGrid: {},
      gridDataProcessor: {},
      init: function () {
        this.mainGrid = GPW.layout.mainGridLayout.attachGrid();
        this.mainGrid.setImagePath(GLOBAL.IconsPath);
        this.mainGrid.setHeader("排序,品牌名称,分类名称,图标,是否关联");
        this.mainGrid.setColumnIds("brandSort,brandName,classId,logoImage,isSelect");
        this.mainGrid.setInitWidths("50,100,100,100,100");
        this.mainGrid.setColAlign("left,left,left,left,left");
        this.mainGrid.setColTypes("ro,ro,co,img,ch");
        this.mainGrid.setColSorting("str,str,str,str,str");
        GLOBAL.initGridCombo(this.mainGrid, GLOBAL.P.P_GRID_FORMGRIDASSEMBLE);
        this.mainGrid.setDateFormat("%Y-%m-%d");
        this.mainGrid.init();


        this.gridDataProcessor = new dataProcessor(GPC.url.updateUrl); //lock feed url
        //this.gridDataProcessor.setTransactionMode("POST",false); //set mode as send-all-by-post
        this.gridDataProcessor.setTransactionMode("REST");
        //this.gridDataProcessor.setUpdateMode("off"); //disable auto-update
        this.gridDataProcessor.enableDataNames(true);
        //只发送更改的项目
        this.gridDataProcessor.enablePartialDataSend(true);
        this.gridDataProcessor.init(this.mainGrid); //link dataprocessor to the grid

        this.gridDataProcessor.attachEvent("onBeforeDataSending", function (id, state, data) {
          //去除Data数据的发送
          _.each(data, function (v, k, value) {
            if (!!v.data) {
              v.data = null;
              delete v.data;
            }
          });
          return true;
        });
        this.gridDataProcessor.attachEvent("onAfterUpdate", function (id, action, tid, element) {
          if (element instanceof Element) {
            if (element.getElementsByTagName("code").item(0) != null) {
              var code = element.getElementsByTagName("code").item(0).childNodes[0].nodeValue;
              var message = element.getElementsByTagName("message").item(0).childNodes[0].nodeValue;
              if (code == -1) {
                parent.dhtmlx.message({
                  type: "error",
                  expire: -1,
                  text: message
                });
              }
            }
          }
        });

        this.mainGrid.attachEvent("onXLE", function (grid_obj, count) {
          SpinnerCtl.close();
        });
        this.refreshMainGrid();
      },
      refreshMainGrid: function (url) {
        if (!url) {
          url = GPC.url.refreshGridUrl;
        }
        this.mainGrid.clearAll();
        SpinnerCtl.show();
        this.mainGrid.load(url, "js");
        GPW.groupBy();
      }
    }

    GPW.toolbar = {
      mainToolbar: {},
      queryOperators: {
        "brandName": [{"text": "包含", "value": "like"}, {"text": "等于", "value": "="}],
        "classId": [{"text": "等于", "value": "="}],
        "isSelect": [{"text": "等于", "value": "="}]
      },
      queryValues: {"isSelect": [{"text": "", "value": ""}, {"text": "否", "value": "0"}, {"text": "是", "value": "1"}]},
      addQueryName: function () {
        this.mainToolbar.addSelectEx("queryName", 1, [{"text": "品牌名称", "value": "brandName"}, {
          "text": "分类名称",
          "value": "classId"
        }/*,{"text":"是否关联","value":"isSelect"}*/], 100, "GPW.toolbar.onChangeQuery()");
      },
      onChangeQuery: function () {
        this.addQueryValue();
      },
      onChangeQueryOperators: function () {
        var toolbar = GPW.toolbar.mainToolbar;
        var operatorValue = toolbar.getInput("operator").value;
        var queryName = GPW.toolbar.mainToolbar.getInput("queryName");
        var selectValue = queryName.value;
        if (operatorValue == GPC.constant.between) {
          this.addBetweenValue(selectValue);
        } else {
          this.addOtherValue(selectValue);
        }
      },
      addBetweenValue: function (selectValue) {

        this.mainToolbar.removeItem("queryValue");
        this.mainToolbar.removeItem("queryBegin");
        this.mainToolbar.removeItem("queryEnd");
        this.mainToolbar.removeItem("text_do");
        this.mainToolbar.addInput("queryBegin", 3, "", 100);
        this.mainToolbar.addText("text_do", 4, "到");
        this.mainToolbar.addInput("queryEnd", 5, "", 100);

        var type;
        _.map(this.queryFormType, function (value, key) {
          if (key == selectValue) {
            type = value;
            return;
          }
        })

        if (type == "calendar") {
          var beginCalendar = new dhtmlXCalendarObject(this.mainToolbar.getInput("queryBegin"));
          //beginCalendar.setDateFormat("%Y-%m-%d");
          //this.mainToolbar.setValue("queryBegin", beginCalendar.getFormatedDate());


          var endCalendar = new dhtmlXCalendarObject(this.mainToolbar.getInput("queryEnd"));
          //endCalendar.setDateFormat("%Y-%m-%d");
          //this.mainToolbar.setValue("queryEnd", endCalendar.getFormatedDate());
        } else {

        }
      },
      addOtherValue: function (selectValue) {
        var toolbar = GPW.toolbar.mainToolbar;
        var isEx = false;
        this.queryValues.classId = GoodsClassByLayeList;
        _.map(this.queryValues, function (value, key) {
          //下拉选项
          if (key == selectValue) {
            toolbar.removeItem("queryValue");
            toolbar.removeItem("queryBegin");
            toolbar.removeItem("text_do");
            toolbar.removeItem("queryEnd");
            toolbar.addSelectEx("queryValue", 3, value, 100);
            isEx = true;
          }
        });
        if (!isEx) {
          toolbar.removeItem("queryValue");
          toolbar.removeItem("queryBegin");
          toolbar.removeItem("text_do");
          toolbar.removeItem("queryEnd");

          var type;
          _.map(this.queryFormType, function (value, key) {
            if (key == selectValue) {
              type = value;
              return;
            }
          })
          toolbar.addInput("queryValue", 3, "", 200);
          if (type == "calendar") {
            new dhtmlXCalendarObject(toolbar.getInput("queryValue"));
          }
        }
      },
      addOperator: function (selectValue) {
        var toolbar = GPW.toolbar.mainToolbar;
        _.find(this.queryOperators, function (oper, key) {
          if (key == selectValue) {
            toolbar.removeItem("operator");
            toolbar.addSelectEx("operator", 2, oper, 70, "GPW.toolbar.onChangeQueryOperators()");
            return;
          }
        });
      },
      addQueryValue: function () {
        var queryName = GPW.toolbar.mainToolbar.getInput("queryName");
        var selectValue = queryName.value;
        var that = this;
        var toolbar = that.mainToolbar;
        that.addOperator(selectValue);
        var operatorValue = toolbar.getInput("operator").value;
        if (operatorValue == GPC.constant.between) {
          this.addBetweenValue(selectValue);
        } else {
          this.addOtherValue(selectValue);
        }
      },
      init: function () {
        this.mainToolbar = GPW.layout.mainGridLayout.attachToolbar();
        var toolbar = this.mainToolbar;
        toolbar.setIconsPath(GLOBAL.IconsPath);
        toolbar.addText("text_select", 0, "选");
        this.addQueryName();
        var selectValue = toolbar.getInput("queryName").value;
        this.addOperator(selectValue);
        this.addQueryValue();
        toolbar.addButton("query", 9, "查询", "search.gif", "search_dis.gif");
        this.mainToolbar.attachEvent("onClick", function (id) {
          switch (id) {
            case GPC.constant.query:
              var toolbar = GPW.toolbar.mainToolbar;
              var where = toolbar.getInput("queryName").value;
              var operator = toolbar.getInput("operator").value;

              var arrayObj = new Array();
              if (operator == GPC.constant.between) {
                var begin = toolbar.getInput("queryBegin").value;
                var end = toolbar.getInput("queryEnd").value;
                arrayObj[0] = GLOBAL.getBetweenQueryObjct(where, operator, begin, end);
              } else {
                var whereValue = toolbar.getInput("queryValue").value;
                arrayObj[0] = GLOBAL.getQueryObjct(where, operator, whereValue);
              }
              var strJosn = JSON.stringify(arrayObj);
              //var url=GPC.url.queryUrl+"&where="+where+"&operator="+operator+"&whereValue="+whereValue;
              var url = GPC.url.queryUrl + "&_json=" + encodeURI(strJosn);
              GPW.grid.refreshMainGrid(url);

              break;
            default:
          }
        });
      }
    }

    $(function () {
      GPW.layout.init();
      GPW.grid.init();
      GPW.toolbar.init();
    });

    GPW.groupBy = function () {
      GPW.grid.mainGrid.customGroupFormat = function (text, count) {
        var dValue;
        _.each(GLOBAL.P.P_GRID_FORMGRIDASSEMBLE.classId, function (val) {
          if (text == val.value) {
            dValue = val.text;
          }
        });
        return dValue + " (" + count + ")";
      };
      GPW.grid.mainGrid.groupBy(2);
    }

  </script>
  </body>

</fis:html>