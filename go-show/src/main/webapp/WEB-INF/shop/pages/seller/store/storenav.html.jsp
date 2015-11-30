<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftStore.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>

        <div id="mainContent" class="main-content">

            <div class="wrap">
                <div class="tabmenu">
                    <ul class="tab pngFix">
                        <li class="active"><a href="${S_URL}/se/storenav/list">导航列表</a></li>
                    </ul>
                    <a title="添加导航" class="ncsc-btn ncsc-btn-green" href="${S_URL}/se/storenav/add">添加导航</a></div>
                <table class="ncsc-table-style">
                    <thead>
                    <tr>
                        <th class="w60">排序</th>
                        <th class="tl">导航名称</th>
                        <th class="w120">是否显示</th>
                        <th class="w110">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <c:if test="${!empty P_CURRENT_LIST}">
                        <c:forEach items="${P_CURRENT_LIST}" var="nav">
                    <tr class="bd-line">
                        <td>${nav.snSort}</td>
                        <td class="tl">
                            <dl class="goods-name">
                                <dt><a target="_blank" href="${nav.snUrl}">${nav.snTitle}</a></dt>
                            </dl>
                        </td>
                        <td>是</td>
                        <td class="nscs-table-handle"><span><a class="btn-blue"
                                                               href="${S_URL}/se/storenav/add?id=${nav.id}"><i
                                class="icon-edit"></i>

                            <p> 编辑</p>
                        </a></span><span> <a class="btn-red" data-sn-id="${nav.id}" nctype="btn_del"
                                             href="javascript:;"><i class="icon-trash"></i>

                            <p>删除</p>
                        </a></span></td>
                    </tr>

                    </c:forEach>
                    </c:if>
                    <c:if test="${empty P_CURRENT_LIST}">
                        <td class="norecord" colspan="20">
                            <div class="warning-option"><i class="icon-warning-sign"></i><span>暂无符合条件的数据记录</span></div>
                        </td>
                    </c:if>
                    </tr>
                    </tbody>
                </table>
            </div>
            <form action="se/storenav/del?for=xml" method="post" id="del_form">
                <input type="hidden" name="sn_id" id="del_sn_id">
            </form>
            <script type="text/javascript">
                $(document).ready(function () {
                    $('[nctype="btn_del"]').on('click', function () {
                        var sn_id = $(this).attr('data-sn-id');
                        if (confirm('确认删除？')) {
                            $('#del_sn_id').val(sn_id);
                            ajaxpost('del_form', '', '', 'onerror')
                        }
                    });
                });
            </script>
        </div>


    </div>
</div>
