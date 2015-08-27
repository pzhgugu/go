<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>

<style type="text/css">
    .ncsc-form-default dl dt { width: 16%;}
    .ncsc-form-default dl dd { width: 82%;}
</style>
<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>

        <div id="mainContent" class="main-content">

            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="normal"><a href="${S_URL}/se/plate/list">版式列表</a></li>
                    <li <c:if test="${!empty P_STORE_PLATE}">class="normal"</c:if><c:if test="${empty P_STORE_PLATE}">class="active"</c:if>><a href="${S_URL}/se/plate/edit/page">添加版式</a></li>
                    <c:if test="${!empty P_STORE_PLATE}">
                        <li class="active"><a href="#">编辑版式</a></li>
                    </c:if>
                </ul>
            </div>

            <!--表单开始-->
            <div class="ncsc-form-default">
                <form id="plate_form" action="se/plate/save?for=form" method="post">
                    <input type="hidden" value='<c:if test="${!empty P_STORE_PLATE}">${P_STORE_PLATE.id}</c:if>' name="id">

                    <dl>
                        <dt><i class="required">*</i>版式名称：</dt>
                        <dd>
                            <input type="text" id="plateName" value='<c:if test="${!empty P_STORE_PLATE}">${P_STORE_PLATE.plateName}</c:if>' name="plateName" class="text w200">
                            <p class="hint">请输入10个字符内的名称，方便商品发布 / 编辑时选择使用。</p>
                        </dd>
                    </dl>
                    <dl>
                        <dt><i class="required">*</i>版式位置：</dt>
                        <dd id="gcategory">
                            <ul class="ncsc-form-radio-list">
                                <li><label><input type="radio" <c:if test="${empty P_STORE_PLATE||P_STORE_PLATE.platePosition==1}">checked="checked" </c:if> class="radio" value="1" name="platePosition">顶部</label></li>
                                <li><label><input type="radio" <c:if test="${!empty P_STORE_PLATE&&P_STORE_PLATE.platePosition==0}">checked="checked" </c:if>  class="radio" value="0" name="platePosition">底部</label></li>
                            </ul>
                            <p class="hint">选择广联版式插入到页面中的位置，选择“顶部”为商品详情上方内容，“底部”为商品详情下方内容。</p>
                        </dd>
                    </dl>
                    <dl>
                        <dt><i class="required">*</i>版式内容：</dt>
                        <dd>
<textarea style="width: 100%; height: 480px; visibility: hidden; display: none;"
          name="plateContent" id="g_body"><c:if test="${!empty P_STORE_PLATE}">${P_STORE_PLATE.plateContent}</c:if>
                </textarea>
                            <div class="hr8">
                                <a href="${S_URL}/se/goods/addstep/two/imagesdesc" nctype="show_desc"
                                   class="ncsc-btn">
                                    <i class="icon-picture">
                                    </i>
                                    插入相册图片
                                </a>
                                <a style="display: none;" class="ncsc-btn ml5" nctype="del_desc" href="javascript:void(0);">
                                    <i class=" icon-circle-arrow-up">
                                    </i>
                                    关闭相册
                                </a>
                                <p>
                                </p>
                                <p id="des_demo">
                                </p>
                            </div>
                        </dd>
                    </dl>
                    <div class="bottom">
                        <label class="submit-border"><input type="submit" value="提交" class="submit"></label>
                    </div>
                </form>
            </div>
            <!--表单结束-->

        </div>


    </div>
</div>
<fis:require id="common:widget/jquery/jquery.ajaxContent.pack.js"/>
<fis:require id="common:widget/kindeditor/kindeditor.js" />
<fis:require id="shop:scripts/plateAdd.js" />