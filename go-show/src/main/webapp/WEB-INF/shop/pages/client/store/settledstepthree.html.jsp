<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta charset="utf-8">
        <title>商家入驻</title>
        <fis:require id="shop:styles/base.css"/>
        <fis:require id="shop:scripts/dialog/dialog.css"/>
        <fis:require id="shop:styles/store_joinin.css"/>

        <fis:styles/>
    </head>


    <body>
    <fis:block url="shop:pages/client/store/header.html.jsp"/>

    <div class="container wrapper">
            <fis:block url="shop:pages/client/store/sidebar.html.jsp"/>

        <div class="main">
            <!-- 店铺信息 -->

            <div class="apply-store-info" id="apply_store_info">
                <div class="apply-company-info" id="apply_company_info">
                    <div class="note"><i></i>店铺经营类目为商城商品分类，请根据实际运营情况添加一个或多个经营类目。</div>
                    <form method="post" action="${S_URL}/storejoinin/step4" id="form_store_info">
                        <table cellspacing="0" cellpadding="0" border="0" class="all">
                            <thead>
                            <tr>
                                <th colspan="20">店铺经营信息</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <th class="w150"><i>*</i>商家帐号：</th>
                                <td><input type="text" class="w200" name="sellerName" id="sellerName">
                                    <span></span>

                                    <p class="emphasis">此帐号为日后登录并管理商家中心时使用，注册后不可修改，请牢记。</p></td>
                            </tr>
                            <tr>
                                <th class="w150"><i>*</i>店铺名称：</th>
                                <td><input type="text" class="w200" name="storeName" id="storeName">
                                    <span></span>

                                    <p class="emphasis">店铺名称注册后不可修改，请认真填写。</p></td>
                            </tr>
                            <tr>
                                <th><i>*</i>店铺等级：</th>
                                <td><select id="sgId" name="sgId">
                                    <option value="0">请选择</option>
                                    <c:forEach items="${P_STOREGRADE}" var="storeGrade">
                                        <option data-explain="${storeGrade.description}"
                                                value="${storeGrade.id}">${storeGrade.name}</option>
                                    </c:forEach>
                                </select>
                                    <input type="hidden" name="sgName" id="sgName">
                                    <span></span>

                                    <div class="grade_explain" id="grade_explain"></div>
                                </td>
                            </tr>
                            <tr>
                                <th><i>*</i>店铺分类：</th>
                                <td><select id="scId" name="scId">
                                    <option value="0">请选择</option>
                                    <c:forEach items="${P_PARENT_STORECLASS}" var="storeClass">
                                        <option value="${storeClass.id}">&nbsp;&nbsp;${storeClass.alias}</option>
                                        <c:if test="${!empty storeClass.children}">
                                            <c:forEach items="${storeClass.children}" var="storeClassChildren">

                                                <option value="${storeClassChildren.id}">
                                                    &nbsp;&nbsp;&nbsp;&nbsp;${storeClassChildren.alias}</option>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                    <input type="hidden" name="scName" id="scName">
                                    <span></span>

                                    <p class="emphasis">请根据您所经营的内容认真选择店铺分类，注册后商家不可自行修改。</p></td>
                            </tr>
                            <tr>
                                <th><i>*</i>经营类目：</th>
                                <td><a class="btn" id="btn_select_category" href="###"
                                       style="display: none;">+选择添加类目</a>

                                    <div style="" id="gcategory">
                                        <select id="gcategory_class1" class="valid">
                                            <option value="0">请选择</option>
                                            <c:forEach items="${P_PARENT_GOODSCLASS}" var="goodsClass">
                                                <option value="${goodsClass.id}">${goodsClass.alias}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="button" value="确认" id="btn_add_category">
                                        <input type="button" value="取消" id="btn_cancel_category">
                                    </div>
                                    <input type="hidden" name="store_class" id="store_class">
                                    <span></span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <table cellspacing="0" cellpadding="0" border="0" class="type" id="table_category">
                                        <thead>
                                        <tr>
                                            <th>一级类目</th>
                                            <th>二级类目</th>
                                            <th>三级类目</th>
                                            <th>操作</th>
                                        </tr>
                                        </thead>
                                    </table>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="20">&nbsp;</td>
                            </tr>
                            </tfoot>
                        </table>
                    </form>
                    <div class="bottom"><a class="btn" href="javascript:;" id="btn_apply_store_next">下一步</a></div>
                </div>
            </div>
        </div>

            <fis:block url="cms:widget/tpl/button.html.jsp"/>
            <fis:block url="cms:widget/tpl/footer.html.jsp"/>


            <fis:require id="common:widget/jquery/jquery.js"/>
            <fis:require id="common:widget/jquery/jquery-browser.js"/>
            <fis:require id="common:widget/jquery/waypoints.js"/>
            <fis:require id="common:widget/jquery/jquery.validation.js"/>
            <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
            <fis:require id="shop:scripts/area_array.js"/>
            <fis:require id="shop:scripts/common.js"/>
            <fis:require id="shop:scripts/common_select.js"/>
            <fis:scripts/>
        <script type="text/javascript">
            var SITEURL = '${S_URL}';
            $(document).ready(function () {
                gcategoryInit("gcategory");

                jQuery.validator.addMethod("sellerName_exist", function (value, element, params) {
                    var result = true;
                    $.ajax({
                        type: "GET",
                        url: '${S_URL}/storejoinin/verification/sellerName',
                        async: false,
                        data: {sellerName: $('#sellerName').val()},
                        success: function (data) {
                            if (data == true) {
                                $.validator.messages.sellerName_exist = "卖家帐号已存在";
                                result = false;
                            }
                        }
                    });
                    return result;
                }, '');

                jQuery.validator.addMethod("storeName_exist", function (value, element, params) {
                    var result = true;
                    $.ajax({
                        type: "GET",
                        url: '${S_URL}/storejoinin/verification/storeName',
                        async: false,
                        data: {storeName: $('#storeName').val()},
                        success: function (data) {
                            if (data == true) {
                                $.validator.messages.storeName_exist = "店铺名称已存在";
                                result = false;
                            }
                        }
                    });
                    return result;
                }, '');

                $('#form_store_info').validate({
                    errorPlacement: function (error, element) {
                        element.nextAll('span').first().after(error);
                    },
                    rules: {
                        sellerName: {
                            required: true,
                            maxlength: 50,
                            sellerName_exist: true
                        },
                        storeName: {
                            required: true,
                            maxlength: 50,
                            storeName_exist: true
                        },
                        sgName: {
                            required: true
                        },
                        scName: {
                            required: true
                        },
                        store_class: {
                            required: true,
                            min: 1
                        }
                    },
                    messages: {
                        sellerName: {
                            required: '请填写卖家用户名',
                            maxlength: jQuery.validator.format("最多{0}个字")
                        },
                        storeName: {
                            required: '请填写店铺名称',
                            maxlength: jQuery.validator.format("最多{0}个字")
                        },
                        sgName: {
                            required: '请选择店铺等级'
                        },
                        scName: {
                            required: '请选择店铺分类'
                        },
                        store_class: {
                            required: '请选择经营类目',
                            min: '请选择经营类目'
                        }
                    }
                });

                $('#btn_select_category').on('click', function () {
                    $('#gcategory').show();
                    $('#btn_select_category').hide();
                    $('#gcategory_class1').val(0).nextAll("select").remove();
                });

                $('#btn_add_category').on('click', function () {
                    var tr_category = '<tr class="store-class-item">';
                    var category_id = '';
                    var category_name = '';
                    var class_count = 0;
                    var validation = true;
                    $('#gcategory').find('select').each(function () {
                        if (parseInt($(this).val(), 10) > 0) {
                            var name = $(this).find('option:selected').text();
                            tr_category += '<td>';
                            tr_category += name;
                            tr_category += '</td>';
                            category_id += $(this).val() + ',';
                            category_name += name + ',';
                            class_count++;
                        } else {
                            validation = false;
                        }
                    });
                    if (validation) {
                        for (; class_count < 3; class_count++) {
                            tr_category += '<td></td>';
                        }
                        tr_category += '<td><a nctype="btn_drop_category" href="javascript:;">删除</a></td>';
                        tr_category += '<input name="store_class_ids[]" type="hidden" value="' + category_id + '" />';
                        tr_category += '<input name="store_class_names[]" type="hidden" value="' + category_name + '" />';
                        tr_category += '</tr>';
                        $('#table_category').append(tr_category);
                        $('#gcategory').hide();
                        $('#btn_select_category').show();
                        select_store_class_count();
                    } else {
                        showError('请选择分类');
                    }
                });

                $('#table_category').on('click', '[nctype="btn_drop_category"]', function () {
                    $(this).parent('td').parent('tr').remove();
                    select_store_class_count();
                });

                // 统计已经选择的经营类目
                function select_store_class_count() {
                    var store_class_count = $('#table_category').find('.store-class-item').length;
                    $('#store_class').val(store_class_count);
                }

                $('#btn_cancel_category').on('click', function () {
                    $('#gcategory').hide();
                    $('#btn_select_category').show();
                });

                $('#sgId').on('change', function () {
                    if (!!$(this).val()) {
                        $('#grade_explain').text($(this).find('option:selected').attr('data-explain'));
                        $('#sgName').val($(this).find('option:selected').text());
                    } else {
                        $('#sgName').val('');
                    }
                });

                $('#scId').on('change', function () {
                    if (!!$(this).val()) {
                        $('#scName').val($(this).find('option:selected').text());
                    } else {
                        $('#scName').val('');
                    }
                });


                $('#btn_apply_store_next').on('click', function () {
                    if ($('#form_store_info').valid()) {
                        $('#form_store_info').submit();
                    }
                });
            });
        </script>
    </body>
</fis:html>