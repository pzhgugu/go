<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>
<div class="eject_con">
    <div id="warning" class="alert alert-error"></div>
    <form method="post" action="se/goodsonline/ad/edit?for=tpl" id="jingle_form">
        <input type="hidden" name="form_submit" value="ok" />
        <input type="hidden" name="commonid" value="${P_COMMONID}" />
        <dl>
            <dt>商品广告词：</dt>
            <dd>
                <input type="text" class="text w300" name="g_jingle" id="g_jingle" value="" />
                <p class="hint">如不填，所有广告词将制空，请谨慎操作</p>
            </dd>
        </dl>
        <div class="bottom">
            <label class="submit-border"><input type="submit" class="submit" value="提交"/></label>
        </div>
    </form>
</div>
<script>
    $(function(){
        $('#jingle_form').validate({
            errorLabelContainer: $('#warning'),
            invalidHandler: function(form, validator) {
                $('#warning').show();
            },
            submitHandler:function(form){
                ajaxpost('jingle_form', '', '', 'onerror');
            },
            rules : {
                g_jingle : {
                    maxlength: 50
                }
            },
            messages : {
                g_jingle : {
                    maxlength: '<i class="icon-exclamation-sign"></i>不能超过50个字符'
                }
            }
        });
    });
</script>
