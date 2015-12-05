<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>店铺管理</title>
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
        var GPW = {
            layout: {},
            tabbar: {}
        };

        LinkInfo = function () {
            alert(1);
        }
        $(window).resize(function () {
            GPW.layout.mainLayout.setSizes();
        });

        var GPC = {
            url: {
                appleUrl: GLOBAL.S.URL + "/admin/storejoinin/tpl/SingleGrid/StoreJoinin/d",
                manageUrl: GLOBAL.S.URL + "/admin/store/tpl/SingleGrid/store/d",
                verifyDefalutUrl: GLOBAL.S.URL + "/admin/storemanage/verify?id=",
                verifyUrl: ""
            },
            constant: {
                add: "add", edit: "edit", delect: "delect", query: "query",
                update: "update", detailQuery: "detailQuery", between: "between"
            }
        }

        GPW.layout = {
            mainLayout: {},
            tabbarLayout: {},
            init: function () {
                this.mainLayout = new dhtmlXLayoutObject("layoutObj", "1C");
                this.tabbarLayout = this.mainLayout.cells("a");
                this.tabbarLayout.hideHeader();

            }
        }


        GPW.tabbar = {
            subListTabbar: {},
            subInfo: [{"name": "manage", "alias": "店铺管理"}, {"name": "apple", "alias": "开店申请"}],
            getTabs: function () {
                var sInfo = GPW.tabbar.subInfo;
                var arr = new Array(sInfo.length);
                for (var i = 0; i < sInfo.length; i++) {
                    var o = {};
                    o.id = sInfo[i].name;
                    o.text = sInfo[i].alias;
                    if (i == 0) {
                        o.active = true;
                    }
                    arr[i] = o;
                }
                return arr;
            },
            attachURL: function (actvId) {
                var sInfo = GPW.tabbar.subInfo;
                var o = _.find(sInfo, function (v) {
                    if (v.name == actvId) {
                        return v;
                    }
                });
                var url;
                if (actvId == 'manage') {
                    url = GPC.url.manageUrl;
                } else if (actvId == 'apple') {
                    url = GPC.url.appleUrl;
                } else if (actvId == 'verify') {
                    url = GPC.url.verifyUrl;
                }
                if (!!url)
                    this.subListTabbar.tabs(actvId).attachURL(url);
            },
            initSubList: function () {
                this.subListTabbar = GPW.layout.tabbarLayout.attachTabbar({
                    tabs: GPW.tabbar.getTabs()
                });
                this.subListTabbar.attachEvent("onSelect", function (actvId, lastId) {
                    if (actvId != "verify" && this.tabs("verify") != null) {
                        this.tabs("verify").close(true);
                    }
                    GPW.tabbar.attachURL(actvId);
                    return true;
                });
                this.loadActiveTab();
            },
            loadActiveTab: function () {
                var actvId = this.subListTabbar.getActiveTab();
                this.attachURL(actvId);
            }
        }

        $(function () {
            GPW.layout.init();
            GPW.tabbar.initSubList();
        });

    </script>
    </body>

</fis:html>