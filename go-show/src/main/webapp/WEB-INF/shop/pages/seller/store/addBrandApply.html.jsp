<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<%--<div class="dialog_wrapper ui-draggable" id="fwin_my_goods_brand_apply"--%>
<%--style="left: 551.5px; top: 189px; width: 480px; position: absolute; z-index: 1100;">--%>
<%--<div class="dialog_body" style="position: relative;"><h3 class="dialog_head" style="cursor: move;"><span--%>
<%--class="dialog_title"><span class="dialog_title_icon">品牌申请</span></span><span--%>
<%--class="dialog_close_button">X</span></h3>--%>

<div class="dialog_content" style="margin: 0px; padding: 0px;">
    <div class="eject_con">
        <div class="alert alert-error" id="warning"></div>
        <form id="brand_apply_form" action="${S_URL}/se/brandApply/brandSave"
              enctype="multipart/form-data" method="post" target="_parent">
            <dl>
                <dt><i class="required">*</i>品牌名称：</dt>
                <dd>
                    <input name="brandName" class="text" id="brandName" type="text" value="">
                </dd>
            </dl>
            <dl>
                <dt>品牌类别：</dt>
                <dd id="gcategory">
                    <input name="classId" class="mls_id" type="hidden" value="">
                    <input name="brandClass" class="mls_name" type="hidden" value="">
                    <select class="valid" name="className">
                        <option value="">请选择...</option>
                        <c:forEach items="${GOODS_CLASS_LIST}" var="goodsClass">
                            <c:if test="${goodsClass.parent eq null}">
                                <option value="${goodsClass.id}">${goodsClass.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt><i class="required">*</i>品牌图标：</dt>
                <dd>
                    <%--<div><span class="sign">--%>
                    <%--<img width="150" height="50"--%>
                    <%--onload="javascript:DrawImage(this,150,50)"--%>
                    <%--src="http://localhost/shopnc/data/upload/shop/common/default_brand_image.gif"--%>
                    <%--nc_type="logo1"></span></div>--%>
                    <%--<div class="ncsc-upload-btn"><a href="javascript:void(0);"><span>--%>
                    <%--<input name="idAtt" class="input-file" id="idAtt" hidefocus="true" type="file" size="1"  nc_type="logo">--%>
                    <%--</span>--%>

                    <%--<p><i class="icon-upload-alt"></i>图片上传</p>--%>
                    <%--</a>--%>
                    <%--</div>--%>
                    <div><input id="idAtt1" name="idAtt1" type="file"></div>
                    <p class="hint">建议上传大小为150x50的品牌图片。<br>申请品牌的目的是方便买家通过品牌索引页查找商品，申请时请填写品牌所属的类别，方便平台归类。在平台审核前，您可以编辑或撤销申请。
                    </p>
                </dd>
            </dl>
            <div class="bottom">
                <label class="submit-border"><input class="submit" type="submit" value="提交"></label>
            </div>
        </form>
    </div>

</div>
<%--</div>--%>
<div style="clear: both; display: block;"></div>
<%--</div>--%>

<script type="text/javascript">

    $(function () {
        gcategoryInit('gcategory');
        $('#brand_apply_form').validate({
            errorLabelContainer: $('#warning'),
            invalidHandler: function (form, validator) {
                $('#warning').show();
            },
//        submitHandler:function(form){
//            ajaxpost('brand_apply_form', '', '', 'onerror')
//        },
            rules: {
                brandName: {
                    required: true,
                    rangelength: [0, 100]
                },
                idAtt1: {
                    required: true
                }

            },
            messages: {
                brandName: {
                    required: '<i class="icon-exclamation-sign"></i>请输入品牌名称',
                    rangelength: '<i class="icon-exclamation-sign"></i>品牌名称不能超过100个字符'
                },
                idAtt1: {
                    required: '<i class="icon-exclamation-sign"></i>请上传品牌图标'
                }
            }
        });
        $('input[nc_type="logo"]').change(function () {
            var src = getFullPath($(this)[0]);
            $('img[nc_type="logo1"]').attr('src', src);
        });
    });

</script>