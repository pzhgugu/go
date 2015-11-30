<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftStore.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>
        <div class="main-content" id="mainContent">
            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="active"><a href="${S_URL}/se/brandApply/addBrandApply">品牌申请</a></li>
                </ul>
                <a class="ncsc-btn ncsc-btn-green" href="javascript:void(0)" uri="${S_URL}/se/brandApply/addBrandApply"
                   dialog_width="480" dialog_id="my_goods_brand_apply" dialog_title="品牌申请" nc_type="dialog">品牌申请</a>
            </div>
            <table class="search-form">
                <form method="get" action="${S_URL}/se/brandApply/list">
                    <%--<input name="act" type="hidden" value="store_brand">--%>
                    <%--<input name="op" type="hidden" value="brand_list">--%>
                    <tbody>
                    <tr>
                        <td>&nbsp;</td>
                        <th>品牌名称</th>
                        <td class="w160"><input name="brandName" class="text" type="text" value=""></td>
                        <td class="w70 tc"><label class="submit-border"><input class="submit" type="submit" value="搜索"></label>
                        </td>
                    </tr>

                    </tbody>
                </form>
            </table>
            <table class="ncsc-table-style">
                <thead>
                <tr>
                    <th class="w150">品牌图标</th>
                    <th>品牌名称</th>
                    <th>所属类别</th>
                    <th>申请状态</th>
                    <th class="w100">操作</th>
                </tr>
                </thead>

                <tbody>
                <c:if test="${fn:length(P_BRAND_GOODS.content)>0}">
                <c:forEach items="${P_BRAND_GOODS.content}" var="brand">
                    <tr class="bd-line">
                        <td>
                                <%--<img width="13" height="13" onload="javascript:DrawImage(this,88,44);" src="http://localhost/shopnc/data/upload/shop/brand/05009950165073152_small.gif">--%>
                            <c:if test="${!empty brand.logoImage}">
                                <img src="${S_URL}/att/download/${brand.logoImage}"
                                     style="width: 100px; height: 20px;"/>
                            </c:if>
                        </td>
                        <td>${brand.brandName}</td>
                        <td>${brand.className}</td>
                        <td>
                            <c:choose>
                                <c:when test='${brand.brandApply=="0"}'>
                                    申请中
                                </c:when>
                                <c:when test='${brand.brandApply=="1"}'>
                                    通过
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="nscs-table-handle">
                            <c:if test="${brand.brandApply==0}">
                        <span>
                        <a class="btn-blue" href="javascript:void(0)"
                           uri="${S_URL}/se/brandApply/editorBrandApply?brandId=${brand.id}" dialog_width="480"
                           dialog_id="my_goods_brand_edit" dialog_title="编辑品牌" nc_type="dialog"><i
                                class="icon-edit"></i>

                            <p>编辑</p></a>
                    </span>
                                <span><a class="btn-red"
                                         onclick="ajax_get_confirm('您确定要删除吗?', 'se/brandApply/brandDelete?brandId=${brand.id}');"
                                         href="javascript:void(0)"><i class="icon-trash"></i>

                                    <p>删除</p></a></span>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>

                <tfoot>
                <tr>
                    <td colspan="20">
                            <%--<div class="pagination"><ul><li><span>首页</span></li><li><span>上一页</span></li><li><span class="currentpage">1</span></li><li><span>下一页</span></li><li><span>末页</span></li></ul></div>--%>
                        <fis:block url="shop:widget/tpl/pagination.html.jsp">
                            <fis:param name="page" value="P_BRAND_GOODS"/>
                            <fis:param name="paginationSize" value="9"/>
                            <fis:param name="reqName" value="curpage"/>
                            <fis:param name="url" value="${S_URL}/se/brandApply/list"/>
                        </fis:block>
                    </td>
                </tr>
                </tfoot>
                </c:if>
                <c:if test="${fn:length(P_BRAND_GOODS.content)==0}">
                    <tr class="no_data" style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                        <td colspan="10">没有符合条件的记录</td>
                    </tr>
                    </tbody>
                </c:if>
            </table>
        </div>
    </div>
</div>