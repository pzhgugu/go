<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>
<html>
<head>
    <title></title>
</head>
<body>

<div class="eject_con">
    <div id="warning"></div>
    <form action="/se/storegoodsclass/save?for=xml" target="_parent" method="post" id="category_form">
        <c:if test="${!empty P_CURRENT_ENTITY}"><input name="id" type="hidden" value="${P_CURRENT_ENTITY.id}"/></c:if>
        <dl>
            <dt><i class="required">*</i>分类名称：</dt>
            <dd>
                <input type="text" value="${P_CURRENT_ENTITY.name}" id="name" name="name" class="text w200">
            </dd>
        </dl>
        <c:if test="${empty P_CURRENT_ENTITY}">
            <dl>
                <dt>上级分类：</dt>
                <dd>
                    <select id="stc_parent_id" name="stc_parent_id">
                        <option>请选择</option>
                        <c:forEach items="${P_STORE_GOODSCLASS_LIST}" var="goodsClass">
                            <option value="${goodsClass.id}" <c:if
                                    test="${(!empty P_PARENT_ENTITY)&&(P_PARENT_ENTITY.id==goodsClass.id)}"> selected="selected"</c:if> >${goodsClass.name}</option>
                        </c:forEach>
                    </select>
                </dd>
            </dl>
        </c:if>
        <dl>
            <dt>排序：</dt>
            <dd>
                <input type="text" value="${P_CURRENT_ENTITY.sequence}" name="sequence" class="text w60">
            </dd>
        </dl>
        <dl>
            <dt>显示状态：</dt>
            <dd>
                <label>

                    <input type="radio"
                           <c:if test="${(!empty P_CURRENT_ENTITY&&P_CURRENT_ENTITY.stcState=='1')||empty P_CURRENT_ENTITY}">checked="checked"</c:if>
                           value="1" name="stcState">
                    是</label>
                <label>
                    <input type="radio"
                           <c:if test="${(!empty P_CURRENT_ENTITY&&P_CURRENT_ENTITY.stcState=='0')}">checked="checked"</c:if>
                           value="0" name="stcState">
                    否</label>
            </dd>
        </dl>
        <div class="bottom">
            <label class="submit-border"><input type="submit" value="提交" class="submit"></label>
        </div>
    </form>
</div>
<script type="text/javascript">

    $(function () {
        $('#category_form').validate({
            errorLabelContainer: $('#warning'),
            invalidHandler: function (form, validator) {
                $('#warning').show();
            },
            submitHandler: function (form) {
                ajaxpost('category_form', '', '', 'onerror')
            },
            rules: {
                name: {
                    required: true
                },
                sequence: {
                    number: true
                }
            },
            messages: {
                name: {
                    required: '分类名称不能为空'

                },
                sequence: {
                    number: '需要输入数字'
                }
            }
        });
    });

</script>
</body>
</html>
