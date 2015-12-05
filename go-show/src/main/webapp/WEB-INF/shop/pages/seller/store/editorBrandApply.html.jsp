<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<%--<div class="dialog_body" style="position: relative;">--%>
<%--<h3 class="dialog_head" style="cursor: move;"><span class="dialog_title"><span class="dialog_title_icon">编辑品牌</span></span><span class="dialog_close_button">X</span></h3>--%>
<div class="dialog_content" style="margin: 0px; padding: 0px;">
    <div class="eject_con">
        <div class="alert alert-error" id="warning"></div>
        <form id="brand_apply_form" action="${S_URL}/se/brandApply/brandSave" enctype="multipart/form-data"
              method="post" target="_parent">
            <%--<input name="form_submit" type="hidden" value="ok">--%>
            <input name="brandId" type="hidden" value="${BRAND_GOODS.brandId}">
            <input name="id" type="hidden" value="${BRAND_GOODS.id}">
            <dl>
                <dt><i class="required">*</i>品牌名称：</dt>
                <dd>
                    <input name="brandName" class="text" id="brandName" type="text" value="${BRAND_GOODS.brandName}">
                </dd>
            </dl>
            <dl>
                <dt>品牌类别：</dt>
                <dd id="gcategory">
                    <input name="classId" class="mls_id" type="hidden" value="${BRAND_GOODS.classId}">
                    <input name="brandClass" class="mls_name" type="hidden" value="${BRAND_GOODS.brandClass}">
                    <span>衬衫</span>
                    <input class="edit_gcategory" type="button" value="编辑">
                    <select class="valid" style="display: none;">
                        <option value="0">请选择...</option>
                        <c:forEach items="${GOODS_CLASS_LIST}" var="goodsClass">
                            <c:if test="${goodsClass.parent eq null}">
                                <option value="${goodsClass.gcId}">${goodsClass.gcName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
            <dl>
                <dt><i class="required">*</i>品牌图标：</dt>
                <dd>
                    <div>
                <span class="sign">
                     <c:if test="${!empty BRAND_GOODS.idAtt}">
                         <img src="${S_URL}/att/download/${BRAND_GOODS.idAtt}"/>
                     </c:if>
                <%--<img width="13" height="13" onload="javascript:DrawImage(this,150,50)" src="http://localhost/shopnc/data/upload/shop/brand/05009950165073152_small.gif" nc_type="logo1">--%>
                </span>
                    </div>
                    <%--<div class="ncsc-upload-btn"> <a href="javascript:void(0);"><span>--%>
                    <%--<input name="brand_pic" class="input-file" id="brand_pic" hidefocus="true" type="file" size="1" nc_type="logo">--%>
                    <%--</span>--%>
                    <%--<p><i class="icon-upload-alt"></i>图片上传</p>--%>
                    <%--</a> </div>--%>
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
                }
            },
            messages: {
                brandName: {
                    required: '<i class="icon-exclamation-sign"></i>请输入品牌名称',
                    rangelength: '<i class="icon-exclamation-sign"></i>品牌名称不能超过100个字符'
                }
            }
        });
        $('input[nc_type="logo"]').change(function () {
            var src = getFullPath($(this)[0]);
            $('img[nc_type="logo1"]').attr('src', src);
        });
    });

</script>