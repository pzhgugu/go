<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta charset="utf-8">
        <fis:require id="shop:styles/base.css"/>
        <fis:require id="shop:styles/seller_center.css"/>
        <fis:out id="common:widget/font-awesome/css/font-awesome.css"/>
        <!--[if IE 7]>
        <fis:out id="common:widget/font-awesome/css/font-awesome-ie7.css"/>
        <![endif]-->

        <fis:styles/>
        <style type="text/css">
            body {
                background-color: transparent;
            }

            .evo-colorind, .evo-colorind-ie, .evo-colorind-ff {
                width: 21px;
                height: 28px;
                border: solid #CCC;
                border-width: 1px 1px 1px 0;
                float: right;
            }

            .iframe-box {
                width: 100%;
                max-height: 550px;
                overflow: hidden;
                position: relative;
            }

            .iframe-content {
                width: 100%;
            }
        </style>
    </head>

    <body>
    <div id="append_parent"></div>
    <div id="ajaxwaitid"></div>
    <form id="spec_form" method="post" action="se/spec/save?for=xml">
        <input type="hidden" name="spId" value="${P_GOODS_SPEC.id}"/>
        <input type="hidden" name="gcId" value="${P_GOODS_CLASS.id}"/>

        <div class="ncsc-spec-info" style=" width: 960px;">
            <span>规格名称：${P_GOODS_SPEC.spName}</span><span>所属分类：${P_GOODS_CLASS.name}</span><a href="javascript:void(0);"
                                                                                              nctype="add_sv"
                                                                                              class="ncsc-btn"><i
                class="icon-th-list"></i>添加规格值</a></div>
        <table class="ncsc-table-style" style="width: 960px;">
            <thead>
            <tr>
                <th class="w80">排序</th>
                <th class="tl">规格值名称</th>
                <c:if test="${DEFAULT_SPEC_COLOR==P_GOODS_SPEC.spName}">
                    <th class="w300 tl">颜色色块</th>
                </c:if>
                <th class="w110">操作</th>
            </tr>
            </thead>
        </table>
        <div id="iframe" class="iframe-box" style=" width: 960px;">
            <div class="iframe-content">
                <table class="ncsc-table-style">
                    <tbody nctype="spec_tbody">
                    <c:forEach items="${P_SPECVALUE_LIST}" var="specValue" varStatus="status">
                        <tr class="bd-line">
                            <td class="w80"><input type="text" class="text w40" name="specValues[${status.index}].sort"
                                                   value="${specValue.sort}"/></td>
                            <td class="tl"><input type="text" class="text w250" name="specValues[${status.index}].name"
                                                  value="${specValue.name}"/></td>
                            <c:if test="${DEFAULT_SPEC_COLOR==P_GOODS_SPEC.spName}">
                                <td class="w300 tl"><input type="text" class="text w100"
                                                           name="specValues[${status.index}].spValueColor"
                                                           value="${specValue.spValueColor}" nctype="spec_color"/></td>
                            </c:if>
                            <td class="w110 nscs-table-handle"><span><a href="javascript:void(0);" class="btn-red"
                                                                        nctype="spec_del"
                                                                        data-param="{id:'${specValue.id}'}"><i
                                    class="icon-trash"></i>

                                <p>删除</p>
                            </a></span></td>
                        </tr>
                        <input type="hidden" name="specValues[${status.index}].id" value="${specValue.id}"/>
                    </c:forEach>
                    <c:if test="${fn:length(P_SPECVALUE_LIST)<1}">
                        <tr class="bd-line">
                            <td class="w80"><input type="text" class="text w40" name="specValues[0].sort" value="0"/>
                            </td>
                            <td class="tl"><input type="text" class="text w250" name="specValues[0].name"/></td>
                            <c:if test="${DEFAULT_SPEC_COLOR==P_GOODS_SPEC.spName}">
                                <td class="w300 tl"><input type="text" class="text w100"
                                                           name="specValues[0].spValueColor" nctype="spec_color"/></td>
                            </c:if>
                            <td class="w110 nscs-table-handle"><span><a href="javascript:void(0);" class="btn-orange"
                                                                        nctype="tr_remove"><i
                                    class="icon-minus-sign"></i>

                                <p>移除</p>
                            </a></span></td>
                        </tr>
                    </c:if>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td colspan="20" class="bottom"><a href="javascript:void(0);" nctype="submit-btn"
                                                           class="submit mt20">提交保存规格值</a></td>
                    </tr>
                    <tr>
                        <td colspan="20" class="h200"></td>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </form>
    </body>

    <script>
        var SITEURL = "${S_URL}";
    </script>
    <fis:require id="common:widget/jquery/jquery.js"/>
    <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
    <fis:require id="common:widget/colorpicker/evol.colorpicker.min.js"/>
    <fis:require id="common:widget/jquery-ui/jquery.mousewheel.js"/>
    <fis:require id="shop:scripts/perfect-scrollbar.min.js"/>
    <fis:scripts/>
    <fis:out id="shop:scripts/dialog/dialog.js" iid="dialog_js"/>
    <script>
        $(function () {
            // 提交表单
            $('a[nctype="submit-btn"]').click(function () {
                var _submit = true;
                $('#spec_form').find('input[name$="[name]"]').each(function () {
                    if ($(this).val() == '') {
                        _submit = false;
                        if (!$(this).hasClass('error')) {
                            $(this).addClass('error').parents('td:first').append('<label class="error"><i class="icon-exclamation-sign"></i>请填写内容</label>');
                        }
                    }
                });
                if (_submit) {
                    ajaxpost('spec_form', '${S_URL}/se/spec/save?for=xml', '', 'onerror');
                }
            });

            // 颜色选择器
            $('input[nctype="spec_color"]').colorpicker({showOn: 'both'}).removeAttr('nctype');

            // 移除
            $('a[nctype="tr_remove"]').click(function () {
                $(this).parents('tr:first').remove();
            });

            // 删除
            $('a[nctype="spec_del"]').click(function () {
                var $this = $(this);
                eval('data_str = ' + $this.attr('data-param'));
                $.getJSON('del?id=' + data_str.id, function (data) {
                    if (data) {
                        $this.parents('tr:first').remove();
                    }
                });
            });

            // 添加规格值
            var i = ${fn:length(P_SPECVALUE_LIST)};  // 自增，防止冲突
            $('a[nctype="add_sv"]').click(function () {
                var _tr = $('<tr class="bd-line"></tr>');
                $('<td class="w80"><input type="text" class="text w40" name="specValues[' + i + '].sort" value="0" /></td>').appendTo(_tr);   // 排序
                $('<td class="tl"><input type="text" class="text w250" name="specValues[' + i + '].name" /></td>').appendTo(_tr);      // 规格名称
                <c:if test="${DEFAULT_SPEC_COLOR==P_GOODS_SPEC.spName}">
                $('<td class="w300 tl"><input type="text" class="text w100" name="specValues[' + i + '].spValueColor" nctype="spec_color" /></td>').appendTo(_tr);  // 颜色色块
                </c:if>

                $('<td class="w110 nscs-table-handle"><span><a href="javascript:void(0);" class="btn-orange"><i class="icon-minus-sign"></i><p>移除</p></a</span></td>').find('a').click(function () {
                    $(this).parents('tr:first').remove();
                }).end().appendTo(_tr);   // 操作
                _tr.appendTo('tbody[nctype="spec_tbody"]');
                // 颜色选择器
                $('tbody[nctype="spec_tbody"]').find('input[nctype="spec_color"]').colorpicker({showOn: 'both'}).removeAttr('nctype');
                i++;
            });
        });
        //超出父级页面iframe设定高度时滚动条
        $(document).ready(function () {
            $('#iframe').perfectScrollbar();
        });
    </script>
</fis:html>