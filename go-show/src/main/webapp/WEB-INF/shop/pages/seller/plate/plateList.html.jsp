<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>


<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>

        <div id="mainContent" class="main-content">

            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="active"><a href="${S_URL}/se/plate/list">版式列表</a></li></ul>
                <a title="添加关联版式" class="ncsc-btn ncsc-btn-green" href="${S_URL}/se/plate/edit/page">添加关联版式</a>
            </div>

<!--帮助开始-->
            <div class="alert mt15 mb5"><strong>操作提示：</strong>
                <ul>
                    <li>1、关联板式可以把预设内容插入到商品描述的顶部或者底部，方便商家对商品描述批量添加或修改。</li>
                </ul>
            </div>
<!--帮助结束-->

<!--搜索开始-->
            <form method="get" >
                <table class="search-form">
                    <tbody><tr>
                        <td>&nbsp;</td>

                        <th>版式位置</th>
                        <td class="w80">
                            <select name="p_position">
                                <option value="">请选择</option>
                                <option value="0">底部</option>
                                <option value="1">顶部</option>
                            </select>
                        </td><th>版式名称</th>
                        <td class="w160"><input type="text" value="" name="p_name" class="text w150"></td>
                        <td class="w70 tc"><label class="submit-border"><input type="submit" value="搜索" class="submit"></label></td>
                    </tr>
                    </tbody></table>
            </form>
<!--搜索结束-->

<!--列表开始-->
            <c:if test="${fn:length(P_PLATE_LIST)==0}">
                <div class="warning-option">
                    <i class="icon-warning-sign">
                        &nbsp;
                    </i>
        <span>
          暂无符合条件的数据记录
        </span>
                </div>
            </c:if>
<c:if test="${fn:length(P_PLATE_LIST)>0}">
            <table class="ncsc-table-style">
                <thead>
                <tr>
                    <th class="w30"></th>
                    <th class="tl">版式名称</th>
                    <th class="w200">版式位置</th>
                    <th class="w110">操作</th>
                </tr>
                <tr>
                    <td class="tc"><input type="checkbox" class="checkall" id="all"></td>
                    <td colspan="10"><label for="all">全选</label>
                        <a class="ncsc-btn-mini" confirm="您确定要删除吗?" name="p_id" uri="se/plate/del?for=a" nc_type="batchbutton" href="javascript:void(0);"><i class="icon-trash"></i>删除</a>
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${P_PLATE_LIST}" var="plate">
                <tr class="bd-line">
                    <td class="tc"><input type="checkbox" value="${plate.id}" class="checkitem tc"></td>
                    <td class="tl">${plate.plateName}</td>
                    <td><c:if test="${plate.platePosition==1}">顶部</c:if><c:if test="${plate.platePosition==0}">底部</c:if></td>
                    <td class="nscs-table-handle">
                        <span><a class="btn-blue" href="${S_URL}/se/plate/edit/page?id=${plate.id}"><i class="icon-edit"></i><p>编辑</p></a></span>
                        <span><a class="btn-red" onclick="ajax_get_confirm('您确定要删除吗?', 'se/plate/del?p_id=${plate.id}');" href="javascript:void(0)"><i class="icon-trash"></i><p>删除</p></a></span>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <th class="tc"><input type="checkbox" class="checkall" id="all"></th>
                    <th colspan="10"><label for="all">全选</label>
                        <a class="ncsc-btn-mini" confirm="您确定要删除吗?" name="p_id" uri="se/plate/del?for=a"  nc_type="batchbutton"  href="javascript:void(0);"><i class="icon-trash"></i>删除</a>
                    </th>
                </tr>
                <tr>
                    <td colspan="20">
                        <fis:block url="shop:widget/tpl/pagination.html.jsp" >
                            <fis:param name="page" value="P_PAGE_SHOW"/>
                            <fis:param name="paginationSize" value="9"/>
                            <fis:param name="reqName" value="curpage"/>
                            <fis:param name="url" value="${S_URL}/se/plate/list"/>
                        </fis:block>
                    </td>
                </tr>
                </tfoot>
            </table>
</c:if>
<!--列表结束-->
        </div>


    </div>
</div>
