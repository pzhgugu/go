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

            <div class="tabmenu">
                <ul class="tab pngFix">

                    <li
                            <c:if test="${P_SET=='set'}">class="active" </c:if>
                            <c:if test="${P_SET!='set'}">class="normal"</c:if>><a
                            href="${S_URL}/se/store/set/list">店铺设置</a>
                    </li>
                    <li
                            <c:if test="${P_SET=='slide'}">class="active" </c:if>
                            <c:if test="${P_SET!='slide'}">class="normal"</c:if>><a
                            href="${S_URL}/se/store/slide">幻灯片设置</a>
                    </li>
                </ul>
            </div>


            <div class="ncsc-form-default">
                <div class="alert">
                    <ul>
                        <li>1. 最多可上传5张幻灯片图片。</li>
                        <li>2.
                            支持jpg、jpeg、gif、png格式上传，建议图片宽度790px、高度在300px到400px之间、大小1.00M以内的图片。提交2~5张图片可以进行幻灯片播放，一张图片没有幻灯片播放效果。
                        </li>
                        <li>3. 操作完成以后，按“提交”按钮，可以在当前页面进行幻灯片展示。</li>
                        <li>4. 跳转链接必须带有<b style="color:red;">“http://”</b></li>
                    </ul>
                </div>


                <div class="flexslider">
                    <ul class="slides">
                        <c:if test="${!empty P_SLIDEIMAGE_LIST}">
                            <c:forEach items="${P_SLIDEIMAGE_LIST}" var="slideImage">
                                <c:if test="${!empty slideImage.attId}">
                                    <li>
                                        <c:if test="${!empty slideImage.url}"><a href="${slideImage.url}"></c:if>
                                        <img src="${S_URL}/att/download/${slideImage.attId}">
                                        <c:if test="${!empty slideImage.url}"></a></c:if>
                                    </li>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty P_SLIDEIMAGE_LIST}">
                            <li><img src="${S_URL}/res/img/store/slides/f01.jpg"></li>
                            <li><img src="${S_URL}/res/img/store/slides/f02.jpg"></li>
                            <li><img src="${S_URL}/res/img/store/slides/f03.jpg"></li>
                            <li><img src="${S_URL}/res/img/store/slides/f04.jpg"></li>
                        </c:if>
                    </ul>
                </div>


                <form onsubmit="ajaxpost('store_slide_form', '', '', 'onerror');return false;" method="post"
                      id="store_slide_form" action="se/store/slide/save?for=xml">
                    <input type="hidden" value="ok" name="form_submit">
                    <!-- 图片上传部分 -->
                    <ul id="goods_images" class="ncsc-store-slider">

                        <c:forEach var="i" begin="1" end="5">
                            <li nc_type="handle_pic" id="thumbnail_${i}">
                                <div class="picture" nctype="file_${i}">
                                    <c:if test="${empty P_SLIDEIMAGE_LIST[i-1].attId}">
                                        <i class="icon-picture"></i>
                                    </c:if>
                                    <c:if test="${!empty P_SLIDEIMAGE_LIST[i-1].attId}">
                                        <img nctype="file_${i}"
                                             src="${S_URL}/att/download/${P_SLIDEIMAGE_LIST[i-1].attId}"/>
                                    </c:if>
                                    <input type="hidden" name="image_path[]" nctype="file_${i}"
                                           value="${P_SLIDEIMAGE_LIST[i-1].attId}"/>
                                </div>
                                <div class="url">
                                    <label>跳转URL...</label>
                                    <input type="text" class="text w150" name="image_url[]"
                                           <c:if test="${empty P_SLIDEIMAGE_LIST[i-1].url}">value="http://"</c:if>
                                            <c:if test="${!empty P_SLIDEIMAGE_LIST[i-1].url}">
                                                value="${P_SLIDEIMAGE_LIST[i-1].url}"
                                            </c:if>/>
                                </div>
                                <div class="ncsc-upload-btn"><a href="javascript:void(0);"><span>
          <input type="file" hidefocus="true" size="1" class="input-file" name="file_${i}" id="file_${i}"/>
          </span>

                                    <p><i class="icon-upload-alt"></i>图片上传</p>
                                </a></div>

                            </li>
                        </c:forEach>
                    </ul>
                    <div class="bottom"><label class="submit-border"><input type="submit" value="提交"
                                                                            class="submit"></label></div>
                </form>


            </div>

            <!-- 引入幻灯片JS -->
            <script type="text/javascript">
                var SITEURL = "${S_URL}";
                var UPLOAD_SITE_URL = '${S_URL}/att/download';
                var SHOP_TEMPLATES_URL = '${S_URL}/res';
            </script>
        </div>


    </div>
</div>

<fis:require id="common:widget/ajaxfileupload/ajaxfileupload.js"/>
<fis:require id="common:widget/jquery/jquery.flexslider-min.js"/>
<fis:require id="shop:scripts/store_slide.js"/>
