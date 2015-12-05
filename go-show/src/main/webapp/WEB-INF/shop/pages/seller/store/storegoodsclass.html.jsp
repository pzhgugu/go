<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<c:set var="S_URL" value="${pageContext.request.contextPath}"/>

<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftStore.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>


        <div id="mainContent" class="main-content">

            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="active"><a href="${S_URL}/se/storegoodsclass/list">商品分类</a></li>
                </ul>
                <a title="新增分类" uri="${S_URL}/se/storegoodsclass/addpage" dialog_width="480" dialog_id="my_category_add"
                   dialog_title="新增分类" nc_type="dialog" class="ncsc-btn ncsc-btn-green"
                   href="javascript:void(0)">新增分类</a></div>
            <table class="ncsc-table-style">
                <thead>

                <tr nc_type="table_header">
                    <th class="w30"></th>
                    <th inputwidth="50%" checker="check_required" column="stc_name" coltype="editable">分类名称</th>
                    <th inputwidth="30px" checker="check_max" column="stc_sort" coltype="editable" class="w60">排序</th>
                    <th offclass="noshowclass-ico-btn" onclass="showclass-ico-btn" checker="" column="stc_state"
                        coltype="switchable" class="w120">显示
                    </th>
                    <th class="w100">操作</th>
                </tr>
                <tr>
                    <td class="tc"><input type="checkbox" class="checkall" id="all"></td>
                    <td colspan="20"><label for="all">全选</label>
                        <a confirm="您确实要删除该分类吗?" name="class_id"
                           uri="index.php?act=store_goods_class&amp;op=drop_goods_class" nc_type="batchbutton"
                           class="ncsc-btn-mini" href="javascript:void(0)"><i class="icon-trash"></i>删除</a></td>
                </tr>
                </thead>
                <tbody id="treet1">
                <c:if test="${fn:length(P_PAGE_SHOW.content)>0}">
                <c:set var="status" value="0"/>
                <c:set var="map" value=""/>
                <c:set var="cstatus" value=""/>
                <c:forEach items="${P_PAGE_SHOW.content}" var="storeGoodsClass">
                    <c:set var="status" value="${status+1}"/>
                    <c:set var="map" value="${map}0,"/>
                    <tr idvalue="1" nc_type="table_item" class="bd-line">
                        <td class="tc"><input type="checkbox" value="${storeGoodsClass.id}" class="checkitem"></td>
                        <td class="tl">

                            <span nc_type="editobj" class="ml5">${storeGoodsClass.name}</span>
            <span class="add_ico_a">
                <a uri="${S_URL}/se/storegoodsclass/addpage?parent=${storeGoodsClass.id}" dialog_id="my_category_add"
                   dialog_title="新增下级" dialog_width="480" nc_type="dialog" class="ncsc-btn" href="javascript:void(0)">新增下级</a>
            </span>
                        </td>
                        <td><span nc_type="editobj">${storeGoodsClass.sequence}</span></td>
                        <td>
                            <c:if test="${storeGoodsClass.stcState=='1'}">
                                <span status="on" class="showclass-ico-btn" nc_type="editobj"></span>
                            </c:if>
                            <c:if test="${storeGoodsClass.stcState!='1'}">
                                <span status="off" class="noshowclass-ico-btn" nc_type="editobj"></span>
                            </c:if>
                        </td>
                        <td class="nscs-table-handle"><span><a class="btn-blue"
                                                               uri="${S_URL}/se/storegoodsclass/addpage?id=${storeGoodsClass.id}"
                                                               dialog_id="my_category_edit" dialog_title="编辑分类"
                                                               dialog_width="480" nc_type="dialog"
                                                               href="javascript:void(0)"><i class="icon-edit"></i>

                            <p>编辑</p>
                        </a></span> <span><a class="btn-red"
                                             onclick="ajax_get_confirm('您确实要删除该分类吗?', 'se/storegoodsclass/del?id=${storeGoodsClass.id}');"
                                             href="javascript:void(0)"><i class="icon-trash"></i>

                            <p>删除</p>
                        </a></span></td>
                    </tr>
                    <c:set var="cstatus" value="${status}"/>
                    <c:if test="${!empty storeGoodsClass.children}">
                        <c:forEach items="${storeGoodsClass.children}" var="children">
                            <c:set var="map" value="${map}${cstatus},"/>
                            <c:set var="status" value="${status+1}"/>
                            <tr idvalue="1" nc_type="table_item" class="bd-line">
                                <td class="tc"><input type="checkbox" value="${children.id}" class="checkitem"></td>
                                <td class="tl">
                                    <span nc_type="editobj" class="ml5">${children.name}</span>
                                </td>
                                <td><span nc_type="editobj">${children.sequence}</span></td>
                                <td>
                                    <c:if test="${children.stcState=='1'}">
                                        <span status="on" class="showclass-ico-btn" nc_type="editobj"></span>
                                    </c:if>
                                    <c:if test="${children.stcState!='1'}">
                                        <span status="off" class="noshowclass-ico-btn" nc_type="editobj"></span>
                                    </c:if>
                                </td>
                                <td class="nscs-table-handle"><span><a class="btn-blue"
                                                                       uri="${S_URL}/se/storegoodsclass/addpage?id=${children.id}"
                                                                       dialog_id="my_category_edit" dialog_title="编辑分类"
                                                                       dialog_width="480" nc_type="dialog"
                                                                       href="javascript:void(0)"><i
                                        class="icon-edit"></i>

                                    <p>编辑</p>
                                </a></span> <span><a class="btn-red"
                                                     onclick="ajax_get_confirm('您确实要删除该分类吗?', 'se/storegoodsclass/del?id=${children.id}');"
                                                     href="javascript:void(0)"><i class="icon-trash"></i>

                                    <p>删除</p>
                                </a></span></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </c:forEach>

                </tbody>
                <tfoot>
                <tr>
                    <th class="tc"><input type="checkbox" class="checkall" id="all2"></th>
                    <th colspan="15"><label for="all2">全选</label>
                        <a confirm="您确实要删除该分类吗?" name="class_id" uri="se/storegoodsclass/dels?for=xml"
                           nc_type="batchbutton" class="ncsc-btn-mini" href="javascript:void(0)"><i
                                class="icon-trash"></i>删除</a></th>
                </tr>
                </tfoot>
                </c:if>
                <c:if test="${fn:length(P_PAGE_SHOW.content)==0}">
                    <tr class="no_data" style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                        <td colspan="10">没有符合条件的记录</td>
                    </tr>
                </c:if>
            </table>
            <style>
                .collapsed {
                    display: none;
                }
            </style>

            <fis:require id="shop:scripts/common_select.js"/>
            <fis:require id="common:widget/jquery/perfect-scrollbar.js"/>
            <fis:require id="common:widget/jquery/jquery.mousewheel.js"/>
            <fis:require id="shop:scripts/jqtreetable.js"/>

            <script>
                $(function () {
                    var map = [${map}];
                    var path = "${S_URL}/res/img/";
                    if (map.length > 0) {
                        var option = {
                            openImg: path + "treetable/tv-collapsable.gif",
                            shutImg: path + "treetable/tv-expandable.gif",
                            leafImg: path + "treetable/tv-item.gif",
                            lastOpenImg: path + "treetable/tv-collapsable-last.gif",
                            lastShutImg: path + "treetable/tv-expandable-last.gif",
                            lastLeafImg: path + "treetable/tv-item-last.gif",
                            vertLineImg: path + "treetable/vertline.gif",
                            blankImg: path + "treetable/blank.gif",
                            collapse: false,
                            column: 1,
                            striped: false,
                            highlight: false,
                            state: false
                        };
                        $("#treet1").jqTreeTable(map, option);
                    }
                });
            </script>


        </div>
    </div>

</div>