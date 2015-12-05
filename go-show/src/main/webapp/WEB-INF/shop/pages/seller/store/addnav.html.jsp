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
                    <li class="normal"><a href="${S_URL}/se/storenav/list">导航列表</a></li>
                    <c:if test="${empty P_CURRENT_ENTITY}">
                    <li class="active"><a href="${S_URL}/se/storenav/add">添加导航</a></li>
                </ul>
                </c:if>
                <c:if test="${!empty P_CURRENT_ENTITY}">
                    <li class="active"><a href="#">编辑导航</a></li>
                    </ul>
                </c:if>
            </div>
            <div class="ncsc-form-default">
                <form id="store_navigation_form" name="store_navigation_form" target="_parent"
                      action="${S_URL}/se/storenav/save" method="post">
                    <c:if test="${!empty P_CURRENT_ENTITY}"><input name="id" type="hidden"
                                                                   value="${P_CURRENT_ENTITY.id}"/></c:if>
                    <dl>
                        <dt><i class="required">*</i>导航名称：</dt>
                        <dd>
                            <input type="text" value="${P_CURRENT_ENTITY.snTitle}" name="snTitle"
                                   class="w150 text"><span></span>
                        </dd>
                    </dl>
                    <dl>
                        <dt>是否显示：</dt>
                        <dd>
                            <ul class="ncsc-form-radio-list">
                                <li>

                                    <label for="snIfShow_0"><input type="radio"
                                                                   <c:if test="${P_CURRENT_ENTITY.snIfShow==1}">checked="checked" </c:if>
                                                                   value="1" id="snIfShow_0" name="snIfShow"
                                                                   class="radio">
                                        是</label></li>
                                <li>
                                    <label for="snIfShow_1"><input type="radio"
                                                                   <c:if test="${P_CURRENT_ENTITY.snIfShow!=1}">checked="checked" </c:if>
                                                                   value="0" id="snIfShow_1" name="snIfShow"
                                                                   class="radio">
                                        否</label></li>
                            </ul>
                        </dd>
                    </dl>
                    <dl>
                        <dt>排序：</dt>
                        <dd>
                            <input type="text" value="${P_CURRENT_ENTITY.snSort}" name="snSort" class="w50 text">
                        </dd>
                    </dl>
                    <dl>
                        <dt>内容：</dt>
                        <dd>
               <textarea style="width: 100%; height: 480px; visibility: hidden; display: none;"
                         name="snContent" id="snContent">${P_CURRENT_ENTITY.snContent}
               </textarea>
                        </dd>
                    </dl>
                    <dl>
                        <dt>导航外链URL：</dt>
                        <dd>
                            <p>
                                <input type="text" value="${P_CURRENT_ENTITY.snUrl}" name="snUrl" class="w300 text">
                            </p>

                            <p class="hint">请填写包含http://的完整URL地址,如果填写此项则点击该导航会跳转到外链</p>

                        </dd>
                    </dl>
                    <dl>
                        <dt>新窗口打开：</dt>
                        <dd>
                            <ul class="ncsc-form-radio-list">
                                <li>
                                    <label for="snNewOpen_1"><input type="radio"
                                                                    <c:if test="${P_CURRENT_ENTITY.snNewOpen==1}">checked="checked" </c:if>
                                                                    value="1" id="snNewOpen_1" name="snNewOpen"
                                                                    class="radio">
                                        是</label></li>
                                <li>
                                    <label for="snNewOpen_0"><input type="radio"
                                                                    <c:if test="${P_CURRENT_ENTITY.snNewOpen!=1}">checked="checked" </c:if>
                                                                    value="0" id="snNewOpen_0" name="snNewOpen"
                                                                    class="radio">
                                        否</label></li>
                            </ul>
                        </dd>
                    </dl>
                    <div class="bottom">
                        <label class="submit-border"><input type="submit" value="提交" class="submit"></label>
                    </div>
                </form>
            </div>

        </div>

    </div>
</div>

<fis:require id="common:widget/kindeditor/kindeditor.js"/>
<script type="text/javascript">

    var KE;
    KindEditor.ready(function (K) {
        KE = K.create("textarea[name='snContent']", {
            items: ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', '|', 'selectall', 'clearhtml', 'quickformat', '|',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about'],
            uploadJson: SITEURL + '/ke/upload',
            allowFlashUpload: false,
            allowMediaUpload: false,
            allowFileManager: false,
            syncType: "form",
            afterCreate: function () {
                var self = this;
                self.sync();
            },
            afterChange: function () {
                var self = this;
                self.sync();
            },
            afterBlur: function () {
                var self = this;
                self.sync();
            }
        });
        KE.appendHtml = function (id, val) {
            this.html(this.html() + val);
            if (this.isCreated) {
                var cmd = this.cmd;
                cmd.range.selectNodeContents(cmd.doc.body).collapse(false);
                cmd.select();
            }
            return this;
        }
    });


    $(document).ready(function () {
        //页面输入内容验证
        $('#store_navigation_form').validate({
            errorPlacement: function (error, element) {
                var error_td = element.parent('dd').children('span');
                error_td.append(error);
            },
            submitHandler: function (form) {
                ajaxpost('add_form', '', '', 'onerror')
            },
            rules: {
                snTitle: {
                    required: true,
                    maxlength: 10
                }
            },
            messages: {
                snTitle: {
                    required: '<i class="icon-exclamation-sign"></i>导航名称不能为空',
                    maxlength: '<i class="icon-exclamation-sign"></i>导航名称最多10个字'
                }
            }
        });
    });
</script>
