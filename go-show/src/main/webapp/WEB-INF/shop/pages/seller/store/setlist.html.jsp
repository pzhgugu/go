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
                    <c:if test="${P_SET!='set'}">class="normal"</c:if>><a href="${S_URL}/se/store/set/list">店铺设置</a>
                    </li>
                    <li
                            <c:if test="${P_SET=='slide'}">class="active" </c:if>
                    <c:if test="${P_SET!='slide'}">class="normal"</c:if>><a href="${S_URL}/se/store/slide">幻灯片设置</a>
                    </li>
                </ul>
            </div>
            <div class="ncsc-form-default">
                <form id="my_store_form" enctype="multipart/form-data" action="se/store/update?for=xml" method="post">
                    <input type="hidden" value="${P_CURRENT_STORE.id}" name="id">
                    <dl>
                        <dt>店铺等级：</dt>
                        <dd>
                            <p>${P_CURRENT_STORE.gradeName}</p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>主营商品：</dt>
                        <dd>
                            <textarea maxlength="50" class="textarea w400" rows="2"
                                      name="storeZy">${P_CURRENT_STORE.storeZy}</textarea>

                            <p class="hint">主营商品关键字（Tag）有助于搜索店铺时找到您的店铺<br>关键字最多可输入50字，请用","进行分隔，例如”男装,女装,童装”</p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>店铺logo：</dt>
                        <dd>
                            <input type="hidden" value="" name="store_old_label">

                            <div class="ncsc-upload-thumb store-logo">
                                <p>
                                    <c:if test="${empty P_CURRENT_STORE.storeLabel}">
                                        <i class="icon-picture"></i>
                                    </c:if>
                                    <c:if test="${!empty P_CURRENT_STORE.storeLabel}">
                                        <img nc_type="store_label"
                                             src="${S_URL}/att/download/${P_CURRENT_STORE.storeLabel}">
                                    </c:if>
                                </p>
                            </div>
                            <div class="ncsc-upload-btn"><a href="javascript:void(0);"><span>
          <input type="file" nc_type="change_store_label" id="storeLablePic" name="store_label" class="input-file"
                 size="1" hidefocus="true">
          </span>

                                <p><i class="icon-upload-alt"></i>图片上传</p>
                            </a></div>
                            <p class="hint">此处为您的店铺logo，将显示在店铺Logo栏里；<br><span style="color:orange;">建议使用宽200像素-高60像素内的GIF或PNG透明图片；点击下方"提交"按钮后生效。</span>
                            </p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>店铺条幅：</dt>
                        <dd>
                            <input type="hidden" value="" name="store_old_banner">

                            <div class="ncsc-upload-thumb store-banner">
                                <p><c:if test="${empty P_CURRENT_STORE.storeBanner}">
                                    <i class="icon-picture"></i>
                                </c:if>
                                    <c:if test="${!empty P_CURRENT_STORE.storeBanner}">
                                        <img nc_type="store_banner"
                                             src="${S_URL}/att/download/${P_CURRENT_STORE.storeBanner}">
                                    </c:if>
                                </p>
                            </div>
                            <div class="ncsc-upload-btn"><a href="javascript:void(0);"><span>
          <input type="file" nc_type="change_store_banner" id="storeBannerPic" name="store_banner" class="input-file"
                 size="1" hidefocus="true">
          </span>

                                <p><i class="icon-upload-alt"></i>图片上传</p>
                            </a></div>
                            <p class="hint">此处为您的店铺条幅，将显示在店铺导航上方的banner位置；<br><span style="color:orange;">建议使用宽1000像素*高250像素的图片；点击下方"提交"按钮后生效。</span>
                            </p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>QQ：</dt>
                        <dd>
                            <input type="text" value="${P_CURRENT_STORE.storeQQ}" id="storeQQ" name="storeQQ"
                                   class="w200 text">
                        </dd>
                    </dl>
                    <dl>
                        <dt>阿里旺旺：</dt>
                        <dd>
                            <input type="text" value="${P_CURRENT_STORE.storeWW}" id="storeWW" name="storeWW"
                                   class="text w200">
                        </dd>
                    </dl>
                    <dl>
                        <dt>SEO关键字：</dt>
                        <dd>
                            <p>
                                <input type="text" value="${P_CURRENT_STORE.storeKeywords}" name="storeKeywords"
                                       class="text w400">
                            </p>

                            <p class="hint">用于店铺搜索引擎的优化，关键字之间请用英文逗号分隔</p>
                        </dd>
                    </dl>
                    <dl>
                        <dt>SEO店铺描述：</dt>
                        <dd>
                            <p>
                                <textarea id="remark_input" class="textarea w400" rows="3"
                                          name="storeDescription">${P_CURRENT_STORE.storeDescription}</textarea>
                            </p>

                            <p class="hint">用于店铺搜索引擎的优化，建议120字以内</p>
                        </dd>
                    </dl>
                    <div class="bottom">
                        <label class="submit-border"><input type="submit" value="提交" class="submit"></label>
                    </div>
                </form>
            </div>
            <script type="text/javascript">
                var SITEURL = "${S_URL}";
                $(function () {

                    regionInit("region");
                    $('input[nc_type="change_store_banner"]').change(function () {
                        var src = getFullPath($(this)[0]);
                        $('img[nc_type="store_banner"]').attr('src', src);
                    });
                    $('input[nc_type="change_store_label"]').change(function () {
                        var src = getFullPath($(this)[0]);
                        $('img[nc_type="store_label"]').attr('src', src);
                    });
                    $('input[class="edit_region"]').click(function () {
                        $(this).css('display', 'none');
                        $('#area_id').val('');
                        $(this).parent().children('select').css('display', '');
                        $(this).parent().children('span').css('display', 'none');
                    });
                    jQuery.validator.addMethod("domain", function (value, element) {
                        return this.optional(element) || /^[\w\-]+$/i.test(value);
                    }, "");
                    $('#my_store_form').validate({
                        submitHandler: function (form) {
                            ajaxpost('my_store_form', '', '', 'onerror')
                        },
                        rules: {},
                        messages: {}
                    });

                    // ajax 修改店铺二维码
                    $('#a_store_code').click(function () {
                        $('#img_store_code').attr('src', '');
                        $.getJSON($(this).attr('href'), function (data) {
                            $('#img_store_code').attr('src', 'http://localhost/shopnc/data/upload/shop/store/' + data);
                        });
                        return false;
                    });

                });
            </script>
        </div>


    </div>
</div>
<fis:require id="shop:scripts/common_select.js"/>
<fis:require id="common:widget/ajaxfileupload/ajaxfileupload.js"/>
<fis:require id="common:widget/jquery/jquery.Jcrop.js"/>
<fis:require id="common:widget/jquery/jquery.mousewheel.js"/>
